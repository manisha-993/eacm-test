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
/*     */ public class GROUPABR01
/*     */   extends PokBaseABR
/*     */ {
/*  46 */   public static final String ABR = new String("GROUPABR01");
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
/*     */   public void execute_run() {
/*     */     try {
/*  73 */       start_ABRBuild();
/*  74 */       this.m_sbMTDESC = new StringBuffer();
/*  75 */       buildReportHeaderII();
/*  76 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  77 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  78 */       println("<br><b>Order Offering: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  80 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*     */       
/*  82 */       setReturnCode(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  91 */       String str1 = getAttributeFlagEnabledValue(this.m_elist, this.m_ei.getEntityType(), this.m_ei.getEntityID(), "OFFERINGTYPE").trim();
/*  92 */       if (str1.equals(SYSTEM)) {
/*  93 */         println("<br /><font color=red>Failed. OF TYPE = SYSTEM</font>");
/*  94 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/*  98 */       log("GROUPABR01 checking GOA");
/*  99 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("GOA");
/*     */ 
/*     */       
/* 102 */       Vector<Integer> vector = getChildrenEntityIds(this.m_elist, this.m_ei
/*     */           
/* 104 */           .getEntityType(), this.m_ei
/* 105 */           .getEntityID(), "GOA", "OFGOA");
/*     */ 
/*     */       
/* 108 */       if (vector.size() <= 0) {
/* 109 */         println("<br /><font color=red>Failed. There is no GOA</font>");
/* 110 */         setReturnCode(-1);
/*     */       } 
/*     */       byte b;
/* 113 */       for (b = 0; b < vector.size(); b++) {
/* 114 */         int i = ((Integer)vector.elementAt(b)).intValue();
/* 115 */         EntityItem entityItem = entityGroup.getEntityItem("GOA" + i);
/* 116 */         this.m_elGOA.put((EANObject)entityItem);
/*     */         
/* 118 */         Vector vector1 = getParentEntityIds(entityItem
/* 119 */             .getEntityType(), entityItem
/* 120 */             .getEntityID(), "OF", "OFGOA");
/*     */ 
/*     */         
/* 123 */         if (vector1.size() > 1) {
/* 124 */           println("<br /><font color=red>Failed. GOA links to more than one OF.</font>");
/* 125 */           setReturnCode(-1);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 130 */       if (getReturnCode() == 0) {
/* 131 */         log("GROUPABR01 checking check attribute COMPUB of relators OFCPGOS");
/* 132 */         for (b = 0; b < this.m_ei.getDownLinkCount(); b++) {
/* 133 */           EntityItem entityItem = (EntityItem)this.m_ei.getDownLink(b);
/* 134 */           if (entityItem.getEntityType().equals("OFCPGOS")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 141 */             String str = getAttributeFlagEnabledValue(this.m_elist, entityItem.getEntityType(), entityItem.getEntityID(), "COMPUB").trim();
/* 142 */             if (str.equals(YES)) {
/*     */ 
/*     */               
/* 145 */               EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 146 */               EntityGroup entityGroup1 = this.m_elist.getEntityGroup("CPG");
/*     */               
/* 148 */               Vector<Integer> vector1 = getParentEntityIds(entityItem1
/* 149 */                   .getEntityType(), entityItem1
/* 150 */                   .getEntityID(), "CPG", "CPGCPGOS");
/*     */ 
/*     */ 
/*     */               
/* 154 */               for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*     */                 
/* 156 */                 int i = ((Integer)vector1.elementAt(b1)).intValue();
/*     */                 
/* 158 */                 EntityItem entityItem2 = entityGroup1.getEntityItem("CPG" + i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 165 */                 String str5 = getAttributeFlagEnabledValue(this.m_elist, entityItem2.getEntityType(), entityItem2.getEntityID(), "MTPUB").trim();
/* 166 */                 if (str5.equals(YES)) {
/*     */                   
/* 168 */                   String str6 = getAttributeValue(this.m_elist, entityItem2
/*     */                       
/* 170 */                       .getEntityType(), entityItem2
/* 171 */                       .getEntityID(), "MACHTYPEDESC");
/*     */                   
/* 173 */                   this.m_sbMTDESC.append(str6 + "<br>");
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 181 */       if (getReturnCode() == 0) {
/* 182 */         if (this.m_sbMTDESC.toString().length() > 0) {
/* 183 */           PDGUtility pDGUtility = new PDGUtility();
/* 184 */           for (byte b1 = 0; b1 < this.m_elGOA.size(); b1++) {
/* 185 */             EntityItem entityItem = (EntityItem)this.m_elGOA.getAt(b1);
/* 186 */             OPICMList oPICMList = new OPICMList();
/* 187 */             oPICMList.put("GOASYSUNITPREQ_LT", "GOASYSUNITPREQ_LT=" + this.m_sbMTDESC
/*     */                 
/* 189 */                 .toString());
/* 190 */             pDGUtility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*     */           } 
/*     */         } else {
/* 193 */           log("MACHTYPEDESC is blank.");
/*     */         } 
/*     */       }
/*     */       
/* 197 */       println("<br/><br /><b>" + 
/*     */           
/* 199 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 202 */               getABRDescription(), 
/* 203 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 206 */       log(
/* 207 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 210 */               getABRDescription(), 
/* 211 */               (getReturnCode() == 0) ? "Passed" : "Failed" }));
/* 212 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 213 */       setReturnCode(-2);
/* 214 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 218 */           .getMessage() + "</font></h3>");
/*     */       
/* 220 */       logError(lockPDHEntityException.getMessage());
/* 221 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 222 */       setReturnCode(-2);
/* 223 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 225 */           .getMessage() + "</font></h3>");
/*     */       
/* 227 */       logError(updatePDHEntityException.getMessage());
/* 228 */     } catch (SBRException sBRException) {
/* 229 */       setReturnCode(-2);
/* 230 */       println("<h3><font color=red>Generate Data error: " + sBRException
/*     */           
/* 232 */           .toString() + "</font></h3>");
/*     */       
/* 234 */       logError(sBRException.toString());
/* 235 */     } catch (Exception exception) {
/*     */       
/* 237 */       println("<br/>Error in " + this.m_abri
/*     */           
/* 239 */           .getABRCode() + ":" + exception
/*     */           
/* 241 */           .getMessage());
/* 242 */       println("" + exception);
/* 243 */       exception.printStackTrace();
/*     */       
/* 245 */       if (getABRReturnCode() != -2) {
/* 246 */         setReturnCode(-3);
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 251 */       String str1 = getABREntityDesc(this.m_ei.getEntityType(), this.m_ei.getEntityID());
/* 252 */       String str2 = getMetaAttributeDescription(this.m_ei, ABR);
/*     */       
/* 254 */       String str3 = str2 + " for " + str1;
/* 255 */       if (str3.length() > 64) {
/* 256 */         str3 = str3.substring(0, 64);
/*     */       }
/*     */       
/* 259 */       setDGTitle(str3);
/*     */ 
/*     */       
/* 262 */       setDGString(getABRReturnCode());
/* 263 */       setDGRptName(ABR);
/* 264 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 268 */       buildReportFooter();
/*     */       
/* 270 */       if (!isReadOnly()) {
/* 271 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 282 */     return "The purpose of this ABR is to set GOA's GOASYSUNITPREQ_LT";
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
/* 293 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 303 */     return new String("$Revision: 1.6 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 313 */     return "$Id: GROUPABR01.java,v 1.6 2006/03/03 19:24:11 bala Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 321 */     return "GROUPABR01.java,v 1.2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\GROUPABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */