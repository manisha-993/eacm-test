/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*     */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*     */ import COM.ibm.opicmpdh.objects.Text;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.SQLException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FCRENAME
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String ABR = "FCRENAME";
/*     */   
/*     */   public static String getVersion() {
/* 164 */     return "FCRENAME.java,v 1.42 2008/01/30 19:39:15 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 174 */     return getVersion();
/*     */   }
/*     */   
/* 177 */   private EntityGroup m_egParent = null;
/* 178 */   private EntityItem m_eiParent = null;
/*     */   
/* 180 */   private EntityGroup m_egChild = null;
/* 181 */   private EntityItem m_eiChild = null;
/* 182 */   private StringBuffer errMessage = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 190 */     String str1 = null;
/* 191 */     String str2 = null;
/* 192 */     String str3 = null;
/* 193 */     String str4 = null;
/* 194 */     String str5 = null;
/* 195 */     String str6 = null;
/* 196 */     String str7 = null;
/* 197 */     String str8 = null;
/* 198 */     String str9 = null;
/*     */ 
/*     */     
/*     */     try {
/* 202 */       start_ABRBuild();
/*     */ 
/*     */       
/* 205 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 206 */       this
/* 207 */         .m_eiParent = (this.m_egParent == null) ? null : this.m_egParent.getEntityItem(0);
/*     */       
/* 209 */       this.m_egChild = this.m_elist.getEntityGroup("FUP");
/*     */       
/* 211 */       if (this.m_egParent == null) {
/* 212 */         logMessage("FCRENAME:" + 
/*     */ 
/*     */             
/* 215 */             getVersion() + ":ERROR:1: m_egParent cannot be established.");
/*     */         
/* 217 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/* 220 */       if (this.m_eiParent == null) {
/* 221 */         logMessage("FCRENAME:" + 
/*     */ 
/*     */             
/* 224 */             getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/*     */         
/* 226 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/* 229 */       logMessage("FCRENAME:" + 
/*     */ 
/*     */           
/* 232 */           getVersion() + ":Request to Work on Entity:" + this.m_eiParent
/*     */           
/* 234 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 236 */           .getEntityID());
/*     */       
/* 238 */       setControlBlock();
/*     */       
/* 240 */       logMessage("FCRENAME:" + 
/*     */ 
/*     */           
/* 243 */           getVersion() + ":Setup Complete:" + this.m_eiParent
/*     */           
/* 245 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 247 */           .getEntityID());
/*     */       
/* 249 */       setDGTitle(setDGName(this.m_eiParent, "FCRENAME"));
/* 250 */       buildReportHeader();
/*     */ 
/*     */       
/* 253 */       setReturnCode(0);
/*     */       
/* 255 */       if (this.m_egChild.getEntityItemCount() == 1) {
/* 256 */         this.m_eiChild = this.m_egChild.getEntityItem(0);
/*     */ 
/*     */ 
/*     */         
/* 260 */         if (isFinal(this.m_eiParent) || isFinal(this.m_eiChild)) {
/* 261 */           setReturnCode(-1);
/* 262 */           if (isFinal(this.m_eiParent)) {
/* 263 */             this.errMessage.append("<br><br><b>ORDEROF COMNAME is in Status OFSTATUS cannot be updated in Final");
/*     */           }
/* 265 */           if (isFinal(this.m_eiChild)) {
/* 266 */             this.errMessage.append("<br><br><b>FUP COMNAME is in Status FUPSTATUS cannot be updated in Final");
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 273 */         str8 = getAttributeValue(this.m_eiParent, "OOFSUBCAT");
/* 274 */         if (!str8.equals("FeatureCode")) {
/* 275 */           setReturnCode(-1);
/* 276 */           this.errMessage.append("<br><br><b>ORDEROF SUBCATEGORY is not 'FeatureCode'");
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 281 */         setReturnCode(-1);
/* 282 */         this.errMessage.append("<br><br><b>ORDEROF COMNAME has " + (
/*     */             
/* 284 */             (this.m_egChild.getEntityItemCount() == 0) ? "(zero) " : "(more than 1) ") + this.m_egChild
/*     */ 
/*     */             
/* 287 */             .getLongDescription());
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 292 */       if (getReturnCode() == 0) {
/* 293 */         println("<br><br><FONT SIZE=4>" + 
/*     */             
/* 295 */             getABRDescription() + " has Passed, no report provided");
/*     */ 
/*     */ 
/*     */         
/* 299 */         if (isReadyForReview(this.m_eiParent)) {
/* 300 */           setFlagValue(this.m_eiParent, 
/*     */               
/* 302 */               getStatusAttributeCode(this.m_eiParent), "116");
/*     */         }
/*     */         
/* 305 */         if (isReadyForReview(this.m_eiChild)) {
/* 306 */           setFlagValue(this.m_eiChild, 
/*     */               
/* 308 */               getStatusAttributeCode(this.m_eiChild), "115");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 313 */         str3 = (getAttributeValue(this.m_eiParent, "MACHTYPE").length() > 0) ? (getAttributeValue(this.m_eiParent, "MACHTYPE") + "-") : "";
/*     */ 
/*     */ 
/*     */         
/* 317 */         str4 = (getAttributeValue(this.m_eiParent, "MODEL").length() > 0) ? (getAttributeValue(this.m_eiParent, "MODEL") + " ") : "";
/*     */ 
/*     */ 
/*     */         
/* 321 */         str5 = (getAttributeValue(this.m_eiParent, "FEATURECODE").length() > 0) ? (getAttributeValue(this.m_eiParent, "FEATURECODE") + " ") : "";
/*     */ 
/*     */ 
/*     */         
/* 325 */         str6 = (getAttributeValue(this.m_eiParent, "INVNAME").length() > 0) ? (getAttributeValue(this.m_eiParent, "INVNAME") + " ") : "";
/*     */ 
/*     */         
/* 328 */         str1 = str3 + str4 + str5 + str6;
/*     */         
/* 330 */         setAttrValue(this.m_eiParent, "COMNAME", str1);
/*     */         
/* 332 */         str7 = str5 + str6;
/* 333 */         if (requireOOFMTM(this.m_eiChild)) {
/* 334 */           str7 = str3 + str4 + str7;
/*     */         }
/* 336 */         setAttrValue(this.m_eiChild, "COMNAME", str7);
/*     */         
/* 338 */         str2 = str5;
/* 339 */         setAttrValue(this.m_eiChild, "FEATURECODE", str2);
/*     */       } 
/*     */ 
/*     */       
/* 343 */       println("<br><br>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 349 */       if (getReturnCode() == -1) {
/* 350 */         displayHeader(this.m_egParent, this.m_eiParent);
/* 351 */         if (this.m_eiChild != null) {
/* 352 */           println(
/* 353 */               displayStatuses(this.m_eiChild, this.m_eiChild.getEntityGroup()));
/* 354 */           println(
/* 355 */               displayNavAttributes(this.m_eiChild, this.m_eiChild
/*     */                 
/* 357 */                 .getEntityGroup()));
/*     */         } 
/* 359 */         println(this.errMessage.toString());
/*     */         
/* 361 */         println("<BR><br /><b>" + 
/*     */             
/* 363 */             buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */                 
/* 366 */                 getABRDescription(), 
/* 367 */                 (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */               }) + "</b>");
/*     */ 
/*     */         
/* 371 */         log(
/* 372 */             buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */                 
/* 375 */                 getABRDescription(), 
/* 376 */                 (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */               }));
/*     */       } 
/* 379 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 380 */       setReturnCode(-2);
/* 381 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 385 */           .getMessage() + "</font></h3>");
/*     */       
/* 387 */       logError(lockPDHEntityException.getMessage());
/* 388 */       lockPDHEntityException.printStackTrace();
/* 389 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 390 */       setReturnCode(-2);
/* 391 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 393 */           .getMessage() + "</font></h3>");
/*     */       
/* 395 */       logError(updatePDHEntityException.getMessage());
/* 396 */       updatePDHEntityException.printStackTrace();
/* 397 */     } catch (Exception exception) {
/* 398 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 399 */       println("" + exception);
/*     */ 
/*     */       
/* 402 */       if (getABRReturnCode() != -2) {
/* 403 */         setReturnCode(-3);
/*     */       }
/* 405 */       exception.printStackTrace();
/*     */       
/* 407 */       StringWriter stringWriter = new StringWriter();
/* 408 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 409 */       str9 = stringWriter.toString();
/* 410 */       println(str9);
/*     */       
/* 412 */       logMessage("Error in " + this.m_abri
/* 413 */           .getABRCode() + ":" + exception.getMessage());
/* 414 */       logMessage("" + exception);
/*     */     }
/*     */     finally {
/*     */       
/* 418 */       setDGString(getABRReturnCode());
/* 419 */       setDGRptName("FCRENAME");
/* 420 */       setDGRptClass("FCRENAME");
/* 421 */       printDGSubmitString();
/*     */ 
/*     */       
/* 424 */       buildReportFooter();
/*     */       
/* 426 */       if (!isReadOnly()) {
/* 427 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean requireOOFMTM(EntityItem paramEntityItem) {
/* 433 */     String str1 = null;
/* 434 */     String str2 = null;
/* 435 */     if (!paramEntityItem.getEntityType().equals("FUP")) {
/* 436 */       return false;
/*     */     }
/* 438 */     str1 = getFlagCode(paramEntityItem, "FUPCAT");
/* 439 */     if (!str1.equals("101")) {
/* 440 */       return false;
/*     */     }
/* 442 */     str2 = getFlagCode(paramEntityItem, "FUPSUBCAT");
/*     */     
/* 444 */     if (str2.equals("376") || str2
/* 445 */       .equals("380") || str2
/* 446 */       .equals("381") || str2
/* 447 */       .equals("384") || str2
/* 448 */       .equals("392") || str2
/* 449 */       .equals("393") || str2
/* 450 */       .equals("EntitleReg")) {
/* 451 */       return true;
/*     */     }
/*     */     
/* 454 */     return false;
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
/*     */   public void setAttrValue(EntityItem paramEntityItem, String paramString1, String paramString2) throws SQLException, MiddlewareException {
/*     */     try {
/* 476 */       ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/*     */ 
/*     */       
/* 479 */       Vector<Text> vector = new Vector();
/* 480 */       Vector<ReturnEntityKey> vector1 = new Vector();
/* 481 */       Text text = null;
/*     */       
/* 483 */       EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/* 484 */       EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString1);
/*     */       
/* 486 */       switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*     */         case 'T':
/* 488 */           if (paramString2.length() == 0) {
/* 489 */             EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 490 */             if (eANAttribute.toString() != null && eANAttribute
/* 491 */               .toString().length() > 0)
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 499 */               ControlBlock controlBlock = new ControlBlock(this.m_strNow, this.m_strNow, this.m_strNow, this.m_strNow, this.m_prof.getOPWGID(), this.m_prof.getTranID());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 506 */               text = new Text(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, eANAttribute.toString(), 1, controlBlock);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 511 */             if (paramString2.lastIndexOf('-') > 0)
/*     */             {
/* 513 */               paramString2 = paramString2.substring(0, paramString2
/*     */                   
/* 515 */                   .length() - 1);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 524 */             ControlBlock controlBlock = new ControlBlock(this.m_strNow, this.m_strForever, this.m_strNow, this.m_strForever, this.m_prof.getOPWGID(), this.m_prof.getTranID());
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 529 */             text = new Text(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, controlBlock);
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 536 */           vector.addElement(text);
/*     */           break;
/*     */       } 
/*     */       
/* 540 */       returnEntityKey.m_vctAttributes = vector;
/* 541 */       vector1.addElement(returnEntityKey);
/*     */       
/* 543 */       this.m_db.update(this.m_prof, vector1);
/* 544 */       this.m_db.commit();
/* 545 */       this.m_db.freeStatement();
/* 546 */       this.m_db.isPending();
/*     */     }
/* 548 */     catch (MiddlewareException middlewareException) {
/* 549 */       logMessage("setAttrValue: " + middlewareException.getMessage());
/* 550 */     } catch (Exception exception) {
/* 551 */       logMessage("setAttrValue: " + exception.getMessage());
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
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 565 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 575 */     return "This ABR is used to copy the FEATURECODE value from the selected OOF to 3 other attributes, COMNAME on both ORDEROF and FUP, and FEATURECODE on FUP";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStyle() {
/* 584 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 594 */     return new String("1.42");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\FCRENAME.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */