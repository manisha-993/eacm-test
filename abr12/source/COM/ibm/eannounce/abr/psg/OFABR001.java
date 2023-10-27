/*      */ package COM.ibm.eannounce.abr.psg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.middleware.SortUtil;
/*      */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TreeSet;
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
/*      */ public final class OFABR001
/*      */   extends PokBaseABR
/*      */ {
/*  102 */   private MessageFormat mfOut = null;
/*  103 */   private Object[] mfParms = (Object[])new String[10];
/*  104 */   private ResourceBundle msgs = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  124 */   private StringBuffer rpt = new StringBuffer();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  130 */     EntityGroup entityGroup = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  139 */     int i = 0;
/*  140 */     int j = 0;
/*  141 */     int k = 0;
/*  142 */     String str1 = null;
/*  143 */     String str2 = null;
/*  144 */     String str3 = null;
/*  145 */     String str4 = null;
/*  146 */     String str5 = null;
/*  147 */     String str6 = null;
/*  148 */     String str7 = null;
/*      */     
/*  150 */     StringBuffer stringBuffer = new StringBuffer();
/*  151 */     boolean bool = true;
/*      */     try {
/*  153 */       start_ABRBuild();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  160 */       entityGroup = new EntityGroup(null, this.m_db, this.m_prof, getRootEntityType(), "Navigate");
/*      */       
/*  162 */       Iterator<EANMetaAttribute> iterator = entityGroup.getMetaAttribute().values().iterator();
/*  163 */       while (iterator.hasNext()) {
/*  164 */         EANMetaAttribute eANMetaAttribute = iterator.next();
/*  165 */         stringBuffer.append(
/*  166 */             getAttributeValue(
/*  167 */               getRootEntityType(), 
/*  168 */               getRootEntityID(), eANMetaAttribute
/*  169 */               .getAttributeCode()));
/*  170 */         if (iterator.hasNext()) {
/*  171 */           stringBuffer.append(" ");
/*      */         }
/*      */       } 
/*  174 */       this
/*  175 */         .msgs = ResourceBundle.getBundle(
/*  176 */           getClass().getName(), 
/*  177 */           getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*  178 */       this.mfParms = (Object[])new String[10];
/*      */ 
/*      */       
/*  181 */       this.mfOut = new MessageFormat(this.msgs.getString("PATH"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  187 */       Iterator<EntityItem> iterator1 = this.m_elist.getEntityGroup("PR").getEntityItem().values().iterator();
/*  188 */       while (iterator1.hasNext()) {
/*  189 */         EntityItem entityItem = iterator1.next();
/*  190 */         this.mfParms[0] = 
/*  191 */           getAttributeValue(entityItem
/*  192 */             .getEntityType(), entityItem
/*  193 */             .getEntityID(), "BRANDCODE");
/*      */         
/*  195 */         this.mfParms[1] = 
/*  196 */           getAttributeValue(entityItem
/*  197 */             .getEntityType(), entityItem
/*  198 */             .getEntityID(), "FAMNAMEASSOC");
/*      */         
/*  200 */         this.mfParms[2] = 
/*  201 */           getAttributeValue(entityItem
/*  202 */             .getEntityType(), entityItem
/*  203 */             .getEntityID(), "SENAMEASSOC");
/*      */         
/*  205 */         this.mfParms[3] = 
/*  206 */           getAttributeValue(entityItem
/*  207 */             .getEntityType(), entityItem
/*  208 */             .getEntityID(), "NAME");
/*      */         
/*  210 */         this.rpt.append(this.mfOut.format(this.mfParms));
/*      */       } 
/*      */       
/*  213 */       i = getRootEntityID();
/*  214 */       str1 = getRootEntityType();
/*      */ 
/*      */       
/*  217 */       printEntity(str1, i, 1);
/*      */ 
/*      */       
/*  220 */       this.mfOut = new MessageFormat(this.msgs.getString("PARENT_STATUS"));
/*  221 */       Vector<Integer> vector = getParentEntityIds(str1, i, "PR", "PROF");
/*  222 */       Iterator<Integer> iterator2 = vector.iterator();
/*  223 */       while (iterator2.hasNext()) {
/*  224 */         int m = ((Integer)iterator2.next()).intValue();
/*  225 */         this.mfParms[0] = getAttributeDescription("PR", "NAME");
/*  226 */         this.mfParms[1] = getAttributeValue("PR", m, "NAME");
/*  227 */         this.mfParms[2] = getAttributeDescription("PR", "PROJECTPHASE");
/*  228 */         this.mfParms[3] = getAttributeValue("PR", m, "PROJECTPHASE");
/*  229 */         this.rpt.append(this.mfOut.format(this.mfParms));
/*      */       } 
/*      */       
/*  232 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  237 */       str4 = getAttributeFlagEnabledValue(str1, i, "OFFERINGTYPE", "");
/*      */       
/*  239 */       if (vector.size() > 0)
/*      */       {
/*  241 */         str5 = getAttributeFlagEnabledValue("PR", ((Integer)vector
/*      */             
/*  243 */             .firstElement()).intValue(), "BRANDCODE", "");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  248 */       str6 = getAttributeFlagEnabledValue(str1, i, "STATUS", "");
/*  249 */       if ("0080".equals(str4)) {
/*      */         
/*  251 */         this.m_elist.getProfile().setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*      */         
/*  253 */         Vector<Integer> vector1 = getChildrenEntityIds(str1, i, "DD", "OFDD");
/*  254 */         j = -1;
/*  255 */         str7 = "";
/*  256 */         if (!vector1.isEmpty()) {
/*  257 */           j = ((Integer)vector1.elementAt(0)).intValue();
/*      */           
/*  259 */           str7 = getAttributeValue("DD", j, "RAMSLOTSAVAIL", "");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  269 */         Iterator<EntityItem> iterator3 = this.m_elist.getEntityGroup("MB").getEntityItem().values().iterator();
/*  270 */         str2 = "";
/*  271 */         str3 = "";
/*  272 */         if (iterator3.hasNext()) {
/*  273 */           EntityItem entityItem = iterator3.next();
/*      */           
/*  275 */           str2 = getAttributeValue("MB", entityItem
/*      */               
/*  277 */               .getEntityID(), "RAMSLOTSTOT", "");
/*      */ 
/*      */ 
/*      */           
/*  281 */           str3 = getAttributeValue("MB", entityItem
/*      */               
/*  283 */               .getEntityID(), "OPTRAMCONFIG", "");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  295 */         Iterator<EntityItem> iterator4 = this.m_elist.getEntityGroup("MEM").getEntityItem().values().iterator();
/*      */         
/*  297 */         if (!iterator4.hasNext()) {
/*  298 */           this.rpt.append(this.msgs.getString("CHECK2_FAIL_MSG1"));
/*  299 */           setReturnCode(-1);
/*      */         } else {
/*  301 */           boolean bool1 = true;
/*  302 */           while (iterator4.hasNext()) {
/*  303 */             EntityItem entityItem = iterator4.next();
/*      */             
/*  305 */             String str = getAttributeValue(entityItem
/*  306 */                 .getEntityType(), entityItem
/*  307 */                 .getEntityID(), "MEMORYFORMFACTOR", "");
/*      */ 
/*      */ 
/*      */             
/*  311 */             int m = str.indexOf(' ');
/*  312 */             if (m > 0)
/*      */             {
/*  314 */               str = str.substring(0, m);
/*      */             }
/*  316 */             bool = true;
/*      */ 
/*      */             
/*  319 */             bool = searchForWord(str2, str);
/*  320 */             bool1 &= bool;
/*  321 */             if (j > -1) {
/*      */               
/*  323 */               bool = searchForWord(str7, str);
/*      */ 
/*      */               
/*  326 */               bool1 &= bool;
/*      */             } 
/*      */             
/*  329 */             if ("10015".equals(str5)) {
/*      */               
/*  331 */               bool = searchForWord(str3, str);
/*      */ 
/*      */               
/*  334 */               bool1 &= bool;
/*      */             } 
/*      */           } 
/*  337 */           if (bool1) {
/*  338 */             this.rpt.append(this.msgs.getString("CHECK2_PASS_MSG"));
/*      */           } else {
/*  340 */             if ("10015".equals(str5)) {
/*  341 */               this.rpt.append(this.msgs.getString("CHECK2_FAIL_MSG3"));
/*      */             } else {
/*  343 */               this.rpt.append(this.msgs.getString("CHECK2_FAIL_MSG2"));
/*      */             } 
/*  345 */             setReturnCode(-1);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  351 */       if ("0080".equals(str4)) {
/*      */         
/*  353 */         TreeSet<String> treeSet1 = new TreeSet();
/*  354 */         TreeSet<String> treeSet2 = new TreeSet();
/*  355 */         TreeSet<String> treeSet3 = new TreeSet();
/*  356 */         Vector vector1 = getChildrenEntityIds(str1, i, "SBB", "OFSBB");
/*      */         
/*  358 */         int m = 0;
/*  359 */         int n = 0;
/*  360 */         int i1 = 0;
/*  361 */         int i2 = 0;
/*  362 */         int i3 = 0;
/*  363 */         int i4 = 0;
/*  364 */         Iterator<Integer> iterator4 = vector1.iterator();
/*  365 */         while (iterator4.hasNext()) {
/*  366 */           Integer integer = iterator4.next();
/*  367 */           m += 
/*  368 */             getChildrenEntityIds("SBB", integer
/*      */               
/*  370 */               .intValue(), "GRA", "SBBGRA")
/*      */ 
/*      */             
/*  373 */             .size();
/*  374 */           n += 
/*  375 */             getChildrenEntityIds("SBB", integer
/*      */               
/*  377 */               .intValue(), "HD", "SBBHD")
/*      */ 
/*      */             
/*  380 */             .size();
/*  381 */           i1 += 
/*  382 */             getChildrenEntityIds("SBB", integer
/*      */               
/*  384 */               .intValue(), "MB", "SBBMB")
/*      */ 
/*      */             
/*  387 */             .size();
/*  388 */           i2 += 
/*  389 */             getChildrenEntityIds("SBB", integer
/*      */               
/*  391 */               .intValue(), "MEM", "SBBMEM")
/*      */ 
/*      */             
/*  394 */             .size();
/*  395 */           i3 += 
/*  396 */             getChildrenEntityIds("SBB", integer
/*      */               
/*  398 */               .intValue(), "PP", "SBBPP")
/*      */ 
/*      */             
/*  401 */             .size();
/*  402 */           i4 += 
/*  403 */             getChildrenEntityIds("SBB", integer
/*      */               
/*  405 */               .intValue(), "WAR", "SBBWAR")
/*      */ 
/*      */             
/*  408 */             .size();
/*      */         } 
/*      */ 
/*      */         
/*  412 */         k = getChildrenEntityIds(str1, i, "DD", "OFDD").size();
/*  413 */         m += 
/*  414 */           getChildrenEntityIds(str1, i, "GRA", "OFGRA").size();
/*  415 */         n += 
/*  416 */           getChildrenEntityIds(str1, i, "HD", "OFHD").size();
/*  417 */         i1 += 
/*  418 */           getChildrenEntityIds(str1, i, "MB", "OFMB").size();
/*  419 */         i2 += 
/*  420 */           getChildrenEntityIds(str1, i, "MEM", "OFMEM").size();
/*  421 */         i3 += 
/*  422 */           getChildrenEntityIds(str1, i, "PP", "OFPP").size();
/*  423 */         i4 += 
/*  424 */           getChildrenEntityIds(str1, i, "WAR", "OFWAR").size();
/*      */         
/*  426 */         if (k > 1) {
/*  427 */           treeSet3.add("DD");
/*      */         }
/*  429 */         if (m == 0) {
/*  430 */           treeSet2.add("GRA");
/*      */         }
/*  432 */         if (n == 0) {
/*  433 */           treeSet2.add("HD");
/*      */         }
/*  435 */         if (i1 == 0) {
/*  436 */           treeSet1.add("MB");
/*      */         }
/*  438 */         if (i1 > 1) {
/*  439 */           treeSet3.add("MB");
/*      */         }
/*  441 */         if (i2 == 0) {
/*  442 */           treeSet1.add("MEM");
/*      */         }
/*  444 */         if (i3 == 0) {
/*  445 */           treeSet1.add("PP");
/*      */         }
/*  447 */         if (i3 > 1) {
/*  448 */           treeSet3.add("PP");
/*      */         }
/*  450 */         if (i4 == 0) {
/*  451 */           treeSet1.add("WAR");
/*      */         }
/*  453 */         if (i4 > 1) {
/*  454 */           treeSet3.add("WAR");
/*      */         }
/*  456 */         if (treeSet3.isEmpty() && treeSet1
/*  457 */           .isEmpty() && treeSet2
/*  458 */           .isEmpty()) {
/*  459 */           this.rpt.append(this.msgs.getString("CHECK3_PASS_MSG"));
/*      */         } else {
/*  461 */           setReturnCode(-1);
/*      */         } 
/*  463 */         Iterator<String> iterator3 = treeSet1.iterator();
/*  464 */         this.mfOut = new MessageFormat(this.msgs.getString("CHECK3_MISSING_MSG"));
/*  465 */         while (iterator3.hasNext()) {
/*  466 */           String str = iterator3.next();
/*  467 */           this.mfParms[0] = getEntityDescription(str);
/*  468 */           this.rpt.append(this.mfOut.format(this.mfParms));
/*      */         } 
/*  470 */         iterator3 = treeSet3.iterator();
/*  471 */         this
/*  472 */           .mfOut = new MessageFormat(this.msgs.getString("CHECK3_TOO_MANY_MSG"));
/*  473 */         while (iterator3.hasNext()) {
/*  474 */           String str = iterator3.next();
/*  475 */           this.mfParms[0] = getEntityDescription(str);
/*  476 */           this.rpt.append(this.mfOut.format(this.mfParms));
/*      */         } 
/*  478 */         iterator3 = treeSet2.iterator();
/*  479 */         this
/*  480 */           .mfOut = new MessageFormat(this.msgs.getString("CHECK3_MISSING_MSG2"));
/*  481 */         while (iterator3.hasNext()) {
/*  482 */           String str = iterator3.next();
/*  483 */           this.mfParms[0] = getEntityDescription(str);
/*  484 */           this.rpt.append(this.mfOut.format(this.mfParms));
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  490 */       if (getReturnCode() == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  495 */         String str = getAttributeFlagEnabledValue(
/*  496 */             getRootEntityType(), 
/*  497 */             getRootEntityID(), "BAVLFORSPECIALBID", "");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  508 */         if ("0010".equals(str6)) {
/*  509 */           if ("0020".equals(str)) {
/*  510 */             setControlBlock();
/*  511 */             setFlagValue("STATUS", "0020");
/*      */           } else {
/*  513 */             setControlBlock();
/*  514 */             setFlagValue("STATUS", "0040");
/*      */           } 
/*  516 */         } else if ("0040".equals(str6)) {
/*  517 */           setControlBlock();
/*  518 */           setFlagValue("STATUS", "0020");
/*  519 */         } else if ("0050".equals(str6)) {
/*  520 */           if ("0020".equals(str)) {
/*  521 */             setControlBlock();
/*  522 */             setFlagValue("STATUS", "0020");
/*      */           } else {
/*  524 */             setControlBlock();
/*  525 */             setFlagValue("STATUS", "0040");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  534 */       if (getReturnCode() == 0) {
/*  535 */         System.out.println("***********Starting check 4*******************");
/*  536 */         str4 = null;
/*  537 */         str4 = getAttributeFlagEnabledValue(getRootEntityType(), getRootEntityID(), "OFFERINGTYPE", "");
/*  538 */         System.out.println("strOFFERINGTYPE for check 4" + getRootEntityType() + ": " + getRootEntityID() + ": " + str4);
/*  539 */         if (str4.equals("0080")) {
/*  540 */           str5 = null;
/*  541 */           if (vector.size() > 0) {
/*  542 */             str5 = getAttributeFlagEnabledValue("PR", ((Integer)vector.firstElement()).intValue(), "BRANDCODE", "");
/*  543 */             System.out.println("strBRANDCODE for check 4: " + str5);
/*  544 */             if (str5.equals("10014")) {
/*  545 */               String str = null;
/*  546 */               str = getAttributeFlagEnabledValue("MON", ((Integer)vector.firstElement()).intValue(), "SCREENSIZEVIEW_IN", "");
/*  547 */               System.out.println("strSCREENSIZEVIEW_IN for check 4: " + str);
/*  548 */               if (str != null) {
/*  549 */                 this.rpt.append(this.msgs.getString("CHECK4_PASS_MSG"));
/*      */               } else {
/*  551 */                 setReturnCode(-1);
/*  552 */                 this.rpt.append(this.msgs.getString("CHECK4_FAIL_MSG"));
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*  557 */         System.out.println("***********Finished check 4*******************");
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  565 */       if (getReturnCode() == 0) {
/*  566 */         System.out.println("***********Starting check 5*******************");
/*  567 */         str4 = null;
/*  568 */         str4 = getAttributeFlagEnabledValue(getRootEntityType(), getRootEntityID(), "OFFERINGTYPE", "");
/*  569 */         System.out.println("strOFFERINGTYPE for check 5" + getRootEntityType() + ": " + getRootEntityID() + ": " + str4);
/*  570 */         if (str4.equals("0080")) {
/*  571 */           str5 = null;
/*  572 */           if (vector.size() > 0) {
/*  573 */             str5 = getAttributeFlagEnabledValue("PR", ((Integer)vector.firstElement()).intValue(), "BRANDCODE", "");
/*  574 */             System.out.println("strBRANDCODE for check 5: " + str5);
/*  575 */             if (str5.equals("10014") || str5.equals("10010")) {
/*  576 */               String str8 = null;
/*  577 */               String str9 = null;
/*  578 */               str8 = getAttributeFlagEnabledValue("DD", ((Integer)vector.firstElement()).intValue(), "MEMRAMSTD", "");
/*  579 */               str9 = getAttributeFlagEnabledValue("DD", ((Integer)vector.firstElement()).intValue(), "MEMRAMSTDUNITS", "");
/*  580 */               System.out.println("strMEMRAMSTD for check 5A: " + str8);
/*  581 */               System.out.println("strMEMRAMSTDUNITS for check 5B: " + str9);
/*  582 */               if (str8 != null) {
/*  583 */                 this.rpt.append(this.msgs.getString("CHECK5A_PASS_MSG"));
/*      */               } else {
/*  585 */                 setReturnCode(-1);
/*  586 */                 this.rpt.append(this.msgs.getString("CHECK5A_FAIL_MSG"));
/*      */               } 
/*  588 */               if (str9 != null) {
/*  589 */                 this.rpt.append(this.msgs.getString("CHECK5B_PASS_MSG"));
/*      */               } else {
/*  591 */                 setReturnCode(-1);
/*  592 */                 this.rpt.append(this.msgs.getString("CHECK5B_FAIL_MSG"));
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*  597 */         System.out.println("***********Finished check 5*******************");
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  602 */     catch (LockPDHEntityException lockPDHEntityException) {
/*  603 */       setReturnCode(-1);
/*  604 */       this.mfOut = new MessageFormat(this.msgs.getString("LOCK_ERROR"));
/*  605 */       this.mfParms[0] = "IAB1007E: Could not get soft lock.  Rule execution is terminated.";
/*  606 */       this.mfParms[1] = lockPDHEntityException.getMessage();
/*  607 */       this.rpt.append(this.mfOut.format(this.mfParms));
/*  608 */       logError(lockPDHEntityException.getMessage());
/*  609 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  610 */       setReturnCode(-1);
/*  611 */       this.mfOut = new MessageFormat(this.msgs.getString("UPDATE_ERROR"));
/*  612 */       this.mfParms[0] = updatePDHEntityException.getMessage();
/*  613 */       this.rpt.append(this.mfOut.format(this.mfParms));
/*  614 */       logError(updatePDHEntityException.getMessage());
/*  615 */     } catch (Exception exception) {
/*  616 */       setReturnCode(-1);
/*      */       
/*  618 */       this.mfOut = new MessageFormat(this.msgs.getString("EXCEPTION_ERROR"));
/*  619 */       this.mfParms[0] = this.m_abri.getABRCode();
/*  620 */       this.mfParms[1] = exception.getMessage();
/*  621 */       this.rpt.append(this.mfOut.format(this.mfParms));
/*  622 */       StringWriter stringWriter = new StringWriter();
/*  623 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*  624 */       this.rpt.append("<pre>");
/*  625 */       this.rpt.append(stringWriter.getBuffer().toString());
/*  626 */       this.rpt.append("</pre>");
/*      */     } finally {
/*      */       
/*  629 */       setDGTitle(stringBuffer.toString());
/*  630 */       setDGRptName(getShortClassName(getClass()));
/*  631 */       setDGRptClass("WWABR");
/*  632 */       if (!isReadOnly()) {
/*  633 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */     
/*  637 */     stringBuffer.append((getReturnCode() == 0) ? " Passed" : " Failed");
/*  638 */     this.mfOut = new MessageFormat(this.msgs.getString("HEADER"));
/*  639 */     this.mfParms[0] = getShortClassName(getClass());
/*  640 */     this.mfParms[1] = stringBuffer.toString();
/*  641 */     this.mfParms[2] = getNow();
/*  642 */     this.mfParms[3] = this.m_prof.getOPName();
/*  643 */     this.mfParms[4] = this.m_prof.getRoleDescription();
/*  644 */     this.mfParms[5] = getDescription();
/*  645 */     this.mfParms[6] = getABRVersion();
/*  646 */     this.rpt.insert(0, this.mfOut.format(this.mfParms));
/*  647 */     println(this.rpt.toString());
/*  648 */     printDGSubmitString();
/*  649 */     buildReportFooter();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/*  658 */     return this.msgs.getString("DESCRIPTION");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/*  667 */     return "$Revision: 1.24 $";
/*      */   }
/*      */   
/*      */   private boolean searchForWord(String paramString1, String paramString2) {
/*  671 */     int i = paramString2.length();
/*  672 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString1);
/*  673 */     while (stringTokenizer.hasMoreTokens()) {
/*  674 */       String str = stringTokenizer.nextToken();
/*  675 */       if (str.length() > i) {
/*  676 */         str = str.substring(0, i);
/*      */       }
/*  678 */       if (str.equalsIgnoreCase(paramString2)) {
/*  679 */         return true;
/*      */       }
/*      */     } 
/*  682 */     return false;
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
/*      */   private void setFlagValue(String paramString1, String paramString2) {
/*  694 */     logMessage("****** strAttributeValue set to: " + paramString2);
/*      */     
/*  696 */     if (paramString2 != null)
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  703 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*      */ 
/*      */ 
/*      */         
/*  707 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  714 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  719 */         Vector<SingleFlag> vector = new Vector();
/*  720 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*      */         
/*  722 */         if (singleFlag != null) {
/*  723 */           vector.addElement(singleFlag);
/*      */           
/*  725 */           returnEntityKey.m_vctAttributes = vector;
/*  726 */           vector1.addElement(returnEntityKey);
/*      */           
/*  728 */           this.m_db.update(this.m_prof, vector1, false, false);
/*  729 */           this.m_db.commit();
/*      */         } 
/*  731 */       } catch (MiddlewareException middlewareException) {
/*  732 */         logMessage("setFlagValue: " + middlewareException.getMessage());
/*  733 */       } catch (Exception exception) {
/*  734 */         logMessage("setFlagValue: " + exception.getMessage());
/*      */       }  
/*      */   }
/*      */   
/*      */   private Locale getLocale(int paramInt) {
/*  739 */     Locale locale = null;
/*  740 */     switch (paramInt)
/*      */     { case 1:
/*  742 */         locale = Locale.US;
/*      */       case 2:
/*  744 */         locale = Locale.GERMAN;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  764 */         return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
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
/*      */   public void printEntity(String paramString, int paramInt1, int paramInt2) {
/*  777 */     String str = null;
/*      */     
/*  779 */     logMessage("In printEntity _strEntityType" + paramString + ":_iEntityID:" + paramInt1 + ":_iLevel:" + paramInt2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  787 */     str = "";
/*  788 */     switch (paramInt2) {
/*      */       
/*      */       case 0:
/*  791 */         str = "PsgReportSection";
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/*  796 */         str = "PsgReportSectionII";
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/*  801 */         str = "PsgReportSectionIII";
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/*  806 */         str = "PsgReportSectionIV";
/*      */         break;
/*      */ 
/*      */       
/*      */       default:
/*  811 */         str = "PsgReportSectionV";
/*      */         break;
/*      */     } 
/*      */     
/*  815 */     logMessage("Printing table width");
/*  816 */     this.rpt.append("<table width=\"100%\"><tr><td class=\"" + str + "\">" + 
/*      */ 
/*      */ 
/*      */         
/*  820 */         getEntityDescription(paramString) + ": " + 
/*      */         
/*  822 */         getAttributeValue(paramString, paramInt1, "NAME", "<em>** Not Populated **</em>") + "</td></tr></table>");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  828 */     logMessage("Printing Attributes");
/*  829 */     printAttributes(paramString, paramInt1, false, false);
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
/*      */   public void printAttributes(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/*  844 */     printAttributes(this.m_elist, paramString, paramInt, paramBoolean1, paramBoolean2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printAttributes(EntityList paramEntityList, String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/*  868 */     EntityItem entityItem = null;
/*  869 */     EntityGroup entityGroup = null;
/*  870 */     String str1 = null;
/*      */ 
/*      */     
/*  873 */     String str2 = null;
/*  874 */     String str3 = null;
/*      */     
/*  876 */     SortUtil sortUtil = null;
/*  877 */     int i = 0;
/*  878 */     int j = 0;
/*  879 */     logMessage("in Print Attributes _strEntityType" + paramString + ":_iEntityID:" + paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  885 */     if (paramString.equals(getEntityType())) {
/*      */       
/*  887 */       entityGroup = paramEntityList.getParentEntityGroup();
/*      */     } else {
/*  889 */       entityGroup = paramEntityList.getEntityGroup(paramString);
/*      */     } 
/*      */     
/*  892 */     if (entityGroup == null) {
/*  893 */       this.rpt.append("<h3>Warning: Cannot locate an EnityGroup for " + paramString + " so no attributes will be printed.</h3>");
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  900 */     entityItem = entityGroup.getEntityItem(paramString, paramInt);
/*      */     
/*  902 */     if (entityItem == null) {
/*      */       
/*  904 */       entityItem = entityGroup.getEntityItem(0);
/*  905 */       this.rpt.append("<h3>Warning: Attributes for " + paramString + ":" + paramInt + " cannot be printed as it is not available in the Extract.</h3>");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  911 */       this.rpt.append("<h3>Warning: Root Entityis " + 
/*      */           
/*  913 */           getEntityType() + ":" + 
/*      */           
/*  915 */           getEntityID() + ".</h3>");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  920 */     str1 = entityGroup.getLongDescription();
/*  921 */     logMessage("Print Attributes Entity desc is " + str1);
/*  922 */     logMessage("Attribute count is" + entityItem.getAttributeCount());
/*  923 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*  924 */     Vector<String> vector = new Vector(); byte b;
/*  925 */     for (b = 0; b < entityItem.getAttributeCount(); b++) {
/*      */       
/*  927 */       EANAttribute eANAttribute = entityItem.getAttribute(b);
/*  928 */       logMessage("printAttributes " + eANAttribute.dump(false));
/*  929 */       logMessage("printAttributes " + eANAttribute.dump(true));
/*      */ 
/*      */       
/*  932 */       str2 = getAttributeValue(paramString, paramInt, eANAttribute
/*      */ 
/*      */           
/*  935 */           .getAttributeCode(), "<em>** Not Populated **</em>");
/*      */       
/*  937 */       str3 = "";
/*      */ 
/*      */       
/*  940 */       if (paramBoolean2) {
/*      */         
/*  942 */         str3 = getMetaDescription(paramString, eANAttribute
/*      */             
/*  944 */             .getAttributeCode(), false);
/*      */       }
/*      */       else {
/*      */         
/*  948 */         str3 = getAttributeDescription(paramString, eANAttribute
/*      */             
/*  950 */             .getAttributeCode());
/*      */       } 
/*      */       
/*  953 */       if (str3.length() > str1.length() && str3
/*  954 */         .substring(0, str1.length()).equalsIgnoreCase(str1))
/*      */       {
/*  956 */         str3 = str3.substring(str1.length());
/*      */       }
/*      */       
/*  959 */       if (paramBoolean1 || str2 != null) {
/*      */         
/*  961 */         hashtable.put(str3, str2);
/*      */         
/*  963 */         vector.add(str3);
/*      */       } 
/*      */     } 
/*  966 */     String[] arrayOfString = new String[entityItem.getAttributeCount()];
/*      */     
/*  968 */     if (!paramBoolean1) {
/*  969 */       arrayOfString = new String[vector.size()];
/*  970 */       for (b = 0; b < arrayOfString.length; b++) {
/*  971 */         arrayOfString[b] = vector.elementAt(b);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  976 */     sortUtil = new SortUtil();
/*  977 */     sortUtil.sort(arrayOfString);
/*      */ 
/*      */     
/*  980 */     this.rpt.append("<table width=\"100%\">");
/*  981 */     i = arrayOfString.length - arrayOfString.length / 2;
/*  982 */     for (b = 0; b < i; b++) {
/*  983 */       this.rpt.append("<tr><td class=\"PsgLabel\" valign=\"top\">" + arrayOfString[b] + "</td><td class=\"PsgText\" valign=\"top\">" + hashtable
/*      */ 
/*      */ 
/*      */           
/*  987 */           .get(arrayOfString[b]) + "</td>");
/*      */       
/*  989 */       j = i + b;
/*  990 */       if (j < arrayOfString.length) {
/*  991 */         this.rpt.append("<td class=\"PsgLabel\" valign=\"top\">" + arrayOfString[j] + "</td><td class=\"PsgText\" valign=\"top\">" + hashtable
/*      */ 
/*      */ 
/*      */             
/*  995 */             .get(arrayOfString[j]) + "</td><tr>");
/*      */       } else {
/*      */         
/*  998 */         this.rpt.append("<td class=\"PsgLabel\"></td><td class=\"PsgText\"></td><tr>");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1004 */     this.rpt.append("</table>\n<br />");
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
/*      */   private String getMetaDescription(String paramString1, String paramString2, boolean paramBoolean) {
/* 1020 */     return getMetaDescription(this.m_elist, paramString1, paramString2, paramBoolean);
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
/*      */   
/*      */   private String getMetaDescription(EntityList paramEntityList, String paramString1, String paramString2, boolean paramBoolean) {
/* 1037 */     EANMetaAttribute eANMetaAttribute = null;
/* 1038 */     String str = null;
/* 1039 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(paramString1);
/* 1040 */     if (entityGroup == null) {
/* 1041 */       logError("Did not find EntityGroup: " + paramString1 + " in entity list to extract getMetaDescription");
/*      */ 
/*      */ 
/*      */       
/* 1045 */       return null;
/*      */     } 
/*      */     
/* 1048 */     eANMetaAttribute = null;
/* 1049 */     if (entityGroup != null) {
/* 1050 */       eANMetaAttribute = entityGroup.getMetaAttribute(paramString2);
/*      */     }
/* 1052 */     str = null;
/* 1053 */     if (eANMetaAttribute != null) {
/* 1054 */       if (paramBoolean) {
/* 1055 */         str = eANMetaAttribute.getLongDescription();
/*      */       } else {
/* 1057 */         str = eANMetaAttribute.getShortDescription();
/*      */       } 
/*      */     }
/* 1060 */     return str;
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\psg\OFABR001.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */