/*      */ package COM.ibm.eannounce.abr.util;public class CtryRecord extends XMLElem { boolean existfinalT1; boolean existfinalT2; String compatModel; String action; String availStatus; String pubfrom; String pubto; String endofservice; String anndate; String firstorder; String plannedavailability; String wdanndate; String eomannnum;
/*      */   String lastorder;
/*      */   String eodanndate;
/*      */   String eodavaildate;
/*      */   String eosanndate;
/*      */   String eosannnum;
/*      */   
/*      */   public CtryRecord(String paramString) {
/*    9 */     super(paramString);
/*      */ 
/*      */ 
/*      */     
/*   13 */     this.existfinalT1 = false;
/*   14 */     this.existfinalT2 = false;
/*   15 */     this.compatModel = "V200309";
/*      */     
/*   17 */     this.action = "@@";
/*      */ 
/*      */ 
/*      */     
/*   21 */     this.availStatus = "@@";
/*      */     
/*   23 */     this.pubfrom = "@@";
/*      */     
/*   25 */     this.pubto = "@@";
/*      */     
/*   27 */     this.endofservice = "@@";
/*      */     
/*   29 */     this.anndate = "@@";
/*      */     
/*   31 */     this.firstorder = "@@";
/*      */     
/*   33 */     this.plannedavailability = "@@";
/*      */     
/*   35 */     this.wdanndate = "@@";
/*      */     
/*   37 */     this.eomannnum = "@@";
/*      */     
/*   39 */     this.lastorder = "@@";
/*      */     
/*   41 */     this.eodanndate = "@@";
/*   42 */     this.eodavaildate = "@@";
/*      */     
/*   44 */     this.eosanndate = "@@";
/*      */     
/*   46 */     this.eosannnum = "@@";
/*      */     
/*   48 */     this.annnumber = "@@";
/*      */     
/*   50 */     this.rfraction = "@@";
/*      */     
/*   52 */     this.rfravailStatus = "@@";
/*      */     
/*   54 */     this.rfrpubfrom = "@@";
/*      */     
/*   56 */     this.rfrpubto = "@@";
/*      */     
/*   58 */     this.rfrendofservice = "@@";
/*      */     
/*   60 */     this.rfranndate = "@@";
/*      */     
/*   62 */     this.rfrfirstorder = "@@";
/*      */     
/*   64 */     this.rfrplannedavailability = "@@";
/*      */     
/*   66 */     this.rfrwdanndate = "@@";
/*      */     
/*   68 */     this.rfreomannnum = "@@";
/*      */     
/*   70 */     this.rfrlastorder = "@@";
/*      */     
/*   72 */     this.rfreodanndate = "@@";
/*   73 */     this.rfreodavaildate = "@@";
/*      */     
/*   75 */     this.rfreosanndate = "@@";
/*      */     
/*   77 */     this.rfreosannnum = "@@";
/*      */     
/*   79 */     this.rfrannnumber = "@@";
/*      */ 
/*      */     
/*   82 */     this.ordersysname = "@@";
/*   83 */     this.rfrordersysname = "@@";
/*      */   }
/*      */   String annnumber; String rfraction; String rfravailStatus; String rfrpubfrom; String rfrpubto; String rfrendofservice; String rfranndate; String rfrfirstorder; String rfrplannedavailability; String rfrwdanndate; String rfreomannnum; String rfrlastorder; String rfreodanndate; String rfreodavaildate; String rfreosanndate; String rfreosannnum; String rfrannnumber; String ordersysname; String rfrordersysname;
/*      */   public String getOrderSysName() {
/*   87 */     return this.ordersysname;
/*      */   }
/*      */   
/*      */   public String getRfrOrderSysName() {
/*   91 */     return this.rfrordersysname;
/*      */   }
/*      */   
/*      */   public String getRfraction() {
/*   95 */     return this.rfraction;
/*      */   }
/*      */   
/*      */   public String getRfravailStatus() {
/*   99 */     return this.rfravailStatus;
/*      */   }
/*      */   
/*      */   public String getRfrpubfrom() {
/*  103 */     return this.rfrpubfrom;
/*      */   }
/*      */   
/*      */   public String getRfrpubto() {
/*  107 */     return this.rfrpubto;
/*      */   }
/*      */   
/*      */   public String getRfrendofservice() {
/*  111 */     return this.rfrendofservice;
/*      */   }
/*      */   
/*      */   public String getRfranndate() {
/*  115 */     return this.rfranndate;
/*      */   }
/*      */   
/*      */   public String getRfrfirstorder() {
/*  119 */     return this.rfrfirstorder;
/*      */   }
/*      */   
/*      */   public String getRfrplannedavailability() {
/*  123 */     return this.rfrplannedavailability;
/*      */   }
/*      */   
/*      */   public String getRfrwdanndate() {
/*  127 */     return this.rfrwdanndate;
/*      */   }
/*      */   
/*      */   public String getRfreomannnum() {
/*  131 */     return this.rfreomannnum;
/*      */   }
/*      */   
/*      */   public String getRfrlastorder() {
/*  135 */     return this.rfrlastorder;
/*      */   }
/*      */   
/*      */   public String getRfreosanndate() {
/*  139 */     return this.rfreosanndate;
/*      */   }
/*      */   
/*      */   public String getRfreosannnum() {
/*  143 */     return this.rfreosannnum;
/*      */   }
/*      */   
/*      */   public String getRfreodanndate() {
/*  147 */     return this.rfreodanndate;
/*      */   }
/*      */   
/*      */   public String getRfreodavaildate() {
/*  151 */     return this.rfreodavaildate;
/*      */   }
/*      */   
/*      */   public String getRfrannnumber() {
/*  155 */     return this.rfrannnumber;
/*      */   }
/*      */   
/*      */   boolean isrfrDisplayable() {
/*  159 */     return !this.rfraction.equals("@@");
/*      */   }
/*      */   
/*      */   void setrfrAction(String paramString) {
/*  163 */     this.rfraction = paramString;
/*      */   }
/*      */   
/*      */   String getAction() {
/*  167 */     return this.action;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String getPubFrom() {
/*  176 */     return this.pubfrom;
/*      */   }
/*      */   
/*      */   String getPubTo() {
/*  180 */     return this.pubto;
/*      */   }
/*      */   
/*      */   String getEndOfService() {
/*  184 */     return this.endofservice;
/*      */   }
/*      */   
/*      */   String getAvailStatus() {
/*  188 */     return this.availStatus;
/*      */   }
/*      */   
/*      */   String getAnndate() {
/*  192 */     return this.anndate;
/*      */   }
/*      */   
/*      */   String getFirstorder() {
/*  196 */     return this.firstorder;
/*      */   }
/*      */   
/*      */   String getPlannedavailability() {
/*  200 */     return this.plannedavailability;
/*      */   }
/*      */   
/*      */   String getWdanndate() {
/*  204 */     return this.wdanndate;
/*      */   }
/*      */   
/*      */   String getEomannnum() {
/*  208 */     return this.eomannnum;
/*      */   }
/*      */   
/*      */   String getLastorder() {
/*  212 */     return this.lastorder;
/*      */   }
/*      */   
/*      */   String getEosanndate() {
/*  216 */     return this.eosanndate;
/*      */   }
/*      */   
/*      */   String getEosannnum() {
/*  220 */     return this.eosannnum;
/*      */   }
/*      */   
/*      */   public String getEodanndate() {
/*  224 */     return this.eodanndate;
/*      */   }
/*      */   
/*      */   public String getEodavaildate() {
/*  228 */     return this.eodavaildate;
/*      */   }
/*      */   
/*      */   String getAnnnumber() {
/*  232 */     return this.annnumber;
/*      */   }
/*      */   
/*      */   boolean isDeleted() {
/*  236 */     return this.action.equals("Delete");
/*      */   }
/*      */   
/*      */   boolean isDisplayable() {
/*  240 */     return !this.action.equals("@@");
/*      */   }
/*      */   
/*      */   void setAction(String paramString) {
/*  244 */     this.action = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean handleResults(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, String[] paramArrayOfString7, String[] paramArrayOfString8, String[] paramArrayOfString9, String[] paramArrayOfString10, String[] paramArrayOfString11, String[] paramArrayOfString12, String[] paramArrayOfString13, String[] paramArrayOfString14, String[] paramArrayOfString15, String[] paramArrayOfString16, String[] paramArrayOfString17, String[] paramArrayOfString18, String[] paramArrayOfString19, String[] paramArrayOfString20, String paramString, Element paramElement, StringBuffer paramStringBuffer) {
/*  254 */     String str1 = "@@";
/*  255 */     String str2 = "@@";
/*  256 */     String str3 = "@@";
/*  257 */     String str4 = "@@";
/*  258 */     String str5 = "@@";
/*  259 */     String str6 = "@@";
/*  260 */     String str7 = "@@";
/*  261 */     String str8 = "@@";
/*  262 */     String str9 = "@@";
/*  263 */     String str10 = "@@";
/*  264 */     String str11 = "@@";
/*  265 */     String str12 = "@@";
/*  266 */     String str13 = "@@";
/*  267 */     String str14 = "@@";
/*  268 */     String str15 = "@@";
/*  269 */     String str16 = "@@";
/*  270 */     String str17 = "@@";
/*  271 */     String str18 = "@@";
/*  272 */     String str19 = "@@";
/*  273 */     String str20 = "@@";
/*      */     
/*  275 */     this.anndate = paramArrayOfString1[0];
/*  276 */     this.rfranndate = paramArrayOfString1[1];
/*  277 */     str1 = paramArrayOfString2[0];
/*  278 */     str2 = paramArrayOfString2[1];
/*  279 */     this.annnumber = paramArrayOfString3[0];
/*  280 */     this.rfrannnumber = paramArrayOfString3[1];
/*  281 */     str3 = paramArrayOfString4[0];
/*  282 */     str4 = paramArrayOfString4[1];
/*  283 */     this.firstorder = paramArrayOfString5[0];
/*  284 */     this.rfrfirstorder = paramArrayOfString5[1];
/*  285 */     str5 = paramArrayOfString6[0];
/*  286 */     str6 = paramArrayOfString6[1];
/*  287 */     this.plannedavailability = paramArrayOfString7[0];
/*  288 */     this.rfrplannedavailability = paramArrayOfString7[1];
/*  289 */     str7 = paramArrayOfString8[0];
/*  290 */     str8 = paramArrayOfString8[1];
/*  291 */     this.pubfrom = paramArrayOfString9[0];
/*  292 */     this.rfrpubfrom = paramArrayOfString9[1];
/*  293 */     str9 = paramArrayOfString10[0];
/*  294 */     str10 = paramArrayOfString10[1];
/*  295 */     this.pubto = paramArrayOfString11[0];
/*  296 */     this.rfrpubto = paramArrayOfString11[1];
/*  297 */     str11 = paramArrayOfString12[0];
/*  298 */     str12 = paramArrayOfString12[1];
/*  299 */     this.wdanndate = paramArrayOfString13[0];
/*  300 */     this.rfrwdanndate = paramArrayOfString13[1];
/*  301 */     str13 = paramArrayOfString14[0];
/*  302 */     str14 = paramArrayOfString14[1];
/*  303 */     this.lastorder = paramArrayOfString15[0];
/*  304 */     this.rfrlastorder = paramArrayOfString15[1];
/*  305 */     str15 = paramArrayOfString16[0];
/*  306 */     str16 = paramArrayOfString16[1];
/*  307 */     this.endofservice = paramArrayOfString17[0];
/*  308 */     this.rfrendofservice = paramArrayOfString17[1];
/*  309 */     str17 = paramArrayOfString18[0];
/*  310 */     str18 = paramArrayOfString18[1];
/*  311 */     this.eosanndate = paramArrayOfString19[0];
/*  312 */     this.rfreosanndate = paramArrayOfString19[1];
/*  313 */     str19 = paramArrayOfString20[0];
/*  314 */     str20 = paramArrayOfString20[1];
/*      */     
/*  316 */     if ("Delete".equals(this.action)) {
/*  317 */       ABRUtil.append(paramStringBuffer, "setallfileds: coutry is delete:" + paramString);
/*      */       
/*  319 */       str2 = copyfinaltoRFR(str1, str2, true, paramStringBuffer);
/*  320 */       str4 = copyfinaltoRFR(str3, str4, true, paramStringBuffer);
/*  321 */       str6 = copyfinaltoRFR(str5, str6, true, paramStringBuffer);
/*  322 */       str8 = copyfinaltoRFR(str7, str8, true, paramStringBuffer);
/*  323 */       str10 = copyfinaltoRFR(str9, str10, true, paramStringBuffer);
/*  324 */       str12 = copyfinaltoRFR(str11, str12, true, paramStringBuffer);
/*  325 */       str14 = copyfinaltoRFR(str13, str14, true, paramStringBuffer);
/*  326 */       str16 = copyfinaltoRFR(str15, str16, true, paramStringBuffer);
/*  327 */       str18 = copyfinaltoRFR(str17, str18, true, paramStringBuffer);
/*  328 */       str20 = copyfinaltoRFR(str19, str20, true, paramStringBuffer);
/*  329 */       if (this.existfinalT1) {
/*  330 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is exist final T1:" + paramString + NEWLINE);
/*  331 */         setAction("Delete");
/*  332 */         setrfrAction("Delete");
/*  333 */         setAllfieldsEmpty();
/*      */       } else {
/*      */         
/*  336 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is not exist final T1:" + paramString + NEWLINE);
/*  337 */         setAction("@@");
/*  338 */         setrfrAction("Delete");
/*  339 */         setAllfieldsEmpty();
/*      */       }
/*      */     
/*      */     }
/*  343 */     else if ("Update".equals(this.action)) {
/*  344 */       ABRUtil.append(paramStringBuffer, "setallfileds: coutry is new:" + paramString + NEWLINE);
/*      */       
/*  346 */       this.rfranndate = copyfinaltoRFR(this.anndate, this.rfranndate, false, paramStringBuffer);
/*  347 */       this.rfrannnumber = copyfinaltoRFR(this.annnumber, this.rfrannnumber, false, paramStringBuffer);
/*  348 */       this.rfrfirstorder = copyfinaltoRFR(this.firstorder, this.rfrfirstorder, false, paramStringBuffer);
/*  349 */       this.rfrplannedavailability = copyfinaltoRFR(this.plannedavailability, this.rfrplannedavailability, false, paramStringBuffer);
/*  350 */       this.rfrpubfrom = copyfinaltoRFR(this.pubfrom, this.rfrpubfrom, false, paramStringBuffer);
/*  351 */       this.rfrpubto = copyfinaltoRFR(this.pubto, this.rfrpubto, false, paramStringBuffer);
/*  352 */       this.rfrwdanndate = copyfinaltoRFR(this.wdanndate, this.rfrwdanndate, false, paramStringBuffer);
/*  353 */       this.rfrlastorder = copyfinaltoRFR(this.lastorder, this.rfrlastorder, false, paramStringBuffer);
/*  354 */       this.rfrendofservice = copyfinaltoRFR(this.endofservice, this.rfrendofservice, false, paramStringBuffer);
/*  355 */       this.rfreosanndate = copyfinaltoRFR(this.eosanndate, this.rfreosanndate, false, paramStringBuffer);
/*  356 */       if (this.existfinalT2) {
/*  357 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is  exist final T2:" + paramString + NEWLINE);
/*  358 */         setAction("Update");
/*  359 */         setrfrAction("Update");
/*      */       } else {
/*      */         
/*  362 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is not exist final T2:" + paramString + NEWLINE);
/*  363 */         setAction("@@");
/*  364 */         setrfrAction("Update");
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  369 */       ABRUtil.append(paramStringBuffer, "setallfileds: coutry is both exist T1 and T2:" + paramString + NEWLINE);
/*      */       
/*  371 */       str2 = copyfinaltoRFR(str1, str2, true, paramStringBuffer);
/*  372 */       str4 = copyfinaltoRFR(str3, str4, true, paramStringBuffer);
/*  373 */       str6 = copyfinaltoRFR(str5, str6, true, paramStringBuffer);
/*  374 */       str8 = copyfinaltoRFR(str7, str8, true, paramStringBuffer);
/*  375 */       str10 = copyfinaltoRFR(str9, str10, true, paramStringBuffer);
/*  376 */       str12 = copyfinaltoRFR(str11, str12, true, paramStringBuffer);
/*  377 */       str14 = copyfinaltoRFR(str13, str14, true, paramStringBuffer);
/*  378 */       str16 = copyfinaltoRFR(str15, str16, true, paramStringBuffer);
/*  379 */       str18 = copyfinaltoRFR(str17, str18, true, paramStringBuffer);
/*  380 */       str20 = copyfinaltoRFR(str19, str20, true, paramStringBuffer);
/*      */       
/*  382 */       this.rfranndate = copyfinaltoRFR(this.anndate, this.rfranndate, false, paramStringBuffer);
/*  383 */       this.rfrannnumber = copyfinaltoRFR(this.annnumber, this.rfrannnumber, false, paramStringBuffer);
/*  384 */       this.rfrfirstorder = copyfinaltoRFR(this.firstorder, this.rfrfirstorder, false, paramStringBuffer);
/*  385 */       this.rfrplannedavailability = copyfinaltoRFR(this.plannedavailability, this.rfrplannedavailability, false, paramStringBuffer);
/*  386 */       this.rfrpubfrom = copyfinaltoRFR(this.pubfrom, this.rfrpubfrom, false, paramStringBuffer);
/*  387 */       this.rfrpubto = copyfinaltoRFR(this.pubto, this.rfrpubto, false, paramStringBuffer);
/*  388 */       this.rfrwdanndate = copyfinaltoRFR(this.wdanndate, this.rfrwdanndate, false, paramStringBuffer);
/*  389 */       this.rfrlastorder = copyfinaltoRFR(this.lastorder, this.rfrlastorder, false, paramStringBuffer);
/*  390 */       this.rfrendofservice = copyfinaltoRFR(this.endofservice, this.rfrendofservice, false, paramStringBuffer);
/*  391 */       this.rfreosanndate = copyfinaltoRFR(this.eosanndate, this.rfreosanndate, false, paramStringBuffer);
/*      */       
/*  393 */       if (this.existfinalT1 && !this.existfinalT2) {
/*  394 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  exist final T1 but T2:" + paramString + NEWLINE);
/*  395 */         setAction("Delete");
/*  396 */         setfinalAllfieldsEmpty();
/*      */       }
/*  398 */       else if (this.existfinalT2 && !this.existfinalT1) {
/*  399 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  exist final T2 but T1:" + paramString + NEWLINE);
/*  400 */         setAction("Update");
/*  401 */         setrfrAction("Update");
/*      */       }
/*  403 */       else if (this.existfinalT2 && this.existfinalT1) {
/*  404 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  exist final T1 and T2:" + paramString + NEWLINE);
/*  405 */         compareT1vT2(this.anndate, str1, false);
/*  406 */         compareT1vT2(this.annnumber, str3, false);
/*  407 */         compareT1vT2(this.firstorder, str5, false);
/*  408 */         compareT1vT2(this.plannedavailability, str7, false);
/*  409 */         compareT1vT2(this.pubfrom, str9, false);
/*  410 */         compareT1vT2(this.pubto, str11, false);
/*  411 */         compareT1vT2(this.wdanndate, str13, false);
/*  412 */         compareT1vT2(this.lastorder, str15, false);
/*  413 */         compareT1vT2(this.endofservice, str17, false);
/*  414 */         compareT1vT2(this.eosanndate, str19, false);
/*  415 */         ABRUtil.append(paramStringBuffer, "setallfileds: after compare action :" + this.action + NEWLINE);
/*      */       } else {
/*      */         
/*  418 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  not exist final T1 and T2:" + paramString + NEWLINE);
/*  419 */         setAction("@@");
/*      */       } 
/*  421 */       compareT1vT2(this.rfranndate, str2, true);
/*  422 */       compareT1vT2(this.rfrannnumber, str4, true);
/*  423 */       compareT1vT2(this.rfrfirstorder, str6, true);
/*  424 */       compareT1vT2(this.rfrplannedavailability, str8, true);
/*  425 */       compareT1vT2(this.rfrpubfrom, str10, true);
/*  426 */       compareT1vT2(this.rfrpubto, str12, true);
/*  427 */       compareT1vT2(this.rfrwdanndate, str14, true);
/*  428 */       compareT1vT2(this.rfrlastorder, str16, true);
/*  429 */       compareT1vT2(this.rfrendofservice, str18, true);
/*  430 */       compareT1vT2(this.rfreosanndate, str20, true);
/*  431 */       ABRUtil.append(paramStringBuffer, "setallfileds: after compare rfr values action:" + this.rfraction + NEWLINE);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  441 */     String str21 = ABRServerProperties.getValue("ADSABRSTATUS", "_compatibility", "@@");
/*      */     
/*  443 */     ABRUtil.append(paramStringBuffer, "compatModel compatbility mode:" + str21);
/*      */     
/*  445 */     if (!this.compatModel.equals(str21)) {
/*  446 */       NodeList nodeList = paramElement.getElementsByTagName("STATUS");
/*  447 */       int i = nodeList.getLength();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  453 */       if (i > 0) {
/*  454 */         String str = nodeList.item(0).getFirstChild().getNodeValue();
/*  455 */         ABRUtil.append(paramStringBuffer, "compatModel root status:" + str);
/*  456 */         if ("0020".equals(str)) {
/*  457 */           setrfrAction("@@");
/*      */         } else {
/*  459 */           setAction("@@");
/*      */         } 
/*      */       } else {
/*  462 */         ABRUtil.append(paramStringBuffer, "compatModel there is no status value");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  467 */     return this.existfinalT2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean handleResults(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, String[] paramArrayOfString7, String[] paramArrayOfString8, String[] paramArrayOfString9, String[] paramArrayOfString10, String[] paramArrayOfString11, String[] paramArrayOfString12, String[] paramArrayOfString13, String[] paramArrayOfString14, String[] paramArrayOfString15, String[] paramArrayOfString16, String[] paramArrayOfString17, String[] paramArrayOfString18, String[] paramArrayOfString19, String[] paramArrayOfString20, String paramString, boolean paramBoolean1, boolean paramBoolean2, StringBuffer paramStringBuffer) {
/*  478 */     String str1 = "@@";
/*  479 */     String str2 = "@@";
/*  480 */     String str3 = "@@";
/*  481 */     String str4 = "@@";
/*  482 */     String str5 = "@@";
/*  483 */     String str6 = "@@";
/*  484 */     String str7 = "@@";
/*  485 */     String str8 = "@@";
/*  486 */     String str9 = "@@";
/*  487 */     String str10 = "@@";
/*  488 */     String str11 = "@@";
/*  489 */     String str12 = "@@";
/*  490 */     String str13 = "@@";
/*  491 */     String str14 = "@@";
/*  492 */     String str15 = "@@";
/*  493 */     String str16 = "@@";
/*  494 */     String str17 = "@@";
/*  495 */     String str18 = "@@";
/*  496 */     String str19 = "@@";
/*  497 */     String str20 = "@@";
/*      */     
/*  499 */     this.anndate = paramArrayOfString1[0];
/*  500 */     this.rfranndate = paramArrayOfString1[1];
/*  501 */     str1 = paramArrayOfString2[0];
/*  502 */     str2 = paramArrayOfString2[1];
/*  503 */     this.annnumber = paramArrayOfString3[0];
/*  504 */     this.rfrannnumber = paramArrayOfString3[1];
/*  505 */     str3 = paramArrayOfString4[0];
/*  506 */     str4 = paramArrayOfString4[1];
/*  507 */     this.firstorder = paramArrayOfString5[0];
/*  508 */     this.rfrfirstorder = paramArrayOfString5[1];
/*  509 */     str5 = paramArrayOfString6[0];
/*  510 */     str6 = paramArrayOfString6[1];
/*  511 */     this.plannedavailability = paramArrayOfString7[0];
/*  512 */     this.rfrplannedavailability = paramArrayOfString7[1];
/*  513 */     str7 = paramArrayOfString8[0];
/*  514 */     str8 = paramArrayOfString8[1];
/*  515 */     this.pubfrom = paramArrayOfString9[0];
/*  516 */     this.rfrpubfrom = paramArrayOfString9[1];
/*  517 */     str9 = paramArrayOfString10[0];
/*  518 */     str10 = paramArrayOfString10[1];
/*  519 */     this.pubto = paramArrayOfString11[0];
/*  520 */     this.rfrpubto = paramArrayOfString11[1];
/*  521 */     str11 = paramArrayOfString12[0];
/*  522 */     str12 = paramArrayOfString12[1];
/*  523 */     this.wdanndate = paramArrayOfString13[0];
/*  524 */     this.rfrwdanndate = paramArrayOfString13[1];
/*  525 */     str13 = paramArrayOfString14[0];
/*  526 */     str14 = paramArrayOfString14[1];
/*  527 */     this.lastorder = paramArrayOfString15[0];
/*  528 */     this.rfrlastorder = paramArrayOfString15[1];
/*  529 */     str15 = paramArrayOfString16[0];
/*  530 */     str16 = paramArrayOfString16[1];
/*  531 */     this.endofservice = paramArrayOfString17[0];
/*  532 */     this.rfrendofservice = paramArrayOfString17[1];
/*  533 */     str17 = paramArrayOfString18[0];
/*  534 */     str18 = paramArrayOfString18[1];
/*  535 */     this.eosanndate = paramArrayOfString19[0];
/*  536 */     this.rfreosanndate = paramArrayOfString19[1];
/*  537 */     str19 = paramArrayOfString20[0];
/*  538 */     str20 = paramArrayOfString20[1];
/*      */     
/*  540 */     if ("Delete".equals(this.action)) {
/*  541 */       ABRUtil.append(paramStringBuffer, "setallfileds: coutry is delete:" + paramString);
/*      */       
/*  543 */       str2 = copyfinaltoRFR(str1, str2, true, paramStringBuffer);
/*  544 */       str4 = copyfinaltoRFR(str3, str4, true, paramStringBuffer);
/*  545 */       str6 = copyfinaltoRFR(str5, str6, true, paramStringBuffer);
/*  546 */       str8 = copyfinaltoRFR(str7, str8, true, paramStringBuffer);
/*  547 */       str10 = copyfinaltoRFR(str9, str10, true, paramStringBuffer);
/*  548 */       str12 = copyfinaltoRFR(str11, str12, true, paramStringBuffer);
/*  549 */       str14 = copyfinaltoRFR(str13, str14, true, paramStringBuffer);
/*  550 */       str16 = copyfinaltoRFR(str15, str16, true, paramStringBuffer);
/*  551 */       str18 = copyfinaltoRFR(str17, str18, true, paramStringBuffer);
/*  552 */       str20 = copyfinaltoRFR(str19, str20, true, paramStringBuffer);
/*  553 */       if (this.existfinalT1) {
/*  554 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is exist final T1:" + paramString + NEWLINE);
/*  555 */         setAction("Delete");
/*  556 */         setrfrAction("Delete");
/*  557 */         setAllfieldsEmpty();
/*      */       } else {
/*      */         
/*  560 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is not exist final T1:" + paramString + NEWLINE);
/*  561 */         setAction("@@");
/*  562 */         setrfrAction("Delete");
/*  563 */         setAllfieldsEmpty();
/*      */       }
/*      */     
/*      */     }
/*  567 */     else if ("Update".equals(this.action)) {
/*  568 */       ABRUtil.append(paramStringBuffer, "setallfileds: coutry is new:" + paramString + NEWLINE);
/*      */       
/*  570 */       this.rfranndate = copyfinaltoRFR(this.anndate, this.rfranndate, false, paramStringBuffer);
/*  571 */       this.rfrannnumber = copyfinaltoRFR(this.annnumber, this.rfrannnumber, false, paramStringBuffer);
/*  572 */       this.rfrfirstorder = copyfinaltoRFR(this.firstorder, this.rfrfirstorder, false, paramStringBuffer);
/*  573 */       this.rfrplannedavailability = copyfinaltoRFR(this.plannedavailability, this.rfrplannedavailability, false, paramStringBuffer);
/*  574 */       this.rfrpubfrom = copyfinaltoRFR(this.pubfrom, this.rfrpubfrom, false, paramStringBuffer);
/*  575 */       this.rfrpubto = copyfinaltoRFR(this.pubto, this.rfrpubto, false, paramStringBuffer);
/*  576 */       this.rfrwdanndate = copyfinaltoRFR(this.wdanndate, this.rfrwdanndate, false, paramStringBuffer);
/*  577 */       this.rfrlastorder = copyfinaltoRFR(this.lastorder, this.rfrlastorder, false, paramStringBuffer);
/*  578 */       this.rfrendofservice = copyfinaltoRFR(this.endofservice, this.rfrendofservice, false, paramStringBuffer);
/*  579 */       this.rfreosanndate = copyfinaltoRFR(this.eosanndate, this.rfreosanndate, false, paramStringBuffer);
/*  580 */       if (this.existfinalT2) {
/*  581 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is  exist final T2:" + paramString + NEWLINE);
/*  582 */         setAction("Update");
/*  583 */         setrfrAction("Update");
/*      */       } else {
/*      */         
/*  586 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is not exist final T2:" + paramString + NEWLINE);
/*  587 */         setAction("@@");
/*  588 */         setrfrAction("Update");
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  593 */       ABRUtil.append(paramStringBuffer, "setallfileds: coutry is both exist T1 and T2:" + paramString + NEWLINE);
/*      */       
/*  595 */       str2 = copyfinaltoRFR(str1, str2, true, paramStringBuffer);
/*  596 */       str4 = copyfinaltoRFR(str3, str4, true, paramStringBuffer);
/*  597 */       str6 = copyfinaltoRFR(str5, str6, true, paramStringBuffer);
/*  598 */       str8 = copyfinaltoRFR(str7, str8, true, paramStringBuffer);
/*  599 */       str10 = copyfinaltoRFR(str9, str10, true, paramStringBuffer);
/*  600 */       str12 = copyfinaltoRFR(str11, str12, true, paramStringBuffer);
/*  601 */       str14 = copyfinaltoRFR(str13, str14, true, paramStringBuffer);
/*  602 */       str16 = copyfinaltoRFR(str15, str16, true, paramStringBuffer);
/*  603 */       str18 = copyfinaltoRFR(str17, str18, true, paramStringBuffer);
/*  604 */       str20 = copyfinaltoRFR(str19, str20, true, paramStringBuffer);
/*      */       
/*  606 */       this.rfranndate = copyfinaltoRFR(this.anndate, this.rfranndate, false, paramStringBuffer);
/*  607 */       this.rfrannnumber = copyfinaltoRFR(this.annnumber, this.rfrannnumber, false, paramStringBuffer);
/*  608 */       this.rfrfirstorder = copyfinaltoRFR(this.firstorder, this.rfrfirstorder, false, paramStringBuffer);
/*  609 */       this.rfrplannedavailability = copyfinaltoRFR(this.plannedavailability, this.rfrplannedavailability, false, paramStringBuffer);
/*  610 */       this.rfrpubfrom = copyfinaltoRFR(this.pubfrom, this.rfrpubfrom, false, paramStringBuffer);
/*  611 */       this.rfrpubto = copyfinaltoRFR(this.pubto, this.rfrpubto, false, paramStringBuffer);
/*  612 */       this.rfrwdanndate = copyfinaltoRFR(this.wdanndate, this.rfrwdanndate, false, paramStringBuffer);
/*  613 */       this.rfrlastorder = copyfinaltoRFR(this.lastorder, this.rfrlastorder, false, paramStringBuffer);
/*  614 */       this.rfrendofservice = copyfinaltoRFR(this.endofservice, this.rfrendofservice, false, paramStringBuffer);
/*  615 */       this.rfreosanndate = copyfinaltoRFR(this.eosanndate, this.rfreosanndate, false, paramStringBuffer);
/*      */       
/*  617 */       if (this.existfinalT1 && !this.existfinalT2) {
/*  618 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  exist final T1 but T2:" + paramString + NEWLINE);
/*  619 */         setAction("Delete");
/*  620 */         setfinalAllfieldsEmpty();
/*      */       }
/*  622 */       else if (this.existfinalT2 && !this.existfinalT1) {
/*  623 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  exist final T2 but T1:" + paramString + NEWLINE);
/*  624 */         setAction("Update");
/*  625 */         setrfrAction("Update");
/*      */       }
/*  627 */       else if (this.existfinalT2 && this.existfinalT1) {
/*  628 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  exist final T1 and T2:" + paramString + NEWLINE);
/*  629 */         compareT1vT2(this.anndate, str1, false);
/*  630 */         compareT1vT2(this.annnumber, str3, false);
/*  631 */         compareT1vT2(this.firstorder, str5, false);
/*  632 */         compareT1vT2(this.plannedavailability, str7, false);
/*  633 */         compareT1vT2(this.pubfrom, str9, false);
/*  634 */         compareT1vT2(this.pubto, str11, false);
/*  635 */         compareT1vT2(this.wdanndate, str13, false);
/*  636 */         compareT1vT2(this.lastorder, str15, false);
/*  637 */         compareT1vT2(this.endofservice, str17, false);
/*  638 */         compareT1vT2(this.eosanndate, str19, false);
/*  639 */         ABRUtil.append(paramStringBuffer, "setallfileds: after compare action :" + this.action + NEWLINE);
/*      */       } else {
/*      */         
/*  642 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  not exist final T1 and T2:" + paramString + NEWLINE);
/*  643 */         setAction("@@");
/*      */       } 
/*  645 */       compareT1vT2(this.rfranndate, str2, true);
/*  646 */       compareT1vT2(this.rfrannnumber, str4, true);
/*  647 */       compareT1vT2(this.rfrfirstorder, str6, true);
/*  648 */       compareT1vT2(this.rfrplannedavailability, str8, true);
/*  649 */       compareT1vT2(this.rfrpubfrom, str10, true);
/*  650 */       compareT1vT2(this.rfrpubto, str12, true);
/*  651 */       compareT1vT2(this.rfrwdanndate, str14, true);
/*  652 */       compareT1vT2(this.rfrlastorder, str16, true);
/*  653 */       compareT1vT2(this.rfrendofservice, str18, true);
/*  654 */       compareT1vT2(this.rfreosanndate, str20, true);
/*  655 */       ABRUtil.append(paramStringBuffer, "setallfileds: after compare rfr values action:" + this.rfraction + NEWLINE);
/*      */     } 
/*      */     
/*  658 */     if (!paramBoolean2) {
/*  659 */       if (paramBoolean1) {
/*  660 */         setrfrAction("@@");
/*      */       } else {
/*  662 */         setAction("@@");
/*      */       } 
/*      */     }
/*      */     
/*  666 */     return this.existfinalT2;
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
/*      */   boolean handleResults(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, String[] paramArrayOfString7, String[] paramArrayOfString8, String[] paramArrayOfString9, String[] paramArrayOfString10, String[] paramArrayOfString11, String[] paramArrayOfString12, String[] paramArrayOfString13, String[] paramArrayOfString14, String[] paramArrayOfString15, String[] paramArrayOfString16, String[] paramArrayOfString17, String[] paramArrayOfString18, String[] paramArrayOfString19, String[] paramArrayOfString20, String[] paramArrayOfString21, String[] paramArrayOfString22, String paramString, boolean paramBoolean1, boolean paramBoolean2, StringBuffer paramStringBuffer) {
/*  680 */     String str1 = "@@";
/*  681 */     String str2 = "@@";
/*  682 */     String str3 = "@@";
/*  683 */     String str4 = "@@";
/*  684 */     String str5 = "@@";
/*  685 */     String str6 = "@@";
/*  686 */     String str7 = "@@";
/*  687 */     String str8 = "@@";
/*  688 */     String str9 = "@@";
/*  689 */     String str10 = "@@";
/*  690 */     String str11 = "@@";
/*  691 */     String str12 = "@@";
/*  692 */     String str13 = "@@";
/*  693 */     String str14 = "@@";
/*  694 */     String str15 = "@@";
/*  695 */     String str16 = "@@";
/*  696 */     String str17 = "@@";
/*  697 */     String str18 = "@@";
/*  698 */     String str19 = "@@";
/*  699 */     String str20 = "@@";
/*  700 */     String str21 = "@@";
/*  701 */     String str22 = "@@";
/*      */     
/*  703 */     this.anndate = paramArrayOfString1[0];
/*  704 */     this.rfranndate = paramArrayOfString1[1];
/*  705 */     str1 = paramArrayOfString2[0];
/*  706 */     str2 = paramArrayOfString2[1];
/*  707 */     this.annnumber = paramArrayOfString3[0];
/*  708 */     this.rfrannnumber = paramArrayOfString3[1];
/*  709 */     str3 = paramArrayOfString4[0];
/*  710 */     str4 = paramArrayOfString4[1];
/*  711 */     this.firstorder = paramArrayOfString5[0];
/*  712 */     this.rfrfirstorder = paramArrayOfString5[1];
/*  713 */     str5 = paramArrayOfString6[0];
/*  714 */     str6 = paramArrayOfString6[1];
/*  715 */     this.plannedavailability = paramArrayOfString7[0];
/*  716 */     this.rfrplannedavailability = paramArrayOfString7[1];
/*  717 */     str7 = paramArrayOfString8[0];
/*  718 */     str8 = paramArrayOfString8[1];
/*  719 */     this.pubfrom = paramArrayOfString9[0];
/*  720 */     this.rfrpubfrom = paramArrayOfString9[1];
/*  721 */     str9 = paramArrayOfString10[0];
/*  722 */     str10 = paramArrayOfString10[1];
/*  723 */     this.pubto = paramArrayOfString11[0];
/*  724 */     this.rfrpubto = paramArrayOfString11[1];
/*  725 */     str11 = paramArrayOfString12[0];
/*  726 */     str12 = paramArrayOfString12[1];
/*  727 */     this.wdanndate = paramArrayOfString13[0];
/*  728 */     this.rfrwdanndate = paramArrayOfString13[1];
/*  729 */     str13 = paramArrayOfString14[0];
/*  730 */     str14 = paramArrayOfString14[1];
/*  731 */     this.lastorder = paramArrayOfString17[0];
/*  732 */     this.rfrlastorder = paramArrayOfString17[1];
/*  733 */     str15 = paramArrayOfString18[0];
/*  734 */     str16 = paramArrayOfString18[1];
/*  735 */     this.endofservice = paramArrayOfString19[0];
/*  736 */     this.rfrendofservice = paramArrayOfString19[1];
/*  737 */     str17 = paramArrayOfString20[0];
/*  738 */     str18 = paramArrayOfString20[1];
/*  739 */     this.eosanndate = paramArrayOfString21[0];
/*  740 */     this.rfreosanndate = paramArrayOfString21[1];
/*  741 */     str19 = paramArrayOfString22[0];
/*  742 */     str20 = paramArrayOfString22[1];
/*  743 */     this.eomannnum = paramArrayOfString15[0];
/*  744 */     this.rfreomannnum = paramArrayOfString15[1];
/*  745 */     str21 = paramArrayOfString16[0];
/*  746 */     str22 = paramArrayOfString16[1];
/*      */     
/*  748 */     if ("Delete".equals(this.action)) {
/*  749 */       ABRUtil.append(paramStringBuffer, "setallfileds: coutry is delete:" + paramString);
/*      */       
/*  751 */       str2 = copyfinaltoRFR(str1, str2, true, paramStringBuffer);
/*  752 */       str4 = copyfinaltoRFR(str3, str4, true, paramStringBuffer);
/*  753 */       str6 = copyfinaltoRFR(str5, str6, true, paramStringBuffer);
/*  754 */       str8 = copyfinaltoRFR(str7, str8, true, paramStringBuffer);
/*  755 */       str10 = copyfinaltoRFR(str9, str10, true, paramStringBuffer);
/*  756 */       str12 = copyfinaltoRFR(str11, str12, true, paramStringBuffer);
/*  757 */       str14 = copyfinaltoRFR(str13, str14, true, paramStringBuffer);
/*  758 */       str16 = copyfinaltoRFR(str15, str16, true, paramStringBuffer);
/*  759 */       str18 = copyfinaltoRFR(str17, str18, true, paramStringBuffer);
/*  760 */       str20 = copyfinaltoRFR(str19, str20, true, paramStringBuffer);
/*  761 */       str22 = copyfinaltoRFR(str21, str22, true, paramStringBuffer);
/*  762 */       if (this.existfinalT1) {
/*  763 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is exist final T1:" + paramString + NEWLINE);
/*  764 */         setAction("Delete");
/*  765 */         setrfrAction("Delete");
/*  766 */         setAllfieldsEmpty();
/*      */       } else {
/*      */         
/*  769 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is not exist final T1:" + paramString + NEWLINE);
/*  770 */         setAction("@@");
/*  771 */         setrfrAction("Delete");
/*  772 */         setAllfieldsEmpty();
/*      */       }
/*      */     
/*      */     }
/*  776 */     else if ("Update".equals(this.action)) {
/*  777 */       ABRUtil.append(paramStringBuffer, "setallfileds: coutry is new:" + paramString + NEWLINE);
/*      */       
/*  779 */       this.rfranndate = copyfinaltoRFR(this.anndate, this.rfranndate, false, paramStringBuffer);
/*  780 */       this.rfrannnumber = copyfinaltoRFR(this.annnumber, this.rfrannnumber, false, paramStringBuffer);
/*  781 */       this.rfrfirstorder = copyfinaltoRFR(this.firstorder, this.rfrfirstorder, false, paramStringBuffer);
/*  782 */       this.rfrplannedavailability = copyfinaltoRFR(this.plannedavailability, this.rfrplannedavailability, false, paramStringBuffer);
/*  783 */       this.rfrpubfrom = copyfinaltoRFR(this.pubfrom, this.rfrpubfrom, false, paramStringBuffer);
/*  784 */       this.rfrpubto = copyfinaltoRFR(this.pubto, this.rfrpubto, false, paramStringBuffer);
/*  785 */       this.rfrwdanndate = copyfinaltoRFR(this.wdanndate, this.rfrwdanndate, false, paramStringBuffer);
/*  786 */       this.rfrlastorder = copyfinaltoRFR(this.lastorder, this.rfrlastorder, false, paramStringBuffer);
/*  787 */       this.rfrendofservice = copyfinaltoRFR(this.endofservice, this.rfrendofservice, false, paramStringBuffer);
/*  788 */       this.rfreosanndate = copyfinaltoRFR(this.eosanndate, this.rfreosanndate, false, paramStringBuffer);
/*  789 */       this.rfreomannnum = copyfinaltoRFR(this.eomannnum, this.rfreomannnum, false, paramStringBuffer);
/*  790 */       if (this.existfinalT2) {
/*  791 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is  exist final T2:" + paramString + NEWLINE);
/*  792 */         setAction("Update");
/*  793 */         setrfrAction("Update");
/*      */       } else {
/*      */         
/*  796 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is not exist final T2:" + paramString + NEWLINE);
/*  797 */         setAction("@@");
/*  798 */         setrfrAction("Update");
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  803 */       ABRUtil.append(paramStringBuffer, "setallfileds: coutry is both exist T1 and T2:" + paramString + NEWLINE);
/*      */       
/*  805 */       str2 = copyfinaltoRFR(str1, str2, true, paramStringBuffer);
/*  806 */       str4 = copyfinaltoRFR(str3, str4, true, paramStringBuffer);
/*  807 */       str6 = copyfinaltoRFR(str5, str6, true, paramStringBuffer);
/*  808 */       str8 = copyfinaltoRFR(str7, str8, true, paramStringBuffer);
/*  809 */       str10 = copyfinaltoRFR(str9, str10, true, paramStringBuffer);
/*  810 */       str12 = copyfinaltoRFR(str11, str12, true, paramStringBuffer);
/*  811 */       str14 = copyfinaltoRFR(str13, str14, true, paramStringBuffer);
/*  812 */       str16 = copyfinaltoRFR(str15, str16, true, paramStringBuffer);
/*  813 */       str18 = copyfinaltoRFR(str17, str18, true, paramStringBuffer);
/*  814 */       str20 = copyfinaltoRFR(str19, str20, true, paramStringBuffer);
/*  815 */       str22 = copyfinaltoRFR(str21, str22, true, paramStringBuffer);
/*      */       
/*  817 */       this.rfranndate = copyfinaltoRFR(this.anndate, this.rfranndate, false, paramStringBuffer);
/*  818 */       this.rfrannnumber = copyfinaltoRFR(this.annnumber, this.rfrannnumber, false, paramStringBuffer);
/*  819 */       this.rfrfirstorder = copyfinaltoRFR(this.firstorder, this.rfrfirstorder, false, paramStringBuffer);
/*  820 */       this.rfrplannedavailability = copyfinaltoRFR(this.plannedavailability, this.rfrplannedavailability, false, paramStringBuffer);
/*  821 */       this.rfrpubfrom = copyfinaltoRFR(this.pubfrom, this.rfrpubfrom, false, paramStringBuffer);
/*  822 */       this.rfrpubto = copyfinaltoRFR(this.pubto, this.rfrpubto, false, paramStringBuffer);
/*  823 */       this.rfrwdanndate = copyfinaltoRFR(this.wdanndate, this.rfrwdanndate, false, paramStringBuffer);
/*  824 */       this.rfrlastorder = copyfinaltoRFR(this.lastorder, this.rfrlastorder, false, paramStringBuffer);
/*  825 */       this.rfrendofservice = copyfinaltoRFR(this.endofservice, this.rfrendofservice, false, paramStringBuffer);
/*  826 */       this.rfreosanndate = copyfinaltoRFR(this.eosanndate, this.rfreosanndate, false, paramStringBuffer);
/*  827 */       this.rfreomannnum = copyfinaltoRFR(this.eomannnum, this.rfreomannnum, false, paramStringBuffer);
/*      */       
/*  829 */       if (this.existfinalT1 && !this.existfinalT2) {
/*  830 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  exist final T1 but T2:" + paramString + NEWLINE);
/*  831 */         setAction("Delete");
/*  832 */         setfinalAllfieldsEmpty();
/*      */       }
/*  834 */       else if (this.existfinalT2 && !this.existfinalT1) {
/*  835 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  exist final T2 but T1:" + paramString + NEWLINE);
/*  836 */         setAction("Update");
/*  837 */         setrfrAction("Update");
/*      */       }
/*  839 */       else if (this.existfinalT2 && this.existfinalT1) {
/*  840 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  exist final T1 and T2:" + paramString + NEWLINE);
/*  841 */         compareT1vT2(this.anndate, str1, false);
/*  842 */         compareT1vT2(this.annnumber, str3, false);
/*  843 */         compareT1vT2(this.firstorder, str5, false);
/*  844 */         compareT1vT2(this.plannedavailability, str7, false);
/*  845 */         compareT1vT2(this.pubfrom, str9, false);
/*  846 */         compareT1vT2(this.pubto, str11, false);
/*  847 */         compareT1vT2(this.wdanndate, str13, false);
/*  848 */         compareT1vT2(this.lastorder, str15, false);
/*  849 */         compareT1vT2(this.endofservice, str17, false);
/*  850 */         compareT1vT2(this.eosanndate, str19, false);
/*  851 */         compareT1vT2(this.eomannnum, str21, false);
/*  852 */         ABRUtil.append(paramStringBuffer, "setallfileds: after compare action :" + this.action + NEWLINE);
/*      */       } else {
/*      */         
/*  855 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  not exist final T1 and T2:" + paramString + NEWLINE);
/*  856 */         setAction("@@");
/*      */       } 
/*  858 */       compareT1vT2(this.rfranndate, str2, true);
/*  859 */       compareT1vT2(this.rfrannnumber, str4, true);
/*  860 */       compareT1vT2(this.rfrfirstorder, str6, true);
/*  861 */       compareT1vT2(this.rfrplannedavailability, str8, true);
/*  862 */       compareT1vT2(this.rfrpubfrom, str10, true);
/*  863 */       compareT1vT2(this.rfrpubto, str12, true);
/*  864 */       compareT1vT2(this.rfrwdanndate, str14, true);
/*  865 */       compareT1vT2(this.rfrlastorder, str16, true);
/*  866 */       compareT1vT2(this.rfrendofservice, str18, true);
/*  867 */       compareT1vT2(this.rfreosanndate, str20, true);
/*  868 */       compareT1vT2(this.rfreomannnum, str22, true);
/*  869 */       ABRUtil.append(paramStringBuffer, "setallfileds: after compare rfr values action:" + this.rfraction + NEWLINE);
/*      */     } 
/*      */     
/*  872 */     if (!paramBoolean2) {
/*  873 */       if (paramBoolean1) {
/*  874 */         setrfrAction("@@");
/*      */       } else {
/*  876 */         setAction("@@");
/*      */       } 
/*      */     }
/*      */     
/*  880 */     return this.existfinalT2;
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
/*      */   boolean handleResults(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, String[] paramArrayOfString7, String[] paramArrayOfString8, String[] paramArrayOfString9, String[] paramArrayOfString10, String[] paramArrayOfString11, String[] paramArrayOfString12, String[] paramArrayOfString13, String[] paramArrayOfString14, String[] paramArrayOfString15, String[] paramArrayOfString16, String[] paramArrayOfString17, String[] paramArrayOfString18, String[] paramArrayOfString19, String[] paramArrayOfString20, String[] paramArrayOfString21, String[] paramArrayOfString22, String[] paramArrayOfString23, String[] paramArrayOfString24, String paramString, boolean paramBoolean1, boolean paramBoolean2, StringBuffer paramStringBuffer) {
/*  894 */     String str1 = "@@";
/*  895 */     String str2 = "@@";
/*  896 */     String str3 = "@@";
/*  897 */     String str4 = "@@";
/*  898 */     String str5 = "@@";
/*  899 */     String str6 = "@@";
/*  900 */     String str7 = "@@";
/*  901 */     String str8 = "@@";
/*  902 */     String str9 = "@@";
/*  903 */     String str10 = "@@";
/*  904 */     String str11 = "@@";
/*  905 */     String str12 = "@@";
/*  906 */     String str13 = "@@";
/*  907 */     String str14 = "@@";
/*  908 */     String str15 = "@@";
/*  909 */     String str16 = "@@";
/*  910 */     String str17 = "@@";
/*  911 */     String str18 = "@@";
/*  912 */     String str19 = "@@";
/*  913 */     String str20 = "@@";
/*  914 */     String str21 = "@@";
/*  915 */     String str22 = "@@";
/*  916 */     String str23 = "@@";
/*  917 */     String str24 = "@@";
/*      */     
/*  919 */     this.anndate = paramArrayOfString1[0];
/*  920 */     this.rfranndate = paramArrayOfString1[1];
/*  921 */     str1 = paramArrayOfString2[0];
/*  922 */     str2 = paramArrayOfString2[1];
/*  923 */     this.annnumber = paramArrayOfString3[0];
/*  924 */     this.rfrannnumber = paramArrayOfString3[1];
/*  925 */     str3 = paramArrayOfString4[0];
/*  926 */     str4 = paramArrayOfString4[1];
/*  927 */     this.firstorder = paramArrayOfString5[0];
/*  928 */     this.rfrfirstorder = paramArrayOfString5[1];
/*  929 */     str5 = paramArrayOfString6[0];
/*  930 */     str6 = paramArrayOfString6[1];
/*  931 */     this.plannedavailability = paramArrayOfString7[0];
/*  932 */     this.rfrplannedavailability = paramArrayOfString7[1];
/*  933 */     str7 = paramArrayOfString8[0];
/*  934 */     str8 = paramArrayOfString8[1];
/*  935 */     this.pubfrom = paramArrayOfString9[0];
/*  936 */     this.rfrpubfrom = paramArrayOfString9[1];
/*  937 */     str9 = paramArrayOfString10[0];
/*  938 */     str10 = paramArrayOfString10[1];
/*  939 */     this.pubto = paramArrayOfString11[0];
/*  940 */     this.rfrpubto = paramArrayOfString11[1];
/*  941 */     str11 = paramArrayOfString12[0];
/*  942 */     str12 = paramArrayOfString12[1];
/*  943 */     this.wdanndate = paramArrayOfString13[0];
/*  944 */     this.rfrwdanndate = paramArrayOfString13[1];
/*  945 */     str13 = paramArrayOfString14[0];
/*  946 */     str14 = paramArrayOfString14[1];
/*  947 */     this.eomannnum = paramArrayOfString15[0];
/*  948 */     this.rfreomannnum = paramArrayOfString15[1];
/*  949 */     str15 = paramArrayOfString16[0];
/*  950 */     str16 = paramArrayOfString16[1];
/*  951 */     this.lastorder = paramArrayOfString17[0];
/*  952 */     this.rfrlastorder = paramArrayOfString17[1];
/*  953 */     str17 = paramArrayOfString18[0];
/*  954 */     str18 = paramArrayOfString18[1];
/*  955 */     this.endofservice = paramArrayOfString19[0];
/*  956 */     this.rfrendofservice = paramArrayOfString19[1];
/*  957 */     str19 = paramArrayOfString20[0];
/*  958 */     str20 = paramArrayOfString20[1];
/*  959 */     this.eosanndate = paramArrayOfString21[0];
/*  960 */     this.rfreosanndate = paramArrayOfString21[1];
/*  961 */     str21 = paramArrayOfString22[0];
/*  962 */     str22 = paramArrayOfString22[1];
/*  963 */     this.eosannnum = paramArrayOfString23[0];
/*  964 */     this.rfreosannnum = paramArrayOfString23[1];
/*  965 */     str23 = paramArrayOfString24[0];
/*  966 */     str24 = paramArrayOfString24[1];
/*      */     
/*  968 */     if ("Delete".equals(this.action)) {
/*  969 */       ABRUtil.append(paramStringBuffer, "setallfileds: coutry is delete:" + paramString);
/*      */       
/*  971 */       str2 = copyfinaltoRFR(str1, str2, true, paramStringBuffer);
/*  972 */       str4 = copyfinaltoRFR(str3, str4, true, paramStringBuffer);
/*  973 */       str6 = copyfinaltoRFR(str5, str6, true, paramStringBuffer);
/*  974 */       str8 = copyfinaltoRFR(str7, str8, true, paramStringBuffer);
/*  975 */       str10 = copyfinaltoRFR(str9, str10, true, paramStringBuffer);
/*  976 */       str12 = copyfinaltoRFR(str11, str12, true, paramStringBuffer);
/*  977 */       str14 = copyfinaltoRFR(str13, str14, true, paramStringBuffer);
/*  978 */       str16 = copyfinaltoRFR(str15, str16, true, paramStringBuffer);
/*  979 */       str18 = copyfinaltoRFR(str17, str18, true, paramStringBuffer);
/*  980 */       str20 = copyfinaltoRFR(str19, str20, true, paramStringBuffer);
/*  981 */       str22 = copyfinaltoRFR(str21, str22, true, paramStringBuffer);
/*  982 */       str24 = copyfinaltoRFR(str23, str24, true, paramStringBuffer);
/*  983 */       if (this.existfinalT1) {
/*  984 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is exist final T1:" + paramString + NEWLINE);
/*  985 */         setAction("Delete");
/*  986 */         setrfrAction("Delete");
/*  987 */         setAllfieldsEmpty();
/*      */       } else {
/*      */         
/*  990 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is not exist final T1:" + paramString + NEWLINE);
/*  991 */         setAction("@@");
/*  992 */         setrfrAction("Delete");
/*  993 */         setAllfieldsEmpty();
/*      */       }
/*      */     
/*      */     }
/*  997 */     else if ("Update".equals(this.action)) {
/*  998 */       ABRUtil.append(paramStringBuffer, "setallfileds: coutry is new:" + paramString + NEWLINE);
/*      */       
/* 1000 */       this.rfranndate = copyfinaltoRFR(this.anndate, this.rfranndate, false, paramStringBuffer);
/* 1001 */       this.rfrannnumber = copyfinaltoRFR(this.annnumber, this.rfrannnumber, false, paramStringBuffer);
/* 1002 */       this.rfrfirstorder = copyfinaltoRFR(this.firstorder, this.rfrfirstorder, false, paramStringBuffer);
/* 1003 */       this.rfrplannedavailability = copyfinaltoRFR(this.plannedavailability, this.rfrplannedavailability, false, paramStringBuffer);
/* 1004 */       this.rfrpubfrom = copyfinaltoRFR(this.pubfrom, this.rfrpubfrom, false, paramStringBuffer);
/* 1005 */       this.rfrpubto = copyfinaltoRFR(this.pubto, this.rfrpubto, false, paramStringBuffer);
/* 1006 */       this.rfrwdanndate = copyfinaltoRFR(this.wdanndate, this.rfrwdanndate, false, paramStringBuffer);
/* 1007 */       this.rfreomannnum = copyfinaltoRFR(this.eomannnum, this.rfreomannnum, false, paramStringBuffer);
/* 1008 */       this.rfrlastorder = copyfinaltoRFR(this.lastorder, this.rfrlastorder, false, paramStringBuffer);
/* 1009 */       this.rfrendofservice = copyfinaltoRFR(this.endofservice, this.rfrendofservice, false, paramStringBuffer);
/* 1010 */       this.rfreosanndate = copyfinaltoRFR(this.eosanndate, this.rfreosanndate, false, paramStringBuffer);
/* 1011 */       this.rfreosannnum = copyfinaltoRFR(this.eosannnum, this.rfreosannnum, false, paramStringBuffer);
/* 1012 */       if (this.existfinalT2) {
/* 1013 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is  exist final T2:" + paramString + NEWLINE);
/* 1014 */         setAction("Update");
/* 1015 */         setrfrAction("Update");
/*      */       } else {
/*      */         
/* 1018 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is not exist final T2:" + paramString + NEWLINE);
/* 1019 */         setAction("@@");
/* 1020 */         setrfrAction("Update");
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1025 */       ABRUtil.append(paramStringBuffer, "setallfileds: coutry is both exist T1 and T2:" + paramString + NEWLINE);
/*      */       
/* 1027 */       str2 = copyfinaltoRFR(str1, str2, true, paramStringBuffer);
/* 1028 */       str4 = copyfinaltoRFR(str3, str4, true, paramStringBuffer);
/* 1029 */       str6 = copyfinaltoRFR(str5, str6, true, paramStringBuffer);
/* 1030 */       str8 = copyfinaltoRFR(str7, str8, true, paramStringBuffer);
/* 1031 */       str10 = copyfinaltoRFR(str9, str10, true, paramStringBuffer);
/* 1032 */       str12 = copyfinaltoRFR(str11, str12, true, paramStringBuffer);
/* 1033 */       str14 = copyfinaltoRFR(str13, str14, true, paramStringBuffer);
/* 1034 */       str16 = copyfinaltoRFR(str15, str16, true, paramStringBuffer);
/* 1035 */       str18 = copyfinaltoRFR(str17, str18, true, paramStringBuffer);
/* 1036 */       str20 = copyfinaltoRFR(str19, str20, true, paramStringBuffer);
/* 1037 */       str22 = copyfinaltoRFR(str21, str22, true, paramStringBuffer);
/* 1038 */       str24 = copyfinaltoRFR(str23, str24, true, paramStringBuffer);
/*      */       
/* 1040 */       this.rfranndate = copyfinaltoRFR(this.anndate, this.rfranndate, false, paramStringBuffer);
/* 1041 */       this.rfrannnumber = copyfinaltoRFR(this.annnumber, this.rfrannnumber, false, paramStringBuffer);
/* 1042 */       this.rfrfirstorder = copyfinaltoRFR(this.firstorder, this.rfrfirstorder, false, paramStringBuffer);
/* 1043 */       this.rfrplannedavailability = copyfinaltoRFR(this.plannedavailability, this.rfrplannedavailability, false, paramStringBuffer);
/* 1044 */       this.rfrpubfrom = copyfinaltoRFR(this.pubfrom, this.rfrpubfrom, false, paramStringBuffer);
/* 1045 */       this.rfrpubto = copyfinaltoRFR(this.pubto, this.rfrpubto, false, paramStringBuffer);
/* 1046 */       this.rfrwdanndate = copyfinaltoRFR(this.wdanndate, this.rfrwdanndate, false, paramStringBuffer);
/* 1047 */       this.rfreomannnum = copyfinaltoRFR(this.eomannnum, this.rfreomannnum, true, paramStringBuffer);
/* 1048 */       this.rfrlastorder = copyfinaltoRFR(this.lastorder, this.rfrlastorder, false, paramStringBuffer);
/* 1049 */       this.rfrendofservice = copyfinaltoRFR(this.endofservice, this.rfrendofservice, false, paramStringBuffer);
/* 1050 */       this.rfreosanndate = copyfinaltoRFR(this.eosanndate, this.rfreosanndate, false, paramStringBuffer);
/* 1051 */       this.rfreosannnum = copyfinaltoRFR(this.eosannnum, this.rfreosannnum, true, paramStringBuffer);
/*      */       
/* 1053 */       if (this.existfinalT1 && !this.existfinalT2) {
/* 1054 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  exist final T1 but T2:" + paramString + NEWLINE);
/* 1055 */         setAction("Delete");
/* 1056 */         setfinalAllfieldsEmpty();
/*      */       }
/* 1058 */       else if (this.existfinalT2 && !this.existfinalT1) {
/* 1059 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  exist final T2 but T1:" + paramString + NEWLINE);
/* 1060 */         setAction("Update");
/* 1061 */         setrfrAction("Update");
/*      */       }
/* 1063 */       else if (this.existfinalT2 && this.existfinalT1) {
/* 1064 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  exist final T1 and T2:" + paramString + NEWLINE);
/* 1065 */         compareT1vT2(this.anndate, str1, false);
/* 1066 */         compareT1vT2(this.annnumber, str3, false);
/* 1067 */         compareT1vT2(this.firstorder, str5, false);
/* 1068 */         compareT1vT2(this.plannedavailability, str7, false);
/* 1069 */         compareT1vT2(this.pubfrom, str9, false);
/* 1070 */         compareT1vT2(this.pubto, str11, false);
/* 1071 */         compareT1vT2(this.wdanndate, str13, false);
/* 1072 */         compareT1vT2(this.eomannnum, str15, false);
/* 1073 */         compareT1vT2(this.lastorder, str17, false);
/* 1074 */         compareT1vT2(this.endofservice, str19, false);
/* 1075 */         compareT1vT2(this.eosanndate, str21, false);
/* 1076 */         compareT1vT2(this.eosannnum, str23, false);
/* 1077 */         ABRUtil.append(paramStringBuffer, "setallfileds: after compare action :" + this.action + NEWLINE);
/*      */       } else {
/*      */         
/* 1080 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  not exist final T1 and T2:" + paramString + NEWLINE);
/* 1081 */         setAction("@@");
/*      */       } 
/* 1083 */       compareT1vT2(this.rfranndate, str2, true);
/* 1084 */       compareT1vT2(this.rfrannnumber, str4, true);
/* 1085 */       compareT1vT2(this.rfrfirstorder, str6, true);
/* 1086 */       compareT1vT2(this.rfrplannedavailability, str8, true);
/* 1087 */       compareT1vT2(this.rfrpubfrom, str10, true);
/* 1088 */       compareT1vT2(this.rfrpubto, str12, true);
/* 1089 */       compareT1vT2(this.rfrwdanndate, str14, true);
/* 1090 */       compareT1vT2(this.rfreomannnum, str16, true);
/* 1091 */       compareT1vT2(this.rfrlastorder, str18, true);
/* 1092 */       compareT1vT2(this.rfrendofservice, str20, true);
/* 1093 */       compareT1vT2(this.rfreosanndate, str22, true);
/* 1094 */       compareT1vT2(this.rfreosannnum, str24, true);
/* 1095 */       ABRUtil.append(paramStringBuffer, "setallfileds: after compare rfr values action:" + this.rfraction + NEWLINE);
/*      */     } 
/*      */     
/* 1098 */     if (!paramBoolean2) {
/* 1099 */       if (paramBoolean1) {
/* 1100 */         setrfrAction("@@");
/*      */       } else {
/* 1102 */         setAction("@@");
/*      */       } 
/*      */     }
/*      */     
/* 1106 */     return this.existfinalT2;
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
/*      */   boolean handleResults(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, String[] paramArrayOfString7, String[] paramArrayOfString8, String[] paramArrayOfString9, String[] paramArrayOfString10, String[] paramArrayOfString11, String[] paramArrayOfString12, String[] paramArrayOfString13, String[] paramArrayOfString14, String[] paramArrayOfString15, String[] paramArrayOfString16, String[] paramArrayOfString17, String[] paramArrayOfString18, String[] paramArrayOfString19, String[] paramArrayOfString20, String[] paramArrayOfString21, String[] paramArrayOfString22, String[] paramArrayOfString23, String[] paramArrayOfString24, String[] paramArrayOfString25, String[] paramArrayOfString26, String[] paramArrayOfString27, String[] paramArrayOfString28, String[] paramArrayOfString29, String[] paramArrayOfString30, String paramString, boolean paramBoolean1, boolean paramBoolean2, StringBuffer paramStringBuffer) {
/* 1122 */     String str1 = "@@";
/* 1123 */     String str2 = "@@";
/* 1124 */     String str3 = "@@";
/* 1125 */     String str4 = "@@";
/* 1126 */     String str5 = "@@";
/* 1127 */     String str6 = "@@";
/* 1128 */     String str7 = "@@";
/* 1129 */     String str8 = "@@";
/* 1130 */     String str9 = "@@";
/* 1131 */     String str10 = "@@";
/* 1132 */     String str11 = "@@";
/* 1133 */     String str12 = "@@";
/* 1134 */     String str13 = "@@";
/* 1135 */     String str14 = "@@";
/* 1136 */     String str15 = "@@";
/* 1137 */     String str16 = "@@";
/* 1138 */     String str17 = "@@";
/* 1139 */     String str18 = "@@";
/* 1140 */     String str19 = "@@";
/* 1141 */     String str20 = "@@";
/* 1142 */     String str21 = "@@";
/* 1143 */     String str22 = "@@";
/* 1144 */     String str23 = "@@";
/* 1145 */     String str24 = "@@";
/* 1146 */     String str25 = "@@";
/* 1147 */     String str26 = "@@";
/* 1148 */     String str27 = "@@";
/* 1149 */     String str28 = "@@";
/* 1150 */     String str29 = "@@";
/* 1151 */     String str30 = "@@";
/*      */     
/* 1153 */     this.anndate = paramArrayOfString1[0];
/* 1154 */     this.rfranndate = paramArrayOfString1[1];
/* 1155 */     str1 = paramArrayOfString2[0];
/* 1156 */     str2 = paramArrayOfString2[1];
/* 1157 */     this.annnumber = paramArrayOfString3[0];
/* 1158 */     this.rfrannnumber = paramArrayOfString3[1];
/* 1159 */     str3 = paramArrayOfString4[0];
/* 1160 */     str4 = paramArrayOfString4[1];
/* 1161 */     this.firstorder = paramArrayOfString5[0];
/* 1162 */     this.rfrfirstorder = paramArrayOfString5[1];
/* 1163 */     str5 = paramArrayOfString6[0];
/* 1164 */     str6 = paramArrayOfString6[1];
/* 1165 */     this.plannedavailability = paramArrayOfString7[0];
/* 1166 */     this.rfrplannedavailability = paramArrayOfString7[1];
/* 1167 */     str7 = paramArrayOfString8[0];
/* 1168 */     str8 = paramArrayOfString8[1];
/* 1169 */     this.pubfrom = paramArrayOfString9[0];
/* 1170 */     this.rfrpubfrom = paramArrayOfString9[1];
/* 1171 */     str9 = paramArrayOfString10[0];
/* 1172 */     str10 = paramArrayOfString10[1];
/* 1173 */     this.pubto = paramArrayOfString11[0];
/* 1174 */     this.rfrpubto = paramArrayOfString11[1];
/* 1175 */     str11 = paramArrayOfString12[0];
/* 1176 */     str12 = paramArrayOfString12[1];
/* 1177 */     this.wdanndate = paramArrayOfString13[0];
/* 1178 */     this.rfrwdanndate = paramArrayOfString13[1];
/* 1179 */     str13 = paramArrayOfString14[0];
/* 1180 */     str14 = paramArrayOfString14[1];
/* 1181 */     this.eomannnum = paramArrayOfString15[0];
/* 1182 */     this.rfreomannnum = paramArrayOfString15[1];
/* 1183 */     str15 = paramArrayOfString16[0];
/* 1184 */     str16 = paramArrayOfString16[1];
/* 1185 */     this.lastorder = paramArrayOfString17[0];
/* 1186 */     this.rfrlastorder = paramArrayOfString17[1];
/* 1187 */     str17 = paramArrayOfString18[0];
/* 1188 */     str18 = paramArrayOfString18[1];
/* 1189 */     this.endofservice = paramArrayOfString19[0];
/* 1190 */     this.rfrendofservice = paramArrayOfString19[1];
/* 1191 */     str19 = paramArrayOfString20[0];
/* 1192 */     str20 = paramArrayOfString20[1];
/*      */     
/* 1194 */     this.eodanndate = paramArrayOfString21[0];
/* 1195 */     this.rfreodanndate = paramArrayOfString21[1];
/* 1196 */     str21 = paramArrayOfString21[0];
/* 1197 */     str22 = paramArrayOfString21[1];
/* 1198 */     this.eodavaildate = paramArrayOfString23[0];
/* 1199 */     this.rfreodavaildate = paramArrayOfString23[1];
/* 1200 */     str23 = paramArrayOfString24[0];
/* 1201 */     str24 = paramArrayOfString24[1];
/*      */     
/* 1203 */     this.eosanndate = paramArrayOfString25[0];
/* 1204 */     this.rfreosanndate = paramArrayOfString25[1];
/* 1205 */     str25 = paramArrayOfString26[0];
/* 1206 */     str26 = paramArrayOfString26[1];
/* 1207 */     this.eosannnum = paramArrayOfString27[0];
/* 1208 */     this.rfreosannnum = paramArrayOfString27[1];
/* 1209 */     str27 = paramArrayOfString28[0];
/* 1210 */     str28 = paramArrayOfString28[1];
/* 1211 */     this.ordersysname = paramArrayOfString29[0];
/* 1212 */     this.rfrordersysname = paramArrayOfString29[1];
/* 1213 */     str29 = paramArrayOfString30[0];
/* 1214 */     str30 = paramArrayOfString30[1];
/*      */ 
/*      */     
/* 1217 */     if ("Delete".equals(this.action)) {
/* 1218 */       ABRUtil.append(paramStringBuffer, "setallfileds: coutry is delete:" + paramString);
/*      */       
/* 1220 */       str2 = copyfinaltoRFR(str1, str2, true, paramStringBuffer);
/* 1221 */       str4 = copyfinaltoRFR(str3, str4, true, paramStringBuffer);
/* 1222 */       str6 = copyfinaltoRFR(str5, str6, true, paramStringBuffer);
/* 1223 */       str8 = copyfinaltoRFR(str7, str8, true, paramStringBuffer);
/*      */       
/* 1225 */       str10 = copyfinaltoRFR(str9, str10, true, paramStringBuffer);
/* 1226 */       str12 = copyfinaltoRFR(str11, str12, true, paramStringBuffer);
/* 1227 */       str14 = copyfinaltoRFR(str13, str14, true, paramStringBuffer);
/*      */       
/* 1229 */       str22 = copyfinaltoRFR(str21, str22, true, paramStringBuffer);
/* 1230 */       str24 = copyfinaltoRFR(str23, str24, true, paramStringBuffer);
/*      */ 
/*      */       
/* 1233 */       str16 = copyfinaltoRFR(str15, str16, true, paramStringBuffer);
/* 1234 */       str18 = copyfinaltoRFR(str17, str18, true, paramStringBuffer);
/* 1235 */       str20 = copyfinaltoRFR(str19, str20, true, paramStringBuffer);
/* 1236 */       str26 = copyfinaltoRFR(str25, str26, true, paramStringBuffer);
/*      */       
/* 1238 */       str28 = copyfinaltoRFR(str27, str28, true, paramStringBuffer);
/* 1239 */       str30 = copyfinaltoRFR(str29, str30, true, paramStringBuffer);
/*      */       
/* 1241 */       if (this.existfinalT1) {
/* 1242 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is exist final T1:" + paramString + NEWLINE);
/* 1243 */         setAction("Delete");
/* 1244 */         setrfrAction("Delete");
/* 1245 */         setAllfieldsEmpty();
/*      */       } else {
/*      */         
/* 1248 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is not exist final T1:" + paramString + NEWLINE);
/* 1249 */         setAction("@@");
/* 1250 */         setrfrAction("Delete");
/* 1251 */         setAllfieldsEmpty();
/*      */       }
/*      */     
/*      */     }
/* 1255 */     else if ("Update".equals(this.action)) {
/* 1256 */       ABRUtil.append(paramStringBuffer, "setallfileds: coutry is new:" + paramString + NEWLINE);
/*      */       
/* 1258 */       this.rfranndate = copyfinaltoRFR(this.anndate, this.rfranndate, false, paramStringBuffer);
/* 1259 */       this.rfrannnumber = copyfinaltoRFR(this.annnumber, this.rfrannnumber, false, paramStringBuffer);
/* 1260 */       this.rfrfirstorder = copyfinaltoRFR(this.firstorder, this.rfrfirstorder, false, paramStringBuffer);
/* 1261 */       this.rfrplannedavailability = copyfinaltoRFR(this.plannedavailability, this.rfrplannedavailability, false, paramStringBuffer);
/* 1262 */       this.rfrpubfrom = copyfinaltoRFR(this.pubfrom, this.rfrpubfrom, false, paramStringBuffer);
/* 1263 */       this.rfrpubto = copyfinaltoRFR(this.pubto, this.rfrpubto, false, paramStringBuffer);
/* 1264 */       this.rfrwdanndate = copyfinaltoRFR(this.wdanndate, this.rfrwdanndate, false, paramStringBuffer);
/*      */       
/* 1266 */       this.rfreodanndate = copyfinaltoRFR(this.eodanndate, this.rfreodanndate, false, paramStringBuffer);
/* 1267 */       this.rfreodavaildate = copyfinaltoRFR(this.eodavaildate, this.rfreodavaildate, false, paramStringBuffer);
/*      */       
/* 1269 */       this.rfreomannnum = copyfinaltoRFR(this.eomannnum, this.rfreomannnum, false, paramStringBuffer);
/* 1270 */       this.rfrlastorder = copyfinaltoRFR(this.lastorder, this.rfrlastorder, false, paramStringBuffer);
/* 1271 */       this.rfrendofservice = copyfinaltoRFR(this.endofservice, this.rfrendofservice, false, paramStringBuffer);
/* 1272 */       this.rfreosanndate = copyfinaltoRFR(this.eosanndate, this.rfreosanndate, false, paramStringBuffer);
/*      */       
/* 1274 */       this.rfreosannnum = copyfinaltoRFR(this.eosannnum, this.rfreosannnum, false, paramStringBuffer);
/* 1275 */       this.rfrordersysname = copyfinaltoRFR(this.ordersysname, this.rfrordersysname, false, paramStringBuffer);
/*      */       
/* 1277 */       if (this.existfinalT2) {
/* 1278 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is  exist final T2:" + paramString + NEWLINE);
/* 1279 */         setAction("Update");
/* 1280 */         setrfrAction("Update");
/*      */       } else {
/*      */         
/* 1283 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry is not exist final T2:" + paramString + NEWLINE);
/* 1284 */         setAction("@@");
/* 1285 */         setrfrAction("Update");
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1290 */       ABRUtil.append(paramStringBuffer, "setallfileds: coutry is both exist T1 and T2:" + paramString + NEWLINE);
/*      */       
/* 1292 */       str2 = copyfinaltoRFR(str1, str2, true, paramStringBuffer);
/* 1293 */       str4 = copyfinaltoRFR(str3, str4, true, paramStringBuffer);
/* 1294 */       str6 = copyfinaltoRFR(str5, str6, true, paramStringBuffer);
/* 1295 */       str8 = copyfinaltoRFR(str7, str8, true, paramStringBuffer);
/*      */       
/* 1297 */       str10 = copyfinaltoRFR(str9, str10, true, paramStringBuffer);
/* 1298 */       str12 = copyfinaltoRFR(str11, str12, true, paramStringBuffer);
/* 1299 */       str14 = copyfinaltoRFR(str13, str14, true, paramStringBuffer);
/*      */       
/* 1301 */       str22 = copyfinaltoRFR(str21, str22, true, paramStringBuffer);
/* 1302 */       str24 = copyfinaltoRFR(str23, str24, true, paramStringBuffer);
/*      */ 
/*      */       
/* 1305 */       str16 = copyfinaltoRFR(str15, str16, true, paramStringBuffer);
/* 1306 */       str18 = copyfinaltoRFR(str17, str18, true, paramStringBuffer);
/* 1307 */       str20 = copyfinaltoRFR(str19, str20, true, paramStringBuffer);
/* 1308 */       str26 = copyfinaltoRFR(str25, str26, true, paramStringBuffer);
/*      */       
/* 1310 */       str28 = copyfinaltoRFR(str27, str28, true, paramStringBuffer);
/* 1311 */       str30 = copyfinaltoRFR(str29, str30, true, paramStringBuffer);
/*      */ 
/*      */       
/* 1314 */       this.rfranndate = copyfinaltoRFR(this.anndate, this.rfranndate, false, paramStringBuffer);
/* 1315 */       this.rfrannnumber = copyfinaltoRFR(this.annnumber, this.rfrannnumber, false, paramStringBuffer);
/* 1316 */       this.rfrfirstorder = copyfinaltoRFR(this.firstorder, this.rfrfirstorder, false, paramStringBuffer);
/* 1317 */       this.rfrplannedavailability = copyfinaltoRFR(this.plannedavailability, this.rfrplannedavailability, false, paramStringBuffer);
/* 1318 */       this.rfrpubfrom = copyfinaltoRFR(this.pubfrom, this.rfrpubfrom, false, paramStringBuffer);
/* 1319 */       this.rfrpubto = copyfinaltoRFR(this.pubto, this.rfrpubto, false, paramStringBuffer);
/* 1320 */       this.rfrwdanndate = copyfinaltoRFR(this.wdanndate, this.rfrwdanndate, false, paramStringBuffer);
/*      */       
/* 1322 */       this.rfreodanndate = copyfinaltoRFR(this.eodanndate, this.rfreodanndate, false, paramStringBuffer);
/* 1323 */       this.rfreodavaildate = copyfinaltoRFR(this.eodavaildate, this.rfreodavaildate, false, paramStringBuffer);
/*      */       
/* 1325 */       this.rfreomannnum = copyfinaltoRFR(this.eomannnum, this.rfreomannnum, false, paramStringBuffer);
/* 1326 */       this.rfrlastorder = copyfinaltoRFR(this.lastorder, this.rfrlastorder, false, paramStringBuffer);
/* 1327 */       this.rfrendofservice = copyfinaltoRFR(this.endofservice, this.rfrendofservice, false, paramStringBuffer);
/* 1328 */       this.rfreosanndate = copyfinaltoRFR(this.eosanndate, this.rfreosanndate, false, paramStringBuffer);
/*      */       
/* 1330 */       this.rfreosannnum = copyfinaltoRFR(this.eosannnum, this.rfreosannnum, false, paramStringBuffer);
/* 1331 */       this.rfrordersysname = copyfinaltoRFR(this.ordersysname, this.rfrordersysname, false, paramStringBuffer);
/*      */ 
/*      */       
/* 1334 */       if (this.existfinalT1 && !this.existfinalT2) {
/* 1335 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  exist final T1 but T2:" + paramString + NEWLINE);
/* 1336 */         setAction("Delete");
/* 1337 */         setfinalAllfieldsEmpty();
/*      */       }
/* 1339 */       else if (this.existfinalT2 && !this.existfinalT1) {
/* 1340 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  exist final T2 but T1:" + paramString + NEWLINE);
/* 1341 */         setAction("Update");
/* 1342 */         setrfrAction("Update");
/*      */       }
/* 1344 */       else if (this.existfinalT2 && this.existfinalT1) {
/* 1345 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  exist final T1 and T2:" + paramString + NEWLINE);
/* 1346 */         compareT1vT2(this.anndate, str1, false);
/* 1347 */         compareT1vT2(this.annnumber, str3, false);
/* 1348 */         compareT1vT2(this.firstorder, str5, false);
/* 1349 */         compareT1vT2(this.plannedavailability, str7, false);
/* 1350 */         compareT1vT2(this.pubfrom, str9, false);
/* 1351 */         compareT1vT2(this.pubto, str11, false);
/* 1352 */         compareT1vT2(this.wdanndate, str13, false);
/*      */         
/* 1354 */         compareT1vT2(this.eodanndate, str21, false);
/* 1355 */         compareT1vT2(this.eodavaildate, str23, false);
/*      */         
/* 1357 */         compareT1vT2(this.eomannnum, str15, false);
/* 1358 */         compareT1vT2(this.lastorder, str17, false);
/* 1359 */         compareT1vT2(this.endofservice, str19, false);
/* 1360 */         compareT1vT2(this.eosanndate, str25, false);
/*      */         
/* 1362 */         compareT1vT2(this.eosannnum, str27, false);
/* 1363 */         compareT1vT2(this.ordersysname, str29, false);
/*      */         
/* 1365 */         ABRUtil.append(paramStringBuffer, "setallfileds: after compare action :" + this.action + NEWLINE);
/*      */       } else {
/*      */         
/* 1368 */         ABRUtil.append(paramStringBuffer, "setallfileds: coutry  not exist final T1 and T2:" + paramString + NEWLINE);
/* 1369 */         setAction("@@");
/*      */       } 
/* 1371 */       compareT1vT2(this.rfranndate, str2, true);
/* 1372 */       compareT1vT2(this.rfrannnumber, str4, true);
/* 1373 */       compareT1vT2(this.rfrfirstorder, str6, true);
/* 1374 */       compareT1vT2(this.rfrplannedavailability, str8, true);
/* 1375 */       compareT1vT2(this.rfrpubfrom, str10, true);
/* 1376 */       compareT1vT2(this.rfrpubto, str12, true);
/* 1377 */       compareT1vT2(this.rfrwdanndate, str14, true);
/*      */       
/* 1379 */       compareT1vT2(this.rfreodanndate, str22, false);
/* 1380 */       compareT1vT2(this.rfreodavaildate, str24, false);
/*      */       
/* 1382 */       compareT1vT2(this.rfreomannnum, str16, false);
/* 1383 */       compareT1vT2(this.rfrlastorder, str18, true);
/* 1384 */       compareT1vT2(this.rfrendofservice, str20, true);
/* 1385 */       compareT1vT2(this.rfreosanndate, str26, true);
/*      */       
/* 1387 */       compareT1vT2(this.rfreosannnum, str28, false);
/* 1388 */       compareT1vT2(this.rfrordersysname, str30, true);
/*      */       
/* 1390 */       ABRUtil.append(paramStringBuffer, "setallfileds: after compare rfr values action:" + this.rfraction + NEWLINE);
/*      */     } 
/*      */     
/* 1393 */     if (!paramBoolean2) {
/* 1394 */       if (paramBoolean1) {
/* 1395 */         setrfrAction("@@");
/*      */       } else {
/* 1397 */         setAction("@@");
/*      */       } 
/*      */     }
/*      */     
/* 1401 */     return this.existfinalT2;
/*      */   }
/*      */   
/*      */   String copyfinaltoRFR(String paramString1, String paramString2, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 1405 */     if (!"@@".equals(paramString1)) {
/* 1406 */       ABRUtil.append(paramStringBuffer, "Exist final data:" + paramString1 + " copy final to rfr.");
/* 1407 */       if (paramBoolean) {
/* 1408 */         this.existfinalT1 = true;
/*      */       } else {
/* 1410 */         this.existfinalT2 = true;
/*      */       } 
/* 1412 */       return paramString1;
/*      */     } 
/* 1414 */     return paramString2;
/*      */   }
/*      */ 
/*      */   
/*      */   void compareT1vT2(String paramString1, String paramString2, boolean paramBoolean) {
/* 1419 */     if (paramBoolean) {
/* 1420 */       if (!isrfrDisplayable() && 
/* 1421 */         !paramString1.equals(paramString2)) {
/* 1422 */         setrfrAction("Update");
/*      */       
/*      */       }
/*      */     }
/* 1426 */     else if (!isDisplayable() && 
/* 1427 */       !paramString1.equals(paramString2)) {
/* 1428 */       setAction("Update");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void setAllfieldsEmpty() {
/* 1435 */     this.rfrpubfrom = "@@";
/* 1436 */     this.rfrpubto = "@@";
/* 1437 */     this.rfrendofservice = "@@";
/* 1438 */     this.rfranndate = "@@";
/* 1439 */     this.rfrfirstorder = "@@";
/* 1440 */     this.rfrplannedavailability = "@@";
/* 1441 */     this.rfrwdanndate = "@@";
/* 1442 */     this.rfreomannnum = "@@";
/* 1443 */     this.rfrlastorder = "@@";
/* 1444 */     this.rfreosanndate = "@@";
/* 1445 */     this.rfreosannnum = "@@";
/* 1446 */     this.rfrannnumber = "@@";
/*      */     
/* 1448 */     this.pubfrom = "@@";
/* 1449 */     this.pubto = "@@";
/* 1450 */     this.endofservice = "@@";
/* 1451 */     this.anndate = "@@";
/* 1452 */     this.firstorder = "@@";
/* 1453 */     this.plannedavailability = "@@";
/* 1454 */     this.wdanndate = "@@";
/* 1455 */     this.eomannnum = "@@";
/* 1456 */     this.lastorder = "@@";
/* 1457 */     this.eosanndate = "@@";
/* 1458 */     this.eosannnum = "@@";
/* 1459 */     this.annnumber = "@@";
/*      */     
/* 1461 */     this.ordersysname = "@@";
/* 1462 */     this.rfrordersysname = "@@";
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
/*      */   void setfinalAllfieldsEmpty() {
/* 1476 */     this.pubfrom = "@@";
/* 1477 */     this.pubto = "@@";
/* 1478 */     this.endofservice = "@@";
/* 1479 */     this.anndate = "@@";
/* 1480 */     this.firstorder = "@@";
/* 1481 */     this.plannedavailability = "@@";
/* 1482 */     this.wdanndate = "@@";
/* 1483 */     this.eomannnum = "@@";
/* 1484 */     this.lastorder = "@@";
/* 1485 */     this.eosanndate = "@@";
/* 1486 */     this.eosannnum = "@@";
/* 1487 */     this.annnumber = "@@";
/* 1488 */     this.ordersysname = "@@";
/*      */   }
/*      */ 
/*      */   
/*      */   void dereference() {
/* 1493 */     this.action = null;
/*      */     
/* 1495 */     this.availStatus = null;
/* 1496 */     this.pubfrom = null;
/* 1497 */     this.pubto = null;
/* 1498 */     this.endofservice = null;
/* 1499 */     this.anndate = null;
/* 1500 */     this.firstorder = null;
/* 1501 */     this.plannedavailability = null;
/* 1502 */     this.wdanndate = null;
/* 1503 */     this.eomannnum = null;
/* 1504 */     this.lastorder = null;
/* 1505 */     this.eosanndate = null;
/* 1506 */     this.eosannnum = null;
/* 1507 */     this.annnumber = null;
/*      */     
/* 1509 */     this.rfrpubfrom = null;
/* 1510 */     this.rfrpubto = null;
/* 1511 */     this.rfrendofservice = null;
/* 1512 */     this.rfranndate = null;
/* 1513 */     this.rfrfirstorder = null;
/* 1514 */     this.rfrplannedavailability = null;
/* 1515 */     this.rfrwdanndate = null;
/* 1516 */     this.rfreomannnum = null;
/* 1517 */     this.rfrlastorder = null;
/* 1518 */     this.rfreosanndate = null;
/* 1519 */     this.rfreosannnum = null;
/* 1520 */     this.rfrannnumber = null;
/*      */     
/* 1522 */     this.ordersysname = null;
/* 1523 */     this.rfrordersysname = null;
/*      */   } }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\CtryRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */