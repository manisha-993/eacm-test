/*      */ package COM.ibm.eannounce.abr.sg.bh;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.AttrComparator;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
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
/*      */ public class SVCSEOABRSTATUS
/*      */   extends DQABRSTATUS
/*      */ {
/*   58 */   private String SVCSEOFO = null;
/*   59 */   private String SVCSEOPA = null;
/*   60 */   private String SVCSEOAD = null;
/*   61 */   private String SVCMODFO = null;
/*   62 */   private String SVCMODPA = null;
/*   63 */   private String SVCMODAD = null;
/*   64 */   private Vector plaAvailVctY = null;
/*   65 */   private Vector loAvailVctY = null;
/*      */   
/*      */   public void dereference() {
/*   68 */     this.SVCSEOFO = null;
/*   69 */     this.SVCSEOPA = null;
/*   70 */     this.SVCSEOAD = null;
/*   71 */     this.SVCMODFO = null;
/*   72 */     this.SVCMODPA = null;
/*   73 */     this.SVCMODAD = null;
/*   74 */     if (this.plaAvailVctY != null) {
/*   75 */       this.plaAvailVctY.clear();
/*   76 */       this.plaAvailVctY = null;
/*      */     } 
/*   78 */     if (this.loAvailVctY != null) {
/*   79 */       this.loAvailVctY.clear();
/*   80 */       this.loAvailVctY = null;
/*      */     } 
/*   82 */     super.dereference();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/*  149 */     addHeading(3, this.m_elist.getEntityGroup("PRCPT").getLongDescription() + " checks:");
/*      */ 
/*      */     
/*  152 */     int i = getCount("SVCSEOPRCPT");
/*  153 */     EntityGroup entityGroup1 = this.m_elist.getEntityGroup("PRCPT");
/*  154 */     if (i == 0) {
/*      */       
/*  156 */       this.args[0] = entityGroup1.getLongDescription();
/*  157 */       createMessage(3, "MINIMUM_ERR", this.args);
/*      */     } 
/*      */ 
/*      */     
/*  161 */     for (byte b1 = 0; b1 < entityGroup1.getEntityItemCount(); b1++) {
/*  162 */       EntityItem entityItem = entityGroup1.getEntityItem(b1);
/*  163 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 4);
/*      */     } 
/*      */     
/*  166 */     ArrayList arrayList = new ArrayList();
/*  167 */     EntityGroup entityGroup2 = this.m_elist.getEntityGroup("CNTRYEFF");
/*  168 */     for (byte b2 = 0; b2 < entityGroup2.getEntityItemCount(); b2++) {
/*  169 */       EntityItem entityItem = entityGroup2.getEntityItem(b2);
/*  170 */       getCountriesAsList(entityItem, arrayList, -1);
/*      */     } 
/*  172 */     addDebug("all prcpt-cntryeffCtrys " + arrayList);
/*      */ 
/*      */     
/*  175 */     checkSVCSEOAvails(paramEntityItem, arrayList, paramString);
/*      */ 
/*      */     
/*  178 */     checkSVCMODAvails(paramEntityItem, arrayList, paramString);
/*      */ 
/*      */     
/*  181 */     checkFODates(paramEntityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  189 */     oneValidOverTime(paramEntityItem);
/*      */     
/*  191 */     arrayList.clear();
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
/*      */   private void oneValidOverTime(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  213 */     StringBuffer stringBuffer = new StringBuffer(paramEntityItem.getEntityGroup().getLongDescription());
/*  214 */     stringBuffer.append(" " + this.m_elist.getEntityGroup("AVAIL").getLongDescription());
/*  215 */     stringBuffer.append(" and " + this.m_elist.getEntityGroup("PRCPTCNTRYEFF").getLongDescription());
/*      */     
/*  217 */     addHeading(3, stringBuffer.toString() + " validity checks:");
/*      */     
/*  219 */     if (this.plaAvailVctY.size() == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  224 */     AttrComparator attrComparator1 = new AttrComparator("EFFECTIVEDATE");
/*      */ 
/*      */     
/*  227 */     Collections.sort(this.plaAvailVctY, (Comparator<?>)attrComparator1);
/*  228 */     Collections.sort(this.loAvailVctY, (Comparator<?>)attrComparator1);
/*      */     
/*  230 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>(); int i;
/*  231 */     for (i = this.plaAvailVctY.size() - 1; i >= 0; i--) {
/*  232 */       ArrayList arrayList = new ArrayList();
/*  233 */       EntityItem entityItem = this.plaAvailVctY.elementAt(i);
/*  234 */       String str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", "1980-01-01", false);
/*  235 */       getCountriesAsList(entityItem, arrayList, 4);
/*      */       
/*  237 */       Iterator<String> iterator = arrayList.iterator();
/*  238 */       while (iterator.hasNext()) {
/*  239 */         String str1 = iterator.next();
/*  240 */         DQABRSTATUS.TPIC tPIC = (DQABRSTATUS.TPIC)hashtable1.get(str1);
/*  241 */         if (tPIC != null) {
/*  242 */           tPIC.fromDate = str; continue;
/*      */         } 
/*  244 */         hashtable1.put(str1, new DQABRSTATUS.TPIC(str1, str));
/*      */       } 
/*      */       
/*  247 */       arrayList.clear();
/*      */     } 
/*  249 */     for (i = 0; i < this.loAvailVctY.size(); i++) {
/*  250 */       ArrayList arrayList = new ArrayList();
/*  251 */       EntityItem entityItem = this.loAvailVctY.elementAt(i);
/*  252 */       String str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", "9999-12-31", false);
/*  253 */       getCountriesAsList(entityItem, arrayList, 4);
/*      */       
/*  255 */       Iterator<String> iterator = arrayList.iterator();
/*  256 */       while (iterator.hasNext()) {
/*  257 */         String str1 = iterator.next();
/*  258 */         DQABRSTATUS.TPIC tPIC = (DQABRSTATUS.TPIC)hashtable1.get(str1);
/*  259 */         if (tPIC != null) {
/*  260 */           tPIC.toDate = str;
/*      */         }
/*      */       } 
/*  263 */       arrayList.clear();
/*      */     } 
/*      */     
/*  266 */     addDebug("oneValidOverTime " + paramEntityItem.getKey() + " availByCtryTbl " + hashtable1);
/*      */ 
/*      */ 
/*      */     
/*  270 */     Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCSEOPRCPT", "PRCPT");
/*  271 */     Vector<?> vector1 = PokUtils.getAllLinkedEntities(vector, "PRCPTCNTRYEFF", "CNTRYEFF");
/*  272 */     addDebug("oneValidOverTime prcptVct " + vector.size() + " CNTRYEFF childVct " + vector1.size());
/*      */     
/*  274 */     AttrComparator attrComparator2 = new AttrComparator("EFFECTIVEDATE");
/*      */     
/*  276 */     Collections.sort(vector1, (Comparator<?>)attrComparator2);
/*      */     
/*  278 */     Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*  279 */     for (int j = vector1.size() - 1; j >= 0; j--) {
/*  280 */       ArrayList arrayList = new ArrayList();
/*  281 */       EntityItem entityItem = (EntityItem)vector1.elementAt(j);
/*  282 */       String str1 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", "1980-01-01", false);
/*  283 */       String str2 = PokUtils.getAttributeValue(entityItem, "ENDDATE", "", "9999-12-31", false);
/*  284 */       getCountriesAsList(entityItem, arrayList, 4);
/*      */       
/*  286 */       Iterator<String> iterator = arrayList.iterator();
/*  287 */       while (iterator.hasNext()) {
/*  288 */         String str = iterator.next();
/*  289 */         Vector<DQABRSTATUS.TPIC> vector2 = (Vector)hashtable2.get(str);
/*  290 */         if (vector2 == null) {
/*  291 */           vector2 = new Vector();
/*  292 */           hashtable2.put(str, vector2);
/*      */         } 
/*  294 */         vector2.add(new DQABRSTATUS.TPIC(str, str1, str2));
/*      */       } 
/*  296 */       arrayList.clear();
/*      */     } 
/*  298 */     addDebug("oneValidOverTime CNTRYEFF childByCtryTbl " + hashtable2);
/*      */     
/*  300 */     Iterator<String> iterator1 = hashtable1.keySet().iterator();
/*  301 */     while (iterator1.hasNext()) {
/*  302 */       String str1 = iterator1.next();
/*  303 */       String str2 = getUnmatchedDescriptions(this.m_elist.getEntityGroup("AVAIL"), "COUNTRYLIST", str1);
/*  304 */       DQABRSTATUS.TPIC tPIC1 = (DQABRSTATUS.TPIC)hashtable1.get(str1);
/*  305 */       addDebug("oneValidOverTime parent " + tPIC1);
/*  306 */       Vector<Comparable> vector2 = (Vector)hashtable2.get(str1);
/*  307 */       if (vector2 == null) {
/*      */ 
/*      */         
/*  310 */         this.args[0] = this.m_elist.getEntityGroup("PRCPT").getLongDescription();
/*  311 */         this.args[1] = this.m_elist.getEntityGroup("CNTRYEFF").getLongDescription();
/*  312 */         this.args[2] = paramEntityItem.getEntityGroup().getLongDescription();
/*  313 */         this.args[3] = this.m_elist.getEntityGroup("AVAIL").getLongDescription();
/*  314 */         createMessage(4, "ONE_VALID_ERR", this.args);
/*      */         
/*  316 */         this.args[0] = "No " + this.m_elist.getEntityGroup("CNTRYEFF").getLongDescription();
/*  317 */         this.args[1] = "Planned Availability";
/*  318 */         this.args[2] = "Country: " + str2;
/*  319 */         addResourceMsg("VALUE_FND", this.args);
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*  325 */       Collections.sort(vector2);
/*      */ 
/*      */       
/*  328 */       DQABRSTATUS.TPIC tPIC2 = (DQABRSTATUS.TPIC)vector2.firstElement();
/*  329 */       String str3 = tPIC2.toDate;
/*  330 */       addDebug("oneValidOverTime first child " + tPIC2);
/*  331 */       if (tPIC2.fromDate.compareTo(tPIC1.fromDate) > 0) {
/*      */ 
/*      */         
/*  334 */         this.args[0] = this.m_elist.getEntityGroup("PRCPT").getLongDescription();
/*  335 */         this.args[1] = this.m_elist.getEntityGroup("CNTRYEFF").getLongDescription();
/*  336 */         this.args[2] = paramEntityItem.getEntityGroup().getLongDescription();
/*  337 */         this.args[3] = this.m_elist.getEntityGroup("AVAIL").getLongDescription();
/*  338 */         createMessage(4, "ONE_VALID_ERR", this.args);
/*      */ 
/*      */         
/*  341 */         this.args[0] = this.m_elist.getEntityGroup("CNTRYEFF").getLongDescription();
/*  342 */         this.args[1] = PokUtils.getAttributeDescription(this.m_elist.getEntityGroup("CNTRYEFF"), "EFFECTIVEDATE", "EFFECTIVEDATE") + " (" + tPIC2.fromDate + ")";
/*      */         
/*  344 */         this.args[2] = this.m_elist.getEntityGroup("AVAIL").getLongDescription() + " Planned Availability";
/*  345 */         this.args[3] = PokUtils.getAttributeDescription(this.m_elist.getEntityGroup("AVAIL"), "EFFECTIVEDATE", "EFFECTIVEDATE") + " (" + tPIC1.fromDate + ")";
/*      */         
/*  347 */         this.args[4] = " for Country " + str2;
/*  348 */         addResourceMsg("CANNOT_BE_LATER_ERR2", this.args);
/*      */         
/*      */         continue;
/*      */       } 
/*      */       byte b;
/*  353 */       for (b = 0; b < vector2.size(); b++) {
/*  354 */         DQABRSTATUS.TPIC tPIC = (DQABRSTATUS.TPIC)vector2.elementAt(b);
/*  355 */         tPIC.setFromSort(false);
/*      */       } 
/*  357 */       Collections.sort(vector2);
/*      */       
/*  359 */       tPIC2 = (DQABRSTATUS.TPIC)vector2.lastElement();
/*  360 */       addDebug("oneValidOverTime last child " + tPIC2);
/*  361 */       if (tPIC2.toDate.compareTo(tPIC1.toDate) < 0) {
/*      */ 
/*      */         
/*  364 */         this.args[0] = this.m_elist.getEntityGroup("PRCPT").getLongDescription();
/*  365 */         this.args[1] = this.m_elist.getEntityGroup("CNTRYEFF").getLongDescription();
/*  366 */         this.args[2] = paramEntityItem.getEntityGroup().getLongDescription();
/*  367 */         this.args[3] = this.m_elist.getEntityGroup("AVAIL").getLongDescription();
/*  368 */         createMessage(4, "ONE_VALID_ERR", this.args);
/*      */ 
/*      */         
/*  371 */         this.args[0] = this.m_elist.getEntityGroup("CNTRYEFF").getLongDescription();
/*  372 */         this.args[1] = PokUtils.getAttributeDescription(this.m_elist.getEntityGroup("CNTRYEFF"), "ENDDATE", "ENDDATE") + " (" + tPIC2.toDate + ")";
/*      */         
/*  374 */         this.args[2] = this.m_elist.getEntityGroup("AVAIL").getLongDescription() + " Last Order Availability ";
/*  375 */         this.args[3] = PokUtils.getAttributeDescription(this.m_elist.getEntityGroup("AVAIL"), "EFFECTIVEDATE", "EFFECTIVEDATE") + " (" + tPIC1.toDate + ")";
/*      */         
/*  377 */         this.args[4] = " for Country " + str2;
/*  378 */         addResourceMsg("CANNOT_BE_EARLIER_ERR3", this.args);
/*      */         
/*      */         continue;
/*      */       } 
/*  382 */       for (b = 0; b < vector2.size(); b++) {
/*  383 */         DQABRSTATUS.TPIC tPIC = (DQABRSTATUS.TPIC)vector2.elementAt(b);
/*  384 */         tPIC.setFromSort(true);
/*      */       } 
/*  386 */       Collections.sort(vector2);
/*      */ 
/*      */       
/*  389 */       for (b = 1; b < vector2.size(); b++) {
/*  390 */         tPIC2 = (DQABRSTATUS.TPIC)vector2.elementAt(b);
/*  391 */         String str4 = tPIC2.fromDate;
/*  392 */         String str5 = tPIC2.toDate;
/*  393 */         addDebug("oneValidOverTime gaps " + tPIC2);
/*  394 */         if (str4.compareTo(str3) > 0) {
/*      */ 
/*      */           
/*  397 */           this.args[0] = this.m_elist.getEntityGroup("PRCPT").getLongDescription();
/*  398 */           this.args[1] = this.m_elist.getEntityGroup("CNTRYEFF").getLongDescription();
/*  399 */           this.args[2] = paramEntityItem.getEntityGroup().getLongDescription();
/*  400 */           this.args[3] = this.m_elist.getEntityGroup("AVAIL").getLongDescription();
/*  401 */           createMessage(4, "ONE_VALID_ERR", this.args);
/*      */ 
/*      */           
/*  404 */           this.args[0] = this.m_elist.getEntityGroup("CNTRYEFF").getLongDescription();
/*  405 */           this.args[1] = str3;
/*  406 */           this.args[2] = str4 + " for Country " + str2;
/*      */           
/*  408 */           addResourceMsg("GAPS_ERR", this.args);
/*      */           break;
/*      */         } 
/*  411 */         str3 = str5;
/*      */       } 
/*      */     } 
/*      */     
/*  415 */     Iterator<String> iterator2 = hashtable1.keySet().iterator();
/*  416 */     while (iterator2.hasNext()) {
/*  417 */       String str = iterator2.next();
/*  418 */       DQABRSTATUS.TPIC tPIC = (DQABRSTATUS.TPIC)hashtable1.get(str);
/*  419 */       tPIC.dereference();
/*      */     } 
/*  421 */     hashtable1.clear();
/*      */     
/*  423 */     iterator2 = hashtable2.keySet().iterator();
/*  424 */     while (iterator2.hasNext()) {
/*  425 */       String str = iterator2.next();
/*  426 */       Vector<DQABRSTATUS.TPIC> vector2 = (Vector)hashtable2.get(str);
/*  427 */       for (byte b = 0; b < vector2.size(); b++) {
/*  428 */         DQABRSTATUS.TPIC tPIC = vector2.elementAt(b);
/*  429 */         tPIC.dereference();
/*      */       } 
/*      */     } 
/*  432 */     hashtable2.clear();
/*      */     
/*  434 */     vector1.clear();
/*  435 */     vector.clear();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkSVCSEOAvails(EntityItem paramEntityItem, ArrayList paramArrayList, String paramString) throws SQLException, MiddlewareException {
/*  471 */     EntityGroup entityGroup1 = this.m_elist.getEntityGroup("PRCPT");
/*  472 */     EntityGroup entityGroup2 = this.m_elist.getEntityGroup("CNTRYEFF");
/*      */     
/*  474 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " First Order " + this.m_elist
/*  475 */         .getEntityGroup("AVAIL").getLongDescription() + " checks:");
/*      */ 
/*      */ 
/*      */     
/*  479 */     Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCSEOAVAIL", "AVAIL");
/*  480 */     Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "143");
/*  481 */     this.plaAvailVctY = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/*  482 */     this.loAvailVctY = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "149");
/*  483 */     addDebug(paramEntityItem.getKey() + " availVct " + vector.size() + " foAvailVctY " + vector1.size() + " plaAvailVctY " + this.plaAvailVctY
/*  484 */         .size()); byte b;
/*  485 */     for (b = 0; b < vector1.size(); b++) {
/*  486 */       EntityItem entityItem = vector1.elementAt(b);
/*  487 */       String str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", null, false);
/*      */       
/*  489 */       if (this.SVCSEOFO == null) {
/*  490 */         this.SVCSEOFO = str;
/*      */       }
/*  492 */       else if (this.SVCSEOFO.compareTo(str) > 0) {
/*  493 */         this.SVCSEOFO = str;
/*      */       } 
/*      */       
/*  496 */       addDebug("svcseo fo avail " + entityItem.getKey() + " effDate " + str + " SVCSEOFO " + this.SVCSEOFO);
/*      */     } 
/*  498 */     vector1.clear();
/*      */ 
/*      */     
/*  501 */     if (this.SVCSEOFO == null) {
/*  502 */       this.args[0] = "No Date";
/*      */     } else {
/*  504 */       this.args[0] = this.SVCSEOFO;
/*      */     } 
/*  506 */     this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/*  507 */     this.args[2] = "First Order date";
/*  508 */     addResourceMsg("VALUE_FND", this.args);
/*      */     
/*  510 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " Planned Availability checks:");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  515 */     checkPlannedAvailsExist(this.plaAvailVctY, getCheck_RW_RW_RE(paramString));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  521 */     checkPlannedAvailsStatus(this.plaAvailVctY, paramEntityItem, 3);
/*      */     
/*  523 */     for (b = 0; b < this.plaAvailVctY.size(); b++) {
/*  524 */       EntityItem entityItem = this.plaAvailVctY.elementAt(b);
/*  525 */       String str1 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", null, false);
/*      */       
/*  527 */       if (this.SVCSEOPA == null) {
/*  528 */         this.SVCSEOPA = str1;
/*      */       }
/*  530 */       else if (this.SVCSEOPA.compareTo(str1) > 0) {
/*  531 */         this.SVCSEOPA = str1;
/*      */       } 
/*      */ 
/*      */       
/*  535 */       addDebug("svcseo pla avail " + entityItem.getKey() + " effDate " + str1 + " SVCSEOPA " + this.SVCSEOPA);
/*      */       
/*  537 */       if (entityGroup2.getEntityItemCount() > 0) {
/*  538 */         String str = checkCtryMismatch(entityItem, paramArrayList, 4);
/*  539 */         if (str.length() > 0) {
/*  540 */           ArrayList arrayList = new ArrayList();
/*  541 */           getCountriesAsList(entityItem, arrayList, -1);
/*  542 */           addDebug(entityItem.getKey() + " COUNTRYLIST had extra [" + str + "] availctrylist " + arrayList);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  549 */           this.args[0] = getLD_NDN(entityItem);
/*  550 */           this.args[1] = "";
/*  551 */           this.args[2] = entityGroup1.getLongDescription() + " " + entityGroup2.getLongDescription();
/*  552 */           this.args[3] = PokUtils.getAttributeDescription(entityGroup2, "COUNTRYLIST", "COUNTRYLIST");
/*  553 */           this.args[4] = str;
/*  554 */           createMessage(4, "INCLUDE_ERR2", this.args);
/*      */           
/*  556 */           arrayList.clear();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  561 */       String str2 = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/*  562 */       if (str2 == null) {
/*  563 */         str2 = "RFA";
/*      */       }
/*  565 */       Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/*  566 */       addDebug(entityItem.getKey() + " availAnntypeFlag " + str2 + " annVct " + vector2.size());
/*      */       
/*  568 */       if ("RFA".equals(str2)) {
/*  569 */         for (byte b1 = 0; b1 < vector2.size(); b1++) {
/*  570 */           EntityItem entityItem1 = vector2.elementAt(b1);
/*  571 */           String str = getAttributeFlagEnabledValue(entityItem1, "ANNTYPE");
/*  572 */           addDebug(entityItem1.getKey() + " type " + str);
/*      */           
/*  574 */           if ("19".equals(str))
/*      */           {
/*      */             
/*  577 */             String str3 = PokUtils.getAttributeValue(entityItem1, "ANNDATE", "", null, false);
/*  578 */             if (this.SVCSEOAD == null) {
/*  579 */               this.SVCSEOAD = str3;
/*      */             }
/*  581 */             else if (this.SVCSEOAD.compareTo(str3) > 0) {
/*  582 */               this.SVCSEOAD = str3;
/*      */             } 
/*      */             
/*  585 */             addDebug("svcseo pla avail ann " + entityItem1.getKey() + " annDate " + str3 + " SVCSEOAD " + this.SVCSEOAD);
/*      */           
/*      */           }
/*      */           else
/*      */           {
/*      */             
/*  591 */             this.args[0] = getLD_NDN(entityItem);
/*  592 */             this.args[1] = getLD_NDN(entityItem1);
/*  593 */             createMessage(4, "MUST_NOT_BE_IN_ERR2", this.args);
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  600 */         for (byte b1 = 0; b1 < vector2.size(); b1++) {
/*      */           
/*  602 */           this.args[0] = getLD_NDN(entityItem);
/*  603 */           this.args[1] = getLD_Value(vector2.elementAt(b1), "ANNTYPE");
/*  604 */           this.args[2] = getLD_NDN(vector2.elementAt(b1));
/*  605 */           createMessage(4, "MUST_NOT_BE_IN_THIS_ERR", this.args);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  610 */       vector2.clear();
/*      */     } 
/*      */ 
/*      */     
/*  614 */     if (this.SVCSEOPA == null) {
/*  615 */       this.args[0] = "No Date";
/*      */     } else {
/*  617 */       this.args[0] = this.SVCSEOPA;
/*      */     } 
/*  619 */     this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/*  620 */     this.args[2] = "Planned Availability date";
/*  621 */     addResourceMsg("VALUE_FND", this.args);
/*      */ 
/*      */     
/*  624 */     if (this.SVCSEOAD == null) {
/*  625 */       this.args[0] = "No Date";
/*      */     } else {
/*  627 */       this.args[0] = this.SVCSEOAD;
/*      */     } 
/*  629 */     this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/*  630 */     this.args[2] = "New Announcement date";
/*  631 */     addResourceMsg("VALUE_FND", this.args);
/*      */     
/*  633 */     vector.clear();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkSVCMODAvails(EntityItem paramEntityItem, ArrayList<?> paramArrayList, String paramString) throws SQLException, MiddlewareException {
/*  664 */     EntityGroup entityGroup1 = this.m_elist.getEntityGroup("PRCPT");
/*  665 */     EntityGroup entityGroup2 = this.m_elist.getEntityGroup("CNTRYEFF");
/*      */     
/*  667 */     Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCMODSVCSEO", "SVCMOD");
/*      */     
/*  669 */     if (vector1.size() == 0) {
/*  670 */       addHeading(3, "No " + this.m_elist.getEntityGroup("SVCMODSVCSEO").getLongDescription() + " found");
/*      */       
/*      */       return;
/*      */     } 
/*  674 */     addHeading(3, this.m_elist.getEntityGroup("SVCMOD").getLongDescription() + " First Order " + this.m_elist
/*  675 */         .getEntityGroup("AVAIL").getLongDescription() + " checks:");
/*      */     
/*  677 */     Vector vector = PokUtils.getAllLinkedEntities(vector1, "SVCMODAVAIL", "AVAIL");
/*  678 */     Vector<EntityItem> vector2 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "143");
/*  679 */     addDebug("svcmodVct " + vector1.size() + " availVctZ " + vector.size() + " foAvailVctZ " + vector2.size());
/*      */     
/*      */     byte b;
/*  682 */     for (b = 0; b < vector2.size(); b++) {
/*  683 */       EntityItem entityItem = vector2.elementAt(b);
/*  684 */       String str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", null, false);
/*      */       
/*  686 */       if (this.SVCMODFO == null) {
/*  687 */         this.SVCMODFO = str;
/*      */       }
/*  689 */       else if (this.SVCMODFO.compareTo(str) > 0) {
/*  690 */         this.SVCMODFO = str;
/*      */       } 
/*      */       
/*  693 */       addDebug("svcmod fo avail " + entityItem.getKey() + " effDate " + str + " SVCMODFO " + this.SVCMODFO);
/*      */     } 
/*      */ 
/*      */     
/*  697 */     if (this.SVCMODFO == null) {
/*  698 */       this.args[0] = "No Date";
/*      */     } else {
/*  700 */       this.args[0] = this.SVCMODFO;
/*      */     } 
/*  702 */     this.args[1] = this.m_elist.getEntityGroup("SVCMOD").getLongDescription();
/*  703 */     this.args[2] = "First Order date";
/*  704 */     addResourceMsg("VALUE_FND", this.args);
/*      */     
/*  706 */     addHeading(3, this.m_elist.getEntityGroup("SVCMOD").getLongDescription() + " Planned Availability checks:");
/*      */ 
/*      */ 
/*      */     
/*  710 */     for (b = 0; b < vector1.size(); b++) {
/*  711 */       EntityItem entityItem = vector1.elementAt(b);
/*  712 */       vector = PokUtils.getAllLinkedEntities(entityItem, "SVCMODAVAIL", "AVAIL");
/*  713 */       Vector<EntityItem> vector3 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/*  714 */       addDebug(entityItem.getKey() + " availVctZ " + vector.size() + " plaAvailVctZ " + vector3
/*  715 */           .size());
/*  716 */       if (vector3.size() == 0) {
/*      */         
/*  718 */         this.args[0] = getLD_NDN(entityItem);
/*  719 */         this.args[1] = "Planned Availability";
/*  720 */         createMessage(getCheck_RW_RW_RE(paramString), "MINIMUM2_ERR", this.args);
/*      */       } 
/*  722 */       ArrayList arrayList = new ArrayList(); byte b1;
/*  723 */       for (b1 = 0; b1 < vector3.size(); b1++) {
/*  724 */         EntityItem entityItem1 = vector3.elementAt(b1);
/*  725 */         String str = PokUtils.getAttributeValue(entityItem1, "EFFECTIVEDATE", "", null, false);
/*      */         
/*  727 */         if (this.SVCMODPA == null) {
/*  728 */           this.SVCMODPA = str;
/*      */         }
/*  730 */         else if (this.SVCMODPA.compareTo(str) > 0) {
/*  731 */           this.SVCMODPA = str;
/*      */         } 
/*      */ 
/*      */         
/*  735 */         getCountriesAsList(entityItem1, arrayList, 4);
/*      */         
/*  737 */         Vector<EntityItem> vector4 = PokUtils.getAllLinkedEntities(entityItem1, "AVAILANNA", "ANNOUNCEMENT");
/*  738 */         addDebug(entityItem1.getKey() + " annVct " + vector4.size());
/*  739 */         for (byte b2 = 0; b2 < vector4.size(); b2++) {
/*  740 */           EntityItem entityItem2 = vector4.elementAt(b2);
/*      */ 
/*      */           
/*  743 */           String str1 = PokUtils.getAttributeValue(entityItem2, "ANNDATE", "", null, false);
/*  744 */           if (this.SVCMODAD == null) {
/*  745 */             this.SVCMODAD = str1;
/*      */           }
/*  747 */           else if (this.SVCMODAD.compareTo(str1) > 0) {
/*  748 */             this.SVCMODAD = str1;
/*      */           } 
/*      */           
/*  751 */           addDebug(entityItem.getKey() + " pla avail ann " + entityItem2.getKey() + " annDate " + str1 + " SVCMODAD " + this.SVCMODAD);
/*      */         } 
/*      */         
/*  754 */         vector4.clear();
/*      */       } 
/*      */       
/*  757 */       addDebug(entityItem.getKey() + " all pla availCtrys " + arrayList);
/*      */       
/*  759 */       if (entityGroup2.getEntityItemCount() > 0 && vector3.size() > 0 && 
/*  760 */         !arrayList.containsAll(paramArrayList)) {
/*  761 */         for (b1 = 0; b1 < entityGroup1.getEntityItemCount(); b1++) {
/*  762 */           EntityItem entityItem1 = entityGroup1.getEntityItem(b1);
/*  763 */           Vector<EntityItem> vector4 = PokUtils.getAllLinkedEntities(entityItem1, "PRCPTCNTRYEFF", "CNTRYEFF");
/*  764 */           for (byte b2 = 0; b2 < vector4.size(); b2++) {
/*  765 */             EntityItem entityItem2 = vector4.elementAt(b2);
/*  766 */             String str = checkCtryMismatch(entityItem2, arrayList, 4);
/*      */ 
/*      */ 
/*      */             
/*  770 */             if (str.length() > 0) {
/*  771 */               this.args[0] = getLD_NDN(entityItem1);
/*  772 */               this.args[1] = getLD_NDN(entityItem2);
/*      */               
/*  774 */               this.args[2] = getLD_NDN(entityItem) + " " + this.m_elist
/*  775 */                 .getEntityGroup("AVAIL").getLongDescription();
/*  776 */               this.args[3] = PokUtils.getAttributeDescription(entityGroup2, "COUNTRYLIST", "COUNTRYLIST");
/*  777 */               this.args[4] = str;
/*  778 */               createMessage(4, "INCLUDE_ERR2", this.args);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  785 */       vector.clear();
/*  786 */       vector3.clear();
/*  787 */       arrayList.clear();
/*      */     } 
/*      */ 
/*      */     
/*  791 */     if (this.SVCMODPA == null) {
/*  792 */       this.args[0] = "No Date";
/*      */     } else {
/*  794 */       this.args[0] = this.SVCMODPA;
/*      */     } 
/*  796 */     this.args[1] = this.m_elist.getEntityGroup("SVCMOD").getLongDescription();
/*  797 */     this.args[2] = "Planned Availability date";
/*  798 */     addResourceMsg("VALUE_FND", this.args);
/*      */ 
/*      */     
/*  801 */     if (this.SVCMODAD == null) {
/*  802 */       this.args[0] = "No Date";
/*      */     } else {
/*  804 */       this.args[0] = this.SVCMODAD;
/*      */     } 
/*  806 */     this.args[1] = this.m_elist.getEntityGroup("SVCMOD").getLongDescription();
/*  807 */     this.args[2] = "New Announcement date";
/*  808 */     addResourceMsg("VALUE_FND", this.args);
/*      */     
/*  810 */     vector.clear();
/*  811 */     vector2.clear();
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
/*      */   private void checkFODates(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  832 */     String str1 = null;
/*  833 */     String str2 = null;
/*      */ 
/*      */ 
/*      */     
/*  837 */     if (this.SVCSEOFO != null) {
/*  838 */       str1 = this.SVCSEOFO;
/*  839 */     } else if (this.SVCSEOAD != null) {
/*  840 */       str1 = this.SVCSEOAD;
/*  841 */     } else if (this.SVCSEOPA != null) {
/*  842 */       str1 = this.SVCSEOPA;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  848 */     if (this.SVCMODFO != null) {
/*  849 */       str2 = this.SVCMODFO;
/*  850 */     } else if (this.SVCMODAD != null) {
/*  851 */       str2 = this.SVCMODAD;
/*  852 */     } else if (this.SVCMODPA != null) {
/*  853 */       str2 = this.SVCMODPA;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  860 */     if (str2 != null && str1 != null && 
/*  861 */       str2.compareTo(str1) > 0) {
/*  862 */       this.args[0] = this.m_elist.getEntityGroup("SVCMOD").getLongDescription();
/*  863 */       this.args[1] = str2;
/*  864 */       this.args[2] = getLD_NDN(paramEntityItem);
/*  865 */       this.args[3] = str1;
/*  866 */       createMessage(3, "FODATE_ERR", this.args);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  900 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SVCMOD");
/*  901 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  902 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  909 */       if (statusIsFinal(entityItem)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  918 */         setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */         
/*  926 */         doRFR_ADSXML(entityItem);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  969 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SVCMOD");
/*  970 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  971 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  983 */       if (statusIsRFR(entityItem))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  988 */         setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
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
/*      */   public String getDescription() {
/* 1003 */     return "SVCSEO ABR.";
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
/*      */   public String getABRVersion() {
/* 1015 */     return "$Revision: 1.4 $";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\SVCSEOABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */