/*     */ package COM.ibm.eannounce.abr.pcd;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*     */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*     */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*     */ import COM.ibm.opicmpdh.translation.PackageID;
/*     */ import COM.ibm.opicmpdh.translation.Translation;
/*     */ import COM.ibm.opicmpdh.translation.TranslationDataRequest;
/*     */ import COM.ibm.opicmpdh.translation.TranslationPackage;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XLATEABR01
/*     */   extends PokBaseABR
/*     */ {
/* 141 */   public static final String ABR = new String("XLATEABR01");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public static final String DEF_NOT_POPULATED_HTML = new String("** Not Populated **");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   public static final String NULL = new String("");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public static final String XLATEGRP = new String("XLATEGRP");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public static final String METAXLATEGRP = new String("METAXLATEGRP");
/*     */ 
/*     */ 
/*     */   
/* 166 */   public static final String XLATEGRP_DESC = new String("Translation Group");
/*     */ 
/*     */ 
/*     */   
/* 170 */   public static final String XLATEGRPSTATUS = new String("XLATEGRPSTATUS");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public static final String METAXLATEGRPSTATUS = new String("METAXLATEGRPSTATUS");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   public static final String XLSTATUS2 = new String("XLSTATUS2");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 187 */   public static final String XLSTATUS3 = new String("XLSTATUS3");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   public static final String XLSTATUS4 = new String("XLSTATUS4");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 197 */   public static final String XLSTATUS5 = new String("XLSTATUS5");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 202 */   public static final String XLSTATUS6 = new String("XLSTATUS6");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 207 */   public static final String XLSTATUS7 = new String("XLSTATUS7");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 212 */   public static final String XLSTATUS8 = new String("XLSTATUS8");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 217 */   public static final String XLSTATUS9 = new String("XLSTATUS9");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 222 */   public static final String XLSTATUS10 = new String("XLSTATUS10");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 227 */   public static final String XLSTATUS11 = new String("XLSTATUS11");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 232 */   public static final String XLSTATUS12 = new String("XLSTATUS12");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 237 */   public static final String XLSTATUS13 = new String("XLSTATUS13");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 243 */   public static final String METAXLSTATUS2 = new String("METAXLSTATUS2");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 248 */   public static final String METAXLSTATUS3 = new String("METAXLSTATUS3");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 253 */   public static final String METAXLSTATUS4 = new String("METAXLSTATUS4");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 258 */   public static final String METAXLSTATUS5 = new String("METAXLSTATUS5");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 263 */   public static final String METAXLSTATUS6 = new String("METAXLSTATUS6");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 268 */   public static final String METAXLSTATUS7 = new String("METAXLSTATUS7");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 273 */   public static final String METAXLSTATUS8 = new String("METAXLSTATUS8");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 278 */   public static final String METAXLSTATUS9 = new String("METAXLSTATUS9");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 283 */   public static final String METAXLSTATUS10 = new String("METAXLSTATUS10");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 288 */   public static final String METAXLSTATUS11 = new String("METAXLSTATUS11");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 293 */   public static final String METAXLSTATUS12 = new String("METAXLSTATUS12");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 298 */   public static final String METAXLSTATUS13 = new String("METAXLSTATUS13");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 309 */   private final String m_strRelProMgmtValue = "F0030";
/*     */   
/* 311 */   private final String m_strRelProMgmtValue1 = "F0030";
/*     */ 
/*     */   
/* 314 */   private final String m_strXLSTATUS2Value = "XL20";
/*     */   
/* 316 */   private final String m_strXLSTATUS3Value = "XL20";
/*     */   
/* 318 */   private final String m_strXLSTATUS4Value = "XL20";
/*     */   
/* 320 */   private final String m_strXLSTATUS5Value = "XL20";
/*     */   
/* 322 */   private final String m_strXLSTATUS6Value = "XL20";
/*     */   
/* 324 */   private final String m_strXLSTATUS7Value = "XL20";
/*     */   
/* 326 */   private final String m_strXLSTATUS8Value = "XL20";
/*     */   
/* 328 */   private final String m_strXLSTATUS9Value = "XL20";
/*     */   
/* 330 */   private final String m_strXLSTATUS10Value = "XL20";
/*     */   
/* 332 */   private final String m_strXLSTATUS11Value = "XL20";
/*     */   
/* 334 */   private final String m_strXLSTATUS12Value = "XL20";
/*     */   
/* 336 */   private final String m_strXLSTATUS13Value = "XL20";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */     try {
/* 358 */       start_ABRBuild(false);
/* 359 */       buildReportHeader();
/* 360 */       setControlBlock();
/* 361 */       logMessage("************ Root Entity Type and id " + 
/*     */           
/* 363 */           getEntityType() + ":" + 
/*     */           
/* 365 */           getEntityID());
/* 366 */       setReturnCode(0);
/*     */ 
/*     */ 
/*     */       
/* 370 */       EntityGroup entityGroup = this.m_db.getEntityGroup(this.m_prof, this.m_abri.getEntityType(), "Edit");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 377 */       EntityItem entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */ 
/*     */       
/* 380 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("BILLINGCODE");
/* 381 */       String str = "";
/* 382 */       if (eANFlagAttribute != null) {
/* 383 */         str = eANFlagAttribute.getFirstActiveFlagCode();
/*     */       }
/* 385 */       if (str.equals("PCD")) {
/* 386 */         str = "";
/*     */       }
/*     */ 
/*     */       
/* 390 */       println("<b><tr><td class=\"PsgText\" width=\"100%\" >" + 
/*     */           
/* 392 */           getEntityType() + ":</b></td><b><td class=\"PsgText\" width=\"100%\">" + 
/*     */           
/* 394 */           getEntityID() + "</b></td>");
/*     */       
/* 396 */       printNavigateAttributes(entityItem, entityGroup, true);
/*     */ 
/*     */       
/* 399 */       if (entityGroup.getEntityType().equals("XLATEGRP")) {
/* 400 */         logMessage("**********Using Entity #1:" + getEntityType());
/* 401 */         EANAttribute eANAttribute = entityItem.getAttribute("XLLANGUAGES");
/*     */         
/* 403 */         if (eANAttribute != null) {
/*     */           
/* 405 */           EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)eANAttribute;
/* 406 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute1.get();
/* 407 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/* 408 */             if (arrayOfMetaFlag[b].isSelected()) {
/* 409 */               String str1 = arrayOfMetaFlag[b].getFlagCode().trim();
/* 410 */               logMessage("*************** sFlagClass: " + str1);
/*     */               
/* 412 */               if (str1.equals("")) {
/* 413 */                 setReturnCode(-1);
/* 414 */               } else if (str1.equals("2")) {
/* 415 */                 setFlagValue(XLATEGRP, 
/*     */                     
/* 417 */                     getEntityID(), XLSTATUS2);
/*     */               }
/* 419 */               else if (str1.equals("3")) {
/* 420 */                 setFlagValue(XLATEGRP, 
/*     */                     
/* 422 */                     getEntityID(), XLSTATUS3);
/*     */               }
/* 424 */               else if (str1.equals("4")) {
/* 425 */                 setFlagValue(XLATEGRP, 
/*     */                     
/* 427 */                     getEntityID(), XLSTATUS4);
/*     */               }
/* 429 */               else if (str1.equals("5")) {
/* 430 */                 setFlagValue(XLATEGRP, 
/*     */                     
/* 432 */                     getEntityID(), XLSTATUS5);
/*     */               }
/* 434 */               else if (str1.equals("6")) {
/* 435 */                 setFlagValue(XLATEGRP, 
/*     */                     
/* 437 */                     getEntityID(), XLSTATUS6);
/*     */               }
/* 439 */               else if (str1.equals("7")) {
/* 440 */                 setFlagValue(XLATEGRP, 
/*     */                     
/* 442 */                     getEntityID(), XLSTATUS7);
/*     */               }
/* 444 */               else if (str1.equals("8")) {
/* 445 */                 setFlagValue(XLATEGRP, 
/*     */                     
/* 447 */                     getEntityID(), XLSTATUS8);
/*     */               }
/* 449 */               else if (str1.equals("9")) {
/* 450 */                 setFlagValue(XLATEGRP, 
/*     */                     
/* 452 */                     getEntityID(), XLSTATUS9);
/*     */               }
/* 454 */               else if (str1.equals("10")) {
/* 455 */                 setFlagValue(XLATEGRP, 
/*     */                     
/* 457 */                     getEntityID(), XLSTATUS10);
/*     */               }
/* 459 */               else if (str1.equals("11")) {
/* 460 */                 setFlagValue(XLATEGRP, 
/*     */                     
/* 462 */                     getEntityID(), XLSTATUS11);
/*     */               }
/* 464 */               else if (str1.equals("12")) {
/* 465 */                 setFlagValue(XLATEGRP, 
/*     */                     
/* 467 */                     getEntityID(), XLSTATUS12);
/*     */               }
/* 469 */               else if (str1.equals("13")) {
/* 470 */                 setFlagValue(XLATEGRP, 
/*     */                     
/* 472 */                     getEntityID(), XLSTATUS13);
/*     */               } 
/*     */               
/* 475 */               int i = Integer.parseInt(str1);
/*     */ 
/*     */ 
/*     */               
/* 479 */               PackageID packageID = new PackageID(getEntityType(), getEntityID(), i, "TRANSOUTBOUNDX" + i, this.m_strNow, str);
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 484 */               logMessage("*************** pkID: " + packageID
/* 485 */                   .toString());
/*     */               
/* 487 */               TranslationPackage translationPackage = Translation.generatePDHPackage(this.m_db, this.m_prof, packageID);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 497 */               Translation.putPDHPackage(this.m_db, this.m_prof, translationPackage);
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 504 */           setReturnCode(-1);
/* 505 */           println("*The Translation Package you're working with does not have any Requested Languages Selected");
/*     */         } 
/*     */ 
/*     */         
/* 509 */         if (getReturnCode() == 0) {
/* 510 */           setFlagValue(XLATEGRP, getEntityID(), XLATEGRPSTATUS);
/*     */         }
/*     */       } else {
/*     */         
/* 514 */         logMessage("**********Using Entity #2:" + getEntityType());
/* 515 */         EANAttribute eANAttribute = entityItem.getAttribute("XLLANGUAGES");
/*     */         
/* 517 */         if (eANAttribute != null) {
/*     */           
/* 519 */           EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)eANAttribute;
/* 520 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute1.get();
/* 521 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/* 522 */             if (arrayOfMetaFlag[b].isSelected()) {
/* 523 */               String str1 = arrayOfMetaFlag[b].getFlagCode().trim();
/* 524 */               logMessage("***************   sFlagClass: " + str1);
/*     */               
/* 526 */               if (str1.equals("")) {
/* 527 */                 setReturnCode(-1);
/* 528 */               } else if (str1.equals("2")) {
/* 529 */                 setFlagValue(METAXLATEGRP, 
/*     */                     
/* 531 */                     getEntityID(), METAXLSTATUS2);
/*     */               }
/* 533 */               else if (str1.equals("3")) {
/* 534 */                 setFlagValue(METAXLATEGRP, 
/*     */                     
/* 536 */                     getEntityID(), METAXLSTATUS3);
/*     */               }
/* 538 */               else if (str1.equals("4")) {
/* 539 */                 setFlagValue(METAXLATEGRP, 
/*     */                     
/* 541 */                     getEntityID(), METAXLSTATUS4);
/*     */               }
/* 543 */               else if (str1.equals("5")) {
/*     */                 
/* 545 */                 setFlagValue(METAXLATEGRP, 
/*     */                     
/* 547 */                     getEntityID(), METAXLSTATUS5);
/*     */               }
/* 549 */               else if (str1.equals("6")) {
/* 550 */                 setFlagValue(METAXLATEGRP, 
/*     */                     
/* 552 */                     getEntityID(), METAXLSTATUS6);
/*     */               }
/* 554 */               else if (str1.equals("7")) {
/* 555 */                 setFlagValue(METAXLATEGRP, 
/*     */                     
/* 557 */                     getEntityID(), METAXLSTATUS7);
/*     */               }
/* 559 */               else if (str1.equals("8")) {
/* 560 */                 setFlagValue(METAXLATEGRP, 
/*     */                     
/* 562 */                     getEntityID(), METAXLSTATUS8);
/*     */               }
/* 564 */               else if (str1.equals("9")) {
/* 565 */                 setFlagValue(METAXLATEGRP, 
/*     */                     
/* 567 */                     getEntityID(), METAXLSTATUS9);
/*     */               }
/* 569 */               else if (str1.equals("10")) {
/* 570 */                 setFlagValue(METAXLATEGRP, 
/*     */                     
/* 572 */                     getEntityID(), METAXLSTATUS10);
/*     */               }
/* 574 */               else if (str1.equals("11")) {
/* 575 */                 setFlagValue(METAXLATEGRP, 
/*     */                     
/* 577 */                     getEntityID(), METAXLSTATUS11);
/*     */               }
/* 579 */               else if (str1.equals("12")) {
/* 580 */                 setFlagValue(METAXLATEGRP, 
/*     */                     
/* 582 */                     getEntityID(), METAXLSTATUS12);
/*     */               }
/* 584 */               else if (str1.equals("13")) {
/* 585 */                 setFlagValue(METAXLATEGRP, 
/*     */                     
/* 587 */                     getEntityID(), METAXLSTATUS13);
/*     */               } 
/*     */               
/* 590 */               int i = Integer.parseInt(str1);
/*     */ 
/*     */ 
/*     */               
/* 594 */               PackageID packageID = new PackageID(getEntityType(), getEntityID(), i, "TRANSOUTBOUNDX" + i, this.m_strNow, str);
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 599 */               logMessage("***************   pkID: " + packageID
/* 600 */                   .toString());
/*     */               
/* 602 */               TranslationPackage translationPackage = Translation.generatePDHPackage(this.m_db, this.m_prof, packageID);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 612 */               Translation.putPDHPackage(this.m_db, this.m_prof, translationPackage);
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 617 */               TranslationDataRequest translationDataRequest = translationPackage.getDataRequest();
/*     */             } 
/*     */           } 
/*     */         } else {
/*     */           
/* 622 */           setReturnCode(-1);
/*     */         } 
/*     */         
/* 625 */         if (getReturnCode() == 0) {
/* 626 */           setFlagValue(METAXLATEGRP, 
/*     */               
/* 628 */               getEntityID(), METAXLATEGRPSTATUS);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 634 */       println("<br /><b>" + 
/*     */           
/* 636 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 639 */               getABRDescription(), 
/* 640 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 643 */       log(
/* 644 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 647 */               getABRDescription(), 
/* 648 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/* 650 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 651 */       setReturnCode(-2);
/* 652 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 656 */           .getMessage() + "</font></h3>");
/*     */       
/* 658 */       logError(lockPDHEntityException.getMessage());
/* 659 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 660 */       setReturnCode(-2);
/* 661 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 663 */           .getMessage() + "</font></h3>");
/*     */       
/* 665 */       logError(updatePDHEntityException.getMessage());
/* 666 */     } catch (Exception exception) {
/*     */       
/* 668 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 669 */       println("" + exception);
/*     */ 
/*     */       
/* 672 */       if (getABRReturnCode() != -2) {
/* 673 */         setReturnCode(-3);
/*     */       }
/* 675 */       exception.printStackTrace();
/*     */       
/* 677 */       StringWriter stringWriter = new StringWriter();
/* 678 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 679 */       String str = stringWriter.toString();
/* 680 */       println(str);
/*     */       
/* 682 */       logMessage("Error in " + this.m_abri
/* 683 */           .getABRCode() + ":" + exception.getMessage());
/* 684 */       logMessage("" + exception);
/*     */ 
/*     */       
/* 687 */       if (getABRReturnCode() != -2) {
/* 688 */         setReturnCode(-3);
/*     */       
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 694 */       setDGString(getABRReturnCode());
/* 695 */       setDGRptName("XLATEABR01");
/* 696 */       setDGRptClass("XLATEABR01");
/* 697 */       printDGSubmitString();
/*     */ 
/*     */       
/* 700 */       buildReportFooter();
/*     */       
/* 702 */       if (!isReadOnly()) {
/* 703 */         clearSoftLock();
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
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 717 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 727 */     return "<br /><br />";
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
/* 738 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 747 */     return new String("$Revision: 1.63 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setControlBlock() {
/* 754 */     this.m_strNow = getNow();
/* 755 */     this.m_strForever = getForever();
/* 756 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 763 */       .m_cbOn = new ControlBlock(this.m_strNow, this.m_strForever, this.m_strNow, this.m_strForever, this.m_prof.getOPWGID(), this.m_prof.getTranID());
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
/*     */   public void setFlagValue(String paramString1, int paramInt, String paramString2) {
/* 781 */     String str = null;
/*     */     
/* 783 */     if (paramString2.equals(XLATEGRPSTATUS)) {
/* 784 */       logMessage("****** XLATEGRPSTATUS set to: " + XLATEGRPSTATUS);
/* 785 */       str = "F0030";
/*     */     }
/* 787 */     else if (paramString2.equals(METAXLATEGRPSTATUS)) {
/*     */       
/* 789 */       logMessage("****** METAXLATEGRPSTATUS set to: " + METAXLATEGRPSTATUS);
/*     */       
/* 791 */       str = "F0030";
/*     */     }
/* 793 */     else if (paramString2.equals(XLSTATUS2)) {
/* 794 */       logMessage("****** XLSTATUS2 set to: " + XLSTATUS2);
/* 795 */       str = "XL20";
/*     */     }
/* 797 */     else if (paramString2.equals(XLSTATUS3)) {
/* 798 */       logMessage("****** XLSTATUS3 set to: " + XLSTATUS3);
/* 799 */       str = "XL20";
/*     */     }
/* 801 */     else if (paramString2.equals(XLSTATUS4)) {
/* 802 */       logMessage("****** XLSTATUS4 set to: " + XLSTATUS4);
/* 803 */       str = "XL20";
/*     */     }
/* 805 */     else if (paramString2.equals(XLSTATUS5)) {
/* 806 */       logMessage("****** XLSTATUS5 set to: " + XLSTATUS5);
/* 807 */       str = "XL20";
/*     */     }
/* 809 */     else if (paramString2.equals(XLSTATUS6)) {
/* 810 */       logMessage("****** XLSTATUS6 set to: " + XLSTATUS6);
/* 811 */       str = "XL20";
/*     */     }
/* 813 */     else if (paramString2.equals(XLSTATUS7)) {
/* 814 */       logMessage("****** XLSTATUS7 set to: " + XLSTATUS7);
/* 815 */       str = "XL20";
/*     */     }
/* 817 */     else if (paramString2.equals(XLSTATUS8)) {
/* 818 */       logMessage("****** XLSTATUS8 set to: " + XLSTATUS8);
/* 819 */       str = "XL20";
/*     */     }
/* 821 */     else if (paramString2.equals(XLSTATUS9)) {
/* 822 */       logMessage("****** XLSTATUS9 set to: " + XLSTATUS9);
/* 823 */       str = "XL20";
/*     */     }
/* 825 */     else if (paramString2.equals(XLSTATUS10)) {
/* 826 */       logMessage("****** sXLSTATUS10 set to: " + XLSTATUS10);
/* 827 */       str = "XL20";
/*     */     }
/* 829 */     else if (paramString2.equals(XLSTATUS11)) {
/* 830 */       logMessage("****** sXLSTATUS11 set to: " + XLSTATUS11);
/* 831 */       str = "XL20";
/*     */     }
/* 833 */     else if (paramString2.equals(XLSTATUS12)) {
/* 834 */       logMessage("****** sXLSTATUS12 set to: " + XLSTATUS12);
/* 835 */       str = "XL20";
/*     */     }
/* 837 */     else if (paramString2.equals(XLSTATUS13)) {
/* 838 */       logMessage("****** sXLSTATUS13 set to: " + XLSTATUS13);
/* 839 */       str = "XL20";
/*     */     }
/* 841 */     else if (paramString2.equals(METAXLSTATUS2)) {
/* 842 */       logMessage("****** sMETAXLSTATUS2 set to: " + METAXLSTATUS2);
/* 843 */       str = "XL20";
/*     */     }
/* 845 */     else if (paramString2.equals(METAXLSTATUS3)) {
/* 846 */       logMessage("****** sMETAXLSTATUS3 set to: " + METAXLSTATUS3);
/* 847 */       str = "XL20";
/*     */     }
/* 849 */     else if (paramString2.equals(METAXLSTATUS4)) {
/* 850 */       logMessage("****** sMETAXLSTATUS4 set to: " + METAXLSTATUS4);
/* 851 */       str = "XL20";
/*     */     }
/* 853 */     else if (paramString2.equals(METAXLSTATUS5)) {
/* 854 */       logMessage("****** sMETAXLSTATUS5 set to: " + METAXLSTATUS5);
/* 855 */       str = "XL20";
/*     */     }
/* 857 */     else if (paramString2.equals(METAXLSTATUS6)) {
/* 858 */       logMessage("****** sMETAXLSTATUS6 set to: " + METAXLSTATUS6);
/* 859 */       str = "XL20";
/*     */     }
/* 861 */     else if (paramString2.equals(METAXLSTATUS7)) {
/* 862 */       logMessage("****** sMETAXLSTATUS7 set to: " + METAXLSTATUS7);
/* 863 */       str = "XL20";
/*     */     }
/* 865 */     else if (paramString2.equals(METAXLSTATUS8)) {
/* 866 */       logMessage("****** sMETAXLSTATUS8 set to: " + METAXLSTATUS8);
/* 867 */       str = "XL20";
/*     */     }
/* 869 */     else if (paramString2.equals(METAXLSTATUS9)) {
/* 870 */       logMessage("****** sMETAXLSTATUS9 set to: " + METAXLSTATUS9);
/* 871 */       str = "XL20";
/*     */     }
/* 873 */     else if (paramString2.equals(METAXLSTATUS10)) {
/* 874 */       logMessage("****** sMETAXLSTATUS10 set to: " + METAXLSTATUS10);
/* 875 */       str = "XL20";
/*     */     }
/* 877 */     else if (paramString2.equals(METAXLSTATUS11)) {
/* 878 */       logMessage("****** sMETAXLSTATUS11 set to: " + METAXLSTATUS11);
/* 879 */       str = "XL20";
/*     */     }
/* 881 */     else if (paramString2.equals(METAXLSTATUS12)) {
/* 882 */       logMessage("****** sMETAXLSTATUS12 set to: " + METAXLSTATUS12);
/* 883 */       str = "XL20";
/*     */     }
/* 885 */     else if (paramString2.equals(METAXLSTATUS13)) {
/* 886 */       logMessage("****** sMETAXLSTATUS13 set to: " + METAXLSTATUS13);
/* 887 */       str = "XL20";
/*     */     }
/*     */     else {
/*     */       
/* 891 */       str = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 896 */     if (str != null) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 904 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */         
/* 908 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 916 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString2, str, 1, this.m_cbOn);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 921 */         Vector<SingleFlag> vector = new Vector();
/* 922 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*     */         
/* 924 */         if (singleFlag != null) {
/* 925 */           vector.addElement(singleFlag);
/* 926 */           returnEntityKey.m_vctAttributes = vector;
/* 927 */           vector1.addElement(returnEntityKey);
/* 928 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 929 */           this.m_db.commit();
/*     */         } 
/* 931 */       } catch (MiddlewareException middlewareException) {
/* 932 */         logMessage("setFlagValue: " + middlewareException.getMessage());
/* 933 */       } catch (Exception exception) {
/* 934 */         logMessage("setFlagValue: " + exception.getMessage());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printPassMessage() {
/* 943 */     println("<br /><b>send to Production Management</b><br /><br />");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 952 */     return "$Id: XLATEABR01.java,v 1.63 2006/06/27 02:46:54 yang Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 961 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\XLATEABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */