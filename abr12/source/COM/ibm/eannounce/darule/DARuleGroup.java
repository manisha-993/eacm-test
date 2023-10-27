/*     */ package COM.ibm.eannounce.darule;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*     */ import COM.ibm.eannounce.objects.DeleteActionItem;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.LockException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.Serializable;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collections;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DARuleGroup
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  95 */   private String relatorType = null;
/*  96 */   private String createActionName = null;
/*  97 */   private String attributeCode = null;
/*  98 */   private Vector daRuleVct = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   protected static final Hashtable CATREL_TBL = new Hashtable<>(); static {
/* 112 */     CATREL_TBL.put("FEATURE", "FEATURECATDATA");
/* 113 */     CATREL_TBL.put("MODEL", "MODELCATDATA");
/* 114 */     CATREL_TBL.put("WWSEO", "WWSEOCATDATA");
/* 115 */     CATREL_TBL.put("SVCMOD", "SVCMODCATDATA");
/* 116 */     CATREL_TBL.put("LSEO", "LSEOCATDATA");
/* 117 */     CATREL_TBL.put("LSEOBUNDLE", "LSEOBUNDLECATDATA");
/* 118 */     CATREL_TBL.put("SWFEATURE", "SWFEATURECATDATA");
/*     */   }
/*     */ 
/*     */   
/* 122 */   private static final Hashtable CATACTION_TBL = new Hashtable<>(); static {
/* 123 */     CATACTION_TBL.put("FEATURE", "CRFEATCATDATA");
/* 124 */     CATACTION_TBL.put("MODEL", "CRMDLCATDATA");
/* 125 */     CATACTION_TBL.put("WWSEO", "CRWWSEOCATDATA");
/* 126 */     CATACTION_TBL.put("SVCMOD", "CRSVCMCATDATA");
/* 127 */     CATACTION_TBL.put("LSEO", "CRLSEOCATDATA");
/* 128 */     CATACTION_TBL.put("LSEOBUNDLE", "CRLSEOBCATDATA");
/* 129 */     CATACTION_TBL.put("SWFEATURE", "CRSWFCATDATA");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String DELETEACTION_NAME = "DELCATDATA";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DARuleGroup(Database paramDatabase, Profile paramProfile, String paramString1, String paramString2, StringBuffer paramStringBuffer) throws Exception {
/* 147 */     this(paramString2, paramString1);
/*     */ 
/*     */     
/* 150 */     EntityItem[] arrayOfEntityItem = DARuleEngine.searchForDARules(paramDatabase, paramProfile, paramString1, paramString2, paramStringBuffer);
/* 151 */     if (arrayOfEntityItem != null) {
/*     */       
/* 153 */       verifyDARules(arrayOfEntityItem, paramStringBuffer);
/*     */ 
/*     */       
/* 156 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 157 */         EntityItem entityItem = arrayOfEntityItem[b];
/* 158 */         String str1 = PokUtils.getAttributeFlagValue(entityItem, "DARULETYPE");
/* 159 */         String str2 = PokUtils.getAttributeFlagValue(entityItem, "DALIFECYCLE");
/* 160 */         DARuleEngineMgr.addDebugComment(3, paramStringBuffer, "DARuleGroup:[" + b + "] " + entityItem.getKey() + " ruletype " + str1 + " lifecycle " + str2);
/*     */ 
/*     */         
/* 163 */         if ("20".equals(str2) || "10"
/* 164 */           .equals(str2) || "50"
/* 165 */           .equals(str2)) {
/* 166 */           DARuleEquation dARuleEquation; DARuleSubstitution dARuleSubstitution = null;
/* 167 */           if ("20".equals(str1)) {
/* 168 */             dARuleSubstitution = new DARuleSubstitution(entityItem);
/* 169 */           } else if ("30".equals(str1)) {
/* 170 */             DARuleScanReplace dARuleScanReplace = new DARuleScanReplace(entityItem);
/* 171 */           } else if ("40".equals(str1)) {
/* 172 */             dARuleEquation = new DARuleEquation(entityItem);
/*     */           } 
/* 174 */           if (dARuleEquation != null) {
/* 175 */             addRuleItem(dARuleEquation);
/*     */           } else {
/* 177 */             DARuleEngineMgr.addDebugComment(0, paramStringBuffer, "DARuleGroup: " + entityItem.getKey() + " ruletype " + str1 + " is not supported");
/*     */           } 
/*     */         } else {
/* 180 */           DARuleEngineMgr.addDebugComment(1, paramStringBuffer, "DARuleGroup: " + entityItem.getKey() + " lifecycle " + str2 + " is not used for IDL");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void verifyDARules(EntityItem[] paramArrayOfEntityItem, StringBuffer paramStringBuffer) throws InvalidDARuleException {
/*     */     // Byte code:
/*     */     //   0: aconst_null
/*     */     //   1: astore_3
/*     */     //   2: aconst_null
/*     */     //   3: astore #4
/*     */     //   5: aconst_null
/*     */     //   6: astore #5
/*     */     //   8: new java/lang/StringBuffer
/*     */     //   11: dup
/*     */     //   12: ldc 'Invalid DARULE found:'
/*     */     //   14: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   17: astore #6
/*     */     //   19: iconst_0
/*     */     //   20: istore #7
/*     */     //   22: iload #7
/*     */     //   24: aload_1
/*     */     //   25: arraylength
/*     */     //   26: if_icmpge -> 521
/*     */     //   29: aload_1
/*     */     //   30: iload #7
/*     */     //   32: aaload
/*     */     //   33: astore #8
/*     */     //   35: aload #8
/*     */     //   37: ldc 'RULESEQ'
/*     */     //   39: ldc ''
/*     */     //   41: ldc '0'
/*     */     //   43: iconst_0
/*     */     //   44: invokestatic getAttributeValue : (LCOM/ibm/eannounce/objects/EntityItem;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)Ljava/lang/String;
/*     */     //   47: astore #9
/*     */     //   49: aload #8
/*     */     //   51: ldc 'DALIFECYCLE'
/*     */     //   53: invokestatic getAttributeFlagValue : (LCOM/ibm/eannounce/objects/EntityItem;Ljava/lang/String;)Ljava/lang/String;
/*     */     //   56: astore #10
/*     */     //   58: iconst_3
/*     */     //   59: aload_2
/*     */     //   60: new java/lang/StringBuilder
/*     */     //   63: dup
/*     */     //   64: invokespecial <init> : ()V
/*     */     //   67: ldc 'verifyDARules: '
/*     */     //   69: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   72: aload #8
/*     */     //   74: invokevirtual getKey : ()Ljava/lang/String;
/*     */     //   77: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   80: ldc ' sequence '
/*     */     //   82: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   85: aload #9
/*     */     //   87: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   90: ldc ' lifecycle '
/*     */     //   92: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   95: aload #10
/*     */     //   97: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   100: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   103: invokestatic addDebugComment : (ILjava/lang/StringBuffer;Ljava/lang/String;)V
/*     */     //   106: ldc '30'
/*     */     //   108: aload #10
/*     */     //   110: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   113: ifeq -> 172
/*     */     //   116: aload_3
/*     */     //   117: ifnonnull -> 128
/*     */     //   120: new java/util/Vector
/*     */     //   123: dup
/*     */     //   124: invokespecial <init> : ()V
/*     */     //   127: astore_3
/*     */     //   128: aload #6
/*     */     //   130: new java/lang/StringBuilder
/*     */     //   133: dup
/*     */     //   134: invokespecial <init> : ()V
/*     */     //   137: ldc ' '
/*     */     //   139: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   142: aload #8
/*     */     //   144: invokevirtual getKey : ()Ljava/lang/String;
/*     */     //   147: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   150: ldc ' DALIFECYCLE is Production.'
/*     */     //   152: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   155: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   158: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*     */     //   161: pop
/*     */     //   162: aload_3
/*     */     //   163: aload #8
/*     */     //   165: invokevirtual add : (Ljava/lang/Object;)Z
/*     */     //   168: pop
/*     */     //   169: goto -> 515
/*     */     //   172: ldc '60'
/*     */     //   174: aload #10
/*     */     //   176: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   179: ifeq -> 238
/*     */     //   182: aload_3
/*     */     //   183: ifnonnull -> 194
/*     */     //   186: new java/util/Vector
/*     */     //   189: dup
/*     */     //   190: invokespecial <init> : ()V
/*     */     //   193: astore_3
/*     */     //   194: aload #6
/*     */     //   196: new java/lang/StringBuilder
/*     */     //   199: dup
/*     */     //   200: invokespecial <init> : ()V
/*     */     //   203: ldc ' '
/*     */     //   205: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   208: aload #8
/*     */     //   210: invokevirtual getKey : ()Ljava/lang/String;
/*     */     //   213: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   216: ldc ' DALIFECYCLE is Retire.'
/*     */     //   218: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   221: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   224: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*     */     //   227: pop
/*     */     //   228: aload_3
/*     */     //   229: aload #8
/*     */     //   231: invokevirtual add : (Ljava/lang/Object;)Z
/*     */     //   234: pop
/*     */     //   235: goto -> 515
/*     */     //   238: ldc '20'
/*     */     //   240: aload #10
/*     */     //   242: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   245: ifeq -> 363
/*     */     //   248: aload #5
/*     */     //   250: ifnull -> 359
/*     */     //   253: aload_3
/*     */     //   254: ifnonnull -> 265
/*     */     //   257: new java/util/Vector
/*     */     //   260: dup
/*     */     //   261: invokespecial <init> : ()V
/*     */     //   264: astore_3
/*     */     //   265: aload_3
/*     */     //   266: aload #8
/*     */     //   268: invokevirtual add : (Ljava/lang/Object;)Z
/*     */     //   271: pop
/*     */     //   272: aload #6
/*     */     //   274: new java/lang/StringBuilder
/*     */     //   277: dup
/*     */     //   278: invokespecial <init> : ()V
/*     */     //   281: ldc ' '
/*     */     //   283: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   286: aload #8
/*     */     //   288: invokevirtual getKey : ()Ljava/lang/String;
/*     */     //   291: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   294: ldc ' DALIFECYCLE is Ready.'
/*     */     //   296: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   299: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   302: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*     */     //   305: pop
/*     */     //   306: aload_3
/*     */     //   307: aload #5
/*     */     //   309: invokevirtual contains : (Ljava/lang/Object;)Z
/*     */     //   312: ifne -> 515
/*     */     //   315: aload_3
/*     */     //   316: aload #5
/*     */     //   318: invokevirtual add : (Ljava/lang/Object;)Z
/*     */     //   321: pop
/*     */     //   322: aload #6
/*     */     //   324: new java/lang/StringBuilder
/*     */     //   327: dup
/*     */     //   328: invokespecial <init> : ()V
/*     */     //   331: ldc ' '
/*     */     //   333: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   336: aload #5
/*     */     //   338: invokevirtual getKey : ()Ljava/lang/String;
/*     */     //   341: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   344: ldc ' DALIFECYCLE is Ready.'
/*     */     //   346: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   349: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   352: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*     */     //   355: pop
/*     */     //   356: goto -> 515
/*     */     //   359: aload #8
/*     */     //   361: astore #5
/*     */     //   363: ldc '20'
/*     */     //   365: aload #10
/*     */     //   367: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   370: ifne -> 393
/*     */     //   373: ldc '10'
/*     */     //   375: aload #10
/*     */     //   377: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   380: ifne -> 393
/*     */     //   383: ldc '50'
/*     */     //   385: aload #10
/*     */     //   387: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   390: ifeq -> 515
/*     */     //   393: ldc '0'
/*     */     //   395: aload #9
/*     */     //   397: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   400: ifeq -> 515
/*     */     //   403: aload #4
/*     */     //   405: ifnull -> 511
/*     */     //   408: aload_3
/*     */     //   409: ifnonnull -> 420
/*     */     //   412: new java/util/Vector
/*     */     //   415: dup
/*     */     //   416: invokespecial <init> : ()V
/*     */     //   419: astore_3
/*     */     //   420: aload_3
/*     */     //   421: aload #8
/*     */     //   423: invokevirtual add : (Ljava/lang/Object;)Z
/*     */     //   426: pop
/*     */     //   427: aload #6
/*     */     //   429: new java/lang/StringBuilder
/*     */     //   432: dup
/*     */     //   433: invokespecial <init> : ()V
/*     */     //   436: ldc ' '
/*     */     //   438: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   441: aload #5
/*     */     //   443: invokevirtual getKey : ()Ljava/lang/String;
/*     */     //   446: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   449: ldc ' duplicate Sequence 0 or empty.'
/*     */     //   451: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   454: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   457: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*     */     //   460: pop
/*     */     //   461: aload_3
/*     */     //   462: aload #4
/*     */     //   464: invokevirtual contains : (Ljava/lang/Object;)Z
/*     */     //   467: ifne -> 511
/*     */     //   470: aload_3
/*     */     //   471: aload #4
/*     */     //   473: invokevirtual add : (Ljava/lang/Object;)Z
/*     */     //   476: pop
/*     */     //   477: aload #6
/*     */     //   479: new java/lang/StringBuilder
/*     */     //   482: dup
/*     */     //   483: invokespecial <init> : ()V
/*     */     //   486: ldc ' '
/*     */     //   488: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   491: aload #4
/*     */     //   493: invokevirtual getKey : ()Ljava/lang/String;
/*     */     //   496: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   499: ldc ' duplicate Sequence 0 or empty.'
/*     */     //   501: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   504: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   507: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*     */     //   510: pop
/*     */     //   511: aload #8
/*     */     //   513: astore #4
/*     */     //   515: iinc #7, 1
/*     */     //   518: goto -> 22
/*     */     //   521: aload_3
/*     */     //   522: ifnull -> 539
/*     */     //   525: new COM/ibm/eannounce/darule/InvalidDARuleException
/*     */     //   528: dup
/*     */     //   529: aload #6
/*     */     //   531: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   534: aload_3
/*     */     //   535: invokespecial <init> : (Ljava/lang/String;Ljava/util/Vector;)V
/*     */     //   538: athrow
/*     */     //   539: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #200	-> 0
/*     */     //   #201	-> 2
/*     */     //   #202	-> 5
/*     */     //   #203	-> 8
/*     */     //   #204	-> 19
/*     */     //   #205	-> 29
/*     */     //   #206	-> 35
/*     */     //   #207	-> 49
/*     */     //   #208	-> 58
/*     */     //   #211	-> 106
/*     */     //   #212	-> 116
/*     */     //   #213	-> 120
/*     */     //   #215	-> 128
/*     */     //   #216	-> 162
/*     */     //   #217	-> 169
/*     */     //   #219	-> 172
/*     */     //   #220	-> 182
/*     */     //   #221	-> 186
/*     */     //   #223	-> 194
/*     */     //   #224	-> 228
/*     */     //   #225	-> 235
/*     */     //   #228	-> 238
/*     */     //   #229	-> 248
/*     */     //   #230	-> 253
/*     */     //   #231	-> 257
/*     */     //   #233	-> 265
/*     */     //   #234	-> 272
/*     */     //   #235	-> 306
/*     */     //   #236	-> 315
/*     */     //   #237	-> 322
/*     */     //   #241	-> 359
/*     */     //   #245	-> 363
/*     */     //   #246	-> 377
/*     */     //   #247	-> 387
/*     */     //   #248	-> 393
/*     */     //   #249	-> 403
/*     */     //   #250	-> 408
/*     */     //   #251	-> 412
/*     */     //   #253	-> 420
/*     */     //   #254	-> 427
/*     */     //   #255	-> 461
/*     */     //   #256	-> 470
/*     */     //   #257	-> 477
/*     */     //   #260	-> 511
/*     */     //   #204	-> 515
/*     */     //   #265	-> 521
/*     */     //   #266	-> 525
/*     */     //   #268	-> 539
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DARuleGroup(String paramString1, String paramString2) {
/* 276 */     this.attributeCode = paramString1;
/* 277 */     this.relatorType = CATREL_TBL.get(paramString2).toString();
/* 278 */     this.createActionName = CATACTION_TBL.get(paramString2).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addRuleItem(DARuleItem paramDARuleItem) {
/* 286 */     if (this.daRuleVct == null) {
/* 287 */       this.daRuleVct = new Vector();
/*     */     }
/* 289 */     this.daRuleVct.add(paramDARuleItem);
/* 290 */     if (this.daRuleVct.size() > 1)
/*     */     {
/* 292 */       Collections.sort(this.daRuleVct);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean updateCatData(Database paramDatabase, Profile paramProfile, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws Exception {
/* 319 */     String str = getDerivedValue(paramDatabase, paramProfile, paramEntityItem, paramStringBuffer);
/*     */ 
/*     */ 
/*     */     
/* 323 */     return doCatdataUpdate(paramDatabase, paramProfile, paramEntityItem, str, paramStringBuffer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean[] idlCatData(Database paramDatabase, Profile paramProfile, EntityItem[] paramArrayOfEntityItem, StringBuffer paramStringBuffer1, StringBuffer paramStringBuffer2) throws Exception {
/* 352 */     String[] arrayOfString = getDerivedValues(paramDatabase, paramProfile, paramArrayOfEntityItem, paramStringBuffer2);
/*     */ 
/*     */ 
/*     */     
/* 356 */     return doCatdataUpdates(paramDatabase, paramProfile, paramArrayOfEntityItem, arrayOfString, paramStringBuffer1, paramStringBuffer2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getDARULEEntitys() {
/* 367 */     if (this.daRuleVct == null) {
/* 368 */       return null;
/*     */     }
/* 370 */     Vector<EntityItem> vector = new Vector(this.daRuleVct.size());
/* 371 */     for (byte b = 0; b < this.daRuleVct.size(); b++) {
/* 372 */       DARuleItem dARuleItem = this.daRuleVct.elementAt(b);
/* 373 */       vector.add(dARuleItem.getDARULEEntity());
/*     */     } 
/* 375 */     return vector;
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
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean doCatdataUpdate(Database paramDatabase, Profile paramProfile, EntityItem paramEntityItem, String paramString, StringBuffer paramStringBuffer) throws Exception {
/* 391 */     boolean bool = false;
/*     */     
/* 393 */     EntityList entityList = paramEntityItem.getEntityGroup().getEntityList();
/* 394 */     if (entityList == null) {
/* 395 */       throw new DARuleException("CATDATA EntityGroup not found.  No EntityList for " + paramEntityItem.getKey());
/*     */     }
/* 397 */     EntityGroup entityGroup = entityList.getEntityGroup("CATDATA");
/* 398 */     if (entityGroup == null) {
/* 399 */       throw new DARuleException("CATDATA EntityGroup not found in EntityList for " + paramEntityItem.getKey());
/*     */     }
/*     */ 
/*     */     
/* 403 */     int i = 254;
/* 404 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute("DAATTRIBUTEVALUE");
/* 405 */     if (eANMetaAttribute != null) {
/* 406 */       i = eANMetaAttribute.getMaxLen();
/*     */     }
/*     */     
/* 409 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, this.relatorType, "CATDATA");
/* 410 */     DARuleEngineMgr.addDebugComment(3, paramStringBuffer, "doCatdataUpdate: " + this.relatorType + " catdataVct.size " + vector
/* 411 */         .size() + " darGrpAttrcode " + this.attributeCode);
/*     */ 
/*     */     
/* 414 */     EntityItem entityItem = null;
/* 415 */     for (byte b = 0; b < vector.size(); b++) {
/* 416 */       EntityItem entityItem1 = vector.elementAt(b);
/* 417 */       String str = PokUtils.getAttributeFlagValue(entityItem1, "DAATTRIBUTECODE");
/* 418 */       DARuleEngineMgr.addDebugComment(3, paramStringBuffer, "doCatdataUpdate: " + entityItem1.getKey() + " catDataAttrcode " + str);
/* 419 */       if (this.attributeCode.equals(str)) {
/* 420 */         entityItem = entityItem1;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 425 */     if (paramString != null && paramString.trim().length() > 0 && 
/* 426 */       paramString.length() > i) {
/*     */       
/* 428 */       DARuleEngineMgr.addInformation(paramStringBuffer, "Warning: Derived value exceeded " + i + " characters.  It was truncated.");
/*     */ 
/*     */       
/* 431 */       paramString = paramString.substring(0, i - 1);
/*     */     } 
/*     */ 
/*     */     
/* 435 */     if (entityItem == null) {
/* 436 */       if (paramString != null && paramString.trim().length() > 0)
/*     */       {
/* 438 */         bool = createCATDATA(paramDatabase, paramProfile, entityList, paramEntityItem, paramString, paramStringBuffer, paramStringBuffer);
/*     */       }
/*     */     }
/* 441 */     else if (paramString != null && paramString.trim().length() > 0) {
/*     */       
/* 443 */       String str = PokUtils.getAttributeValue(entityItem, "DAATTRIBUTEVALUE", "", null, false);
/* 444 */       if (!paramString.equals(str)) {
/* 445 */         bool = true;
/* 446 */         updateAttribute(paramDatabase, entityItem, paramString, paramStringBuffer);
/*     */         
/* 448 */         DARuleEngineMgr.addInformation(paramStringBuffer, "Updated " + 
/* 449 */             DARuleEngineMgr.getNavigationName(paramDatabase, paramProfile, entityItem) + " for " + 
/* 450 */             DARuleEngineMgr.getNavigationName(paramDatabase, paramProfile, paramEntityItem));
/*     */       } else {
/* 452 */         DARuleEngineMgr.addDebugComment(3, paramStringBuffer, " " + entityItem.getKey() + " value did not change " + str);
/*     */       } 
/*     */     } else {
/*     */       
/* 456 */       bool = true;
/*     */       
/* 458 */       DARuleEngineMgr.addInformation(paramStringBuffer, "Deleted " + 
/* 459 */           DARuleEngineMgr.getNavigationName(paramDatabase, paramProfile, entityItem) + " for " + 
/* 460 */           DARuleEngineMgr.getNavigationName(paramDatabase, paramProfile, paramEntityItem));
/* 461 */       deleteCatdata(paramDatabase, paramProfile, new EntityItem[] { entityItem });
/*     */     } 
/*     */ 
/*     */     
/* 465 */     return bool;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean createCATDATA(Database paramDatabase, Profile paramProfile, EntityList paramEntityList, EntityItem paramEntityItem, String paramString, StringBuffer paramStringBuffer1, StringBuffer paramStringBuffer2) throws Exception {
/* 483 */     boolean bool = false;
/*     */     
/* 485 */     EntityItem entityItem = createCATDATA(paramDatabase, paramProfile, paramEntityItem, paramString, paramStringBuffer2);
/* 486 */     if (entityItem != null) {
/* 487 */       bool = true;
/* 488 */       EntityGroup entityGroup1 = paramEntityList.getEntityGroup("CATDATA");
/* 489 */       EntityList entityList = entityItem.getEntityGroup().getEntityList();
/* 490 */       EntityGroup entityGroup2 = entityList.getEntityGroup("CATDATA");
/* 491 */       entityGroup1.putEntityItem(entityItem);
/* 492 */       entityItem.reassign(entityGroup1);
/*     */       
/* 494 */       entityGroup2.removeEntityItem(entityItem);
/*     */       
/* 496 */       EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(0);
/* 497 */       entityGroup1 = paramEntityList.getEntityGroup(this.relatorType);
/* 498 */       entityGroup2 = entityList.getEntityGroup(this.relatorType);
/* 499 */       entityGroup1.putEntityItem(entityItem1);
/* 500 */       entityItem1.reassign(entityGroup1);
/*     */       
/* 502 */       entityGroup2.removeEntityItem(entityItem1);
/*     */ 
/*     */       
/* 505 */       entityList.dereference();
/*     */ 
/*     */       
/* 508 */       DARuleEngineMgr.addInformation(paramStringBuffer1, "Created and referenced " + 
/* 509 */           DARuleEngineMgr.getNavigationName(paramDatabase, paramProfile, entityItem) + " for " + 
/* 510 */           DARuleEngineMgr.getNavigationName(paramDatabase, paramProfile, paramEntityItem));
/*     */     } else {
/* 512 */       throw new DARuleException("Unable to create CATDATA for " + 
/* 513 */           DARuleEngineMgr.getNavigationName(paramDatabase, paramProfile, paramEntityItem));
/*     */     } 
/*     */     
/* 516 */     return bool;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void deleteCatdata(Database paramDatabase, Profile paramProfile, EntityItem[] paramArrayOfEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/* 535 */     DeleteActionItem deleteActionItem = new DeleteActionItem(null, paramDatabase, paramProfile, "DELCATDATA");
/*     */ 
/*     */     
/* 538 */     deleteActionItem.setEntityItems(paramArrayOfEntityItem);
/* 539 */     paramDatabase.executeAction(paramProfile, deleteActionItem);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean[] doCatdataUpdates(Database paramDatabase, Profile paramProfile, EntityItem[] paramArrayOfEntityItem, String[] paramArrayOfString, StringBuffer paramStringBuffer1, StringBuffer paramStringBuffer2) throws Exception {
/* 555 */     boolean[] arrayOfBoolean = new boolean[paramArrayOfEntityItem.length];
/* 556 */     boolean bool = false;
/*     */     
/* 558 */     EntityList entityList = paramArrayOfEntityItem[0].getEntityGroup().getEntityList();
/* 559 */     if (entityList == null) {
/* 560 */       throw new DARuleException("CATDATA EntityGroup not found.  No EntityList for " + paramArrayOfEntityItem[0]
/* 561 */           .getEntityType() + ".  Execution terminated.");
/*     */     }
/* 563 */     EntityGroup entityGroup = entityList.getEntityGroup("CATDATA");
/* 564 */     if (entityGroup == null) {
/* 565 */       throw new DARuleException("CATDATA EntityGroup not found in EntityList for " + paramArrayOfEntityItem[0]
/* 566 */           .getEntityType() + ".  Execution terminated.");
/*     */     }
/*     */ 
/*     */     
/* 570 */     int i = 254;
/* 571 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute("DAATTRIBUTEVALUE");
/* 572 */     if (eANMetaAttribute != null) {
/* 573 */       i = eANMetaAttribute.getMaxLen();
/*     */     }
/*     */     
/* 576 */     Vector<EntityItem> vector = new Vector();
/*     */     
/* 578 */     for (byte b = 0; b < paramArrayOfEntityItem.length; b++) {
/* 579 */       EntityItem entityItem1 = paramArrayOfEntityItem[b];
/* 580 */       arrayOfBoolean[b] = false;
/* 581 */       String str = null;
/* 582 */       if (paramArrayOfString != null) {
/* 583 */         str = paramArrayOfString[b];
/*     */       }
/* 585 */       DARuleEngineMgr.addDebugComment(3, paramStringBuffer2, "doCatdataUpdates: " + entityItem1.getKey() + " darattrcode " + this.attributeCode);
/*     */ 
/*     */       
/* 588 */       if (str != null && str.trim().length() > 0 && 
/* 589 */         str.length() > i) {
/*     */         
/* 591 */         DARuleEngineMgr.addInformation(paramStringBuffer1, "Warning: Derived value exceeded " + i + " characters.  It was truncated for " + 
/* 592 */             DARuleEngineMgr.getNavigationName(paramDatabase, paramProfile, entityItem1) + ".");
/*     */         
/* 594 */         str = str.substring(0, i - 1);
/*     */       } 
/*     */ 
/*     */       
/* 598 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem1, this.relatorType, "CATDATA");
/* 599 */       DARuleEngineMgr.addDebugComment(3, paramStringBuffer2, "doCatdataUpdates: " + entityItem1.getKey() + " " + this.relatorType + " catdataVct.size " + vector1
/* 600 */           .size());
/*     */ 
/*     */       
/* 603 */       EntityItem entityItem2 = null;
/* 604 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 605 */         EntityItem entityItem = vector1.elementAt(b1);
/* 606 */         String str1 = PokUtils.getAttributeFlagValue(entityItem, "DAATTRIBUTECODE");
/* 607 */         DARuleEngineMgr.addDebugComment(3, paramStringBuffer2, "doCatdataUpdates: " + entityItem.getKey() + " attrcode " + str1);
/* 608 */         if (this.attributeCode.equals(str1)) {
/* 609 */           entityItem2 = entityItem;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 614 */       if (entityItem2 == null) {
/* 615 */         if (str != null && str.trim().length() > 0)
/*     */         {
/* 617 */           arrayOfBoolean[b] = createCATDATA(paramDatabase, paramProfile, entityList, entityItem1, str, paramStringBuffer1, paramStringBuffer2);
/*     */         }
/*     */       }
/* 620 */       else if (str != null && str.trim().length() > 0) {
/*     */         
/* 622 */         String str1 = PokUtils.getAttributeValue(entityItem2, "DAATTRIBUTEVALUE", "", null, false);
/* 623 */         if (!str.equals(str1)) {
/* 624 */           arrayOfBoolean[b] = true;
/* 625 */           bool = true;
/* 626 */           StringBuffer stringBuffer = new StringBuffer();
/*     */           
/* 628 */           ABRUtil.setText(entityItem2, "DAATTRIBUTEVALUE", str, stringBuffer);
/* 629 */           if (stringBuffer.length() > 0) {
/* 630 */             DARuleEngineMgr.addDebugComment(3, paramStringBuffer2, "updateAttribute " + entityItem2.getKey() + " value " + str);
/* 631 */             DARuleEngineMgr.addDebugComment(3, paramStringBuffer2, stringBuffer.toString());
/*     */           } 
/*     */           
/* 634 */           DARuleEngineMgr.addInformation(paramStringBuffer1, "Updated " + 
/* 635 */               DARuleEngineMgr.getNavigationName(paramDatabase, paramProfile, entityItem2) + " for " + 
/* 636 */               DARuleEngineMgr.getNavigationName(paramDatabase, paramProfile, entityItem1));
/*     */         } 
/*     */       } else {
/*     */         
/* 640 */         arrayOfBoolean[b] = true;
/*     */         
/* 642 */         DARuleEngineMgr.addInformation(paramStringBuffer1, "Deleted " + 
/* 643 */             DARuleEngineMgr.getNavigationName(paramDatabase, paramProfile, entityItem2) + " for " + 
/* 644 */             DARuleEngineMgr.getNavigationName(paramDatabase, paramProfile, entityItem1));
/* 645 */         vector.add(entityItem2);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 650 */     if (bool)
/*     */     {
/* 652 */       entityGroup.commit(paramDatabase, null);
/*     */     }
/*     */     
/* 655 */     if (vector.size() > 0) {
/* 656 */       EntityItem[] arrayOfEntityItem = new EntityItem[vector.size()];
/* 657 */       vector.copyInto((Object[])arrayOfEntityItem);
/* 658 */       deleteCatdata(paramDatabase, paramProfile, arrayOfEntityItem);
/* 659 */       vector.clear();
/* 660 */       arrayOfEntityItem = null;
/*     */     } 
/*     */     
/* 663 */     return arrayOfBoolean;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getDerivedValue(Database paramDatabase, Profile paramProfile, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws Exception {
/* 681 */     String str = null;
/* 682 */     if (this.daRuleVct != null) {
/* 683 */       for (byte b = 0; b < this.daRuleVct.size(); b++) {
/* 684 */         DARuleItem dARuleItem = this.daRuleVct.elementAt(b);
/* 685 */         str = dARuleItem.getDerivedValue(paramDatabase, paramProfile, paramEntityItem, str, paramStringBuffer);
/* 686 */         DARuleEngineMgr.addDebugComment(4, paramStringBuffer, "getDerivedValue[" + b + "]: " + dARuleItem.getKey() + " results " + str);
/*     */       } 
/*     */     }
/* 689 */     return str;
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
/*     */   
/*     */   private String[] getDerivedValues(Database paramDatabase, Profile paramProfile, EntityItem[] paramArrayOfEntityItem, StringBuffer paramStringBuffer) throws Exception {
/* 703 */     String[] arrayOfString = null;
/* 704 */     if (this.daRuleVct != null && paramArrayOfEntityItem != null && paramArrayOfEntityItem.length > 0) {
/* 705 */       for (byte b = 0; b < this.daRuleVct.size(); b++) {
/* 706 */         DARuleItem dARuleItem = this.daRuleVct.elementAt(b);
/* 707 */         arrayOfString = dARuleItem.getDerivedValues(paramDatabase, paramProfile, paramArrayOfEntityItem, arrayOfString, paramStringBuffer);
/*     */       } 
/*     */     }
/* 710 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dereference() {
/* 717 */     if (this.daRuleVct != null) {
/* 718 */       for (byte b = 0; b < this.daRuleVct.size(); b++) {
/* 719 */         DARuleItem dARuleItem = this.daRuleVct.elementAt(b);
/* 720 */         dARuleItem.dereference();
/*     */       } 
/* 722 */       this.daRuleVct.clear();
/* 723 */       this.daRuleVct = null;
/*     */     } 
/*     */     
/* 726 */     this.attributeCode = null;
/* 727 */     this.relatorType = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 734 */     StringBuffer stringBuffer = new StringBuffer("DARuleGroup: ");
/* 735 */     stringBuffer.append("DAATTRIBUTECODE: " + this.attributeCode + " \n");
/* 736 */     if (this.daRuleVct != null) {
/* 737 */       for (byte b = 0; b < this.daRuleVct.size(); b++) {
/* 738 */         DARuleItem dARuleItem = this.daRuleVct.elementAt(b);
/* 739 */         stringBuffer.append(dARuleItem.toString() + "\n");
/*     */       } 
/*     */     } else {
/* 742 */       stringBuffer.append("No DARULEs");
/*     */     } 
/*     */     
/* 745 */     return stringBuffer.toString();
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
/*     */ 
/*     */   
/*     */   private void updateAttribute(Database paramDatabase, EntityItem paramEntityItem, String paramString, StringBuffer paramStringBuffer) throws Exception {
/* 760 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 762 */     ABRUtil.setText(paramEntityItem, "DAATTRIBUTEVALUE", paramString, stringBuffer);
/* 763 */     DARuleEngineMgr.addDebugComment(3, paramStringBuffer, "updateAttribute " + paramEntityItem.getKey() + " to value " + paramString);
/* 764 */     if (stringBuffer.length() > 0) {
/* 765 */       DARuleEngineMgr.addDebugComment(1, paramStringBuffer, stringBuffer.toString());
/*     */     }
/*     */     
/* 768 */     paramEntityItem.commit(paramDatabase, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityItem createCATDATA(Database paramDatabase, Profile paramProfile, EntityItem paramEntityItem, String paramString, StringBuffer paramStringBuffer) throws Exception {
/* 785 */     EntityItem entityItem = null;
/* 786 */     DARuleEngineMgr.addDebugComment(4, paramStringBuffer, "createCATDATA  darattrcode " + this.attributeCode + " derivedvalue " + paramString);
/*     */ 
/*     */     
/* 789 */     Vector<String> vector = new Vector();
/* 790 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 791 */     vector.addElement("DAATTRIBUTECODE");
/* 792 */     hashtable.put("DAATTRIBUTECODE", this.attributeCode);
/* 793 */     vector.addElement("DAATTRIBUTEVALUE");
/* 794 */     hashtable.put("DAATTRIBUTEVALUE", paramString);
/*     */     
/* 796 */     StringBuffer stringBuffer = new StringBuffer();
/* 797 */     entityItem = ABRUtil.createEntity(paramDatabase, paramProfile, this.createActionName, paramEntityItem, "CATDATA", vector, hashtable, stringBuffer);
/*     */     
/* 799 */     if (stringBuffer.length() > 0) {
/* 800 */       DARuleEngineMgr.addDebugComment(3, paramStringBuffer, "createCATDATA " + stringBuffer.toString());
/*     */     }
/*     */     
/* 803 */     vector.clear();
/* 804 */     hashtable.clear();
/*     */     
/* 806 */     return entityItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 815 */     return "$Revision: 1.9 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\darule\DARuleGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */