/*     */ package COM.ibm.eannounce.abr.sg.bh;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.AttrComparator;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PRCPTABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/*     */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/*  96 */     Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "CHRGCOMPPRCPT", "CHRGCOMP");
/*  97 */     Vector vector2 = PokUtils.getAllLinkedEntities(vector1, "SVCMODCHRGCOMP", "SVCMOD");
/*  98 */     Vector vector3 = PokUtils.getAllLinkedEntities(vector2, "SVCMODAVAIL", "AVAIL");
/*  99 */     Vector vector4 = PokUtils.getEntitiesWithMatchedAttr(vector3, "AVAILTYPE", "146");
/*     */ 
/*     */     
/* 102 */     ArrayList arrayList1 = getCountriesAsList(vector4, 4);
/*     */     
/* 104 */     addDebug(paramEntityItem.getKey() + " chrgcompVct " + vector1.size() + " svcmodVct " + vector2
/* 105 */         .size() + " svcmodAvailVct " + vector3.size() + " svcmodPlaAvailVctA " + vector4
/* 106 */         .size() + " all svcmod plaAvailCtry " + arrayList1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCSEOPRCPT", "SVCSEO");
/* 112 */     Vector vector5 = PokUtils.getAllLinkedEntities(vector, "SVCSEOAVAIL", "AVAIL");
/* 113 */     Vector vector6 = PokUtils.getEntitiesWithMatchedAttr(vector5, "AVAILTYPE", "146");
/* 114 */     ArrayList arrayList2 = getCountriesAsList(vector6, 4);
/* 115 */     addDebug(" svcseoVct " + vector.size() + " svcseoAvailVct " + vector5
/* 116 */         .size() + " svcseoPlaAvailVctB " + vector6
/* 117 */         .size());
/* 118 */     addDebug("all svcseoPlaAvailVctB countrys " + arrayList2);
/*     */     
/* 120 */     EntityGroup entityGroup1 = this.m_elist.getEntityGroup("CNTRYEFF");
/* 121 */     addHeading(3, entityGroup1.getLongDescription() + " and Planned Availability checks:");
/*     */     
/* 123 */     ArrayList arrayList3 = new ArrayList();
/*     */     
/*     */     int i;
/* 126 */     for (i = 0; i < entityGroup1.getEntityItemCount(); i++) {
/* 127 */       ArrayList arrayList = new ArrayList();
/* 128 */       EntityItem entityItem = entityGroup1.getEntityItem(i);
/* 129 */       getCountriesAsList(entityItem, arrayList, 4);
/* 130 */       addDebug(entityItem.getKey() + " cntryeffCtry " + arrayList);
/* 131 */       arrayList3.addAll(arrayList);
/*     */       
/* 133 */       String str = null;
/*     */       
/* 135 */       if (vector4.size() > 0) {
/* 136 */         str = checkCtryMismatch(entityItem, arrayList1, 4);
/* 137 */         if (str.length() > 0) {
/* 138 */           addDebug(entityItem.getKey() + " COUNTRYLIST had extra [" + str + "] from svcmod A:AVAIL");
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 143 */           this.args[0] = "";
/* 144 */           this.args[1] = getLD_NDN(entityItem);
/* 145 */           this.args[2] = this.m_elist.getEntityGroup("SVCMOD").getLongDescription() + " " + this.m_elist
/* 146 */             .getEntityGroup("AVAIL").getLongDescription();
/* 147 */           this.args[3] = PokUtils.getAttributeDescription(entityGroup1, "COUNTRYLIST", "COUNTRYLIST");
/* 148 */           this.args[4] = str;
/* 149 */           createMessage(4, "INCLUDE_ERR2", this.args);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 154 */       if (vector6.size() > 0) {
/* 155 */         str = checkCtryMismatch(entityItem, arrayList2, 4);
/* 156 */         if (str.length() > 0) {
/* 157 */           addDebug(entityItem.getKey() + " COUNTRYLIST had extra [" + str + "] from svcseo B:AVAIL");
/*     */ 
/*     */           
/* 160 */           this.args[0] = "";
/* 161 */           this.args[1] = getLD_NDN(entityItem);
/* 162 */           this.args[2] = this.m_elist.getEntityGroup("SVCSEO").getLongDescription() + " " + this.m_elist
/* 163 */             .getEntityGroup("AVAIL").getLongDescription();
/* 164 */           this.args[3] = PokUtils.getAttributeDescription(entityGroup1, "COUNTRYLIST", "COUNTRYLIST");
/* 165 */           this.args[4] = str;
/* 166 */           createMessage(4, "INCLUDE_ERR2", this.args);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 171 */     addDebug(paramEntityItem.getKey() + " allCntryeffCtry " + arrayList3);
/*     */     
/* 173 */     addHeading(3, this.m_elist.getEntityGroup("CVMSPEC").getLongDescription() + " checks:");
/*     */ 
/*     */     
/* 176 */     i = getCount("PRCPTCVMSPEC");
/* 177 */     EntityGroup entityGroup2 = this.m_elist.getEntityGroup("CVMSPEC");
/*     */     
/* 179 */     if (i == 0) {
/*     */       
/* 181 */       this.args[0] = entityGroup2.getLongDescription();
/* 182 */       createMessage(4, "MINIMUM_ERR", this.args);
/*     */     } 
/*     */     int j;
/* 185 */     for (j = 0; j < entityGroup2.getEntityItemCount(); j++) {
/* 186 */       EntityItem entityItem = entityGroup2.getEntityItem(j);
/* 187 */       addDebug("doDQChecking: " + entityItem.getKey());
/*     */       
/* 189 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 4);
/*     */     } 
/*     */     
/* 192 */     addHeading(3, this.m_elist.getEntityGroup("SVCSEO").getLongDescription() + " Planned Availability checks:");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 197 */     for (j = 0; j < vector.size(); j++) {
/* 198 */       EntityItem entityItem = vector.elementAt(j);
/* 199 */       Vector vector7 = PokUtils.getAllLinkedEntities(entityItem, "SVCSEOAVAIL", "AVAIL");
/* 200 */       Vector<EntityItem> vector8 = PokUtils.getEntitiesWithMatchedAttr(vector7, "AVAILTYPE", "146");
/* 201 */       addDebug(" svcseoitem " + entityItem.getKey() + " availVct " + vector7.size() + " plaAvailVctB " + vector8.size());
/* 202 */       for (byte b1 = 0; b1 < vector8.size(); b1++) {
/* 203 */         EntityItem entityItem1 = vector8.elementAt(b1);
/* 204 */         String str = checkCtryMismatch(entityItem1, arrayList3, 4);
/* 205 */         if (str.length() > 0) {
/* 206 */           addDebug(" " + entityItem1.getKey() + " COUNTRYLIST had extra [" + str + "] not in PRCPT CNTRYEFF COUNTRYLIST");
/*     */ 
/*     */ 
/*     */           
/* 210 */           this.args[0] = getLD_NDN(entityItem) + " " + getLD_NDN(entityItem1);
/* 211 */           this.args[1] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 212 */           this.args[2] = paramEntityItem.getEntityGroup().getLongDescription() + " " + entityGroup1.getLongDescription();
/* 213 */           this.args[3] = this.args[1];
/* 214 */           this.args[4] = str;
/* 215 */           createMessage(4, "INCLUDE_ERR2", this.args);
/*     */         } 
/*     */       } 
/* 218 */       vector7.clear();
/* 219 */       vector8.clear();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     oneValidOverTime(paramEntityItem);
/*     */     
/* 227 */     j = getCheck_W_E_E(paramString);
/*     */ 
/*     */     
/* 230 */     EntityGroup entityGroup3 = this.m_elist.getEntityGroup("PRCPTCVMSPEC");
/*     */     
/* 232 */     addHeading(3, entityGroup3.getLongDescription() + " and " + this.m_elist
/* 233 */         .getEntityGroup("CVMSPEC").getLongDescription() + " checks:");
/*     */     
/* 235 */     for (byte b = 0; b < entityGroup3.getEntityItemCount(); b++) {
/* 236 */       EntityItem entityItem1 = entityGroup3.getEntityItem(b);
/* 237 */       EntityItem entityItem2 = getDownLinkEntityItem(entityItem1, "CVMSPEC");
/* 238 */       String str1 = PokUtils.getAttributeValue(entityItem1, "EFFECTIVEDATE", ", ", null, false);
/* 239 */       String str2 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", ", ", null, false);
/* 240 */       String str3 = PokUtils.getAttributeValue(entityItem1, "ENDDATE", ", ", "9999-12-31", false);
/* 241 */       String str4 = PokUtils.getAttributeValue(entityItem2, "ENDDATE", ", ", "9999-12-31", false);
/* 242 */       addDebug("doDQChecking: " + entityItem1.getKey() + " prcptcvmspecEffdate " + str1 + " prcptcvmspecEnddate " + str3 + " " + entityItem2
/* 243 */           .getKey() + " cvmspecEffdate " + str2 + " cvmspecEnddate " + str4);
/*     */       
/* 245 */       if (str1 != null && str2 != null) {
/*     */         
/* 247 */         boolean bool1 = checkDates(str1, str2, 1);
/* 248 */         if (!bool1) {
/*     */ 
/*     */           
/* 251 */           this.args[0] = getLD_NDN(entityItem1);
/* 252 */           this.args[1] = getLD_Value(entityItem1, "EFFECTIVEDATE");
/* 253 */           this.args[2] = getLD_NDN(entityItem2);
/* 254 */           this.args[3] = getLD_Value(entityItem2, "EFFECTIVEDATE");
/* 255 */           createMessage(j, "CANNOT_BE_EARLIER_ERR2", this.args);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 260 */       boolean bool = checkDates(str3, str4, 2);
/* 261 */       if (!bool) {
/*     */ 
/*     */         
/* 264 */         this.args[0] = getLD_NDN(entityItem1);
/* 265 */         this.args[1] = getLD_Value(entityItem1, "ENDDATE");
/* 266 */         this.args[2] = getLD_NDN(entityItem2);
/* 267 */         this.args[3] = getLD_Value(entityItem2, "ENDDATE");
/* 268 */         createMessage(j, "CANNOT_BE_LATER_ERR", this.args);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 273 */     arrayList3.clear();
/* 274 */     vector1.clear();
/* 275 */     vector2.clear();
/* 276 */     vector3.clear();
/* 277 */     vector4.clear();
/* 278 */     vector.clear();
/* 279 */     vector5.clear();
/* 280 */     vector6.clear();
/* 281 */     arrayList1.clear();
/* 282 */     arrayList2.clear();
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
/*     */   private void oneValidOverTime(EntityItem paramEntityItem) {
/* 300 */     StringBuffer stringBuffer = new StringBuffer(paramEntityItem.getEntityGroup().getLongDescription());
/* 301 */     stringBuffer.append(" " + this.m_elist.getEntityGroup("CNTRYEFF").getLongDescription());
/* 302 */     stringBuffer.append(" and " + this.m_elist.getEntityGroup("PRCPTCVMSPEC").getLongDescription());
/*     */     
/* 304 */     Vector<?> vector1 = getDownLinkEntityItems(paramEntityItem, "PRCPTCVMSPEC");
/* 305 */     Vector<?> vector2 = PokUtils.getAllLinkedEntities(paramEntityItem, "PRCPTCNTRYEFF", "CNTRYEFF");
/*     */     
/* 307 */     addHeading(3, stringBuffer.toString() + " validity checks:");
/*     */     
/* 309 */     addDebug("oneValidOverTime entered for " + paramEntityItem.getKey() + " PRCPTCVMSPEC-childVct.cnt " + vector1.size() + " CNTRYEFF-parentVct.cnt " + vector2
/* 310 */         .size());
/*     */     
/* 312 */     if (vector2.size() == 0) {
/* 313 */       vector1.clear();
/*     */       
/*     */       return;
/*     */     } 
/* 317 */     AttrComparator attrComparator1 = new AttrComparator("EFFECTIVEDATE");
/*     */     
/* 319 */     Collections.sort(vector1, (Comparator<?>)attrComparator1);
/*     */     
/* 321 */     Collections.sort(vector2, (Comparator<?>)attrComparator1);
/*     */     
/* 323 */     EntityItem entityItem1 = (EntityItem)vector2.firstElement();
/* 324 */     String str1 = PokUtils.getAttributeValue(entityItem1, "EFFECTIVEDATE", "", "1980-01-01", false);
/* 325 */     addDebug("oneValidOverTime first parent " + entityItem1.getKey() + " parentEffFrom " + str1);
/* 326 */     if (vector1.size() == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 332 */       vector2.clear();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 337 */     EntityItem entityItem2 = (EntityItem)vector1.firstElement();
/* 338 */     String str2 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", "", "1980-01-01", false);
/* 339 */     String str3 = PokUtils.getAttributeValue(entityItem2, "ENDDATE", "", "9999-12-31", false);
/* 340 */     addDebug("oneValidOverTime first child " + entityItem2.getKey() + " childEffFrom " + str2 + " prevEffTo " + str3);
/* 341 */     if (str2.compareTo(str1) > 0) {
/*     */ 
/*     */       
/* 344 */       this.args[0] = entityItem2.getEntityGroup().getLongDescription();
/* 345 */       this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/* 346 */       this.args[2] = entityItem1.getEntityGroup().getLongDescription();
/* 347 */       this.args[3] = str1;
/* 348 */       this.args[4] = PokUtils.getAttributeValue((EntityItem)vector2.lastElement(), "ENDDATE", "", "9999-12-31", false);
/* 349 */       createMessage(4, "INVALID_CHILD_ERR2", this.args);
/*     */ 
/*     */       
/* 352 */       this.args[0] = entityItem2.getEntityGroup().getLongDescription();
/* 353 */       this.args[1] = PokUtils.getAttributeDescription(entityItem2.getEntityGroup(), "EFFECTIVEDATE", "EFFECTIVEDATE") + " (" + str2 + ")";
/*     */       
/* 355 */       this.args[2] = paramEntityItem.getEntityGroup().getLongDescription() + " " + entityItem1.getEntityGroup().getLongDescription();
/* 356 */       this.args[3] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "EFFECTIVEDATE", "EFFECTIVEDATE") + " (" + str1 + ")";
/*     */       
/* 358 */       addResourceMsg("CANNOT_BE_LATER_ERR", this.args);
/* 359 */       vector1.clear();
/* 360 */       vector2.clear();
/*     */       
/*     */       return;
/*     */     } 
/* 364 */     AttrComparator attrComparator2 = new AttrComparator("ENDDATE");
/*     */     
/* 366 */     Collections.sort(vector1, (Comparator<?>)attrComparator2);
/*     */     
/* 368 */     Collections.sort(vector2, (Comparator<?>)attrComparator2);
/*     */     
/* 370 */     entityItem2 = (EntityItem)vector1.lastElement();
/* 371 */     entityItem1 = (EntityItem)vector2.lastElement();
/* 372 */     String str4 = PokUtils.getAttributeValue(entityItem2, "ENDDATE", "", "9999-12-31", false);
/* 373 */     String str5 = PokUtils.getAttributeValue(entityItem1, "ENDDATE", "", "9999-12-31", false);
/* 374 */     addDebug("oneValidOverTime last child " + entityItem2.getKey() + " childEffTo " + str4);
/* 375 */     addDebug("oneValidOverTime last parent " + entityItem1.getKey() + " parentEffTo " + str5);
/* 376 */     if (str4.compareTo(str5) < 0) {
/*     */       
/* 378 */       this.args[0] = entityItem2.getEntityGroup().getLongDescription();
/* 379 */       this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/* 380 */       this.args[2] = entityItem1.getEntityGroup().getLongDescription();
/* 381 */       this.args[3] = PokUtils.getAttributeValue((EntityItem)vector2.firstElement(), "EFFECTIVEDATE", "", "1980-01-01", false);
/* 382 */       this.args[4] = str5;
/* 383 */       createMessage(4, "INVALID_CHILD_ERR2", this.args);
/*     */ 
/*     */       
/* 386 */       this.args[0] = entityItem2.getEntityGroup().getLongDescription();
/* 387 */       this.args[1] = PokUtils.getAttributeDescription(entityItem2.getEntityGroup(), "ENDDATE", "ENDDATE") + " (" + str4 + ")";
/*     */       
/* 389 */       this.args[2] = paramEntityItem.getEntityGroup().getLongDescription() + " " + entityItem1.getEntityGroup().getLongDescription();
/* 390 */       this.args[3] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "EFFECTIVEDATE", "EFFECTIVEDATE") + " (" + str5 + ")";
/*     */       
/* 392 */       addResourceMsg("CANNOT_BE_EARLIER_ERR2", this.args);
/* 393 */       vector1.clear();
/* 394 */       vector2.clear();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 399 */     Collections.sort(vector1, (Comparator<?>)attrComparator1);
/*     */     
/* 401 */     Collections.sort(vector2, (Comparator<?>)attrComparator1);
/*     */ 
/*     */     
/* 404 */     for (byte b = 1; b < vector1.size(); b++) {
/* 405 */       entityItem2 = (EntityItem)vector1.elementAt(b);
/* 406 */       String str6 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", "", "1980-01-01", false);
/* 407 */       String str7 = PokUtils.getAttributeValue(entityItem2, "ENDDATE", "", "9999-12-31", false);
/* 408 */       addDebug("oneValidOverTime gaps " + entityItem2.getKey() + " prevEffTo " + str3 + " nextEffFrom " + str6 + " nextEffTo " + str7);
/*     */       
/* 410 */       if (str6.compareTo(str3) > 0) {
/*     */         
/* 412 */         this.args[0] = entityItem2.getEntityGroup().getLongDescription();
/* 413 */         this.args[1] = paramEntityItem.getEntityGroup().getLongDescription() + " " + entityItem1.getEntityGroup().getLongDescription();
/* 414 */         createMessage(4, "INVALID_CHILD_ERR", this.args);
/*     */         
/* 416 */         this.args[0] = entityItem2.getEntityGroup().getLongDescription();
/* 417 */         this.args[1] = str3;
/* 418 */         this.args[2] = str6;
/*     */         
/* 420 */         addResourceMsg("GAPS_ERR", this.args);
/*     */         break;
/*     */       } 
/* 423 */       str3 = str7;
/*     */     } 
/*     */     
/* 426 */     vector1.clear();
/* 427 */     vector2.clear();
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
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 451 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CHRGCOMP");
/* 452 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 453 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 454 */       addDebug("completeNowFinalProcessing: " + entityItem.getKey());
/*     */       
/* 456 */       if (statusIsRFRorFinal(entityItem)) {
/* 457 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "SVCMODCHRGCOMP", "SVCMOD");
/* 458 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 459 */           EntityItem entityItem1 = vector.elementAt(b1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 466 */           if (statusIsFinal(entityItem1)) {
/*     */             
/* 468 */             if (statusIsFinal(entityItem))
/*     */             {
/*     */               
/* 471 */               setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValueForItem(entityItem1, "ADSABRSTATUS"), entityItem1);
/*     */             
/*     */             }
/*     */           }
/*     */           else {
/*     */             
/* 477 */             doRFR_ADSXML(entityItem1);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 485 */         vector.clear();
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
/*     */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 510 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CHRGCOMP");
/* 511 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 512 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 513 */       addDebug("completeNowR4RProcessing: " + entityGroup.getKey());
/*     */       
/* 515 */       if (statusIsRFRorFinal(entityItem)) {
/* 516 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "SVCMODCHRGCOMP", "SVCMOD");
/* 517 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 518 */           EntityItem entityItem1 = vector.elementAt(b1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 526 */           if (statusIsRFR(entityItem1))
/*     */           {
/*     */ 
/*     */             
/* 530 */             setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(entityItem1, "ADSABRSTATUS"), entityItem1);
/*     */           }
/*     */         } 
/* 533 */         vector.clear();
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
/*     */   public String getDescription() {
/* 545 */     return "PRCPT ABR.";
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
/*     */   public String getABRVersion() {
/* 557 */     return "$Revision: 1.5 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\PRCPTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */