/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.LockActionItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.objects.SingleFlag;
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
/*      */ public class ANQRFRABR01
/*      */   extends PokBaseABR
/*      */ {
/*  134 */   public static final String ABR = new String("ANQRFRABR01");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  139 */   public static final String CHANGEREQUEST_STATUS = new String("CRSTATUS");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  144 */   public static final String ANNOUNCEMNET = new String("ANNOUNCEMNET");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  149 */   public static final String ANCYCLESTATUS = new String("ANCYCLESTATUS");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  154 */   public static final String RFAHWABR01_STATUS = new String("RFAHWABR01");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  160 */   public static final Hashtable c_hshEntities = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  165 */   public static final Hashtable c_hshANNtoANN = new Hashtable<>();
/*      */ 
/*      */   
/*      */   static {
/*  169 */     c_hshEntities.put("ANNAREAMKTINFO", "HI");
/*  170 */     c_hshEntities.put("ANNDELIVERABLE", "HI");
/*  171 */     c_hshEntities.put("CONFIGURATOR", "HI");
/*  172 */     c_hshEntities.put("EDUCATION", "HI");
/*  173 */     c_hshEntities.put("OP", "HI");
/*  174 */     c_hshEntities.put("ORGANUNIT", "HI");
/*      */     
/*  176 */     c_hshEntities.put("SALESMANCHG", "HI");
/*  177 */     c_hshEntities.put("AVAIL", "HI");
/*      */     
/*  179 */     c_hshEntities.put("BPEXHIBIT", "HI");
/*  180 */     c_hshEntities.put("COFOOFMGMTGRP", "HI");
/*  181 */     c_hshEntities.put("CRYPTO", "HI");
/*  182 */     c_hshEntities.put("ENVIRINFO", "HI");
/*  183 */     c_hshEntities.put("FUP", "HI");
/*  184 */     c_hshEntities.put("OOFOOFMGMTGRP", "HI");
/*  185 */     c_hshEntities.put("ORDERINFO", "HI");
/*  186 */     c_hshEntities.put("STANDAMENDTEXT", "HI");
/*  187 */     c_hshEntities.put("ORDEROF", "HI");
/*  188 */     c_hshEntities.put("PACKAGING", "HI");
/*  189 */     c_hshEntities.put("RULES", "HI");
/*  190 */     c_hshEntities.put("SHIPINFO", "HI");
/*  191 */     c_hshEntities.put("PRICEFININFO", "HI");
/*  192 */     c_hshEntities.put("CATINCL", "HI");
/*  193 */     c_hshEntities.put("CHANNEL", "HI");
/*  194 */     c_hshEntities.put("COFCOFMGMTGRP", "HI");
/*  195 */     c_hshEntities.put("COMMERCIALOF", "HI");
/*  196 */     c_hshEntities.put("IVOCAT", "HI");
/*  197 */     c_hshEntities.put("ORDERINFO", "HI");
/*  198 */     c_hshEntities.put("PUBLICATION", "HI");
/*  199 */     c_hshEntities.put("PUBTABLE", "PUBTABLE");
/*  200 */     c_hshEntities.put("TECHCAPABILITY", "HI");
/*  201 */     c_hshEntities.put("ORDEROF", "HI");
/*      */     
/*  203 */     c_hshEntities.put("FUPFUPMGMTGRP", "HI");
/*  204 */     c_hshEntities.put("FUPPOFMGMTGRP", "HI");
/*  205 */     c_hshEntities.put("PHYSICALOF", "HI");
/*  206 */     c_hshEntities.put("POFPOFMGMTGRP", "HI");
/*  207 */     c_hshEntities.put("ENGCHANGE", "HI");
/*      */     
/*  209 */     c_hshANNtoANN.put("ANNOUNCEMENT", "ANNOUNCEMENT");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  214 */   private EntityGroup m_egParent = null;
/*  215 */   private EntityItem m_eiParent = null;
/*      */ 
/*      */   
/*  218 */   private final String m_strApprovedValue = "114";
/*      */   
/*  220 */   private final String m_strReadyFinalReviewValue = "112";
/*      */   
/*  222 */   private final String m_strApprovedRiskValue = "115";
/*      */   
/*  224 */   private final String m_strQueueRFAValue = "0020";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  232 */     LockActionItem lockActionItem = null;
/*  233 */     String str1 = null;
/*  234 */     String str2 = null;
/*      */     
/*      */     try {
/*  237 */       start_ABRBuild();
/*      */       
/*  239 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  240 */       this.m_eiParent = this.m_egParent.getEntityItem(0);
/*      */       
/*  242 */       if (this.m_egParent == null) {
/*  243 */         println(ABR + ":" + 
/*      */ 
/*      */             
/*  246 */             getVersion() + ":ERROR:1: m_egParent cannot be established.");
/*      */         
/*  248 */         setReturnCode(-1);
/*      */         return;
/*      */       } 
/*  251 */       if (this.m_eiParent == null) {
/*  252 */         println(ABR + ":" + 
/*      */ 
/*      */             
/*  255 */             getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/*      */         
/*  257 */         setReturnCode(-1);
/*      */         
/*      */         return;
/*      */       } 
/*  261 */       logMessage(ABR + ":" + 
/*      */ 
/*      */           
/*  264 */           getVersion() + ":Request to Work on Entity:" + this.m_eiParent
/*      */           
/*  266 */           .getEntityType() + ":" + this.m_eiParent
/*      */           
/*  268 */           .getEntityID());
/*  269 */       buildReportHeader();
/*  270 */       setControlBlock();
/*  271 */       setDGTitle(setDGName(this.m_eiParent, ABR));
/*      */       
/*  273 */       logMessage(ABR + ":" + 
/*      */ 
/*      */           
/*  276 */           getVersion() + ":Setup Complete:" + this.m_eiParent
/*      */           
/*  278 */           .getEntityType() + ":" + this.m_eiParent
/*      */           
/*  280 */           .getEntityID());
/*      */ 
/*      */       
/*  283 */       setReturnCode(0);
/*      */       
/*  285 */       displayHeader(this.m_egParent, this.m_eiParent);
/*      */       
/*  287 */       checkFinalStatus();
/*  288 */       checkANNStatus();
/*  289 */       checkCRstatus();
/*      */       
/*  291 */       if (getReturnCode() == 0) {
/*      */ 
/*      */         
/*  294 */         String str = getFlagCode(this.m_eiParent, getStatusAttributeCode(this.m_eiParent));
/*  295 */         logMessage("****strFlagCode****" + str + this.m_eiParent + 
/*      */ 
/*      */ 
/*      */             
/*  299 */             getStatusAttributeCode(this.m_eiParent));
/*  300 */         if (str.equals("111")) {
/*  301 */           setFlagValue(ANNOUNCEMNET, getEntityID(), ANCYCLESTATUS);
/*      */         }
/*  303 */         else if (str.equals("113")) {
/*  304 */           setFlagValue(ANNOUNCEMNET, getEntityID(), ANCYCLESTATUS);
/*      */         }
/*  306 */         else if (str.equals("118")) {
/*  307 */           setFlagValue1(ANNOUNCEMNET, getEntityID(), ANCYCLESTATUS);
/*      */         }
/*  309 */         else if (str.equals("119")) {
/*  310 */           setFlagValue2(ANNOUNCEMNET, getEntityID(), ANCYCLESTATUS);
/*      */         }
/*  312 */         else if (str.equals("120")) {
/*  313 */           setFlagValue3(ANNOUNCEMNET, 
/*      */               
/*  315 */               getEntityID(), RFAHWABR01_STATUS);
/*      */         
/*      */         }
/*  318 */         else if (str.equals("121")) {
/*  319 */           setFlagValue3(ANNOUNCEMNET, 
/*      */               
/*  321 */               getEntityID(), RFAHWABR01_STATUS);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  327 */         lockActionItem = new LockActionItem(null, this.m_db, this.m_prof, "LOCKEXTANN01LOCK1");
/*      */         
/*  329 */         EntityItem[] arrayOfEntityItem = { this.m_eiParent };
/*  330 */         lockActionItem.setEntityItems(arrayOfEntityItem);
/*  331 */         lockActionItem.executeAction(this.m_db, this.m_prof);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  336 */         EANAttribute eANAttribute = this.m_eiParent.getAttribute("ANNCODENAME");
/*      */         
/*  338 */         str1 = (eANAttribute != null) ? eANAttribute.toString() : "<em>** Not Populated **</em>";
/*  339 */         logMessage("***** m_strParentAnnCodeNameValue is " + str1);
/*      */ 
/*      */ 
/*      */         
/*  343 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/*  344 */         str2 = null;
/*      */         
/*  346 */         if (arrayOfMetaFlag != null) {
/*  347 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*  348 */             if (arrayOfMetaFlag[b]
/*  349 */               .getLongDescription()
/*  350 */               .equals(str1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  356 */               logMessage("***** j is " + b + " parentValue " + str1);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  361 */               logMessage("***** j is " + b + " getFlagCode " + arrayOfMetaFlag[b]
/*      */ 
/*      */ 
/*      */                   
/*  365 */                   .getFlagCode());
/*  366 */               logMessage("***** j is " + b + " getLongDescription " + arrayOfMetaFlag[b]
/*      */ 
/*      */ 
/*      */                   
/*  370 */                   .getLongDescription());
/*      */ 
/*      */               
/*  373 */               str2 = arrayOfMetaFlag[b].getFlagCode();
/*  374 */               this.m_db.setOutOfCirculation(this.m_prof, this.m_eiParent
/*      */                   
/*  376 */                   .getEntityType(), "ANNCODENAME", str2, false);
/*      */             } 
/*      */           } 
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  385 */         this.m_prof.setValOn(getValOn());
/*  386 */         this.m_prof.setEffOn(getEffOn());
/*      */       } 
/*      */ 
/*      */       
/*  390 */       println("<br /><b>" + 
/*      */           
/*  392 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*      */ 
/*      */               
/*  395 */               getABRDescription(), 
/*  396 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*      */             }) + "</b>");
/*      */       
/*  399 */       log(
/*  400 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*      */ 
/*      */               
/*  403 */               getABRDescription(), 
/*  404 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*      */             }));
/*  406 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  407 */       setReturnCode(-2);
/*  408 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*      */ 
/*      */ 
/*      */           
/*  412 */           .getMessage() + "</font></h3>");
/*      */       
/*  414 */       logError(lockPDHEntityException.getMessage());
/*  415 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  416 */       setReturnCode(-2);
/*  417 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*      */           
/*  419 */           .getMessage() + "</font></h3>");
/*      */       
/*  421 */       logError(updatePDHEntityException.getMessage());
/*  422 */     } catch (Exception exception) {
/*      */       
/*  424 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/*  425 */       println("" + exception);
/*      */ 
/*      */       
/*  428 */       if (getABRReturnCode() != -2) {
/*  429 */         setReturnCode(-3);
/*      */       }
/*      */     } finally {
/*      */       
/*  433 */       if (getReturnCode() == 0) {
/*  434 */         setReturnCode(0);
/*      */       }
/*      */       
/*  437 */       setDGString(getABRReturnCode());
/*  438 */       setDGRptName("ANQRFRABR01");
/*  439 */       setDGRptClass("ANQRFRABR01");
/*  440 */       printDGSubmitString();
/*      */ 
/*      */       
/*  443 */       buildReportFooter();
/*      */       
/*  445 */       if (!isReadOnly()) {
/*  446 */         clearSoftLock();
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
/*      */   public void printFAILmessage(EntityGroup paramEntityGroup) {
/*  458 */     println("<br><br><b>" + paramEntityGroup
/*  459 */         .getLongDescription() + " does not exist</b>");
/*  460 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printWARNINGmessage(EntityGroup paramEntityGroup) {
/*  470 */     println("<br><br><b>" + paramEntityGroup
/*      */         
/*  472 */         .getLongDescription() + " does not exist (Warning - Pass)</b>");
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
/*  484 */     println("<br><br><b>" + paramEntityGroup
/*      */         
/*  486 */         .getLongDescription() + " needs to be 'Final'status</b>");
/*      */     
/*  488 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printWARNINGmessage_2(EntityGroup paramEntityGroup) {
/*  498 */     println("<br><br><b>" + paramEntityGroup
/*      */         
/*  500 */         .getLongDescription() + " needs to be 'Final'status (Warning - Pass)</b>");
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
/*  512 */     println("<br><br><b>" + paramEntityGroup
/*      */         
/*  514 */         .getLongDescription() + " needs to be at least 'Ready for Final Review'</b>");
/*      */     
/*  516 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printWARNINGmessage_3(EntityGroup paramEntityGroup) {
/*  526 */     println("<br><br><b>" + paramEntityGroup
/*      */         
/*  528 */         .getLongDescription() + " needs to be at least 'Ready for Final Review' (Warning - Pass)</b>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkANNStatus() {
/*  539 */     boolean bool1 = false;
/*  540 */     boolean bool2 = false;
/*  541 */     boolean bool3 = false;
/*  542 */     EntityItem entityItem = null;
/*      */     
/*  544 */     for (byte b = 0; b < this.m_eiParent.getDownLinkCount(); b++) {
/*  545 */       EntityItem entityItem1 = (EntityItem)this.m_eiParent.getDownLink(b);
/*  546 */       EntityGroup entityGroup = entityItem1.getEntityGroup();
/*      */       
/*  548 */       if (entityItem1 == null) {
/*  549 */         println("<br><br><b>We have downlink to an non-existant relator... from " + this.m_eiParent
/*      */             
/*  551 */             .getKey() + "</b>");
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  556 */         if (entityGroup.getEntityType().equals("ANNWITHDRAWSANN")) {
/*  557 */           bool1 = true;
/*  558 */         } else if (entityGroup
/*  559 */           .getEntityType().equals("ANNDERIVESFROMANN")) {
/*  560 */           bool2 = true;
/*  561 */         } else if (entityGroup.getEntityType().equals("RELATEDANN")) {
/*  562 */           bool3 = true;
/*      */         } 
/*      */         
/*  565 */         entityItem = (EntityItem)entityItem1.getDownLink(0);
/*  566 */         if (entityItem == null) {
/*  567 */           println("<br><br><b>We have a Relator that points to nowhere..." + entityItem1
/*      */               
/*  569 */               .getKey() + "</b>");
/*      */ 
/*      */         
/*      */         }
/*  573 */         else if (c_hshANNtoANN.containsKey(entityItem.getEntityType()) && 
/*  574 */           isStatusableEntity(entityItem)) {
/*      */           
/*  576 */           EANAttribute eANAttribute = entityItem.getAttribute(
/*  577 */               getStatusAttributeCode(entityItem));
/*      */ 
/*      */           
/*  580 */           String str = (eANAttribute != null) ? eANAttribute.toString() : "<em>** Not Populated **</em>";
/*      */ 
/*      */           
/*  583 */           if (!isANNStatusOK(str)) {
/*  584 */             EntityGroup entityGroup1 = entityItem.getEntityGroup();
/*      */             
/*  586 */             if (entityItem1
/*  587 */               .getEntityType()
/*  588 */               .equals("ANNWITHDRAWSANN")) {
/*  589 */               printFAILmessage_3(entityGroup);
/*      */             } else {
/*      */               
/*  592 */               printWARNINGmessage_3(entityGroup);
/*      */             } 
/*  594 */             println(displayNavAttributes(entityItem, entityGroup1));
/*  595 */             println(displayStatuses(entityItem, entityGroup1));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  606 */     if (!bool1) {
/*  607 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("ANNWITHDRAWSANN");
/*  608 */       logMessage("************we are getting here ANNWITHDRAWSANN" + entityGroup);
/*  609 */       printWARNINGmessage(entityGroup);
/*      */     } 
/*  611 */     if (!bool2) {
/*  612 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("ANNDERIVESFROMANN");
/*  613 */       printWARNINGmessage(entityGroup);
/*      */     } 
/*  615 */     if (!bool3) {
/*  616 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("RELATEDANN");
/*  617 */       printWARNINGmessage(entityGroup);
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
/*      */   public boolean isANNStatusOK(String paramString) {
/*  630 */     boolean bool = false;
/*  631 */     logMessage("isANNStatusOK _sStatus " + paramString);
/*  632 */     if (paramString != null && (
/*  633 */       paramString.equals("Ready for Final Review") || paramString
/*  634 */       .equals("Approved") || paramString
/*  635 */       .equals("Approved with Risk") || paramString
/*  636 */       .equals("Released to Production Management") || paramString
/*  637 */       .equals("Announced"))) {
/*  638 */       bool = true;
/*      */     }
/*      */     
/*  641 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkFinalStatus() {
/*  651 */     for (byte b = 0; b < this.m_elist.getEntityGroupCount(); b++) {
/*      */       
/*  653 */       EntityGroup entityGroup = this.m_elist.getEntityGroup(b);
/*      */       
/*  655 */       if (c_hshEntities.containsKey(entityGroup.getEntityType())) {
/*  656 */         Vector<EntityItem> vector = new Vector();
/*      */ 
/*      */         
/*  659 */         if (entityGroup.getEntityItemCount() > 0) {
/*      */           
/*  661 */           for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/*  662 */             EntityItem entityItem = entityGroup.getEntityItem(b1);
/*  663 */             if (entityItem != null && 
/*  664 */               getStatusAttributeCode(entityItem) != null) {
/*      */               
/*  666 */               EANAttribute eANAttribute = entityItem.getAttribute(getStatusAttributeCode(entityItem));
/*      */ 
/*      */               
/*  669 */               String str = (eANAttribute != null) ? eANAttribute.toString() : "<em>** Not Populated **</em>";
/*      */               
/*  671 */               if (!str.equals("Final")) {
/*  672 */                 vector.addElement(entityItem);
/*      */               }
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  680 */           if (vector != null && vector.size() > 0) {
/*  681 */             EntityItem entityItem = vector.elementAt(0);
/*  682 */             EntityGroup entityGroup1 = entityItem.getEntityGroup();
/*      */             
/*  684 */             if (entityGroup1.getEntityType().equals("EDUCATION") || entityGroup1
/*  685 */               .getEntityType().equals("OP") || entityGroup1
/*  686 */               .getEntityType().equals("ORGANUNIT") || entityGroup1
/*  687 */               .getEntityType().equals("STANDAMENDTEXT")) {
/*      */               
/*  689 */               printWARNINGmessage_2(entityGroup1);
/*      */             } else {
/*      */               
/*  692 */               printFAILmessage_2(entityGroup1);
/*      */             } 
/*      */             
/*  695 */             for (byte b2 = 0; b2 < vector.size(); b2++)
/*      */             {
/*  697 */               EntityItem entityItem1 = vector.elementAt(b2);
/*  698 */               if (entityItem1 != null) {
/*  699 */                 println(displayNavAttributes(entityItem1, entityGroup1));
/*  700 */                 println(displayStatuses(entityItem1, entityGroup1));
/*      */               
/*      */               }
/*      */ 
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         }
/*  710 */         else if (entityGroup.getEntityType().equals("EDUCATION") || entityGroup
/*  711 */           .getEntityType().equals("OP") || entityGroup
/*  712 */           .getEntityType().equals("ORGANUNIT") || entityGroup
/*  713 */           .getEntityType().equals("STANDAMENDTEXT")) {
/*      */           
/*  715 */           printWARNINGmessage(entityGroup);
/*      */         } else {
/*      */           
/*  718 */           logMessage("*****We are hitting here aswell" + entityGroup);
/*  719 */           printWARNINGmessage(entityGroup);
/*      */ 
/*      */           
/*  722 */           if (entityGroup.getEntityType().equals("AVAIL")) {
/*  723 */             println("<br><br><b>There needs to be at least one Availability</b>");
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkCRstatus() {
/*  740 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CHANGEREQUEST");
/*  741 */     if (entityGroup != null) {
/*  742 */       Vector<EntityItem> vector = new Vector();
/*      */       
/*  744 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  745 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*  746 */         if (entityItem != null) {
/*  747 */           EANAttribute eANAttribute = entityItem.getAttribute(CHANGEREQUEST_STATUS);
/*      */           
/*  749 */           String str = (eANAttribute != null) ? eANAttribute.toString() : "<em>** Not Populated **</em>";
/*  750 */           if (str.equals("Approved")) {
/*  751 */             vector.addElement(entityItem);
/*  752 */             logMessage("checkCR " + entityItem.dump(true));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  757 */       if (vector != null && vector.size() > 0) {
/*  758 */         EntityItem entityItem = vector.elementAt(0);
/*  759 */         if (entityItem != null) {
/*  760 */           EntityGroup entityGroup1 = entityItem.getEntityGroup();
/*  761 */           if (entityGroup1 != null) {
/*  762 */             println("<br><br><b>The following " + entityGroup1
/*      */                 
/*  764 */                 .getLongDescription() + " are still in the Approved state</b>");
/*      */             
/*  766 */             println("<br><br><b>" + entityGroup1
/*  767 */                 .getLongDescription() + "</b>");
/*  768 */             println(displayAttributes(entityItem, entityGroup1, true));
/*      */           } 
/*      */         } 
/*  771 */         setReturnCode(-1);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlagValue(String paramString1, int paramInt, String paramString2) {
/*  788 */     String str = null;
/*      */     
/*  790 */     if (paramString2.equals(ANCYCLESTATUS)) {
/*  791 */       logMessage("****** Announcement Lifecycle Status set to: 112");
/*      */ 
/*      */       
/*  794 */       str = "112";
/*      */     }
/*      */     else {
/*      */       
/*  798 */       str = null;
/*      */     } 
/*      */     
/*  801 */     if (str != null) {
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  808 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*      */ 
/*      */ 
/*      */         
/*  812 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  819 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString2, str, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  824 */         Vector<SingleFlag> vector = new Vector();
/*  825 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*      */         
/*  827 */         if (singleFlag != null) {
/*  828 */           vector.addElement(singleFlag);
/*  829 */           returnEntityKey.m_vctAttributes = vector;
/*  830 */           vector1.addElement(returnEntityKey);
/*  831 */           this.m_db.update(this.m_prof, vector1, false, false);
/*  832 */           this.m_db.commit();
/*      */         } 
/*  834 */       } catch (MiddlewareException middlewareException) {
/*  835 */         logMessage("setFlagValue: " + middlewareException.getMessage());
/*  836 */       } catch (Exception exception) {
/*  837 */         logMessage("setFlagValue: " + exception.getMessage());
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlagValue1(String paramString1, int paramInt, String paramString2) {
/*  855 */     String str = null;
/*      */     
/*  857 */     if (paramString2.equals(ANCYCLESTATUS)) {
/*  858 */       logMessage("****** Announcement Lifecycle Status set to: 114");
/*      */ 
/*      */       
/*  861 */       str = "114";
/*      */     }
/*      */     else {
/*      */       
/*  865 */       str = null;
/*      */     } 
/*      */     
/*  868 */     if (str != null) {
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  875 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*      */ 
/*      */ 
/*      */         
/*  879 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  886 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString2, str, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  891 */         Vector<SingleFlag> vector = new Vector();
/*  892 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*      */         
/*  894 */         if (singleFlag != null) {
/*  895 */           vector.addElement(singleFlag);
/*  896 */           returnEntityKey.m_vctAttributes = vector;
/*  897 */           vector1.addElement(returnEntityKey);
/*  898 */           this.m_db.update(this.m_prof, vector1, false, false);
/*  899 */           this.m_db.commit();
/*      */         } 
/*  901 */       } catch (MiddlewareException middlewareException) {
/*  902 */         logMessage("setFlagValue1: " + middlewareException.getMessage());
/*  903 */       } catch (Exception exception) {
/*  904 */         logMessage("setFlagValue1: " + exception.getMessage());
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlagValue2(String paramString1, int paramInt, String paramString2) {
/*  922 */     String str = null;
/*      */     
/*  924 */     if (paramString2.equals(ANCYCLESTATUS)) {
/*  925 */       logMessage("****** Announcement Lifecycle Status set to: 115");
/*      */ 
/*      */       
/*  928 */       str = "115";
/*      */     }
/*      */     else {
/*      */       
/*  932 */       str = null;
/*      */     } 
/*      */     
/*  935 */     if (str != null) {
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  942 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*      */ 
/*      */ 
/*      */         
/*  946 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  953 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString2, str, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  958 */         Vector<SingleFlag> vector = new Vector();
/*  959 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*      */         
/*  961 */         if (singleFlag != null) {
/*  962 */           vector.addElement(singleFlag);
/*  963 */           returnEntityKey.m_vctAttributes = vector;
/*  964 */           vector1.addElement(returnEntityKey);
/*  965 */           this.m_db.update(this.m_prof, vector1, false, false);
/*  966 */           this.m_db.commit();
/*      */         } 
/*  968 */       } catch (MiddlewareException middlewareException) {
/*  969 */         logMessage("setFlagValue2: " + middlewareException.getMessage());
/*  970 */       } catch (Exception exception) {
/*  971 */         logMessage("setFlagValue2: " + exception.getMessage());
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlagValue3(String paramString1, int paramInt, String paramString2) {
/*  989 */     String str = null;
/*      */     
/*  991 */     if (paramString2.equals(RFAHWABR01_STATUS)) {
/*  992 */       logMessage("****** Announcement Lifecycle Status set to: 0020");
/*      */ 
/*      */       
/*  995 */       str = "0020";
/*      */     }
/*      */     else {
/*      */       
/*  999 */       str = null;
/*      */     } 
/*      */     
/* 1002 */     if (str != null) {
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1009 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*      */ 
/*      */ 
/*      */         
/* 1013 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1020 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString2, str, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1025 */         Vector<SingleFlag> vector = new Vector();
/* 1026 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*      */         
/* 1028 */         if (singleFlag != null) {
/* 1029 */           vector.addElement(singleFlag);
/* 1030 */           returnEntityKey.m_vctAttributes = vector;
/* 1031 */           vector1.addElement(returnEntityKey);
/* 1032 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 1033 */           this.m_db.commit();
/*      */         } 
/* 1035 */       } catch (MiddlewareException middlewareException) {
/* 1036 */         logMessage("setFlagValue3: " + middlewareException.getMessage());
/* 1037 */       } catch (Exception exception) {
/* 1038 */         logMessage("setFlagValue3: " + exception.getMessage());
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 1048 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1057 */     return "<br />This needs to be defined <br />";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getStyle() {
/* 1066 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRevision() {
/* 1075 */     return new String("1.28");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getVersion() {
/* 1085 */     return "ANQRFRABR01.java,v 1.28 2008/01/30 19:39:14 wendy Exp";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1093 */     return getVersion();
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ANQRFRABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */