/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Hashtable;
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
/*      */ public class CHECKABR
/*      */   extends PokBaseABR
/*      */ {
/*   50 */   public static final String ABR = new String("CHECKABR");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   57 */   public static final String EFFECTIVEDATE = new String("EFFECTIVEDATE");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   62 */   public static final String AVAILTYPE = new String("AVAILTYPE");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   67 */   public static final String GENAREASELECTION = new String("GENAREASELECTION");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   75 */   public static final String MKTGNAME = new String("MKTGNAME");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   80 */   public static final String COMPONENTID = new String("COMPONENTID");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   85 */   public static final String DESCRIPTION = new String("DESCRIPTION");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   90 */   public static final String OVERVIEWABSTRACT = new String("OVERVIEWABSTRACT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   96 */   public static final String DISTRCODE = new String("DISTRCODE");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  101 */   public static final String SVCCAT = new String("SVCCAT");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  106 */   public static final String VAE = new String("VAE");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  112 */   public static final Hashtable c_hshWarningEntities = new Hashtable<>();
/*      */ 
/*      */   
/*      */   static {
/*  116 */     c_hshWarningEntities.put("ANNOUNCEMENT", "ANNOUNCEMENT");
/*  117 */     c_hshWarningEntities.put("ANNDELIVERABLE", "ANNDELIVERABLE");
/*  118 */     c_hshWarningEntities.put("ANNPROJ", "ANNPROJ");
/*  119 */     c_hshWarningEntities.put("ANNPROJREV", "ANNPROJREV");
/*  120 */     c_hshWarningEntities.put("ANNREVIEW", "ANNREVIEW");
/*  121 */     c_hshWarningEntities.put("ANNTOCONFIG", "ANNTOCONFIG");
/*  122 */     c_hshWarningEntities.put("CATINCL", "CATINCL");
/*  123 */     c_hshWarningEntities.put("FEATURERELCMPNT", "FEATURERELCMPNT");
/*  124 */     c_hshWarningEntities.put("FEATURERELSOF", "FEATURERELSOF");
/*  125 */     c_hshWarningEntities.put("CONFIGURATOR", "CONFIGURATOR");
/*  126 */     c_hshWarningEntities.put("FINOF", "FINOF");
/*  127 */     c_hshWarningEntities.put("GENERALAREA", "GENERALAREA");
/*  128 */     c_hshWarningEntities.put("CMPNTRELFEATURE", "CMPNTRELFEATURE");
/*  129 */     c_hshWarningEntities.put("CMPNTRELCMPNT", "CMPNTRELCMPNT");
/*  130 */     c_hshWarningEntities.put("CMPNTRELSOF", "CMPNTRELSOF");
/*  131 */     c_hshWarningEntities.put("SOFRELCMPNT", "SOFRELCMPNT");
/*  132 */     c_hshWarningEntities.put("SOFRELFEATURE", "SOFRELFEATURE");
/*  133 */     c_hshWarningEntities.put("ORGANUNIT", "ORGANUNIT");
/*  134 */     c_hshWarningEntities.put("PARAMETERCODE", "PARAMETERCODE");
/*  135 */     c_hshWarningEntities.put("FEATURERELFEATURE", "FEATURERELFEATURE");
/*  136 */     c_hshWarningEntities.put("SOFRELSOF", "SOFRELSOF");
/*  137 */     c_hshWarningEntities.put("CMPNTSALESCNTCTOP", "CMPNTSALESCNTCTOP");
/*  138 */     c_hshWarningEntities.put("FEATRSALESCNTCTOP", "FEATRSALESCNTCTOP");
/*  139 */     c_hshWarningEntities.put("ANNOP", "ANNOP");
/*  140 */     c_hshWarningEntities.put("SOFADMINOP", "SOFADMINOP");
/*  141 */     c_hshWarningEntities.put("SOFSALESCNTCTOP", "SOFSALESCNTCTOP");
/*      */   }
/*      */ 
/*      */   
/*  145 */   private EntityGroup m_egParent = null;
/*  146 */   private EntityItem m_eiParent = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  154 */     StringWriter stringWriter = null;
/*  155 */     String str = null;
/*      */     
/*      */     try {
/*  158 */       start_ABRBuild();
/*  159 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  160 */       this.m_eiParent = this.m_egParent.getEntityItem(0);
/*      */       
/*  162 */       if (this.m_egParent == null) {
/*  163 */         println(ABR + ":" + 
/*      */ 
/*      */             
/*  166 */             getVersion() + ":ERROR:1: m_egParent cannot be established.");
/*      */         
/*  168 */         setReturnCode(-1);
/*      */         return;
/*      */       } 
/*  171 */       if (this.m_eiParent == null) {
/*  172 */         println(ABR + ":" + 
/*      */ 
/*      */             
/*  175 */             getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/*      */         
/*  177 */         setReturnCode(-1);
/*      */         
/*      */         return;
/*      */       } 
/*  181 */       logMessage(ABR + ":" + 
/*      */ 
/*      */           
/*  184 */           getVersion() + ":Request to Work on Entity:" + this.m_eiParent
/*      */           
/*  186 */           .getEntityType() + ":" + this.m_eiParent
/*      */           
/*  188 */           .getEntityID());
/*  189 */       buildReportHeader();
/*  190 */       setControlBlock();
/*  191 */       setDGTitle(setDGName(this.m_eiParent, ABR));
/*      */       
/*  193 */       displayHeader(this.m_egParent, this.m_eiParent);
/*      */ 
/*      */       
/*  196 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */       
/*  200 */       checkANNOUNCEMENTstatus();
/*  201 */       checkAVAILstatus();
/*  202 */       checkCHANNELstatus();
/*  203 */       checkCMPNTstatus();
/*  204 */       checkEMEATRANSLATIONstatus();
/*  205 */       checkFEATUREstatus();
/*  206 */       checkGBTstatus();
/*  207 */       checkOFDEVLPROJstatus();
/*  208 */       checkPRICEINFOstatus();
/*  209 */       checkSOFstatus();
/*  210 */       checkWarningStatus();
/*      */       
/*  212 */       logMessage(ABR + ":" + 
/*      */ 
/*      */           
/*  215 */           getVersion() + ":Setup Complete:" + this.m_eiParent
/*      */           
/*  217 */           .getEntityType() + ":" + this.m_eiParent
/*      */           
/*  219 */           .getEntityID());
/*      */       
/*  221 */       logMessage("***getReturnCode()***" + getReturnCode());
/*  222 */       if (getReturnCode() == 0) {
/*      */         
/*  224 */         this.m_prof.setValOn(getValOn());
/*  225 */         this.m_prof.setEffOn(getEffOn());
/*  226 */         start_ABRBuild();
/*      */       } 
/*      */       
/*  229 */       println("<br /><b>" + 
/*      */           
/*  231 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*      */ 
/*      */               
/*  234 */               getABRDescription(), 
/*  235 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*      */             }) + "</b>");
/*      */       
/*  238 */       log(
/*  239 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*      */ 
/*      */               
/*  242 */               getABRDescription(), 
/*  243 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*      */             }));
/*  245 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  246 */       setReturnCode(-2);
/*  247 */       println("<h3>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*      */ 
/*      */ 
/*      */           
/*  251 */           .getMessage() + "</font></h3>");
/*      */       
/*  253 */       logError(lockPDHEntityException.getMessage());
/*  254 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  255 */       setReturnCode(-2);
/*  256 */       println("<h3>UpdatePDH error: " + updatePDHEntityException.getMessage() + "</font></h3>");
/*  257 */       logError(updatePDHEntityException.getMessage());
/*  258 */     } catch (Exception exception) {
/*      */       
/*  260 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/*  261 */       println("" + exception);
/*      */ 
/*      */       
/*  264 */       if (getABRReturnCode() != -2) {
/*  265 */         setReturnCode(-3);
/*      */       }
/*      */       
/*  268 */       exception.printStackTrace();
/*      */       
/*  270 */       stringWriter = new StringWriter();
/*  271 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*  272 */       str = stringWriter.toString();
/*  273 */       println(str);
/*      */       
/*  275 */       logMessage("Error in " + this.m_abri
/*  276 */           .getABRCode() + ":" + exception.getMessage());
/*  277 */       logMessage("" + exception);
/*      */     }
/*      */     finally {
/*      */       
/*  281 */       if (getReturnCode() == 0) {
/*      */         
/*  283 */         setReturnCode(0);
/*      */         
/*  285 */         setDGString(getABRReturnCode());
/*  286 */         setDGRptName("CHECKABR");
/*  287 */         setDGRptClass("CHECKABR");
/*  288 */         printDGSubmitString();
/*      */ 
/*      */         
/*  291 */         buildReportFooter();
/*      */         
/*  293 */         if (!isReadOnly()) {
/*  294 */           clearSoftLock();
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
/*      */   public void printFAILmessage(EntityGroup paramEntityGroup) {
/*  307 */     println("<br><br><b>" + paramEntityGroup
/*      */         
/*  309 */         .getLongDescription() + " is not at final status.</b>");
/*      */     
/*  311 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printWARNINGmessage(EntityGroup paramEntityGroup) {
/*  321 */     println("<br><br><b>" + paramEntityGroup
/*      */         
/*  323 */         .getLongDescription() + " does not exist (Warning - Pass)</b>");
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
/*  335 */     println("<br><br><b>" + paramEntityGroup
/*      */         
/*  337 */         .getLongDescription() + " needs to be 'Final'status</b>");
/*      */     
/*  339 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printWARNINGmessage_2(EntityGroup paramEntityGroup) {
/*  349 */     println("<br><br><b>" + paramEntityGroup
/*      */         
/*  351 */         .getLongDescription() + " needs to be 'Final'status (Warning - Pass)</b>");
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
/*  363 */     println("<br><br><b>" + paramEntityGroup
/*      */         
/*  365 */         .getLongDescription() + " needs to be at least 'Ready for Final Review'</b>");
/*      */     
/*  367 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printWARNINGmessage_3(EntityGroup paramEntityGroup) {
/*  377 */     println("<br><br><b>" + paramEntityGroup
/*      */         
/*  379 */         .getLongDescription() + " needs to be at least 'Ready for Final Review' (Warning - Pass)</b>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkWarningStatus() {
/*  390 */     EntityGroup entityGroup = null;
/*      */     
/*  392 */     Collection collection = c_hshWarningEntities.values();
/*  393 */     String[] arrayOfString = (String[])collection.toArray((Object[])new String[collection.size()]);
/*  394 */     Arrays.sort(arrayOfString, new myComparator());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  399 */     println("<br><br><b><U>Missing Forms</b></U>:");
/*      */     
/*  401 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*  402 */       System.out.println(b + " of " + arrayOfString.length + " is: " + arrayOfString[b]);
/*  403 */       entityGroup = this.m_elist.getEntityGroup(arrayOfString[b]);
/*      */       
/*  405 */       if (entityGroup != null && entityGroup.getEntityItemCount() == 0)
/*      */       {
/*      */         
/*  408 */         println("<br>" + entityGroup.getLongDescription());
/*      */       }
/*  410 */       if (entityGroup == null || entityGroup.getEntityItemCount() <= 0)
/*      */       {
/*      */ 
/*      */         
/*  414 */         System.out.println("This Entity is not included in the Entity Checklist " + arrayOfString[b]);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class myComparator
/*      */     implements Comparator
/*      */   {
/*      */     private myComparator() {}
/*      */ 
/*      */     
/*      */     public int compare(Object param1Object1, Object param1Object2) {
/*  428 */       EntityGroup entityGroup1 = null;
/*  429 */       EntityGroup entityGroup2 = null;
/*  430 */       String str1 = null;
/*  431 */       String str2 = null;
/*  432 */       if (param1Object1 == null || param1Object2 == null) {
/*  433 */         return -1;
/*      */       }
/*  435 */       entityGroup1 = CHECKABR.this.m_elist.getEntityGroup(param1Object1.toString());
/*  436 */       entityGroup2 = CHECKABR.this.m_elist.getEntityGroup(param1Object2.toString());
/*  437 */       str1 = (entityGroup1 == null) ? "" : entityGroup1.getLongDescription();
/*  438 */       str2 = (entityGroup2 == null) ? "" : entityGroup2.getLongDescription();
/*  439 */       return str1.compareToIgnoreCase(str2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkANNOUNCEMENTstatus() {
/*  449 */     this.m_egParent = this.m_elist.getParentEntityGroup();
/*  450 */     this.m_eiParent = this.m_egParent.getEntityItem(0);
/*      */     
/*  452 */     if (this.m_egParent == null) {
/*  453 */       logMessage("********** 1 Announcement Not found");
/*  454 */       setReturnCode(-1);
/*      */     } else {
/*      */       
/*  457 */       for (byte b = 0; b < this.m_egParent.getEntityItemCount(); b++) {
/*      */         
/*  459 */         EntityItem entityItem = this.m_egParent.getEntityItem(b);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  464 */         if (entityItem != null) {
/*  465 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[8];
/*  466 */           arrayOfEANAttribute[0] = entityItem.getAttribute("ANNCODENAMEU");
/*  467 */           arrayOfEANAttribute[1] = entityItem.getAttribute("ANNDATE");
/*  468 */           arrayOfEANAttribute[2] = entityItem.getAttribute("ANNNUMBER");
/*  469 */           arrayOfEANAttribute[3] = entityItem.getAttribute("ANNTITLE");
/*  470 */           arrayOfEANAttribute[4] = entityItem.getAttribute("ANNTYPE");
/*  471 */           arrayOfEANAttribute[5] = entityItem.getAttribute("ATAGLANCE");
/*  472 */           arrayOfEANAttribute[6] = entityItem.getAttribute("DESCRIPTION");
/*  473 */           arrayOfEANAttribute[7] = entityItem.getAttribute("OVERVIEWABSTRACT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  483 */           if (arrayOfEANAttribute[0] == null || arrayOfEANAttribute[1] == null || arrayOfEANAttribute[2] == null || arrayOfEANAttribute[3] == null || arrayOfEANAttribute[4] == null || arrayOfEANAttribute[5] == null || arrayOfEANAttribute[6] == null || arrayOfEANAttribute[7] == null)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  491 */             println("<br><br><b>" + this.m_egParent
/*      */                 
/*  493 */                 .getLongDescription() + " Entity ID: " + entityItem
/*      */                 
/*  495 */                 .getEntityID() + " " + arrayOfEANAttribute[2] + " - Missing Data</b>");
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  501 */           if (arrayOfEANAttribute[0] == null) {
/*  502 */             println("<br>Announcement Code Name");
/*  503 */             setReturnCode(-1);
/*      */           } 
/*  505 */           if (arrayOfEANAttribute[1] == null) {
/*  506 */             println("<br>Announce Date");
/*  507 */             setReturnCode(-1);
/*      */           } 
/*  509 */           if (arrayOfEANAttribute[2] == null) {
/*  510 */             println("<br>Announcement Number");
/*  511 */             setReturnCode(-1);
/*      */           } 
/*  513 */           if (arrayOfEANAttribute[3] == null) {
/*  514 */             println("<br>Title");
/*  515 */             setReturnCode(-1);
/*      */           } 
/*  517 */           if (arrayOfEANAttribute[4] == null) {
/*  518 */             println("<br>Announcement Type");
/*  519 */             setReturnCode(-1);
/*      */           } 
/*  521 */           if (arrayOfEANAttribute[5] == null) {
/*  522 */             println("<br>At A Glance");
/*  523 */             setReturnCode(-1);
/*      */           } 
/*  525 */           if (arrayOfEANAttribute[6] == null) {
/*  526 */             println("<br>Description");
/*  527 */             setReturnCode(-1);
/*      */           } 
/*  529 */           if (arrayOfEANAttribute[7] == null) {
/*  530 */             println("<br>Overview/Abstract");
/*  531 */             setReturnCode(-1);
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
/*      */   public void checkAVAILstatus() {
/*  544 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*  545 */     String str = null;
/*  546 */     if (entityGroup != null)
/*      */     {
/*  548 */       if (entityGroup.getEntityItemCount() == 0) {
/*  549 */         println("<br><br><b>" + entityGroup
/*      */             
/*  551 */             .getLongDescription() + " - Missing Data</b>");
/*      */         
/*  553 */         println("<br>Effective Date");
/*  554 */         setReturnCode(-1);
/*      */       } else {
/*      */         
/*  557 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  558 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*  559 */           if (entityItem != null) {
/*  560 */             EANAttribute[] arrayOfEANAttribute = new EANAttribute[2];
/*  561 */             arrayOfEANAttribute[0] = entityItem.getAttribute("EFFECTIVEDATE");
/*  562 */             arrayOfEANAttribute[1] = entityItem.getAttribute("COMNAME");
/*      */ 
/*      */             
/*  565 */             str = arrayOfEANAttribute[0].toString();
/*      */             
/*  567 */             if (str.equals("")) {
/*  568 */               println("<br><br><b>" + entityGroup
/*      */                   
/*  570 */                   .getLongDescription() + " Entity ID: " + entityItem
/*      */                   
/*  572 */                   .getEntityID() + " " + arrayOfEANAttribute[1] + " - Missing Data</b>");
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  577 */               println("<br>Effective Date");
/*  578 */               setReturnCode(-1);
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
/*  592 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CHANNEL");
/*  593 */     if (entityGroup != null) {
/*      */       
/*  595 */       logMessage("_eg.getEntityItemCount() for CHANNEL:" + entityGroup
/*      */           
/*  597 */           .getEntityItemCount());
/*  598 */       if (entityGroup.getEntityItemCount() == 0) {
/*  599 */         println("<br><br><b>" + entityGroup
/*      */             
/*  601 */             .getLongDescription() + " - Missing Data</b>");
/*      */         
/*  603 */         println("<br>Channel Name");
/*  604 */         println("<br>Channel Type");
/*  605 */         setReturnCode(-1);
/*      */       } else {
/*  607 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */           
/*  609 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */ 
/*      */           
/*  613 */           if (entityItem != null) {
/*  614 */             EANAttribute[] arrayOfEANAttribute = new EANAttribute[3];
/*  615 */             arrayOfEANAttribute[0] = entityItem.getAttribute("CHANNELNAME");
/*  616 */             arrayOfEANAttribute[1] = entityItem.getAttribute("CHANNELTYPE");
/*      */ 
/*      */ 
/*      */             
/*  620 */             if (arrayOfEANAttribute[0] == null || arrayOfEANAttribute[1] == null) {
/*  621 */               println("<br><br><b>" + entityGroup
/*      */                   
/*  623 */                   .getLongDescription() + " Entity ID: " + entityItem
/*      */                   
/*  625 */                   .getEntityID() + " " + arrayOfEANAttribute[0] + " - Missing Data</b>");
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  631 */             if (arrayOfEANAttribute[0] == null) {
/*  632 */               println("<br>Channel Name");
/*  633 */               setReturnCode(-1);
/*      */             } 
/*  635 */             if (arrayOfEANAttribute[1] == null) {
/*  636 */               println("<br>Channel Type");
/*  637 */               setReturnCode(-1);
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
/*  651 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CMPNT");
/*      */     
/*  653 */     if (entityGroup != null)
/*      */     {
/*  655 */       if (entityGroup.getEntityItemCount() == 0) {
/*  656 */         println("<br><br><b>" + entityGroup
/*      */             
/*  658 */             .getLongDescription() + " - Missing Data</b>");
/*      */         
/*  660 */         println("<br>Component Id");
/*  661 */         println("<br>Description");
/*  662 */         println("<br>Marketing Name");
/*  663 */         println("<br>Overview/Abstract");
/*  664 */         setReturnCode(-1);
/*      */       } else {
/*  666 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */           
/*  668 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  673 */           if (entityItem != null) {
/*  674 */             EANAttribute[] arrayOfEANAttribute = new EANAttribute[5];
/*  675 */             arrayOfEANAttribute[0] = entityItem.getAttribute("COMPONENTID");
/*  676 */             arrayOfEANAttribute[1] = entityItem.getAttribute("DESCRIPTION");
/*  677 */             arrayOfEANAttribute[2] = entityItem.getAttribute("MKTGNAME");
/*  678 */             arrayOfEANAttribute[3] = entityItem.getAttribute("OVERVIEWABSTRACT");
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  683 */             if (arrayOfEANAttribute[0] == null || arrayOfEANAttribute[1] == null || arrayOfEANAttribute[2] == null || arrayOfEANAttribute[3] == null)
/*      */             {
/*      */ 
/*      */               
/*  687 */               println("<br><br><b>" + entityGroup
/*      */                   
/*  689 */                   .getLongDescription() + " Entity ID: " + entityItem
/*      */                   
/*  691 */                   .getEntityID() + " " + arrayOfEANAttribute[2] + " - Missing Data</b>");
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  697 */             if (arrayOfEANAttribute[0] == null) {
/*  698 */               println("<br>Component Id");
/*  699 */               setReturnCode(-1);
/*      */             } 
/*  701 */             if (arrayOfEANAttribute[1] == null) {
/*  702 */               println("<br>Description");
/*  703 */               setReturnCode(-1);
/*      */             } 
/*  705 */             if (arrayOfEANAttribute[2] == null) {
/*  706 */               println("<br>Marketing Name");
/*  707 */               setReturnCode(-1);
/*      */             } 
/*  709 */             if (arrayOfEANAttribute[3] == null) {
/*  710 */               println("<br>Overview/Abstract");
/*  711 */               setReturnCode(-1);
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
/*      */   public void checkEMEATRANSLATIONstatus() {
/*  725 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("EMEATRANSLATION");
/*  726 */     if (entityGroup != null) {
/*      */       
/*  728 */       logMessage("_eg.getEntityItemCount() for EMEATRANSLATION:" + entityGroup
/*      */           
/*  730 */           .getEntityItemCount());
/*  731 */       if (entityGroup.getEntityItemCount() == 0) {
/*  732 */         println("<br><br><b>" + entityGroup
/*      */             
/*  734 */             .getLongDescription() + " - Missing Data</b>");
/*      */         
/*  736 */         println("<br>Language");
/*  737 */         println("<br>Proposal Insert ID");
/*  738 */         setReturnCode(-1);
/*      */       } else {
/*  740 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */           
/*  742 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */ 
/*      */           
/*  746 */           if (entityItem != null) {
/*  747 */             EANAttribute[] arrayOfEANAttribute = new EANAttribute[3];
/*  748 */             arrayOfEANAttribute[0] = entityItem.getAttribute("LANGUAGES");
/*  749 */             arrayOfEANAttribute[1] = entityItem.getAttribute("PROPOSALINSERTID");
/*  750 */             arrayOfEANAttribute[2] = entityItem.getAttribute("PROPOSALINSERTID");
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  755 */             if (arrayOfEANAttribute[0] == null || arrayOfEANAttribute[1] == null) {
/*  756 */               println("<br><br><b>" + entityGroup
/*      */                   
/*  758 */                   .getLongDescription() + " Entity ID: " + entityItem
/*      */                   
/*  760 */                   .getEntityID() + " " + arrayOfEANAttribute[2] + " - Missing Data</b>");
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  766 */             if (arrayOfEANAttribute[0] == null) {
/*  767 */               println("<br>Language");
/*  768 */               setReturnCode(-1);
/*      */             } 
/*  770 */             if (arrayOfEANAttribute[1] == null) {
/*  771 */               println("<br>Proposal Insert ID");
/*  772 */               setReturnCode(-1);
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
/*  786 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("FEATURE");
/*      */     
/*  788 */     if (entityGroup != null)
/*      */     {
/*  790 */       if (entityGroup.getEntityItemCount() == 0) {
/*  791 */         println("<br><br><b>" + entityGroup
/*      */             
/*  793 */             .getLongDescription() + " - Missing Data</b>");
/*      */         
/*  795 */         println("<br>Description");
/*  796 */         println("<br>Feature Number");
/*  797 */         println("<br>Marketing Name");
/*  798 */         println("<br>Overview/Abstract");
/*  799 */         setReturnCode(-1);
/*      */       } else {
/*  801 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */           
/*  803 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  808 */           if (entityItem != null) {
/*  809 */             EANAttribute[] arrayOfEANAttribute = new EANAttribute[4];
/*  810 */             arrayOfEANAttribute[0] = entityItem.getAttribute("DESCRIPTION");
/*  811 */             arrayOfEANAttribute[1] = entityItem.getAttribute("FEATURENUMBER");
/*  812 */             arrayOfEANAttribute[2] = entityItem.getAttribute("MKTGNAME");
/*  813 */             arrayOfEANAttribute[3] = entityItem.getAttribute("OVERVIEWABSTRACT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  819 */             if (arrayOfEANAttribute[0] == null || arrayOfEANAttribute[1] == null || arrayOfEANAttribute[2] == null || arrayOfEANAttribute[3] == null)
/*      */             {
/*      */ 
/*      */               
/*  823 */               println("<br><br><b>" + entityGroup
/*      */                   
/*  825 */                   .getLongDescription() + " Entity ID: " + entityItem
/*      */                   
/*  827 */                   .getEntityID() + " " + arrayOfEANAttribute[2] + " - Missing Data</b>");
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  833 */             if (arrayOfEANAttribute[0] == null) {
/*  834 */               println("<br>Description");
/*  835 */               setReturnCode(-1);
/*      */             } 
/*  837 */             if (arrayOfEANAttribute[1] == null) {
/*  838 */               println("<br>Feature Number");
/*  839 */               setReturnCode(-1);
/*      */             } 
/*  841 */             if (arrayOfEANAttribute[2] == null) {
/*  842 */               println("<br>Marketing Name");
/*  843 */               setReturnCode(-1);
/*      */             } 
/*  845 */             if (arrayOfEANAttribute[3] == null) {
/*  846 */               println("<br>Overview/Abstract");
/*  847 */               setReturnCode(-1);
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
/*      */   public void checkGBTstatus() {
/*  861 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("GBT");
/*  862 */     if (entityGroup != null) {
/*      */       
/*  864 */       logMessage("_eg.getEntityItemCount() for GBT:" + entityGroup
/*  865 */           .getEntityItemCount());
/*  866 */       if (entityGroup.getEntityItemCount() == 0) {
/*  867 */         println("<br><br><b>" + entityGroup
/*      */             
/*  869 */             .getLongDescription() + " - Missing Data</b>");
/*      */         
/*  871 */         println("<br>SAP Primary Brand Code");
/*  872 */         println("<br>SAP Product Family Code");
/*  873 */         setReturnCode(-1);
/*      */       } else {
/*  875 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */           
/*  877 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */ 
/*      */           
/*  881 */           if (entityItem != null) {
/*  882 */             EANAttribute[] arrayOfEANAttribute = new EANAttribute[3];
/*  883 */             arrayOfEANAttribute[0] = entityItem.getAttribute("SAPPRIMBRANDCODE");
/*  884 */             arrayOfEANAttribute[1] = entityItem.getAttribute("SAPPRODFAMCODE");
/*  885 */             arrayOfEANAttribute[2] = entityItem.getAttribute("GBNAME");
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  890 */             if (arrayOfEANAttribute[0] == null || arrayOfEANAttribute[1] == null) {
/*  891 */               println("<br><br><b>" + entityGroup
/*      */                   
/*  893 */                   .getLongDescription() + " Entity ID: " + entityItem
/*      */                   
/*  895 */                   .getEntityID() + " " + arrayOfEANAttribute[2] + " - Missing Data</b>");
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  901 */             if (arrayOfEANAttribute[0] == null) {
/*  902 */               println("<br>SAP Primary Brand Code");
/*  903 */               setReturnCode(-1);
/*      */             } 
/*  905 */             if (arrayOfEANAttribute[1] == null) {
/*  906 */               println("<br>SAP Product Family Code");
/*  907 */               setReturnCode(-1);
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
/*      */   public void checkOFDEVLPROJstatus() {
/*  921 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OFDEVLPROJ");
/*  922 */     if (entityGroup != null)
/*      */     {
/*  924 */       if (entityGroup.getEntityItemCount() == 0) {
/*  925 */         println("<br><br><b>" + entityGroup
/*      */             
/*  927 */             .getLongDescription() + " - Missing Data</b>");
/*      */         
/*  929 */         println("<br>Project Number");
/*  930 */         setReturnCode(-1);
/*      */       } else {
/*      */         
/*  933 */         logMessage("****_eg.getEntityItemCount() for OFDEVLPROJ:" + entityGroup
/*      */             
/*  935 */             .getEntityItemCount());
/*  936 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  937 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*  938 */           if (entityItem != null) {
/*  939 */             EANAttribute eANAttribute1 = entityItem.getAttribute("PROJNUMBER");
/*  940 */             EANAttribute eANAttribute2 = entityItem.getAttribute("NAME");
/*      */             
/*  942 */             if (eANAttribute1 == null) {
/*  943 */               println("<br><br><b>" + entityGroup
/*      */                   
/*  945 */                   .getLongDescription() + " Entity ID: " + entityItem
/*      */                   
/*  947 */                   .getEntityID() + " " + eANAttribute2 + " - Missing Data</b>");
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  952 */               println("<br>Project Number");
/*  953 */               setReturnCode(-1);
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
/*  967 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("PRICEFININFO");
/*      */     
/*  969 */     if (entityGroup != null) {
/*      */       
/*  971 */       logMessage("_eg.getEntityItemCount() for PRICEFININFO:" + entityGroup
/*      */           
/*  973 */           .getEntityItemCount());
/*  974 */       if (entityGroup.getEntityItemCount() == 0) {
/*  975 */         println("<br><br><b>" + entityGroup
/*      */             
/*  977 */             .getLongDescription() + " - Missing Data</b>");
/*      */         
/*  979 */         println("<br>Billing Application");
/*  980 */         println("<br>Charges");
/*  981 */         println("<br>Contract Close Fee");
/*  982 */         println("<br>L/P Fee");
/*  983 */         println("<br>Remarketing Discount");
/*  984 */         setReturnCode(-1);
/*      */       } else {
/*  986 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */           
/*  988 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  993 */           if (entityItem != null) {
/*  994 */             EANAttribute[] arrayOfEANAttribute = new EANAttribute[6];
/*  995 */             arrayOfEANAttribute[0] = entityItem.getAttribute("BILLINGAPP");
/*  996 */             arrayOfEANAttribute[1] = entityItem.getAttribute("CHARGES");
/*  997 */             arrayOfEANAttribute[2] = entityItem.getAttribute("CONTRACTCLOSEFEE");
/*  998 */             arrayOfEANAttribute[3] = entityItem.getAttribute("LPFEE");
/*  999 */             arrayOfEANAttribute[4] = entityItem.getAttribute("REMKTGDISCOUNT");
/* 1000 */             arrayOfEANAttribute[5] = entityItem.getAttribute("COMNAME");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1007 */             if (arrayOfEANAttribute[0] == null || arrayOfEANAttribute[1] == null || arrayOfEANAttribute[2] == null || arrayOfEANAttribute[3] == null || arrayOfEANAttribute[4] == null)
/*      */             {
/*      */ 
/*      */ 
/*      */               
/* 1012 */               println("<br><br><b>" + entityGroup
/*      */                   
/* 1014 */                   .getLongDescription() + " Entity ID: " + entityItem
/*      */                   
/* 1016 */                   .getEntityID() + " " + arrayOfEANAttribute[5] + " - Missing Data</b>");
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1022 */             if (arrayOfEANAttribute[0] == null) {
/* 1023 */               println("<br>Billing Application");
/* 1024 */               setReturnCode(-1);
/*      */             } 
/* 1026 */             if (arrayOfEANAttribute[1] == null) {
/* 1027 */               println("<br>Charges");
/* 1028 */               setReturnCode(-1);
/*      */             } 
/* 1030 */             if (arrayOfEANAttribute[2] == null) {
/* 1031 */               println("<br>Contract Close Fee");
/* 1032 */               setReturnCode(-1);
/*      */             } 
/* 1034 */             if (arrayOfEANAttribute[3] == null) {
/* 1035 */               println("<br>L/P Fee");
/* 1036 */               setReturnCode(-1);
/*      */             } 
/* 1038 */             if (arrayOfEANAttribute[4] == null) {
/* 1039 */               println("<br>Remarketing Discount");
/* 1040 */               setReturnCode(-1);
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
/* 1054 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SOF");
/*      */     
/* 1056 */     if (entityGroup != null)
/*      */     {
/* 1058 */       if (entityGroup.getEntityItemCount() == 0) {
/* 1059 */         println("<br><br><b>" + entityGroup
/*      */             
/* 1061 */             .getLongDescription() + " - Missing Data</b>");
/*      */         
/* 1063 */         println("<br>Description");
/* 1064 */         println("<br>Marketing Name");
/* 1065 */         println("<br>Offering Identification Number");
/* 1066 */         println("<br>Overview/Abstract");
/* 1067 */         setReturnCode(-1);
/*      */       } else {
/*      */         
/* 1070 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */           
/* 1072 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1077 */           if (entityItem != null) {
/* 1078 */             EANAttribute[] arrayOfEANAttribute = new EANAttribute[4];
/* 1079 */             arrayOfEANAttribute[0] = entityItem.getAttribute("DESCRIPTION");
/* 1080 */             arrayOfEANAttribute[1] = entityItem.getAttribute("MKTGNAME");
/* 1081 */             arrayOfEANAttribute[2] = entityItem.getAttribute("OFIDNUMBER");
/* 1082 */             arrayOfEANAttribute[3] = entityItem.getAttribute("OVERVIEWABSTRACT");
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1087 */             if (arrayOfEANAttribute[0] == null || arrayOfEANAttribute[1] == null || arrayOfEANAttribute[2] == null || arrayOfEANAttribute[3] == null)
/*      */             {
/*      */ 
/*      */               
/* 1091 */               println("<br><br><b>" + entityGroup
/*      */                   
/* 1093 */                   .getLongDescription() + " Entity ID: " + entityItem
/*      */                   
/* 1095 */                   .getEntityID() + arrayOfEANAttribute[1] + " - Missing Data</b>");
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 1100 */             if (arrayOfEANAttribute[0] == null) {
/* 1101 */               println("<br>Description");
/* 1102 */               setReturnCode(-1);
/*      */             } 
/* 1104 */             if (arrayOfEANAttribute[1] == null) {
/* 1105 */               println("<br>Marketing Name");
/* 1106 */               setReturnCode(-1);
/*      */             } 
/* 1108 */             if (arrayOfEANAttribute[2] == null) {
/* 1109 */               println("<br>Offering Identification Number");
/* 1110 */               setReturnCode(-1);
/*      */             } 
/* 1112 */             if (arrayOfEANAttribute[3] == null) {
/* 1113 */               println("<br>Overview/Abstract");
/* 1114 */               setReturnCode(-1);
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
/*      */   
/*      */   public void displayHeader(EntityGroup paramEntityGroup, EntityItem paramEntityItem) {
/* 1129 */     if (paramEntityGroup != null && paramEntityGroup != null) {
/* 1130 */       println("<FONT SIZE=6><b>" + paramEntityGroup
/*      */           
/* 1132 */           .getLongDescription() + "</b></FONT><br>");
/*      */ 
/*      */       
/* 1135 */       println(displayNavAttributes(paramEntityItem, paramEntityGroup));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setControlBlock() {
/* 1143 */     this.m_strNow = getNow();
/* 1144 */     this.m_strForever = getForever();
/* 1145 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1152 */       .m_cbOn = new ControlBlock(this.m_strNow, this.m_strForever, this.m_strNow, this.m_strForever, this.m_prof.getOPWGID(), this.m_prof.getTranID());
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
/*      */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 1164 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1175 */     return "<b>Announcement Missing Data and Forms Report</b>";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getStyle() {
/* 1186 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRevision() {
/* 1195 */     return new String("1.7");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getVersion() {
/* 1205 */     return "CHECKABR.java,v 1.7 2008/01/30 19:39:15 wendy Exp";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1215 */     return getVersion();
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\CHECKABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */