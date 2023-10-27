/*     */ package COM.ibm.eannounce.abr.pcd;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANObject;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.PDGUtility;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.opicmpdh.transactions.OPICMList;
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
/*     */ public class GROUPABR02
/*     */   extends PokBaseABR
/*     */ {
/*  46 */   public static final String ABR = new String("GROUPABR02");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   public static final String YES = new String("0010");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static final String SYSTEM = new String("0080");
/*     */   
/*  58 */   private EntityGroup m_egParent = null;
/*  59 */   private EntityItem m_ei = null;
/*  60 */   private EANList m_elGOA = new EANList();
/*  61 */   private StringBuffer m_sbMTDESC = null;
/*     */ 
/*     */ 
/*     */ 
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
/*  76 */       start_ABRBuild();
/*  77 */       this.m_sbMTDESC = new StringBuffer();
/*  78 */       buildReportHeaderII();
/*  79 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  80 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  81 */       println("<br><b>Compatibility Group: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  83 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*     */       
/*  85 */       setReturnCode(0);
/*     */       
/*  87 */       EANList eANList = getOFList(this.m_ei);
/*     */       
/*  89 */       for (byte b = 0; b < eANList.size(); b++) {
/*  90 */         this.m_sbMTDESC = new StringBuffer();
/*  91 */         EntityItem entityItem = (EntityItem)eANList.getAt(b);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 100 */         String str = getAttributeFlagEnabledValue(this.m_elist, entityItem.getEntityType(), entityItem.getEntityID(), "OFFERINGTYPE").trim();
/* 101 */         if (str.equals(SYSTEM)) {
/* 102 */           println("<br /><font color=red>Failed. OF TYPE = SYSTEM</font>");
/* 103 */           setReturnCode(-1);
/*     */         } 
/*     */ 
/*     */         
/* 107 */         log("GROUPABR02 checking GOA");
/* 108 */         EntityGroup entityGroup = this.m_elist.getEntityGroup("GOA");
/*     */ 
/*     */         
/* 111 */         Vector<Integer> vector = getChildrenEntityIds(this.m_elist, entityItem
/*     */             
/* 113 */             .getEntityType(), entityItem
/* 114 */             .getEntityID(), "GOA", "OFGOA");
/*     */ 
/*     */         
/* 117 */         if (vector.size() > 0) {
/*     */           byte b1;
/*     */ 
/*     */           
/* 121 */           for (b1 = 0; b1 < vector.size(); b1++) {
/* 122 */             int i = ((Integer)vector.elementAt(b1)).intValue();
/* 123 */             EntityItem entityItem1 = entityGroup.getEntityItem("GOA" + i);
/* 124 */             this.m_elGOA.put((EANObject)entityItem1);
/*     */             
/* 126 */             Vector vector1 = getParentEntityIds(entityItem1
/* 127 */                 .getEntityType(), entityItem1
/* 128 */                 .getEntityID(), "OF", "OFGOA");
/*     */ 
/*     */             
/* 131 */             if (vector1.size() > 1) {
/* 132 */               println("<br /><font color=red>Failed. GOA links to more than one OF.</font>");
/* 133 */               setReturnCode(-1);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 138 */           if (getReturnCode() == 0) {
/* 139 */             log("GROUPABR02 checking check attribute COMPUB of relators OFCPGOS");
/*     */             
/* 141 */             for (b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/* 142 */               EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(b1);
/* 143 */               if (entityItem1 != null && entityItem1
/* 144 */                 .getEntityType().equals("OFCPGOS")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 151 */                 String str4 = getAttributeFlagEnabledValue(this.m_elist, entityItem1.getEntityType(), entityItem1.getEntityID(), "COMPUB").trim();
/* 152 */                 if (str4.equals(YES)) {
/*     */ 
/*     */                   
/* 155 */                   EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/*     */                   
/* 157 */                   EntityGroup entityGroup1 = this.m_elist.getParentEntityGroup();
/*     */                   
/* 159 */                   Vector<Integer> vector1 = getParentEntityIds(entityItem2
/* 160 */                       .getEntityType(), entityItem2
/* 161 */                       .getEntityID(), "CPG", "CPGCPGOS");
/*     */ 
/*     */ 
/*     */                   
/* 165 */                   for (byte b2 = 0; b2 < vector1.size(); b2++) {
/*     */ 
/*     */                     
/* 168 */                     int i = ((Integer)vector1.elementAt(b2)).intValue();
/*     */                     
/* 170 */                     EntityItem entityItem3 = entityGroup1.getEntityItem("CPG" + i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 177 */                     String str5 = getAttributeFlagEnabledValue(this.m_elist, entityItem3.getEntityType(), entityItem3.getEntityID(), "MTPUB").trim();
/* 178 */                     if (str5.equals(YES)) {
/*     */                       
/* 180 */                       String str6 = getAttributeValue(this.m_elist, entityItem3
/*     */                           
/* 182 */                           .getEntityType(), entityItem3
/* 183 */                           .getEntityID(), "MACHTYPEDESC");
/*     */                       
/* 185 */                       this.m_sbMTDESC.append(str6 + "<br>");
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } else {
/* 190 */                 log("GROUPABR02 not an OFCPGOS entity");
/*     */               } 
/*     */             } 
/*     */             
/* 194 */             if (this.m_sbMTDESC.toString().length() > 0) {
/* 195 */               PDGUtility pDGUtility = new PDGUtility();
/* 196 */               for (byte b2 = 0; b2 < this.m_elGOA.size(); b2++) {
/* 197 */                 EntityItem entityItem1 = (EntityItem)this.m_elGOA.getAt(b2);
/* 198 */                 OPICMList oPICMList = new OPICMList();
/* 199 */                 oPICMList.put("GOASYSUNITPREQ_LT", "GOASYSUNITPREQ_LT=" + this.m_sbMTDESC
/*     */                     
/* 201 */                     .toString());
/* 202 */                 pDGUtility.updateAttribute(this.m_db, this.m_prof, entityItem1, oPICMList);
/*     */               } 
/*     */             } else {
/* 205 */               log("MACHTYPEDESC is blank.");
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 210 */       println("<br/><br /><b>" + 
/*     */           
/* 212 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 215 */               getABRDescription(), 
/* 216 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 219 */       log(
/* 220 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 223 */               getABRDescription(), 
/* 224 */               (getReturnCode() == 0) ? "Passed" : "Failed" }));
/* 225 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 226 */       setReturnCode(-2);
/* 227 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 231 */           .getMessage() + "</font></h3>");
/*     */       
/* 233 */       logError(lockPDHEntityException.getMessage());
/* 234 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 235 */       setReturnCode(-2);
/* 236 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 238 */           .getMessage() + "</font></h3>");
/*     */       
/* 240 */       logError(updatePDHEntityException.getMessage());
/* 241 */     } catch (SBRException sBRException) {
/* 242 */       setReturnCode(-2);
/* 243 */       println("<h3><font color=red>Generate Data error: " + sBRException
/*     */           
/* 245 */           .toString() + "</font></h3>");
/*     */       
/* 247 */       logError(sBRException.toString());
/* 248 */     } catch (Exception exception) {
/*     */       
/* 250 */       println("<br/>Error in " + this.m_abri
/*     */           
/* 252 */           .getABRCode() + ":" + exception
/*     */           
/* 254 */           .getMessage());
/* 255 */       println("" + exception);
/* 256 */       exception.printStackTrace();
/*     */       
/* 258 */       if (getABRReturnCode() != -2) {
/* 259 */         setReturnCode(-3);
/*     */       
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 265 */       String str1 = getABREntityDesc(this.m_ei.getEntityType(), this.m_ei.getEntityID());
/* 266 */       String str2 = getMetaAttributeDescription(this.m_ei, ABR);
/*     */       
/* 268 */       String str3 = str2 + " for " + str1;
/* 269 */       if (str3.length() > 64) {
/* 270 */         str3 = str3.substring(0, 64);
/*     */       }
/*     */       
/* 273 */       setDGTitle(str3);
/*     */ 
/*     */       
/* 276 */       setDGString(getABRReturnCode());
/* 277 */       setDGRptName(ABR);
/* 278 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 282 */       buildReportFooter();
/*     */       
/* 284 */       if (!isReadOnly()) {
/* 285 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private EANList getOFList(EntityItem paramEntityItem) {
/* 291 */     EANList eANList = new EANList();
/*     */ 
/*     */ 
/*     */     
/* 295 */     if (!paramEntityItem.getEntityType().equals("CPG")) {
/* 296 */       return eANList;
/*     */     }
/*     */     
/* 299 */     EntityGroup entityGroup1 = this.m_elist.getEntityGroup("CPGOS");
/* 300 */     EntityGroup entityGroup2 = this.m_elist.getEntityGroup("OF");
/*     */     
/* 302 */     Vector<Integer> vector = getChildrenEntityIds(paramEntityItem
/* 303 */         .getEntityType(), paramEntityItem
/* 304 */         .getEntityID(), "CPGOS", "CPGCPGOS");
/*     */ 
/*     */     
/* 307 */     for (byte b = 0; b < vector.size(); b++) {
/* 308 */       int i = ((Integer)vector.elementAt(b)).intValue();
/* 309 */       EntityItem entityItem = entityGroup1.getEntityItem("CPGOS" + i);
/*     */ 
/*     */       
/* 312 */       Vector<Integer> vector1 = getParentEntityIds(entityItem
/* 313 */           .getEntityType(), entityItem
/* 314 */           .getEntityID(), "OF", "OFCPGOS");
/*     */ 
/*     */       
/* 317 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 318 */         int j = ((Integer)vector1.elementAt(b1)).intValue();
/* 319 */         EntityItem entityItem1 = entityGroup2.getEntityItem("OF" + j);
/* 320 */         eANList.put((EANObject)entityItem1);
/*     */       } 
/*     */     } 
/* 323 */     return eANList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 332 */     return "The purpose of this ABR is to set GOA's GOASYSUNITPREQ_LT";
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
/* 343 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 353 */     return new String("$Revision: 1.6 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 363 */     return "$Id: GROUPABR02.java,v 1.6 2006/03/03 19:24:11 bala Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 371 */     return "GROUPABR02.java,v 1.2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\GROUPABR02.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */