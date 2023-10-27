/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*     */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*     */ import COM.ibm.eannounce.objects.ChangeHistoryGroup;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EannToEpimsInt;
/*     */ import COM.ibm.eannounce.objects.EntityChangeHistoryGroup;
/*     */ import COM.ibm.eannounce.objects.EntityChangeHistoryItem;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.eannounce.objects.PDGUtility;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.opicmpdh.middleware.D;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.T;
/*     */ import COM.ibm.opicmpdh.transactions.OPICMList;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.Arrays;
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
/*     */ public class COMPATABR1
/*     */   extends PokBaseABR
/*     */ {
/*  70 */   public static final String ABR = new String("COMPATABR1");
/*  71 */   private static final String ROOT_ENTITY = new String("MODEL");
/*     */   
/*  73 */   private static final String EG_MDLCGOSMDL = new String("MDLCGOSMDL");
/*  74 */   private static final String EG_MODEL = new String("MODEL");
/*  75 */   private static final String ATT_ANNDATE = new String("ANNDATE");
/*  76 */   private static final String ATT_MT = new String("MACHTYPEATR");
/*  77 */   private static final String ATT_MODEL = new String("MODELATR");
/*  78 */   private static final String ATT_PDHDOMAIN = new String("PDHDOMAIN");
/*  79 */   private static final String ATT_OS = new String("OS");
/*     */   
/*     */   private static final int MODEL1 = 1;
/*     */   private static final int MODEL2 = 2;
/*  83 */   private EntityItem m_ei = null;
/*  84 */   private EntityList m_el = null;
/*  85 */   private PDGUtility m_utility = new PDGUtility();
/*     */   private boolean m_bAlert1 = false;
/*     */   private boolean m_bAlert2 = false;
/*     */   private boolean m_bError = false;
/*  89 */   private OPICMList m_cat2List = new OPICMList();
/*  90 */   private ExtractActionItem m_xai1 = null;
/*  91 */   private ExtractActionItem m_xai2 = null;
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*     */     try {
/*  96 */       EntityGroup entityGroup1 = null;
/*  97 */       EntityGroup entityGroup2 = null;
/*  98 */       EntityGroup entityGroup3 = null;
/*  99 */       start_ABRBuild(false);
/* 100 */       buildReportHeaderII();
/* 101 */       this.m_xai1 = new ExtractActionItem(null, this.m_db, this.m_prof, this.m_abri.getVEName());
/*     */       
/* 103 */       this.m_xai2 = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTCOMPATABR01");
/*     */       
/* 105 */       setReturnCode(0);
/* 106 */       this.m_ei = new EntityItem(null, this.m_prof, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/* 107 */       if (this.m_ei.getEntityType().equals(EG_MDLCGOSMDL)) {
/* 108 */         EntityItem[] arrayOfEntityItem = getRootModel(this.m_db, this.m_prof, this.m_ei);
/*     */         
/* 110 */         if (arrayOfEntityItem == null || arrayOfEntityItem.length <= 0) {
/* 111 */           setCreateDGEntity(false);
/* 112 */           System.out.println(getABRVersion() + " not in compatibility " + this.m_ei.getKey());
/*     */           
/*     */           return;
/*     */         } 
/* 116 */         this.m_el = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xai1, arrayOfEntityItem);
/*     */         
/* 118 */         entityGroup1 = this.m_el.getEntityGroup(EG_MODEL);
/* 119 */         entityGroup3 = this.m_el.getEntityGroup(EG_MDLCGOSMDL);
/* 120 */         entityGroup2 = this.m_el.getParentEntityGroup();
/*     */         
/* 122 */         this.m_ei = entityGroup3.getEntityItem(this.m_ei.getKey());
/* 123 */         printNavigateAttributes(this.m_ei, entityGroup3, true);
/* 124 */         for (byte b = 0; b < entityGroup2.getEntityItemCount(); b++) {
/* 125 */           EntityItem entityItem = entityGroup2.getEntityItem(b);
/* 126 */           if (this.m_ei.getEntityType().equals(EG_MDLCGOSMDL)) {
/*     */             
/* 128 */             EntityItem entityItem1 = entityGroup3.getEntityItem(this.m_ei.getKey());
/*     */             
/* 130 */             if (entityItem1 != null) {
/* 131 */               EntityItem entityItem2 = entityGroup2.getEntityItem(entityItem.getKey());
/* 132 */               EntityItem entityItem3 = (EntityItem)entityItem1.getDownLink(0);
/* 133 */               String str1 = this.m_utility.getAttrValue(entityItem1, ATT_ANNDATE);
/* 134 */               String str2 = getPreviousValue(entityItem1, ATT_ANNDATE);
/* 135 */               if (str1 != null && str1.length() > 0) {
/* 136 */                 String str3 = this.m_utility.getAttrValue(entityItem2, ATT_ANNDATE);
/* 137 */                 int i = 1;
/*     */                 
/* 139 */                 if (str3 != null && str3.length() > 0) {
/* 140 */                   i = this.m_utility.dateCompare(str1, str3);
/*     */                 }
/*     */                 
/* 143 */                 int j = 1;
/* 144 */                 String str4 = this.m_utility.getAttrValue(entityItem3, ATT_ANNDATE);
/* 145 */                 if (str4 != null && str4.length() > 0) {
/* 146 */                   j = this.m_utility.dateCompare(str1, str4);
/*     */                 }
/*     */                 
/* 149 */                 int k = this.m_utility.dateCompare(str3, str4);
/* 150 */                 if (k == 2) {
/* 151 */                   if (i != 1 || j != 1) {
/* 152 */                     println("<br>MODEL1.ANNDATE is earlier than MODEL2.ANNDATE");
/* 153 */                     println("<br><b><font color=red>Error. MDLCGOSMDL.ANNDATE is changed to be earlier than or equal to MODEL 2. ANNDATE and/or MODEL 1. ANNDATE</font></b>");
/* 154 */                     println("<br>" + printTable(entityItem2, entityItem3, entityItem1, (EntityItem)entityItem1.getUpLink(0), str2, 1));
/* 155 */                     this.m_bError = true;
/* 156 */                     this.m_bAlert1 = true;
/* 157 */                     getPDHDomain(entityItem2, 1);
/*     */                   } else {
/* 159 */                     println("<br>MODEL1.ANNDATE is earlier than MODEL2.ANNDATE");
/* 160 */                     println("<br><b>MDLCGOSMDL.ANNDATE is changed.</b>");
/* 161 */                     println("<br>" + printTable(entityItem2, entityItem3, entityItem1, (EntityItem)entityItem1.getUpLink(0), str2, 1));
/* 162 */                     this.m_bAlert2 = true;
/* 163 */                     getPDHDomain(entityItem3, 2);
/*     */                   } 
/* 165 */                 } else if (k == 1) {
/* 166 */                   if (i != 1 || j != 1) {
/* 167 */                     println("<br>MODEL2.ANNDATE is earlier than MODEL1.ANNDATE");
/* 168 */                     println("<br><b><font color=red>Error. MDLCGOSMDL.ANNDATE is changed to be earlier than or equal to MODEL 2. ANNDATE and/or MODEL 1. ANNDATE</font></b>");
/* 169 */                     println("<br>" + printTable(entityItem2, entityItem3, entityItem1, (EntityItem)entityItem1.getUpLink(0), str2, 1));
/* 170 */                     this.m_bError = true;
/* 171 */                     this.m_bAlert1 = true;
/* 172 */                     getPDHDomain(entityItem2, 1);
/*     */                   
/*     */                   }
/*     */                   else {
/*     */                     
/* 177 */                     println("<br>MODEL2.ANNDATE is earlier than MODEL1.ANNDATE");
/* 178 */                     println("<br><b>MDLCGOSMDL.ANNDATE is changed.</font></b>");
/* 179 */                     println("<br>" + printTable(entityItem2, entityItem3, entityItem1, (EntityItem)entityItem1.getUpLink(0), str2, 1));
/* 180 */                     this.m_bAlert2 = true;
/* 181 */                     getPDHDomain(entityItem3, 2);
/*     */                   }
/*     */                 
/*     */                 } 
/* 185 */               } else if (str2 != null && str2.length() > 0) {
/* 186 */                 this.m_bAlert2 = true;
/* 187 */                 println("<br>MDLCGOSMDL.ANNDATE is removed.");
/* 188 */                 println("<br>" + printTable(entityItem2, entityItem3, entityItem1, (EntityItem)entityItem1.getUpLink(0), str2, 1));
/* 189 */                 getPDHDomain(entityItem3, 2);
/*     */               }
/*     */             
/*     */             } 
/*     */           } 
/*     */         } 
/* 195 */       } else if (this.m_ei.getEntityType().equals(EG_MODEL)) {
/*     */         
/* 197 */         EntityItem[] arrayOfEntityItem = { this.m_ei };
/*     */         
/* 199 */         this.m_el = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xai1, arrayOfEntityItem);
/*     */         
/* 201 */         entityGroup1 = this.m_el.getEntityGroup(EG_MODEL);
/* 202 */         entityGroup3 = this.m_el.getEntityGroup(EG_MDLCGOSMDL);
/* 203 */         entityGroup2 = this.m_el.getParentEntityGroup();
/*     */         
/* 205 */         this.m_ei = entityGroup2.getEntityItem(this.m_ei.getKey());
/* 206 */         printNavigateAttributes(this.m_ei, entityGroup2, true);
/* 207 */         T.est((entityGroup1 != null), "EntityGroup MODEL is null for EXTTECHCOMPMAINT1");
/* 208 */         if (entityGroup1 != null && entityGroup1.getEntityItemCount() > 0) {
/*     */           
/* 210 */           EntityItem entityItem = entityGroup2.getEntityItem(this.m_ei.getKey());
/* 211 */           String str1 = this.m_utility.getAttrValue(entityItem, ATT_ANNDATE);
/* 212 */           String str2 = getPreviousValue(entityItem, ATT_ANNDATE);
/*     */           
/* 214 */           for (byte b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/* 215 */             EntityItem entityItem1 = entityGroup1.getEntityItem(b);
/* 216 */             String str3 = this.m_utility.getAttrValue(entityItem1, ATT_ANNDATE);
/* 217 */             EntityItem entityItem2 = getMDLCGOSMDL(this.m_el, entityItem, entityItem1);
/* 218 */             if (entityItem2 != null) {
/*     */ 
/*     */ 
/*     */               
/* 222 */               String str4 = this.m_utility.getAttrValue(entityItem2, ATT_ANNDATE);
/*     */               
/* 224 */               int i = this.m_utility.dateCompare(str2, str3);
/* 225 */               int j = this.m_utility.dateCompare(str1, str3);
/*     */               
/* 227 */               if (i == 2) {
/*     */                 
/* 229 */                 if (j == 2) {
/*     */                   
/* 231 */                   System.out.println(getABRVersion() + " MODEL 1. ANNDATE earlier than MODEL 2. ANNDATE   OK " + entityItem.getKey());
/* 232 */                 } else if (j == 1) {
/*     */ 
/*     */                   
/* 235 */                   this.m_bAlert1 = true;
/* 236 */                   this.m_bAlert2 = true;
/* 237 */                   println("<br>Previous MODEL1.ANNDATE is earlier than MODEL2.ANNDATE.");
/* 238 */                   println("<br><b>MODEL 1. ANNDATE is changed to be later than MODEL 2. ANNDATE.</b>");
/* 239 */                   println("<br>" + printTable(entityItem, entityItem1, entityItem2, (EntityItem)entityItem2.getUpLink(0), str2, 2));
/* 240 */                   getPDHDomain(entityItem, 1);
/* 241 */                   getPDHDomain(entityItem1, 2);
/*     */                 } 
/* 243 */               } else if (i == 1) {
/*     */                 
/* 245 */                 if (j == 2) {
/*     */ 
/*     */                   
/* 248 */                   this.m_bAlert1 = true;
/* 249 */                   this.m_bAlert2 = true;
/* 250 */                   println("<br>Previous MODEL1.ANNDATE is later than MODEL2.ANNDATE.");
/* 251 */                   println("<br><b>MODEL 1. ANNDATE is changed to be earlier than MODEL 2. ANNDATE.</b>");
/* 252 */                   println("<br>" + printTable(entityItem, entityItem1, entityItem2, (EntityItem)entityItem2.getUpLink(0), str2, 2));
/* 253 */                   getPDHDomain(entityItem, 1);
/* 254 */                   getPDHDomain(entityItem1, 2);
/* 255 */                 } else if (j == 1) {
/*     */ 
/*     */ 
/*     */                   
/* 259 */                   this.m_bAlert2 = true;
/* 260 */                   println("<br>Previous MODEL1.ANNDATE is later than MODEL2.ANNDATE.");
/* 261 */                   println("<br>MODEL 1. ANNDATE is changed to be later than MODEL 2. ANNDATE.");
/* 262 */                   println("<br>" + printTable(entityItem, entityItem1, entityItem2, (EntityItem)entityItem2.getUpLink(0), str2, 2));
/* 263 */                   getPDHDomain(entityItem1, 2);
/*     */                 } 
/*     */               } 
/*     */               
/* 267 */               if (str4 != null && str4.length() > 0) {
/*     */                 
/* 269 */                 int k = this.m_utility.dateCompare(str1, str4);
/*     */                 
/* 271 */                 if (k == 2) {
/*     */                   
/* 273 */                   System.out.println(getABRVersion() + " MODEL 1. ANNDATE earlier than Override (MDLCGOSMDL.ANNDATE)  OK " + entityItem.getKey());
/* 274 */                 } else if (k == 1) {
/*     */ 
/*     */                   
/* 277 */                   this.m_bError = true;
/* 278 */                   this.m_bAlert1 = true;
/* 279 */                   getPDHDomain(entityItem, 1);
/* 280 */                   println("<br>A valid Override (MDLCGOSMDL.ANNDATE) date exists.");
/* 281 */                   println("<br><b><font color=red>Error. MODEL1.ANNDATE is changed to be later than MDLCGOSMDL.ANNDATE.</font></b>");
/* 282 */                   println("<br>" + printTable(entityItem, entityItem1, entityItem2, (EntityItem)entityItem2.getUpLink(0), str2, 2));
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 289 */         this.m_el = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xai2, arrayOfEntityItem);
/*     */         
/* 291 */         entityGroup1 = this.m_el.getEntityGroup(EG_MODEL);
/* 292 */         entityGroup3 = this.m_el.getEntityGroup(EG_MDLCGOSMDL);
/* 293 */         entityGroup2 = this.m_el.getParentEntityGroup();
/*     */         
/* 295 */         this.m_ei = entityGroup2.getEntityItem(this.m_ei.getKey());
/*     */         
/* 297 */         T.est((entityGroup1 != null), "EntityGroup MODEL is null for EXTCOMPATABR01");
/* 298 */         if (entityGroup1 != null && entityGroup1.getEntityItemCount() > 0) {
/*     */           
/* 300 */           System.out.println(getABRVersion() + " MODEL2 gets changed");
/* 301 */           EntityItem entityItem = entityGroup2.getEntityItem(this.m_ei.getKey());
/* 302 */           String str1 = this.m_utility.getAttrValue(entityItem, ATT_ANNDATE);
/* 303 */           String str2 = getPreviousValue(entityItem, ATT_ANNDATE);
/*     */           
/* 305 */           for (byte b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/* 306 */             EntityItem entityItem1 = entityGroup1.getEntityItem(b);
/*     */             
/* 308 */             String str3 = this.m_utility.getAttrValue(entityItem1, ATT_ANNDATE);
/* 309 */             EntityItem entityItem2 = getMDLCGOSMDL(this.m_el, entityItem1, entityItem);
/*     */             
/* 311 */             if (entityItem2 != null) {
/*     */ 
/*     */ 
/*     */               
/* 315 */               String str4 = this.m_utility.getAttrValue(entityItem2, ATT_ANNDATE);
/*     */               
/* 317 */               int i = this.m_utility.dateCompare(str1, str2);
/* 318 */               int j = this.m_utility.dateCompare(str1, str3);
/* 319 */               int k = this.m_utility.dateCompare(str3, str2);
/*     */               
/* 321 */               if (k == 2) {
/*     */                 
/* 323 */                 if (j == 1) {
/*     */                   
/* 325 */                   this.m_bAlert1 = true;
/* 326 */                   this.m_bAlert2 = true;
/* 327 */                   getPDHDomain(entityItem1, 1);
/* 328 */                   getPDHDomain(entityItem, 2);
/* 329 */                   println("<br>MODEL1. ANNDATE is earlier than previous MODEL2.ANNDATE");
/* 330 */                   println("<br>MODEL 2. ANNDATE is changed to be later than MODEL 1. ANNDATE");
/* 331 */                   println("<br>" + printTable(entityItem1, entityItem, entityItem2, (EntityItem)entityItem2.getUpLink(0), str2, 3));
/*     */                 } else {
/*     */                   
/* 334 */                   System.out.println(getABRVersion() + " MODEL1. ANNDATE is earlier than previous MODEL2.ANNDATE");
/* 335 */                   System.out.println(getABRVersion() + " Change MODEL 2. ANNDATE  not later than MODEL 1. ANNDATE OK " + entityItem1.getKey());
/*     */                 } 
/* 337 */               } else if (k == 1) {
/*     */                 
/* 339 */                 if (j == 1) {
/*     */                   
/* 341 */                   this.m_bAlert1 = true;
/* 342 */                   this.m_bAlert2 = true;
/* 343 */                   getPDHDomain(entityItem1, 1);
/* 344 */                   getPDHDomain(entityItem, 2);
/* 345 */                   println("<br>Previous MODEL 2.ANNDATE is earlier than MODEL 1.ANNDATE");
/* 346 */                   println("<br>MODEL 2. ANNDATE is changed to be later than MODEL 1. ANNDATE");
/* 347 */                   println("<br>" + printTable(entityItem1, entityItem, entityItem2, (EntityItem)entityItem2.getUpLink(0), str2, 3));
/*     */                 } else {
/*     */                   
/* 350 */                   System.out.println(getABRVersion() + "Previous MODEL 2.ANNDATE is earlier than MODEL 1.ANNDATE");
/* 351 */                   System.out.println(getABRVersion() + "Change MODEL 2. ANNDATE   not later than MODEL 1. ANNDATE OK " + entityItem1.getKey());
/*     */                 } 
/*     */               } 
/*     */ 
/*     */               
/* 356 */               if (str4 != null && str4.length() > 0) {
/*     */                 
/* 358 */                 int m = this.m_utility.dateCompare(str1, str4);
/* 359 */                 if (i == 2) {
/* 360 */                   this.m_bAlert1 = true;
/* 361 */                   getPDHDomain(entityItem1, 1);
/* 362 */                   println("<br>A valid Override (MDLCGOSMDL.ANNDATE) date exists. ");
/* 363 */                   println("<br><b>MODEL 2. ANNDATE is changed to be earlier than its previous value.</b> ");
/* 364 */                   println("<br>" + printTable(entityItem1, entityItem, entityItem2, (EntityItem)entityItem2.getUpLink(0), str2, 3));
/*     */                 } 
/*     */                 
/* 367 */                 if (i == 1 && m == 2) {
/* 368 */                   this.m_bAlert1 = true;
/* 369 */                   getPDHDomain(entityItem1, 1);
/* 370 */                   println("<br>A valid Override (MDLCGOSMDL.ANNDATE) date exists. ");
/* 371 */                   println("<br><b>MDLCGOSMDL.ANNDATE is changed to be later than the original MODEL 2. ANNDATE, but earlier than Override (MDLCGOSMDL.ANNDATE).</b> ");
/* 372 */                   println("<br>" + printTable(entityItem1, entityItem, entityItem2, (EntityItem)entityItem2.getUpLink(0), str2, 3));
/* 373 */                 } else if (m == 1) {
/* 374 */                   this.m_bError = true;
/* 375 */                   this.m_bAlert1 = true;
/* 376 */                   this.m_bAlert2 = true;
/* 377 */                   getPDHDomain(entityItem1, 1);
/* 378 */                   getPDHDomain(entityItem, 2);
/* 379 */                   println("<br>A valid Override (MDLCGOSMDL.ANNDATE) date exists. ");
/* 380 */                   println("<br><b><font color=red>Error.MODEL 2. ANNDATE is changed to be later than Override (MDLCGOSMDL.ANNDATE).</font></b>");
/* 381 */                   println("<br>" + printTable(entityItem1, entityItem, entityItem2, (EntityItem)entityItem2.getUpLink(0), str2, 3));
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 388 */       if (this.m_bError) {
/* 389 */         setReturnCode(-1);
/*     */       }
/* 391 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 392 */       setReturnCode(-2);
/* 393 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 397 */           .getMessage() + "</font></h3>");
/*     */       
/* 399 */       logError(lockPDHEntityException.getMessage());
/* 400 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 401 */       setReturnCode(-2);
/* 402 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 404 */           .getMessage() + "</font></h3>");
/*     */       
/* 406 */       logError(updatePDHEntityException.getMessage());
/* 407 */     } catch (SBRException sBRException) {
/* 408 */       String str2 = sBRException.toString();
/* 409 */       int i = str2.indexOf("(ok)");
/* 410 */       if (i < 0) {
/* 411 */         setReturnCode(-2);
/* 412 */         println("<h3><font color=red>Generate Data error: " + replace(str2, "\n", "<br>") + "</font></h3>");
/* 413 */         logError(sBRException.toString());
/*     */       } else {
/* 415 */         str2 = str2.substring(0, i);
/* 416 */         println(replace(str2, "\n", "<br>"));
/*     */       } 
/* 418 */     } catch (Exception exception) {
/*     */       
/* 420 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 421 */       println("" + exception);
/* 422 */       exception.printStackTrace();
/*     */       
/* 424 */       StringWriter stringWriter = null;
/* 425 */       String str2 = null;
/* 426 */       stringWriter = new StringWriter();
/* 427 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 428 */       str2 = stringWriter.toString();
/* 429 */       println(str2);
/*     */ 
/*     */       
/* 432 */       if (getABRReturnCode() != -2) {
/* 433 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 436 */       log(buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 439 */               getABRDescription(), 
/* 440 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 443 */       String str = getABRDescription() + ":" + this.m_abri.getEntityType() + ":" + this.m_abri.getEntityID();
/* 444 */       if (str.length() > 64) {
/* 445 */         str = str.substring(0, 64);
/*     */       }
/* 447 */       setDGTitle(str);
/* 448 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 451 */       String[] arrayOfString = new String[this.m_cat2List.size()];
/* 452 */       this.m_cat2List.copyTo((Object[])arrayOfString);
/* 453 */       setDGCat2(arrayOfString);
/*     */ 
/*     */       
/* 456 */       setDGString(getABRReturnCode());
/* 457 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 462 */       if (!isReadOnly()) {
/* 463 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private EntityItem[] getRootModel(Database paramDatabase, Profile paramProfile, EntityItem paramEntityItem) {
/* 470 */     EntityItem[] arrayOfEntityItem = null;
/*     */     try {
/* 472 */       EntityChangeHistoryGroup entityChangeHistoryGroup = new EntityChangeHistoryGroup(paramDatabase, paramProfile, paramEntityItem);
/* 473 */       EntityChangeHistoryItem entityChangeHistoryItem = (EntityChangeHistoryItem)EannToEpimsInt.getCurrentChangeItem((ChangeHistoryGroup)entityChangeHistoryGroup);
/* 474 */       String str1 = entityChangeHistoryItem.getChangeDate().substring(0, 11) + "00.00.00.000000";
/* 475 */       String str2 = entityChangeHistoryItem.getChangeDate().substring(0, 11) + "23.59.59.999999";
/*     */       
/* 477 */       if (paramEntityItem.getEntityType().equals(EG_MDLCGOSMDL)) {
/* 478 */         arrayOfEntityItem = EannToEpimsInt.getChangedRootEntities(paramDatabase, paramProfile, ROOT_ENTITY, this.m_abri.getVEName(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), str1, str2);
/*     */       }
/* 480 */     } catch (Exception exception) {
/* 481 */       exception.printStackTrace();
/*     */     } 
/* 483 */     return arrayOfEntityItem;
/*     */   }
/*     */   
/*     */   private void getPDHDomain(EntityItem paramEntityItem, int paramInt) {
/* 487 */     if (paramEntityItem == null) {
/*     */       return;
/*     */     }
/* 490 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(ATT_PDHDOMAIN);
/* 491 */     if (eANAttribute != null) {
/* 492 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/*     */       
/* 494 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/* 495 */         MetaFlag metaFlag = arrayOfMetaFlag[b];
/* 496 */         if (metaFlag.isSelected()) {
/* 497 */           String str = metaFlag.getFlagCode();
/* 498 */           this.m_cat2List.put(str, str);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getPreviousValue(EntityItem paramEntityItem, String paramString) {
/* 558 */     String str = "";
/*     */     
/* 560 */     if (paramEntityItem == null) {
/* 561 */       return str;
/*     */     }
/* 563 */     EANAttribute eANAttribute = (EANAttribute)paramEntityItem.getEANObject(paramEntityItem.getEntityType() + ":" + paramString);
/* 564 */     if (eANAttribute != null) {
/*     */       try {
/* 566 */         AttributeChangeHistoryGroup attributeChangeHistoryGroup = new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*     */         
/* 568 */         String[] arrayOfString = new String[attributeChangeHistoryGroup.getChangeHistoryItemCount()]; int i;
/* 569 */         for (i = 0; i < attributeChangeHistoryGroup.getChangeHistoryItemCount(); i++) {
/* 570 */           AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(i);
/*     */           
/* 572 */           D.ebug(4, getABRVersion() + attributeChangeHistoryItem.dump(false));
/* 573 */           String str1 = attributeChangeHistoryItem.getChangeDate();
/* 574 */           arrayOfString[i] = str1 + ":" + attributeChangeHistoryItem.getKey();
/*     */         } 
/* 576 */         Arrays.sort((Object[])arrayOfString);
/*     */         
/* 578 */         i = arrayOfString.length - 2;
/* 579 */         if (i > 0) {
/* 580 */           String str1 = arrayOfString[i];
/* 581 */           int j = str1.indexOf(":");
/* 582 */           String str2 = str1.substring(j + 1);
/* 583 */           AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(str2);
/* 584 */           str = attributeChangeHistoryItem.getAttributeValue();
/*     */         } 
/* 586 */       } catch (Exception exception) {
/* 587 */         System.out.println(getABRVersion() + exception.toString());
/*     */       } 
/*     */     }
/*     */     
/* 591 */     return str;
/*     */   }
/*     */   
/*     */   private EntityItem getMDLCGOSMDL(EntityList paramEntityList, EntityItem paramEntityItem1, EntityItem paramEntityItem2) {
/* 595 */     String str = " COMPATABR1 getMDLCGOSMDL method ";
/* 596 */     D.ebug(4, str + " eiModel1: " + paramEntityItem1.getKey() + ", eiModel2: " + paramEntityItem2.getKey());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 607 */     Vector<Integer> vector = this.m_utility.getParentEntityIds(paramEntityList, paramEntityItem1.getEntityType(), paramEntityItem1.getEntityID(), "MODELCG", "MDLCGMDL");
/*     */ 
/*     */ 
/*     */     
/* 611 */     EntityGroup entityGroup1 = paramEntityList.getEntityGroup(EG_MDLCGOSMDL);
/* 612 */     EntityGroup entityGroup2 = paramEntityList.getEntityGroup("MODELCGOS");
/*     */     
/* 614 */     D.ebug(4, str + " vMODELCG size: " + vector.size());
/*     */     
/* 616 */     for (byte b = 0; b < vector.size(); b++) {
/* 617 */       int i = ((Integer)vector.elementAt(b)).intValue();
/* 618 */       D.ebug(4, str + " iMODELCG: " + i);
/* 619 */       Vector<Integer> vector1 = this.m_utility.getChildrenEntityIds(paramEntityList, "MODELCG", i, "MODELCGOS", "MDLCGMDLCGOS");
/*     */       
/* 621 */       D.ebug(4, str + " vMODELCGOS size: " + vector1.size());
/* 622 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 623 */         int j = ((Integer)vector1.elementAt(b1)).intValue();
/* 624 */         D.ebug(4, str + " iMODELCGOS: " + j);
/*     */         
/* 626 */         EntityItem entityItem = entityGroup2.getEntityItem("MODELCGOS" + j);
/* 627 */         Vector<Integer> vector2 = this.m_utility.getChildrenEntityIds(paramEntityList, "MODELCGOS", j, "MODEL", "MDLCGOSMDL");
/* 628 */         D.ebug(4, str + " vMODEL2 size: " + vector2.size());
/*     */         
/* 630 */         for (byte b2 = 0; b2 < vector2.size(); b2++) {
/* 631 */           int k = ((Integer)vector2.elementAt(b2)).intValue();
/* 632 */           D.ebug(4, str + " iMODEL2: " + k);
/*     */           
/* 634 */           if (paramEntityItem2.getEntityID() == k) {
/* 635 */             for (byte b3 = 0; b3 < entityGroup1.getEntityItemCount(); b3++) {
/* 636 */               EntityItem entityItem1 = entityGroup1.getEntityItem(b3);
/* 637 */               EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/* 638 */               EntityItem entityItem3 = (EntityItem)entityItem1.getDownLink(0);
/* 639 */               if (entityItem2.getKey().equals(entityItem.getKey()) && entityItem3.getKey().equals(paramEntityItem2.getKey())) {
/* 640 */                 return entityItem1;
/*     */               }
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 648 */     return null;
/*     */   }
/*     */   
/*     */   private String printTable(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3, EntityItem paramEntityItem4, String paramString, int paramInt) {
/* 652 */     StringBuffer stringBuffer = new StringBuffer();
/* 653 */     stringBuffer.append("<table>");
/* 654 */     stringBuffer.append("<tr><td>&nbsp;</td><td>Model 1: " + paramEntityItem1.getKey() + "</td><td>&nbsp;</td>");
/* 655 */     stringBuffer.append("<td>&nbsp;</td><td>Model 2: " + paramEntityItem2.getKey() + "</td><td>&nbsp;</td>");
/* 656 */     stringBuffer.append("<td>Operating System (OS)</td><td>OverRide AnnDate</td></tr>");
/* 657 */     stringBuffer.append("<tr><td>MT</td><td>Model</td><td>AnnDate</td><td>MT</td><td>Model</td><td>AnnDate</td></tr>");
/* 658 */     stringBuffer.append("<tr>");
/* 659 */     stringBuffer.append("<td>" + this.m_utility.getAttrValue(paramEntityItem1, ATT_MT) + "</td>");
/* 660 */     stringBuffer.append("<td>" + this.m_utility.getAttrValue(paramEntityItem1, ATT_MODEL) + "</td>");
/* 661 */     stringBuffer.append("<td>");
/* 662 */     if (paramInt == 2) {
/* 663 */       stringBuffer.append("*" + paramString + "<br>");
/*     */     }
/* 665 */     stringBuffer.append(this.m_utility.getAttrValue(paramEntityItem1, ATT_ANNDATE));
/* 666 */     stringBuffer.append("</td>");
/* 667 */     stringBuffer.append("<td>" + this.m_utility.getAttrValue(paramEntityItem2, ATT_MT) + "</td>");
/* 668 */     stringBuffer.append("<td>" + this.m_utility.getAttrValue(paramEntityItem2, ATT_MODEL) + "</td>");
/* 669 */     stringBuffer.append("<td>");
/* 670 */     if (paramInt == 3) {
/* 671 */       stringBuffer.append("*" + paramString + "<br>");
/*     */     }
/* 673 */     stringBuffer.append(this.m_utility.getAttrValue(paramEntityItem2, ATT_ANNDATE));
/* 674 */     stringBuffer.append("</td>");
/* 675 */     stringBuffer.append("<td>" + this.m_utility.getAttrValueDesc(paramEntityItem4, ATT_OS) + "</td>");
/* 676 */     stringBuffer.append("<td>");
/* 677 */     if (paramInt == 1) {
/* 678 */       stringBuffer.append("*" + paramString + "<br>");
/*     */     }
/* 680 */     stringBuffer.append(this.m_utility.getAttrValue(paramEntityItem3, ATT_ANNDATE));
/* 681 */     stringBuffer.append("</td>");
/* 682 */     stringBuffer.append("</tr>");
/* 683 */     stringBuffer.append("</table>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 692 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 696 */     String str = "";
/* 697 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 699 */     while (paramString1.length() > 0 && i >= 0) {
/* 700 */       str = str + paramString1.substring(0, i) + paramString3;
/* 701 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 702 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 704 */     str = str + paramString1;
/* 705 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 716 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 725 */     return "COMPATABR1";
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
/* 736 */     return "";
/*     */   }
/*     */   
/*     */   public String getRevision() {
/* 740 */     return new String("1.14");
/*     */   }
/*     */   
/*     */   public static String getVersion() {
/* 744 */     return "COMPATABR1.java,v 1.14 2008/01/30 20:02:00 wendy Exp";
/*     */   }
/*     */   
/*     */   public String getABRVersion() {
/* 748 */     return "COMPATABR1.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\COMPATABR1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */