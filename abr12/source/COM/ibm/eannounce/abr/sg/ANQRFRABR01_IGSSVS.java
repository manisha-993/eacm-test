/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.LockActionItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*      */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ANQRFRABR01_IGSSVS
/*      */   extends PokBaseABR
/*      */ {
/*      */   public static final String COPYRIGHT = "(C) Copyright IBM Corp. 2001, 2006  All Rights Reserved.";
/*      */   public static final String ABR = "ANQRFRABR01";
/*      */   public static final String CHANGEREQUEST_STATUS = "CRSTATUS";
/*      */   public static final String ANCYCLESTATUS = "ANCYCLESTATUS";
/*      */   public static final String RFAHWABR01_STATUS = "RFAHWABR01";
/*      */   public static final String ANNOUNCEMNET = "ANNOUNCEMNET";
/*      */   public static final String EFFECTIVEDATE = "EFFECTIVEDATE";
/*      */   public static final String AVAILTYPE = "AVAILTYPE";
/*      */   public static final String GENAREASELECTION = "GENAREASELECTION";
/*      */   public static final String MKTGNAME = "MKTGNAME";
/*      */   public static final String COMPONENTID = "COMPONENTID";
/*      */   public static final String DESCRIPTION = "DESCRIPTION";
/*      */   public static final String OVERVIEWABSTRACT = "OVERVIEWABSTRACT";
/*      */   public static final String DISTRCODE = "DISTRCODE";
/*      */   public static final String SVCCAT = "SVCCAT";
/*      */   public static final String VAE = "VAE";
/*  167 */   public static final Hashtable c_hshANNtoANN = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  172 */   public static final Hashtable c_hshFinalEntities = new Hashtable<>();
/*      */ 
/*      */   
/*      */   static {
/*  176 */     c_hshFinalEntities.put("SOF", "HI");
/*  177 */     c_hshFinalEntities.put("CMPNT", "HI");
/*  178 */     c_hshFinalEntities.put("FEATURE", "HI");
/*  179 */     c_hshFinalEntities.put("ANNOUNCEMENT", "HI");
/*  180 */     c_hshFinalEntities.put("AVAIL", "HI");
/*  181 */     c_hshFinalEntities.put("ANNDELIVERABLE", "HI");
/*  182 */     c_hshFinalEntities.put("CATINCL", "HI");
/*  183 */     c_hshFinalEntities.put("CHANNEL", "HI");
/*  184 */     c_hshFinalEntities.put("FINOF", "HI");
/*  185 */     c_hshFinalEntities.put("CONFIGURATOR", "HI");
/*  186 */     c_hshFinalEntities.put("ORGANUNIT", "HI");
/*  187 */     c_hshFinalEntities.put("PRICEFININFO", "HI");
/*  188 */     c_hshFinalEntities.put("STANDAMENDTEXT", "HI");
/*      */     
/*  190 */     c_hshANNtoANN.put("ANNOUNCEMENT", "ANNOUNCEMENT");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  195 */   private EntityGroup m_egParent = null;
/*  196 */   private EntityItem m_eiParent = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  202 */   private final String m_strApprovedValue = "114";
/*      */   
/*  204 */   private final String m_strReadyFinalReviewValue = "112";
/*      */   
/*  206 */   private final String m_strApprovedRiskValue = "115";
/*      */   
/*  208 */   private final String m_strQueueRFAValue = "0020";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  216 */     EntityGroup entityGroup = null;
/*  217 */     boolean bool = false;
/*      */     
/*      */     try {
/*  220 */       start_ABRBuild();
/*  221 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  222 */       this.m_eiParent = this.m_egParent.getEntityItem(0);
/*      */       
/*  224 */       if (this.m_egParent == null) {
/*  225 */         println("ANQRFRABR01:" + 
/*      */ 
/*      */             
/*  228 */             getVersion() + ":ERROR:1: m_egParent cannot be established.");
/*      */         
/*  230 */         setReturnCode(-1);
/*      */         
/*  232 */         bool = true;
/*      */       } 
/*  234 */       if (!bool && 
/*  235 */         this.m_eiParent == null) {
/*  236 */         println("ANQRFRABR01:" + 
/*      */ 
/*      */             
/*  239 */             getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/*      */         
/*  241 */         setReturnCode(-1);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  246 */       if (!bool)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  251 */         logMessage("ANQRFRABR01:" + 
/*      */ 
/*      */             
/*  254 */             getVersion() + ":Request to Work on Entity:" + this.m_eiParent
/*      */             
/*  256 */             .getEntityType() + ":" + this.m_eiParent
/*      */             
/*  258 */             .getEntityID());
/*  259 */         buildRptHeader();
/*  260 */         setControlBlock();
/*  261 */         setDGTitle(setDGName(this.m_eiParent, "ANQRFRABR01"));
/*      */         
/*  263 */         logMessage("ANQRFRABR01:" + 
/*      */ 
/*      */             
/*  266 */             getVersion() + ":Setup Complete:" + this.m_eiParent
/*      */             
/*  268 */             .getEntityType() + ":" + this.m_eiParent
/*      */             
/*  270 */             .getEntityID());
/*      */ 
/*      */         
/*  273 */         setReturnCode(0);
/*      */ 
/*      */         
/*  276 */         dispHeader(this.m_egParent, this.m_eiParent);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  281 */         checkFinalStatus();
/*  282 */         checkCRstatus();
/*  283 */         checkAVAILstatus();
/*  284 */         checkCMPNTstatus();
/*  285 */         checkSOFstatus();
/*  286 */         checkSTANDTEXTstatus();
/*  287 */         checkFEATUREstatus();
/*  288 */         checkPRICEINFOstatus();
/*  289 */         checkANNOUNCEMENTstatus();
/*      */ 
/*      */         
/*  292 */         entityGroup = this.m_eiParent.getEntityGroup();
/*  293 */         logMessage("***GENERALAREA eg.true***" + entityGroup.dump(true));
/*  294 */         logMessage("***GENERALAREA eg***" + entityGroup);
/*  295 */         logMessage("***GENERALAREA getDownLinkCount()***:" + this.m_eiParent
/*      */             
/*  297 */             .getDownLinkCount()); byte b;
/*  298 */         for (b = 0; b < this.m_eiParent.getDownLinkCount(); b++) {
/*  299 */           EntityItem entityItem1 = (EntityItem)this.m_eiParent.getDownLink(b);
/*      */           
/*  301 */           EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/*      */           
/*  303 */           if (entityItem2.getEntityType().equals("GENERALAREA")) {
/*      */             
/*  305 */             String str = getFlagCode(entityItem2, "RFAGEO");
/*  306 */             logMessage("****strStatus:" + entityItem2
/*      */                 
/*  308 */                 .getEntityType() + "-" + entityItem2
/*      */                 
/*  310 */                 .getEntityID() + ":" + str);
/*      */ 
/*      */             
/*  313 */             if (str.equals("200")) {
/*  314 */               checkCONFIGAVAILDATEstatus();
/*  315 */               checkOPstatus();
/*  316 */               checkCONFIGNAMEstatus();
/*  317 */               logMessage("***RFAGEO is: 200");
/*  318 */             } else if (str.equals("201")) {
/*  319 */               checkCONFIGAVAILDATEstatus();
/*  320 */               checkOPstatus();
/*  321 */               checkCONFIGNAMEstatus();
/*  322 */               logMessage("***RFAGEO is: 201");
/*  323 */             } else if (str.equals("202")) {
/*  324 */               checkCONFIGAVAILDATEstatus();
/*  325 */               checkOPstatus();
/*  326 */               checkCONFIGNAMEstatus();
/*  327 */               logMessage("***RFAGEO is: 202");
/*  328 */             } else if (str.equals("203")) {
/*  329 */               checkCONFIGAVAILDATEstatus();
/*  330 */               checkCHANNELstatus();
/*      */               
/*  332 */               checkOPstatus();
/*  333 */               checkCONFIGNAMEstatus();
/*  334 */               logMessage("***RFAGEO is: 203");
/*  335 */             } else if (str.equals("204")) {
/*  336 */               checkCONFIGAVAILDATEstatus();
/*  337 */               checkOPstatus();
/*  338 */               checkCONFIGNAMEstatus();
/*  339 */               logMessage("***RFAGEO is: 204");
/*  340 */             } else if (str.equals("205")) {
/*  341 */               checkCONFIGAVAILDATEstatus();
/*  342 */               checkOPstatus();
/*  343 */               checkCONFIGNAMEstatus();
/*  344 */               logMessage("***RFAGEO is: 205");
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  350 */         EntityGroup entityGroup1 = this.m_eiParent.getEntityGroup();
/*  351 */         logMessage("***ANNOP eg1.true***" + entityGroup1.dump(true));
/*  352 */         logMessage("***ANNOP eg1***" + entityGroup1);
/*  353 */         logMessage("***ANNOP getDownLinkCount()***:" + this.m_eiParent
/*      */             
/*  355 */             .getDownLinkCount());
/*  356 */         for (b = 0; b < this.m_eiParent.getDownLinkCount(); b++) {
/*  357 */           EntityItem entityItem1 = (EntityItem)this.m_eiParent.getDownLink(b);
/*  358 */           EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/*  359 */           logMessage("***ANNOP eiRelator***:" + entityItem1);
/*  360 */           logMessage("***ANNOP eiChild***:" + entityItem2);
/*  361 */           if (entityItem2.getEntityType().equals("ANNOP")) {
/*  362 */             String str = getFlagCode(entityItem2, "ANNROLETYPE");
/*  363 */             logMessage("***ANNOP eiChild***" + entityItem2);
/*  364 */             logMessage("****strStatus:" + entityItem2
/*      */                 
/*  366 */                 .getEntityType() + "-" + entityItem2
/*      */                 
/*  368 */                 .getEntityID() + ":" + str);
/*      */ 
/*      */             
/*  371 */             if (str.equals("3")) {
/*  372 */               checkOPstatus();
/*  373 */               logMessage("***ANNROLETYPE is: 3");
/*  374 */             } else if (str.equals("5")) {
/*  375 */               checkOPstatus();
/*  376 */               logMessage("***ANNROLETYPE is: 5");
/*  377 */             } else if (str.equals("6")) {
/*  378 */               checkOPstatus();
/*  379 */               logMessage("***ANNROLETYPE is: 6");
/*  380 */             } else if (str.equals("7")) {
/*  381 */               checkOPstatus();
/*  382 */               logMessage("***ANNROLETYPE is: 7");
/*  383 */             } else if (str.equals("9")) {
/*  384 */               checkOPstatus();
/*  385 */               logMessage("***ANNROLETYPE is: 9");
/*  386 */             } else if (str.equals("15")) {
/*  387 */               checkOPstatus();
/*  388 */               logMessage("***ANNROLETYPE is: 15");
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  394 */         EntityGroup entityGroup2 = this.m_eiParent.getEntityGroup();
/*  395 */         logMessage("***ORGANUNIT eg2.true***" + entityGroup2.dump(true));
/*  396 */         logMessage("***ORGANUNIT eg2***" + entityGroup2);
/*  397 */         logMessage("***ORGANUNIT getDownLinkCount()***:" + this.m_eiParent
/*      */             
/*  399 */             .getDownLinkCount());
/*  400 */         for (b = 0; b < this.m_eiParent.getDownLinkCount(); b++) {
/*  401 */           EntityItem entityItem1 = (EntityItem)this.m_eiParent.getDownLink(b);
/*  402 */           EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/*  403 */           logMessage("***ORGANUNIT eiRelator***:" + entityItem1);
/*  404 */           logMessage("***ORGANUNIT eiChild***:" + entityItem2);
/*  405 */           if (entityItem2.getEntityType().equals("ORGANUNIT")) {
/*  406 */             String str = getFlagCode(entityItem2, "ORGANUNITTYPE");
/*  407 */             logMessage("***ORGANUNIT eiChild***" + entityItem2);
/*  408 */             logMessage("****strStatus:" + entityItem2
/*      */                 
/*  410 */                 .getEntityType() + "-" + entityItem2
/*      */                 
/*  412 */                 .getEntityID() + ":" + str);
/*      */ 
/*      */             
/*  415 */             if (str.equals("4156")) {
/*  416 */               checkORGANUNITstatus();
/*  417 */               logMessage("***ORGANUNITTYPE is: 4156");
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  423 */         EntityGroup entityGroup3 = this.m_eiParent.getEntityGroup();
/*  424 */         logMessage("***ANNDELIVERABLE eg3.true***" + entityGroup3.dump(true));
/*  425 */         logMessage("***ANNDELIVERABLE eg3***" + entityGroup3);
/*  426 */         logMessage("***ANNDELIVERABLE getDownLinkCount()***:" + this.m_eiParent
/*      */             
/*  428 */             .getDownLinkCount());
/*  429 */         for (b = 0; b < this.m_eiParent.getDownLinkCount(); b++) {
/*  430 */           EntityItem entityItem1 = (EntityItem)this.m_eiParent.getDownLink(b);
/*  431 */           EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/*  432 */           logMessage("***ANNDELIVERABLE eiRelator***:" + entityItem1);
/*  433 */           logMessage("***ANNDELIVERABLE eiChild***:" + entityItem2);
/*  434 */           if (entityItem2.getEntityType().equals("ORGANUNIT")) {
/*  435 */             String str = getFlagCode(entityItem2, "ORGANUNITTYPE");
/*  436 */             logMessage("***ANNDELIVERABLE eiChild***" + entityItem2);
/*  437 */             logMessage("****strStatus:" + entityItem2
/*      */                 
/*  439 */                 .getEntityType() + "-" + entityItem2
/*      */                 
/*  441 */                 .getEntityID() + ":" + str);
/*      */ 
/*      */             
/*  444 */             if (str.equals("860")) {
/*  445 */               checkSUBJECTLINE_1status();
/*  446 */               logMessage("***ORGANUNITTYPE is: 860");
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  451 */         logMessage("***getReturnCode()***" + getReturnCode());
/*  452 */         if (getReturnCode() == 0) {
/*      */           
/*  454 */           String str1 = getFlagCode(this.m_eiParent, getStatusAttributeCode(this.m_eiParent));
/*      */           
/*  456 */           EntityItem[] arrayOfEntityItem = { this.m_eiParent };
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  461 */           if (str1.equals("111")) {
/*  462 */             setFlagValue("ANCYCLESTATUS");
/*      */           }
/*  464 */           else if (str1.equals("113")) {
/*  465 */             setFlagValue("ANCYCLESTATUS");
/*      */           }
/*  467 */           else if (str1.equals("118")) {
/*  468 */             setFlagValue1("ANCYCLESTATUS");
/*      */           }
/*  470 */           else if (str1.equals("119")) {
/*  471 */             setFlagValue2("ANCYCLESTATUS");
/*      */           }
/*  473 */           else if (str1.equals("120")) {
/*  474 */             setFlagValue3("RFAHWABR01");
/*      */           
/*      */           }
/*  477 */           else if (str1.equals("121")) {
/*  478 */             setFlagValue3("RFAHWABR01");
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  483 */           LockActionItem lockActionItem = new LockActionItem(null, this.m_db, this.m_prof, "LOCKEXTANN01LOCK1");
/*  484 */           lockActionItem.setEntityItems(arrayOfEntityItem);
/*  485 */           lockActionItem.executeAction(this.m_db, this.m_prof);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  490 */           EANAttribute eANAttribute = this.m_eiParent.getAttribute("ANNCODENAMEU");
/*      */           
/*  492 */           logMessage("***** att for ANNCODENAME is " + eANAttribute);
/*  493 */           String str2 = eANAttribute.toString();
/*      */ 
/*      */           
/*  496 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/*  497 */           String str3 = null;
/*      */           
/*  499 */           if (arrayOfMetaFlag != null) {
/*  500 */             for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*  501 */               if (arrayOfMetaFlag[b1]
/*  502 */                 .getLongDescription()
/*  503 */                 .equals(str2)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  509 */                 logMessage("***** j is " + b1 + " parentValue " + str2);
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  514 */                 logMessage("***** j is " + b1 + " getFlagCode " + arrayOfMetaFlag[b1]
/*      */ 
/*      */ 
/*      */                     
/*  518 */                     .getFlagCode());
/*  519 */                 logMessage("***** j is " + b1 + " getLongDescription " + arrayOfMetaFlag[b1]
/*      */ 
/*      */ 
/*      */                     
/*  523 */                     .getLongDescription());
/*      */ 
/*      */                 
/*  526 */                 str3 = arrayOfMetaFlag[b1].getFlagCode();
/*  527 */                 this.m_db.setOutOfCirculation(this.m_prof, this.m_eiParent
/*      */                     
/*  529 */                     .getEntityType(), "ANNCODENAME", str3, false);
/*      */               } 
/*      */             } 
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  538 */           this.m_prof.setValOn(getValOn());
/*  539 */           this.m_prof.setEffOn(getEffOn());
/*      */         } 
/*      */ 
/*      */         
/*  543 */         println("<br /><b>" + 
/*      */             
/*  545 */             buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*      */ 
/*      */                 
/*  548 */                 getABRDescription(), 
/*  549 */                 (getReturnCode() == 0) ? "Passed" : "Failed"
/*      */               }) + "</b>");
/*      */         
/*  552 */         log(
/*  553 */             buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*      */ 
/*      */                 
/*  556 */                 getABRDescription(), 
/*  557 */                 (getReturnCode() == 0) ? "Passed" : "Failed"
/*      */               }));
/*      */       }
/*      */     
/*  561 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  562 */       setReturnCode(-2);
/*  563 */       println("<h3 style=\"color:#c00; font-weight:bold;\">IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*      */ 
/*      */ 
/*      */           
/*  567 */           .getMessage() + "</h3>");
/*      */       
/*  569 */       logError(lockPDHEntityException.getMessage());
/*  570 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  571 */       setReturnCode(-2);
/*  572 */       println("<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: " + updatePDHEntityException
/*      */           
/*  574 */           .getMessage() + "</h3>");
/*      */       
/*  576 */       logError(updatePDHEntityException.getMessage());
/*  577 */     } catch (Exception exception) {
/*  578 */       StringWriter stringWriter = new StringWriter();
/*      */ 
/*      */       
/*  581 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/*  582 */       println("" + exception);
/*      */ 
/*      */       
/*  585 */       if (getABRReturnCode() != -2) {
/*  586 */         setReturnCode(-3);
/*      */       }
/*      */       
/*  589 */       exception.printStackTrace();
/*      */       
/*  591 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*  592 */       String str = stringWriter.toString();
/*  593 */       println(str);
/*      */       
/*  595 */       logMessage("Error in " + this.m_abri
/*  596 */           .getABRCode() + ":" + exception.getMessage());
/*  597 */       logMessage("" + exception);
/*      */     }
/*      */     finally {
/*      */       
/*  601 */       if (getReturnCode() == 0) {
/*  602 */         setReturnCode(0);
/*      */       }
/*      */       
/*  605 */       setDGString(getABRReturnCode());
/*  606 */       setDGRptName("ANQRFRABR01_IGSSVS");
/*  607 */       setDGRptClass("ANQRFRABR01_IGSSVS");
/*      */       
/*  609 */       if (!isReadOnly()) {
/*  610 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */     
/*  614 */     if (!bool) {
/*      */       
/*  616 */       printDGSubmitString();
/*  617 */       println(EACustom.getTOUDiv());
/*  618 */       buildReportFooter();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printFAILmessage(EntityGroup paramEntityGroup) {
/*  629 */     println("<br /><br /><b>" + paramEntityGroup
/*      */         
/*  631 */         .getLongDescription() + " is not at final status.</b>");
/*      */     
/*  633 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printWARNINGmessage(EntityGroup paramEntityGroup) {
/*  643 */     println("<br /><br /><b>" + paramEntityGroup
/*      */         
/*  645 */         .getLongDescription() + " does not exist (Warning - Pass)</b>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printFAILmessage_2(EntityGroup paramEntityGroup) {
/*  657 */     println("<br /><br /><b>" + paramEntityGroup
/*      */         
/*  659 */         .getLongDescription() + " needs to be 'Final'status</b>");
/*      */     
/*  661 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printWARNINGmessage_2(EntityGroup paramEntityGroup) {
/*  671 */     println("<br /><br /><b>" + paramEntityGroup
/*      */         
/*  673 */         .getLongDescription() + " needs to be 'Final'status (Warning - Pass)</b>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printFAILmessage_3(EntityGroup paramEntityGroup) {
/*  685 */     println("<br /><br /><b>" + paramEntityGroup
/*      */         
/*  687 */         .getLongDescription() + " needs to be at least 'Ready for Final Review'</b>");
/*      */     
/*  689 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printWARNINGmessage_3(EntityGroup paramEntityGroup) {
/*  699 */     println("<br /><br /><b>" + paramEntityGroup
/*      */         
/*  701 */         .getLongDescription() + " needs to be at least 'Ready for Final Review' (Warning - Pass)</b>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkFinalStatus() {
/*  712 */     String str = null;
/*      */     
/*  714 */     for (byte b = 0; b < this.m_elist.getEntityGroupCount(); b++) {
/*      */       
/*  716 */       EntityGroup entityGroup = this.m_elist.getEntityGroup(b);
/*      */       
/*  718 */       if (c_hshFinalEntities.containsKey(entityGroup.getEntityType())) {
/*  719 */         logMessage("***** _eg.getEntityType() is " + entityGroup
/*  720 */             .getEntityType());
/*      */ 
/*      */ 
/*      */         
/*  724 */         if (entityGroup.getEntityItemCount() > 0) {
/*      */           
/*  726 */           logMessage("***_eg.getEntityItemCount()***" + entityGroup
/*      */               
/*  728 */               .getEntityItemCount());
/*      */           
/*  730 */           for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/*  731 */             EntityItem entityItem = entityGroup.getEntityItem(b1);
/*      */             
/*  733 */             if (getStatusAttributeCode(entityItem) != null)
/*      */             {
/*      */               
/*  736 */               EANAttribute eANAttribute = entityItem.getAttribute(getStatusAttributeCode(entityItem));
/*  737 */               logMessage("getStatusAttributeCode***: " + entityItem
/*      */                   
/*  739 */                   .getEntityType() + ":" + entityItem
/*      */                   
/*  741 */                   .getEntityID() + ":" + eANAttribute);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  746 */               String str1 = (eANAttribute != null) ? eANAttribute.toString() : "<em>** Not Populated **</em>";
/*      */ 
/*      */               
/*  749 */               str = getFlagCode(entityItem, getStatusAttributeCode(entityItem));
/*  750 */               logMessage("***strStatus getFlagCode " + entityItem
/*      */                   
/*  752 */                   .getEntityType() + ":" + entityItem
/*      */                   
/*  754 */                   .getEntityID() + ":" + str);
/*      */ 
/*      */               
/*  757 */               logMessage("***Entity Attribute values: " + entityItem
/*      */                   
/*  759 */                   .getEntityType() + ":" + entityItem
/*      */                   
/*  761 */                   .getEntityID() + ":" + str1);
/*      */ 
/*      */               
/*  764 */               if (str.equals("111") || str
/*  765 */                 .equals("114") || str
/*  766 */                 .equals("115") || str
/*  767 */                 .equals("116")) {
/*  768 */                 logMessage("strStatusValue not to final***: " + entityItem
/*      */                     
/*  770 */                     .getEntityType() + ":" + entityItem
/*      */                     
/*  772 */                     .getEntityID() + ":" + str1);
/*      */ 
/*      */                 
/*  775 */                 println("<br /><br /><b>" + entityGroup
/*      */                     
/*  777 */                     .getLongDescription() + ":" + entityItem
/*      */                     
/*  779 */                     .getEntityID() + "  is not at final status.</b>");
/*      */                 
/*  781 */                 println(dispNavAttributes(entityItem, entityGroup));
/*  782 */                 println(displayStatus(entityItem, entityGroup));
/*  783 */                 setReturnCode(-1);
/*      */               }
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  792 */           logMessage("***ENTITY DOES NOT EXIST***" + entityGroup.dump(true));
/*  793 */           printWARNINGmessage(entityGroup);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkCRstatus() {
/*  805 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CHANGEREQUEST");
/*  806 */     if (entityGroup != null) {
/*  807 */       Vector<EntityItem> vector = new Vector();
/*      */       
/*  809 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  810 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*  811 */         if (entityItem != null) {
/*  812 */           EANAttribute eANAttribute = entityItem.getAttribute("CRSTATUS");
/*      */           
/*  814 */           String str = (eANAttribute != null) ? eANAttribute.toString() : "<em>** Not Populated **</em>";
/*  815 */           if (str.equals("Approved")) {
/*  816 */             vector.addElement(entityItem);
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  822 */       if (vector != null && vector.size() > 0) {
/*  823 */         EntityItem entityItem = vector.elementAt(0);
/*  824 */         if (entityItem != null) {
/*  825 */           EntityGroup entityGroup1 = entityItem.getEntityGroup();
/*  826 */           if (entityGroup1 != null) {
/*  827 */             println("<br /><br /><b>The following " + entityGroup1
/*      */                 
/*  829 */                 .getLongDescription() + " are still in the Approved state</b>");
/*      */             
/*  831 */             println("<br /><br /><b>" + entityGroup1
/*  832 */                 .getLongDescription() + "</b>");
/*  833 */             println(dispNavAttributes(entityItem, entityGroup1));
/*      */           } 
/*      */         } 
/*  836 */         setReturnCode(-1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkAVAILstatus() {
/*  847 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*  848 */     String str1 = null;
/*  849 */     String str2 = null;
/*      */     
/*  851 */     if (entityGroup != null)
/*      */     {
/*  853 */       if (entityGroup.getEntityItemCount() == 0) {
/*  854 */         println("<br /><br /><b>Entity: " + entityGroup
/*  855 */             .getLongDescription() + "</b>");
/*  856 */         println("<br /><br /><b>There is no value for: EFFECTIVEDATE</b>");
/*  857 */         println("<br /><br /><b>There is no value for: AVAILTYPE</b>");
/*  858 */         println("<br /><br /><b>There is no value for: GENAREASELECTION</b>");
/*  859 */         setReturnCode(-1);
/*      */       } else {
/*      */         
/*  862 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  863 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*  864 */           if (entityItem != null) {
/*  865 */             EANAttribute[] arrayOfEANAttribute = new EANAttribute[3];
/*  866 */             arrayOfEANAttribute[0] = entityItem.getAttribute("EFFECTIVEDATE");
/*  867 */             arrayOfEANAttribute[1] = entityItem.getAttribute("AVAILTYPE");
/*  868 */             arrayOfEANAttribute[2] = entityItem.getAttribute("GENAREASELECTION");
/*      */ 
/*      */ 
/*      */             
/*  872 */             str1 = arrayOfEANAttribute[0].toString();
/*  873 */             str2 = arrayOfEANAttribute[1].toString();
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  878 */             if (str1.equals("")) {
/*  879 */               println("<br /><br /><b>There is no value for: EFFECTIVEDATE</b>");
/*  880 */               println("<br /><br /><b>" + entityItem
/*      */                   
/*  882 */                   .getEntityType() + ":" + entityItem
/*      */                   
/*  884 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/*  887 */               println(dispNavAttributes(entityItem, entityGroup));
/*  888 */               setReturnCode(-1);
/*  889 */             } else if (str2.equals("")) {
/*  890 */               println("<br /><br /><b>There is no value for: AVAILTYPE</b>");
/*  891 */               println("<br /><br /><b>" + entityItem
/*      */                   
/*  893 */                   .getEntityType() + ":" + entityItem
/*      */                   
/*  895 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/*  898 */               println(dispNavAttributes(entityItem, entityGroup));
/*      */               
/*  900 */               setReturnCode(-1);
/*  901 */             } else if (str2.equals("")) {
/*  902 */               println("<br /><br /><b>There is no value for: GENAREASELECTION</b>");
/*  903 */               println("<br /><br /><b>" + entityItem
/*      */                   
/*  905 */                   .getEntityType() + ":" + entityItem
/*      */                   
/*  907 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/*  910 */               println(dispNavAttributes(entityItem, entityGroup));
/*  911 */               setReturnCode(-1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkCMPNTstatus() {
/*  925 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CMPNT");
/*      */     
/*  927 */     if (entityGroup != null)
/*      */     {
/*  929 */       if (entityGroup.getEntityItemCount() == 0) {
/*  930 */         println("<br /><br /><b>Entity: " + entityGroup
/*  931 */             .getLongDescription() + "</b>");
/*  932 */         println("<br /><br /><b>There is no value for: MKTGNAME</b>");
/*  933 */         println("<br /><br /><b>There is no value for: COMPONENTID</b>");
/*  934 */         println("<br /><br /><b>There is no value for: DESCRIPTION</b>");
/*  935 */         println("<br /><br /><b>There is no value for: OVERVIEWABSTRACT</b>");
/*  936 */         println("<br /><br /><b>There is no value for: DISTRCODE</b>");
/*  937 */         println("<br /><br /><b>There is no value for: SVCCAT</b>");
/*  938 */         println("<br /><br /><b>There is no value for: VAE</b>");
/*  939 */         setReturnCode(-1);
/*      */       } else {
/*  941 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */           
/*  943 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */           
/*  945 */           logMessage("_eg.getEntityItem(i) for CMPNT:" + entityGroup
/*      */               
/*  947 */               .getEntityItem(b));
/*  948 */           logMessage("ei for CMPNT:" + entityItem.dump(false));
/*      */           
/*  950 */           if (entityItem != null) {
/*  951 */             EANAttribute[] arrayOfEANAttribute = new EANAttribute[7];
/*  952 */             arrayOfEANAttribute[0] = entityItem.getAttribute("MKTGNAME");
/*  953 */             arrayOfEANAttribute[1] = entityItem.getAttribute("COMPONENTID");
/*  954 */             arrayOfEANAttribute[2] = entityItem.getAttribute("DESCRIPTION");
/*  955 */             arrayOfEANAttribute[3] = entityItem.getAttribute("OVERVIEWABSTRACT");
/*  956 */             arrayOfEANAttribute[4] = entityItem.getAttribute("DISTRCODE");
/*  957 */             arrayOfEANAttribute[5] = entityItem.getAttribute("SVCCAT");
/*  958 */             arrayOfEANAttribute[6] = entityItem.getAttribute("VAE");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  966 */             if (arrayOfEANAttribute[0] == null) {
/*  967 */               println("<br /><br /><b>There is no value for: MKTGNAME</b>");
/*  968 */               println("<br /><br /><b>" + entityItem
/*      */                   
/*  970 */                   .getEntityType() + ":" + entityItem
/*      */                   
/*  972 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/*  975 */               println(dispNavAttributes(entityItem, entityGroup));
/*  976 */               setReturnCode(-1);
/*      */             } 
/*  978 */             if (arrayOfEANAttribute[1] == null) {
/*  979 */               println("<br /><br /><b>There is no value for: COMPONENTID</b>");
/*  980 */               println("<br /><br /><b>" + entityItem
/*      */                   
/*  982 */                   .getEntityType() + ":" + entityItem
/*      */                   
/*  984 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/*  987 */               println(dispNavAttributes(entityItem, entityGroup));
/*  988 */               setReturnCode(-1);
/*      */             } 
/*  990 */             if (arrayOfEANAttribute[2] == null) {
/*  991 */               println("<br /><br /><b>There is no value for: DESCRIPTION</b>");
/*  992 */               println("<br /><br /><b>" + entityItem
/*      */                   
/*  994 */                   .getEntityType() + ":" + entityItem
/*      */                   
/*  996 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/*  999 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1000 */               setReturnCode(-1);
/*      */             } 
/* 1002 */             if (arrayOfEANAttribute[3] == null) {
/* 1003 */               println("<br /><br /><b>There is no value for: OVERVIEWABSTRACT</b>");
/* 1004 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1006 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1008 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1011 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1012 */               setReturnCode(-1);
/*      */             } 
/* 1014 */             if (arrayOfEANAttribute[4] == null) {
/* 1015 */               println("<br /><br /><b>There is no value for: DISTRCODE</b>");
/* 1016 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1018 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1020 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1023 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1024 */               setReturnCode(-1);
/*      */             } 
/* 1026 */             if (arrayOfEANAttribute[5] == null) {
/* 1027 */               println("<br /><br /><b>There is no value for: SVCCAT</b>");
/* 1028 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1030 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1032 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1035 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1036 */               setReturnCode(-1);
/*      */             } 
/* 1038 */             if (arrayOfEANAttribute[6] == null) {
/* 1039 */               println("<br /><br /><b>There is no value for: VAE</b>");
/* 1040 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1042 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1044 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1047 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1048 */               setReturnCode(-1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkSOFstatus() {
/* 1062 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SOF");
/*      */     
/* 1064 */     if (entityGroup != null)
/*      */     {
/* 1066 */       if (entityGroup.getEntityItemCount() == 0) {
/* 1067 */         println("<br /><br /><b>Entity: " + entityGroup
/* 1068 */             .getLongDescription() + "</b>");
/* 1069 */         println("<br /><br /><b>There is no value for: MKTGNAME</b>");
/* 1070 */         println("<br /><br /><b>There is no value for: OFIDNUMBER</b>");
/* 1071 */         println("<br /><br /><b>There is no value for: ASSORTMODULE</b>");
/* 1072 */         println("<br /><br /><b>There is no value for: MATACCGRP</b>");
/* 1073 */         println("<br /><br /><b>There is no value for: OVERVIEWABSTRACT</b>");
/* 1074 */         println("<br /><br /><b>There is no value for: DESCRIPTION</b>");
/* 1075 */         println("<br /><br /><b>There is no value for: GBNAME</b>");
/* 1076 */         setReturnCode(-1);
/* 1077 */         logMessage("SOF getReturnCode" + getReturnCode());
/*      */       } else {
/* 1079 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */           
/* 1081 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */           
/* 1083 */           logMessage("_eg.getEntityItem(i) for SOF:" + entityGroup
/* 1084 */               .getEntityItem(b));
/* 1085 */           logMessage("ei for SOF:" + entityItem.dump(false));
/*      */           
/* 1087 */           if (entityItem != null) {
/* 1088 */             EANAttribute[] arrayOfEANAttribute = new EANAttribute[7];
/* 1089 */             arrayOfEANAttribute[0] = entityItem.getAttribute("MKTGNAME");
/* 1090 */             arrayOfEANAttribute[1] = entityItem.getAttribute("OFIDNUMBER");
/* 1091 */             arrayOfEANAttribute[2] = entityItem.getAttribute("ASSORTMODULE");
/* 1092 */             arrayOfEANAttribute[3] = entityItem.getAttribute("MATACCGRP");
/* 1093 */             arrayOfEANAttribute[4] = entityItem.getAttribute("OVERVIEWABSTRACT");
/* 1094 */             arrayOfEANAttribute[5] = entityItem.getAttribute("DESCRIPTION");
/* 1095 */             arrayOfEANAttribute[6] = entityItem.getAttribute("GBNAME");
/* 1096 */             logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 1097 */             logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/* 1098 */             logMessage("atts[2]:" + arrayOfEANAttribute[2]);
/* 1099 */             logMessage("atts[3]:" + arrayOfEANAttribute[3]);
/* 1100 */             logMessage("atts[4]:" + arrayOfEANAttribute[4]);
/* 1101 */             logMessage("atts[5]:" + arrayOfEANAttribute[5]);
/* 1102 */             logMessage("atts[6]:" + arrayOfEANAttribute[6]);
/* 1103 */             if (arrayOfEANAttribute[0] == null) {
/* 1104 */               println("<br /><br /><b>There is no value for: MKTGNAME</b>");
/* 1105 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1107 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1109 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1112 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1113 */               setReturnCode(-1);
/*      */             } 
/* 1115 */             if (arrayOfEANAttribute[1] == null) {
/* 1116 */               println("<br /><br /><b>There is no value for: OFIDNUMBER</b>");
/* 1117 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1119 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1121 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1124 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1125 */               setReturnCode(-1);
/*      */             } 
/* 1127 */             if (arrayOfEANAttribute[2] == null) {
/* 1128 */               println("<br /><br /><b>There is no value for: ASSORTMODULE</b>");
/* 1129 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1131 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1133 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1136 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1137 */               setReturnCode(-1);
/*      */             } 
/* 1139 */             if (arrayOfEANAttribute[3] == null) {
/* 1140 */               println("<br /><br /><b>There is no value for: MATACCGRP</b>");
/* 1141 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1143 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1145 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1148 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1149 */               setReturnCode(-1);
/*      */             } 
/* 1151 */             if (arrayOfEANAttribute[4] == null) {
/* 1152 */               println("<br /><br /><b>There is no value for: OVERVIEWABSTRACT</b>");
/* 1153 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1155 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1157 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1160 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1161 */               setReturnCode(-1);
/*      */             } 
/* 1163 */             if (arrayOfEANAttribute[5] == null) {
/* 1164 */               println("<br /><br /><b>There is no value for: DESCRIPTION</b>");
/* 1165 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1167 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1169 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1172 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1173 */               setReturnCode(-1);
/*      */             } 
/* 1175 */             if (arrayOfEANAttribute[6] == null) {
/* 1176 */               println("<br /><br /><b>There is no value for: GBNAME</b>");
/* 1177 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1179 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1181 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1184 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1185 */               setReturnCode(-1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkSTANDTEXTstatus() {
/* 1199 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("STANDAMENDTEXT");
/* 1200 */     if (entityGroup != null)
/*      */     {
/* 1202 */       if (entityGroup.getEntityItemCount() == 0) {
/* 1203 */         println("<br /><br /><b>Entity: " + entityGroup
/* 1204 */             .getLongDescription() + "</b>");
/* 1205 */         println("<br /><br /><b>There is no value for: STANDARDAMENDTEXT_TYPE</b>");
/* 1206 */         setReturnCode(-1);
/*      */       } else {
/*      */         
/* 1209 */         logMessage("****_eg.getEntityItemCount() for STANDAMENDTEXT:" + entityGroup
/*      */             
/* 1211 */             .getEntityItemCount());
/* 1212 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1213 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/* 1214 */           if (entityItem != null) {
/*      */             
/* 1216 */             EANAttribute eANAttribute = entityItem.getAttribute("STANDARDAMENDTEXT_TYPE");
/*      */             
/* 1218 */             String str = getFlagCode(entityItem, "STANDARDAMENDTEXT_TYPE");
/* 1219 */             logMessage("****att" + eANAttribute);
/* 1220 */             logMessage("****strStatus:" + entityItem
/*      */                 
/* 1222 */                 .getEntityType() + "-" + entityItem
/*      */                 
/* 1224 */                 .getEntityID() + ":" + str);
/*      */ 
/*      */             
/* 1227 */             if (str.equals("NOT FOUND") || str
/* 1228 */               .equals("100") || str
/* 1229 */               .equals("110") || str
/* 1230 */               .equals("130")) {
/* 1231 */               println("<br /><br /><b>The value for the following attribute is incorrect: STANDARDAMENDTEXT_TYPE</b>");
/* 1232 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1234 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1236 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1239 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1240 */               setReturnCode(-1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkFEATUREstatus() {
/* 1254 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("FEATURE");
/*      */     
/* 1256 */     if (entityGroup != null)
/*      */     {
/* 1258 */       if (entityGroup.getEntityItemCount() == 0) {
/* 1259 */         println("<br /><br /><b>Entity: " + entityGroup
/* 1260 */             .getLongDescription() + "</b>");
/* 1261 */         println("<br /><br /><b>There is no value for: MKTGNAME</b>");
/* 1262 */         println("<br /><br /><b>There is no value for: OVERVIEWABSTRACT</b>");
/* 1263 */         println("<br /><br /><b>There is no value for: DESCRIPTION</b>");
/* 1264 */         setReturnCode(-1);
/*      */       } else {
/* 1266 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */           
/* 1268 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */           
/* 1270 */           logMessage("_eg.getEntityItem(i) for FEATURE:" + entityGroup
/*      */               
/* 1272 */               .getEntityItem(b));
/* 1273 */           logMessage("ei for FEATURE:" + entityItem.dump(true));
/*      */           
/* 1275 */           if (entityItem != null) {
/* 1276 */             EANAttribute[] arrayOfEANAttribute = new EANAttribute[3];
/* 1277 */             arrayOfEANAttribute[0] = entityItem.getAttribute("MKTGNAME");
/* 1278 */             arrayOfEANAttribute[1] = entityItem.getAttribute("OVERVIEWABSTRACT");
/* 1279 */             arrayOfEANAttribute[2] = entityItem.getAttribute("DESCRIPTION");
/* 1280 */             logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 1281 */             logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/* 1282 */             logMessage("atts[2]:" + arrayOfEANAttribute[2]);
/* 1283 */             if (arrayOfEANAttribute[0] == null) {
/* 1284 */               println("<br /><br /><b>There is no value for: MKTGNAME</b>");
/* 1285 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1287 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1289 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1292 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1293 */               setReturnCode(-1);
/*      */             } 
/* 1295 */             if (arrayOfEANAttribute[1] == null) {
/* 1296 */               println("<br /><br /><b>There is no value for: OVERVIEWABSTRACT</b>");
/* 1297 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1299 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1301 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1304 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1305 */               setReturnCode(-1);
/*      */             } 
/* 1307 */             if (arrayOfEANAttribute[2] == null) {
/* 1308 */               println("<br /><br /><b>There is no value for: DESCRIPTION</b>");
/* 1309 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1311 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1313 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1316 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1317 */               setReturnCode(-1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkPRICEINFOstatus() {
/* 1331 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("PRICEFININFO");
/*      */     
/* 1333 */     if (entityGroup != null) {
/*      */       
/* 1335 */       logMessage("_eg.getEntityItemCount() for PRICEFININFO:" + entityGroup
/*      */           
/* 1337 */           .getEntityItemCount());
/* 1338 */       if (entityGroup.getEntityItemCount() == 0) {
/* 1339 */         println("<br /><br /><b>Entity: " + entityGroup
/* 1340 */             .getLongDescription() + "</b>");
/* 1341 */         println("<br /><br /><b>There is no value for: LPFEE</b>");
/* 1342 */         println("<br /><br /><b>There is no value for: CONTRACTCLOSEFEE</b>");
/* 1343 */         println("<br /><br /><b>There is no value for: REMKTGDISCOUNT</b>");
/* 1344 */         println("<br /><br /><b>There is no value for: CHARGES</b>");
/* 1345 */         setReturnCode(-1);
/*      */       } else {
/* 1347 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */           
/* 1349 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */           
/* 1351 */           logMessage("_eg.getEntityItem(i) for PRICEFININFO:" + entityGroup
/*      */               
/* 1353 */               .getEntityItem(b));
/* 1354 */           logMessage("ei for PRICEFININFO:" + entityItem.dump(true));
/*      */           
/* 1356 */           if (entityItem != null) {
/* 1357 */             EANAttribute[] arrayOfEANAttribute = new EANAttribute[4];
/* 1358 */             arrayOfEANAttribute[0] = entityItem.getAttribute("LPFEE");
/* 1359 */             arrayOfEANAttribute[1] = entityItem.getAttribute("CONTRACTCLOSEFEE");
/* 1360 */             arrayOfEANAttribute[2] = entityItem.getAttribute("REMKTGDISCOUNT");
/* 1361 */             arrayOfEANAttribute[3] = entityItem.getAttribute("CHARGES");
/* 1362 */             logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 1363 */             logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/* 1364 */             logMessage("atts[2]:" + arrayOfEANAttribute[2]);
/* 1365 */             logMessage("atts[3]:" + arrayOfEANAttribute[3]);
/* 1366 */             if (arrayOfEANAttribute[0] == null) {
/* 1367 */               println("<br /><br /><b>There is no value for: LPFEE</b>");
/* 1368 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1370 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1372 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1375 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1376 */               setReturnCode(-1);
/*      */             } 
/* 1378 */             if (arrayOfEANAttribute[1] == null) {
/* 1379 */               println("<br /><br /><b>There is no value for: CONTRACTCLOSEFEE</b>");
/* 1380 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1382 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1384 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1387 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1388 */               setReturnCode(-1);
/*      */             } 
/* 1390 */             if (arrayOfEANAttribute[2] == null) {
/* 1391 */               println("<br /><br /><b>There is no value for: REMKTGDISCOUNT</b>");
/* 1392 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1394 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1396 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1399 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1400 */               setReturnCode(-1);
/*      */             } 
/* 1402 */             if (arrayOfEANAttribute[3] == null) {
/* 1403 */               println("<br /><br /><b>There is no value for: CHARGES</b>");
/* 1404 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1406 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1408 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1411 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1412 */               setReturnCode(-1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkANNOUNCEMENTstatus() {
/* 1426 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("ANNOUNCEMENT");
/* 1427 */     if (entityGroup != null) {
/*      */       
/* 1429 */       logMessage("_eg.getEntityItemCount() for ANNOUNCEMENT:" + entityGroup
/*      */           
/* 1431 */           .getEntityItemCount());
/* 1432 */       if (entityGroup.getEntityItemCount() == 0) {
/* 1433 */         println("<br /><br /><b>Entity: " + entityGroup
/* 1434 */             .getLongDescription() + "</b>");
/* 1435 */         println("<br /><br /><b>There is no value for: ANNIMAGES</b>");
/* 1436 */         println("<br /><br /><b>There is no value for: ANNTITLE</b>");
/* 1437 */         println("<br /><br /><b>There is no value for: USSEC508</b>");
/* 1438 */         println("<br /><br /><b>There is no value for: USSEC508LOGO</b>");
/* 1439 */         println("<br /><br /><b>There is no value for: OVERVIEWABSTRACT</b>");
/* 1440 */         println("<br /><br /><b>There is no value for: ATAGLANCE</b>");
/* 1441 */         println("<br /><br /><b>There is no value for: DESCRIPTION</b>");
/* 1442 */         println("<br /><br /><b>There is no value for: AMCALLCENTER</b>");
/* 1443 */         setReturnCode(-1);
/*      */       } else {
/* 1445 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */           
/* 1447 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */           
/* 1449 */           logMessage("_eg.getEntityItem(i) for ANNOUNCEMENT:" + entityGroup
/*      */               
/* 1451 */               .getEntityItem(b));
/* 1452 */           logMessage("ei for ANNOUNCEMENT:" + entityItem.dump(true));
/*      */           
/* 1454 */           if (entityItem != null) {
/* 1455 */             EANAttribute[] arrayOfEANAttribute = new EANAttribute[9];
/* 1456 */             arrayOfEANAttribute[0] = entityItem.getAttribute("ANNIMAGES");
/* 1457 */             arrayOfEANAttribute[1] = entityItem.getAttribute("ANNTITLE");
/* 1458 */             arrayOfEANAttribute[2] = entityItem.getAttribute("USSEC508");
/* 1459 */             arrayOfEANAttribute[3] = entityItem.getAttribute("USSEC508LOGO");
/* 1460 */             arrayOfEANAttribute[4] = entityItem.getAttribute("OVERVIEWABSTRACT");
/* 1461 */             arrayOfEANAttribute[5] = entityItem.getAttribute("ATAGLANCE");
/* 1462 */             arrayOfEANAttribute[6] = entityItem.getAttribute("DESCRIPTION");
/* 1463 */             arrayOfEANAttribute[7] = entityItem.getAttribute("AMCALLCENTER");
/* 1464 */             arrayOfEANAttribute[8] = entityItem.getAttribute("PARAMETERCODENAME");
/*      */             
/* 1466 */             logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 1467 */             logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/* 1468 */             logMessage("atts[2]:" + arrayOfEANAttribute[2]);
/* 1469 */             logMessage("atts[3]:" + arrayOfEANAttribute[3]);
/* 1470 */             logMessage("atts[4]:" + arrayOfEANAttribute[4]);
/* 1471 */             logMessage("atts[5]:" + arrayOfEANAttribute[5]);
/* 1472 */             logMessage("atts[6]:" + arrayOfEANAttribute[6]);
/* 1473 */             logMessage("atts[7]:" + arrayOfEANAttribute[7]);
/* 1474 */             logMessage("atts[8]:" + arrayOfEANAttribute[8]);
/* 1475 */             if (arrayOfEANAttribute[0] == null) {
/* 1476 */               println("<br /><br /><b>There is no value for: ANNIMAGES</b>");
/* 1477 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1479 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1481 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1484 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1485 */               setReturnCode(-1);
/*      */             } 
/* 1487 */             if (arrayOfEANAttribute[1] == null) {
/* 1488 */               println("<br /><br /><b>There is no value for: ANNTITLE</b>");
/* 1489 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1491 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1493 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1496 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1497 */               setReturnCode(-1);
/*      */             } 
/* 1499 */             if (arrayOfEANAttribute[2] == null) {
/* 1500 */               println("<br /><br /><b>There is no value for: USSEC508</b>");
/* 1501 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1503 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1505 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1508 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1509 */               setReturnCode(-1);
/*      */             } 
/* 1511 */             if (arrayOfEANAttribute[3] == null) {
/* 1512 */               println("<br /><br /><b>There is no value for: USSEC508LOGO</b>");
/* 1513 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1515 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1517 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1520 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1521 */               setReturnCode(-1);
/*      */             } 
/* 1523 */             if (arrayOfEANAttribute[4] == null) {
/* 1524 */               println("<br /><br /><b>There is no value for: OVERVIEWABSTRACT</b>");
/* 1525 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1527 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1529 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1532 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1533 */               setReturnCode(-1);
/*      */             } 
/* 1535 */             if (arrayOfEANAttribute[5] == null) {
/* 1536 */               println("<br /><br /><b>There is no value for: ATAGLANCE</b>");
/* 1537 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1539 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1541 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1544 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1545 */               setReturnCode(-1);
/*      */             } 
/* 1547 */             if (arrayOfEANAttribute[6] == null) {
/* 1548 */               println("<br /><br /><b>There is no value for: DESCRIPTION</b>");
/* 1549 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1551 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1553 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1556 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1557 */               setReturnCode(-1);
/*      */             } 
/* 1559 */             if (arrayOfEANAttribute[7] == null) {
/* 1560 */               println("<br /><br /><b>There is no value for: AMCALLCENTER</b>");
/* 1561 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1563 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1565 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1568 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1569 */               setReturnCode(-1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkCONFIGAVAILDATEstatus() {
/* 1583 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("ANNTOCONFIG");
/* 1584 */     if (entityGroup != null)
/*      */     {
/* 1586 */       if (entityGroup.getEntityItemCount() == 0) {
/* 1587 */         println("<br /><br /><b>Entity: " + entityGroup
/* 1588 */             .getLongDescription() + "</b>");
/* 1589 */         println("<br /><br /><b>There is no value for: ANNTOCONFIG</b>");
/* 1590 */         setReturnCode(-1);
/*      */       } else {
/*      */         
/* 1593 */         logMessage("****_eg.getEntityItemCount() for ANNTOCONFIG:" + entityGroup
/*      */             
/* 1595 */             .getEntityItemCount());
/* 1596 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1597 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/* 1598 */           if (entityItem != null) {
/* 1599 */             EANAttribute eANAttribute = entityItem.getAttribute("CONFIGAVAILDATE");
/* 1600 */             logMessage("****att for ANNTOCONFIG:" + eANAttribute);
/* 1601 */             if (eANAttribute == null) {
/* 1602 */               println("<br /><br /><b>There is no value for: CONFIGAVAILDATE</b>");
/* 1603 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1605 */                   .getEntityType() + entityItem
/* 1606 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1609 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1610 */               setReturnCode(-1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkCHANNELstatus() {
/* 1624 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CHANNEL");
/* 1625 */     if (entityGroup != null) {
/*      */       
/* 1627 */       logMessage("_eg.getEntityItemCount() for CHANNEL:" + entityGroup
/*      */           
/* 1629 */           .getEntityItemCount());
/* 1630 */       if (entityGroup.getEntityItemCount() == 0) {
/* 1631 */         println("<br /><br /><b>Entity: " + entityGroup
/* 1632 */             .getLongDescription() + "</b>");
/* 1633 */         println("<br /><br /><b>There is no value for: GENAREASELECTION</b>");
/* 1634 */         println("<br /><br /><b>There is no value for: COUNTRYLIST</b>");
/* 1635 */         println("<br /><br /><b>There is no value for: CHANNELNAME</b>");
/* 1636 */         setReturnCode(-1);
/*      */       } else {
/* 1638 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */           
/* 1640 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */           
/* 1642 */           logMessage("ei for CHANNEL:" + entityItem.dump(true));
/*      */           
/* 1644 */           if (entityItem != null) {
/* 1645 */             EANAttribute[] arrayOfEANAttribute = new EANAttribute[3];
/* 1646 */             arrayOfEANAttribute[0] = entityItem.getAttribute("GENAREASELECTION");
/* 1647 */             arrayOfEANAttribute[1] = entityItem.getAttribute("COUNTRYLIST");
/* 1648 */             arrayOfEANAttribute[2] = entityItem.getAttribute("CHANNELNAME");
/*      */             
/* 1650 */             logMessage("atts[0] for CHANNEL:" + arrayOfEANAttribute[0]);
/* 1651 */             logMessage("atts[1] for CHANNEL:" + arrayOfEANAttribute[1]);
/* 1652 */             logMessage("atts[2] for CHANNEL:" + arrayOfEANAttribute[2]);
/* 1653 */             if (arrayOfEANAttribute[0] == null) {
/* 1654 */               println("<br /><br /><b>There is no value for: GENAREASELECTION</b>");
/* 1655 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1657 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1659 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1662 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1663 */               setReturnCode(-1);
/*      */             } 
/* 1665 */             if (arrayOfEANAttribute[1] == null) {
/* 1666 */               println("<br /><br /><b>There is no value for: COUNTRYLIST</b>");
/* 1667 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1669 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1671 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1674 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1675 */               setReturnCode(-1);
/*      */             } 
/* 1677 */             if (arrayOfEANAttribute[2] == null) {
/* 1678 */               println("<br /><br /><b>There is no value for: CHANNELNAME</b>");
/* 1679 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1681 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1683 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1686 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1687 */               setReturnCode(-1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkCOUNTRYLISTstatus() {
/* 1701 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("ANNOUNCEMENT");
/* 1702 */     if (entityGroup != null)
/*      */     {
/* 1704 */       if (entityGroup.getEntityItemCount() == 0) {
/* 1705 */         println("<br /><br /><b>Entity: " + entityGroup
/* 1706 */             .getLongDescription() + "</b>");
/* 1707 */         println("<br /><br /><b>There is no value for: ANNOUNCEMENT</b>");
/* 1708 */         setReturnCode(-1);
/*      */       } else {
/*      */         
/* 1711 */         logMessage("****_eg.getEntityItemCount() for ANNOUNCEMENT:" + entityGroup
/*      */             
/* 1713 */             .getEntityItemCount());
/* 1714 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1715 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/* 1716 */           if (entityItem != null) {
/* 1717 */             EANAttribute eANAttribute = entityItem.getAttribute("COUNTRYLIST");
/* 1718 */             logMessage("****att for ANNOUNCEMENT:" + eANAttribute);
/* 1719 */             if (eANAttribute == null) {
/* 1720 */               println("<br /><br /><b>There is no value for: COUNTRYLIST</b>");
/* 1721 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1723 */                   .getEntityType() + entityItem
/* 1724 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1727 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1728 */               setReturnCode(-1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkOPstatus() {
/* 1742 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OP");
/* 1743 */     if (entityGroup != null) {
/*      */       
/* 1745 */       logMessage("_eg.getEntityItemCount() for OP:" + entityGroup
/* 1746 */           .getEntityItemCount());
/* 1747 */       if (entityGroup.getEntityItemCount() == 0) {
/* 1748 */         println("<br /><br /><b>Entity: " + entityGroup
/* 1749 */             .getLongDescription() + "</b>");
/* 1750 */         println("<br /><br /><b>There is no value for: FIRSTNAME</b>");
/* 1751 */         println("<br /><br /><b>There is no value for: LASTNAME</b>");
/* 1752 */         println("<br /><br /><b>There is no value for: VNETNODE</b>");
/* 1753 */         println("<br /><br /><b>There is no value for: VNETUID</b>");
/* 1754 */         println("<br /><br /><b>There is no value for: JOBTITLE</b>");
/* 1755 */         println("<br /><br /><b>There is no value for: USERNAME</b>");
/* 1756 */         println("<br /><br /><b>There is no value for: SITE</b>");
/* 1757 */         setReturnCode(-1);
/*      */       } else {
/* 1759 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */           
/* 1761 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */           
/* 1763 */           logMessage("ei for OP:" + entityItem.dump(true));
/*      */           
/* 1765 */           if (entityItem != null) {
/* 1766 */             EANAttribute[] arrayOfEANAttribute = new EANAttribute[7];
/* 1767 */             arrayOfEANAttribute[0] = entityItem.getAttribute("FIRSTNAME");
/* 1768 */             arrayOfEANAttribute[1] = entityItem.getAttribute("LASTNAME");
/* 1769 */             arrayOfEANAttribute[2] = entityItem.getAttribute("VNETNODE");
/* 1770 */             arrayOfEANAttribute[3] = entityItem.getAttribute("VNETUID");
/* 1771 */             arrayOfEANAttribute[4] = entityItem.getAttribute("JOBTITLE");
/* 1772 */             arrayOfEANAttribute[5] = entityItem.getAttribute("USERNAME");
/* 1773 */             arrayOfEANAttribute[6] = entityItem.getAttribute("SITE");
/*      */             
/* 1775 */             logMessage("atts[0] for OP:" + arrayOfEANAttribute[0]);
/* 1776 */             logMessage("atts[1] for OP:" + arrayOfEANAttribute[1]);
/* 1777 */             logMessage("atts[2] for OP:" + arrayOfEANAttribute[2]);
/* 1778 */             logMessage("atts[3] for OP:" + arrayOfEANAttribute[3]);
/* 1779 */             logMessage("atts[4] for OP:" + arrayOfEANAttribute[4]);
/* 1780 */             logMessage("atts[5] for OP:" + arrayOfEANAttribute[5]);
/* 1781 */             logMessage("atts[6] for OP:" + arrayOfEANAttribute[6]);
/* 1782 */             if (arrayOfEANAttribute[0] == null) {
/* 1783 */               println("<br /><br /><b>There is no value for: FIRSTNAME</b>");
/* 1784 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1786 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1788 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1791 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1792 */               setReturnCode(-1);
/*      */             } 
/* 1794 */             if (arrayOfEANAttribute[1] == null) {
/* 1795 */               println("<br /><br /><b>There is no value for: LASTNAME</b>");
/* 1796 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1798 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1800 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1803 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1804 */               setReturnCode(-1);
/*      */             } 
/* 1806 */             if (arrayOfEANAttribute[2] == null) {
/* 1807 */               println("<br /><br /><b>There is no value for: VNETNODE</b>");
/* 1808 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1810 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1812 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1815 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1816 */               setReturnCode(-1);
/*      */             } 
/* 1818 */             if (arrayOfEANAttribute[3] == null) {
/* 1819 */               println("<br /><br /><b>There is no value for: VNETUID</b>");
/* 1820 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1822 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1824 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1827 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1828 */               setReturnCode(-1);
/*      */             } 
/* 1830 */             if (arrayOfEANAttribute[4] == null) {
/* 1831 */               println("<br /><br /><b>There is no value for: JOBTITLE</b>");
/* 1832 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1834 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1836 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1839 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1840 */               setReturnCode(-1);
/*      */             } 
/* 1842 */             if (arrayOfEANAttribute[5] == null) {
/* 1843 */               println("<br /><br /><b>There is no value for: SITE</b>");
/* 1844 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1846 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1848 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1851 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1852 */               setReturnCode(-1);
/*      */             } 
/* 1854 */             if (arrayOfEANAttribute[6] == null) {
/* 1855 */               println("<br /><br /><b>There is no value for: JOBTITLE</b>");
/* 1856 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1858 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1860 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1863 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1864 */               setReturnCode(-1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkCONFIGNAMEstatus() {
/* 1878 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CONFIGURATOR");
/* 1879 */     if (entityGroup != null)
/*      */     {
/* 1881 */       if (entityGroup.getEntityItemCount() == 0) {
/* 1882 */         println("<br /><br /><b>Entity: " + entityGroup
/* 1883 */             .getLongDescription() + "</b>");
/* 1884 */         println("<br /><br /><b>There is no value for: CONFIGNAME</b>");
/* 1885 */         setReturnCode(-1);
/*      */       } else {
/*      */         
/* 1888 */         logMessage("****_eg.getEntityItemCount() for CONFIGNAME:" + entityGroup
/*      */             
/* 1890 */             .getEntityItemCount());
/* 1891 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1892 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/* 1893 */           if (entityItem != null) {
/* 1894 */             EANAttribute eANAttribute = entityItem.getAttribute("COUNTRYLIST");
/* 1895 */             logMessage("****att for CONFIGNAME:" + eANAttribute);
/* 1896 */             if (eANAttribute == null) {
/* 1897 */               println("<br /><br /><b>There is no value for: COUNTRYLIST</b>");
/* 1898 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1900 */                   .getEntityType() + entityItem
/* 1901 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1904 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1905 */               setReturnCode(-1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkORGANUNITstatus() {
/* 1919 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("ORGANUNIT");
/* 1920 */     if (entityGroup != null) {
/*      */       
/* 1922 */       logMessage("_eg.getEntityItemCount() for OP:" + entityGroup
/* 1923 */           .getEntityItemCount());
/* 1924 */       if (entityGroup.getEntityItemCount() == 0) {
/* 1925 */         println("<br /><br /><b>Entity: " + entityGroup
/* 1926 */             .getLongDescription() + "</b>");
/* 1927 */         println("<br /><br /><b>There is no value for: NAME</b>");
/* 1928 */         println("<br /><br /><b>There is no value for: INITIALS</b>");
/* 1929 */         println("<br /><br /><b>There is no value for: STREETADDRESS</b>");
/* 1930 */         println("<br /><br /><b>There is no value for: CITY</b>");
/* 1931 */         println("<br /><br /><b>There is no value for: STATE</b>");
/* 1932 */         println("<br /><br /><b>There is no value for: COUNTRY</b>");
/* 1933 */         println("<br /><br /><b>There is no value for: ZIPCODE</b>");
/* 1934 */         setReturnCode(-1);
/*      */       } else {
/* 1936 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */           
/* 1938 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */           
/* 1940 */           logMessage("ei for OP:" + entityItem.dump(true));
/*      */           
/* 1942 */           if (entityItem != null) {
/* 1943 */             EANAttribute[] arrayOfEANAttribute = new EANAttribute[7];
/* 1944 */             arrayOfEANAttribute[0] = entityItem.getAttribute("NAME");
/* 1945 */             arrayOfEANAttribute[1] = entityItem.getAttribute("INITIALS");
/* 1946 */             arrayOfEANAttribute[2] = entityItem.getAttribute("STREETADDRESS");
/* 1947 */             arrayOfEANAttribute[3] = entityItem.getAttribute("CITY");
/* 1948 */             arrayOfEANAttribute[4] = entityItem.getAttribute("STATE");
/* 1949 */             arrayOfEANAttribute[5] = entityItem.getAttribute("COUNTRY");
/* 1950 */             arrayOfEANAttribute[6] = entityItem.getAttribute("ZIPCODE");
/*      */             
/* 1952 */             logMessage("atts[0] for ORGANUNIT:" + arrayOfEANAttribute[0]);
/* 1953 */             logMessage("atts[1] for ORGANUNIT:" + arrayOfEANAttribute[1]);
/* 1954 */             logMessage("atts[2] for ORGANUNIT:" + arrayOfEANAttribute[2]);
/* 1955 */             logMessage("atts[3] for ORGANUNIT:" + arrayOfEANAttribute[3]);
/* 1956 */             logMessage("atts[4] for ORGANUNIT:" + arrayOfEANAttribute[4]);
/* 1957 */             logMessage("atts[5] for ORGANUNIT:" + arrayOfEANAttribute[5]);
/* 1958 */             logMessage("atts[6] for ORGANUNIT:" + arrayOfEANAttribute[6]);
/* 1959 */             if (arrayOfEANAttribute[0] == null) {
/* 1960 */               println("<br /><br /><b>There is no value for: NAME</b>");
/* 1961 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1963 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1965 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1968 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1969 */               setReturnCode(-1);
/*      */             } 
/* 1971 */             if (arrayOfEANAttribute[1] == null) {
/* 1972 */               println("<br /><br /><b>There is no value for: INITIALS</b>");
/* 1973 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1975 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1977 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1980 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1981 */               setReturnCode(-1);
/*      */             } 
/* 1983 */             if (arrayOfEANAttribute[2] == null) {
/* 1984 */               println("<br /><br /><b>There is no value for: STREETADDRESS</b>");
/* 1985 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1987 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 1989 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 1992 */               println(dispNavAttributes(entityItem, entityGroup));
/* 1993 */               setReturnCode(-1);
/*      */             } 
/* 1995 */             if (arrayOfEANAttribute[3] == null) {
/* 1996 */               println("<br /><br /><b>There is no value for: CITY</b>");
/* 1997 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 1999 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 2001 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 2004 */               println(dispNavAttributes(entityItem, entityGroup));
/* 2005 */               setReturnCode(-1);
/*      */             } 
/* 2007 */             if (arrayOfEANAttribute[4] == null) {
/* 2008 */               println("<br /><br /><b>There is no value for: STATE</b>");
/* 2009 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 2011 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 2013 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 2016 */               println(dispNavAttributes(entityItem, entityGroup));
/* 2017 */               setReturnCode(-1);
/*      */             } 
/* 2019 */             if (arrayOfEANAttribute[5] == null) {
/* 2020 */               println("<br /><br /><b>There is no value for: COUNTRY</b>");
/* 2021 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 2023 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 2025 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 2028 */               println(dispNavAttributes(entityItem, entityGroup));
/* 2029 */               setReturnCode(-1);
/*      */             } 
/* 2031 */             if (arrayOfEANAttribute[6] == null) {
/* 2032 */               println("<br /><br /><b>There is no value for: ZIPCODE</b>");
/* 2033 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 2035 */                   .getEntityType() + ":" + entityItem
/*      */                   
/* 2037 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 2040 */               println(dispNavAttributes(entityItem, entityGroup));
/* 2041 */               setReturnCode(-1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkSUBJECTLINE_1status() {
/* 2055 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("ANNDELIVERABLE");
/* 2056 */     if (entityGroup != null)
/*      */     {
/* 2058 */       if (entityGroup.getEntityItemCount() == 0) {
/* 2059 */         println("<br /><br /><b>Entity: " + entityGroup
/* 2060 */             .getLongDescription() + "</b>");
/* 2061 */         println("<br /><br /><b>There is no value for: SUBJECTLINE_1</b>");
/* 2062 */         setReturnCode(-1);
/*      */       } else {
/*      */         
/* 2065 */         logMessage("****_eg.getEntityItemCount() for ANNDELIVERABLE:" + entityGroup
/*      */             
/* 2067 */             .getEntityItemCount());
/* 2068 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2069 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/* 2070 */           if (entityItem != null) {
/* 2071 */             EANAttribute eANAttribute = entityItem.getAttribute("SUBJECTLINE_1");
/* 2072 */             logMessage("****att for SUBJECTLINE_1:" + eANAttribute);
/* 2073 */             if (eANAttribute == null) {
/* 2074 */               println("<br /><br /><b>There is no value for: SUBJECTLINE_1</b>");
/* 2075 */               println("<br /><br /><b>" + entityItem
/*      */                   
/* 2077 */                   .getEntityType() + entityItem
/* 2078 */                   .getEntityID() + "</b>");
/*      */ 
/*      */               
/* 2081 */               println(dispNavAttributes(entityItem, entityGroup));
/* 2082 */               setReturnCode(-1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setControlBlock() {
/* 2094 */     this.m_strNow = getNow();
/* 2095 */     this.m_strForever = getForever();
/* 2096 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2103 */       .m_cbOn = new ControlBlock(this.m_strNow, this.m_strForever, this.m_strNow, this.m_strForever, this.m_prof.getOPWGID(), this.m_prof.getTranID());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlagValue(String paramString) {
/* 2114 */     String str = null;
/*      */     
/* 2116 */     if (paramString.equals("ANCYCLESTATUS")) {
/* 2117 */       logMessage("****** Announcement Lifecycle Status set to: 112");
/*      */ 
/*      */       
/* 2120 */       str = "112";
/*      */     }
/*      */     else {
/*      */       
/* 2124 */       str = null;
/*      */     } 
/*      */     
/* 2127 */     if (str != null) {
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2134 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*      */ 
/*      */ 
/*      */         
/* 2138 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2145 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString, str, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2150 */         Vector<SingleFlag> vector = new Vector();
/* 2151 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*      */         
/* 2153 */         if (singleFlag != null) {
/* 2154 */           vector.addElement(singleFlag);
/* 2155 */           returnEntityKey.m_vctAttributes = vector;
/* 2156 */           vector1.addElement(returnEntityKey);
/* 2157 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 2158 */           this.m_db.commit();
/*      */         } 
/* 2160 */       } catch (MiddlewareException middlewareException) {
/* 2161 */         logMessage("setFlagValue: " + middlewareException.getMessage());
/* 2162 */       } catch (Exception exception) {
/* 2163 */         logMessage("setFlagValue: " + exception.getMessage());
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlagValue1(String paramString) {
/* 2177 */     String str = null;
/*      */     
/* 2179 */     if (paramString.equals("ANCYCLESTATUS")) {
/* 2180 */       logMessage("****** Announcement Lifecycle Status set to: 114");
/*      */ 
/*      */       
/* 2183 */       str = "114";
/*      */     }
/*      */     else {
/*      */       
/* 2187 */       str = null;
/*      */     } 
/*      */     
/* 2190 */     if (str != null) {
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2197 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*      */ 
/*      */ 
/*      */         
/* 2201 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2208 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString, str, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2213 */         Vector<SingleFlag> vector = new Vector();
/* 2214 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*      */         
/* 2216 */         if (singleFlag != null) {
/* 2217 */           vector.addElement(singleFlag);
/* 2218 */           returnEntityKey.m_vctAttributes = vector;
/* 2219 */           vector1.addElement(returnEntityKey);
/* 2220 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 2221 */           this.m_db.commit();
/*      */         } 
/* 2223 */       } catch (MiddlewareException middlewareException) {
/* 2224 */         logMessage("setFlagValue1: " + middlewareException.getMessage());
/* 2225 */       } catch (Exception exception) {
/* 2226 */         logMessage("setFlagValue1: " + exception.getMessage());
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlagValue2(String paramString) {
/* 2240 */     String str = null;
/*      */     
/* 2242 */     if (paramString.equals("ANCYCLESTATUS")) {
/* 2243 */       logMessage("****** Announcement Lifecycle Status set to: 115");
/*      */ 
/*      */       
/* 2246 */       str = "115";
/*      */     }
/*      */     else {
/*      */       
/* 2250 */       str = null;
/*      */     } 
/*      */     
/* 2253 */     if (str != null) {
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2260 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*      */ 
/*      */ 
/*      */         
/* 2264 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2271 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString, str, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2276 */         Vector<SingleFlag> vector = new Vector();
/* 2277 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*      */         
/* 2279 */         if (singleFlag != null) {
/* 2280 */           vector.addElement(singleFlag);
/* 2281 */           returnEntityKey.m_vctAttributes = vector;
/* 2282 */           vector1.addElement(returnEntityKey);
/* 2283 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 2284 */           this.m_db.commit();
/*      */         } 
/* 2286 */       } catch (MiddlewareException middlewareException) {
/* 2287 */         logMessage("setFlagValue2: " + middlewareException.getMessage());
/* 2288 */       } catch (Exception exception) {
/* 2289 */         logMessage("setFlagValue2: " + exception.getMessage());
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlagValue3(String paramString) {
/* 2303 */     String str = null;
/*      */     
/* 2305 */     if (paramString.equals("RFAHWABR01")) {
/* 2306 */       logMessage("****** Announcement Lifecycle Status set to: 0020");
/*      */ 
/*      */       
/* 2309 */       str = "0020";
/*      */     }
/*      */     else {
/*      */       
/* 2313 */       str = null;
/*      */     } 
/*      */     
/* 2316 */     if (str != null) {
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2323 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*      */ 
/*      */ 
/*      */         
/* 2327 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2334 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString, str, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2339 */         Vector<SingleFlag> vector = new Vector();
/* 2340 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*      */         
/* 2342 */         if (singleFlag != null) {
/* 2343 */           vector.addElement(singleFlag);
/* 2344 */           returnEntityKey.m_vctAttributes = vector;
/* 2345 */           vector1.addElement(returnEntityKey);
/* 2346 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 2347 */           this.m_db.commit();
/*      */         } 
/* 2349 */       } catch (MiddlewareException middlewareException) {
/* 2350 */         logMessage("setFlagValue3: " + middlewareException.getMessage());
/* 2351 */       } catch (Exception exception) {
/* 2352 */         logMessage("setFlagValue3: " + exception.getMessage());
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 2362 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 2371 */     return "<br />This needs to be defined <br />";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getStyle() {
/* 2380 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRevision() {
/* 2389 */     return "1.20";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getVersion() {
/* 2399 */     return "ANQRFRABR01_IGSSVS.java,v 1.20 2008/03/19 19:40:51 wendy Exp";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 2407 */     return getVersion();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void buildRptHeader() throws SQLException, MiddlewareException {
/* 2415 */     String str1 = getVersion();
/* 2416 */     String[] arrayOfString = { getABRDescription(), str1, getEnterprise() };
/* 2417 */     String str2 = buildMessage("<b>IAB0001I: ABRName:</b> %1# <b>Version:</b> %2# <b>Enterprise:</b> %3#", arrayOfString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2424 */     println(EACustom.getDocTypeHtml());
/* 2425 */     println("<head>");
/* 2426 */     println(EACustom.getMetaTags("ANQRFRABR01_IGSSVS.java"));
/* 2427 */     println(EACustom.getCSS());
/* 2428 */     println(EACustom.getTitle(getShortClassName(getClass())));
/* 2429 */     println("</head>");
/* 2430 */     println("<body id=\"ibm-com\">");
/* 2431 */     println(EACustom.getMastheadDiv());
/*      */     
/* 2433 */     println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + str2 + "</em></p>");
/*      */     
/* 2435 */     println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" + 
/*      */         
/* 2437 */         getABRVersion() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" + 
/*      */         
/* 2439 */         getValOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" + 
/*      */         
/* 2441 */         getEffOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" + 
/*      */         
/* 2443 */         getNow() + "</td></tr>" + "\n" + "</table>");
/*      */     
/* 2445 */     println("<h3>Description: </h3>" + getDescription() + "\n" + "<hr />" + "\n");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void dispHeader(EntityGroup paramEntityGroup, EntityItem paramEntityItem) {
/* 2453 */     if (paramEntityGroup != null && paramEntityGroup != null) {
/* 2454 */       println("<h1><b>" + paramEntityGroup.getLongDescription() + "</b></h1><br />");
/* 2455 */       println(displayStatus(paramEntityItem, paramEntityGroup));
/* 2456 */       println(dispNavAttributes(paramEntityItem, paramEntityGroup));
/*      */     } 
/*      */   }
/*      */   
/*      */   private String dispNavAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup) {
/* 2461 */     StringBuffer stringBuffer = new StringBuffer();
/* 2462 */     stringBuffer.append("<br /><br /><table summary=\"layout\" width=\"100%\">\n");
/* 2463 */     stringBuffer.append("<tr><td class=\"PsgLabel\" width=\"35%\">Navigation Attribute</td><td class=\"PsgLabel\" width=\"65%\">Value</td></tr>");
/* 2464 */     for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 2465 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 2466 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 2467 */       if (eANMetaAttribute.isNavigate()) {
/* 2468 */         stringBuffer.append("<tr><td class=\"PsgText\" width=\"35%\">" + eANMetaAttribute.getLongDescription() + "</td><td class=\"PsgText\" width=\"65%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "<em>** Not Populated **</em>" : eANAttribute.toString()) + "</td></tr>");
/*      */       }
/*      */     } 
/* 2471 */     stringBuffer.append("</table>");
/* 2472 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   private String displayStatus(EntityItem paramEntityItem, EntityGroup paramEntityGroup) {
/* 2476 */     StringBuffer stringBuffer = new StringBuffer();
/* 2477 */     stringBuffer.append("<br /><br /><table summary=\"layout\" width=\"100%\">\n");
/* 2478 */     stringBuffer.append("<tr><td class=\"PsgLabel\" width=\"35%\">Status Attribute</td><td class=\"PsgLabel\" width=\"65%\">Value</td></tr>");
/* 2479 */     for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 2480 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 2481 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 2482 */       if (eANMetaAttribute instanceof COM.ibm.eannounce.objects.MetaStatusAttribute) {
/* 2483 */         stringBuffer.append("<tr><td class=\"PsgText\" width=\"35%\">" + eANMetaAttribute.getLongDescription() + "</td><td class=\"PsgText\" width=\"65%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "<em>** Not Populated **</em>" : eANAttribute.toString()) + "</td></tr>");
/*      */       }
/*      */     } 
/* 2486 */     stringBuffer.append("</table>");
/* 2487 */     return stringBuffer.toString();
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ANQRFRABR01_IGSSVS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */