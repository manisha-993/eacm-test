/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FUPPOFQRABR01
/*     */   extends PokBaseABR
/*     */ {
/*  67 */   public static final String ABR = new String("FUPPOFQRABR01");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public static final String FUPPOFMGMTGRP = new String("FUPPOFMGMTGRP");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public static final String FUPPOFSTATUS = new String("FUPPOFSTATUS");
/*     */   
/*  80 */   private EntityGroup m_egParent = null;
/*     */   
/*  82 */   private EntityItem m_eiParent = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*     */     try {
/*  92 */       start_ABRBuild();
/*     */       
/*  94 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  95 */       this.m_eiParent = this.m_egParent.getEntityItem(0);
/*     */       
/*  97 */       if (this.m_egParent == null) {
/*  98 */         logMessage(ABR + ":" + 
/*     */ 
/*     */             
/* 101 */             getVersion() + ":ERROR:1: m_egParent cannot be established.");
/*     */         
/* 103 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/* 106 */       if (this.m_eiParent == null) {
/* 107 */         logMessage(ABR + ":" + 
/*     */ 
/*     */             
/* 110 */             getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/*     */         
/* 112 */         setReturnCode(-1);
/*     */         
/*     */         return;
/*     */       } 
/* 116 */       logMessage(ABR + ":" + 
/*     */ 
/*     */           
/* 119 */           getVersion() + ":Request to Work on Entity:" + this.m_eiParent
/*     */           
/* 121 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 123 */           .getEntityID());
/* 124 */       buildReportHeader();
/* 125 */       setControlBlock();
/* 126 */       setDGTitle(setDGName(this.m_eiParent, ABR));
/*     */       
/* 128 */       logMessage(ABR + ":" + 
/*     */ 
/*     */           
/* 131 */           getVersion() + ":Setup Complete:" + this.m_eiParent
/*     */           
/* 133 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 135 */           .getEntityID());
/*     */ 
/*     */       
/* 138 */       setReturnCode(0);
/*     */       
/* 140 */       displayHeader(this.m_egParent, this.m_eiParent);
/*     */       
/* 142 */       if (!checkS0002(this.m_eiParent)) {
/* 143 */         setReturnCode(-1);
/*     */       }
/* 145 */       if (!checkM0007(this.m_egParent, this.m_eiParent)) {
/* 146 */         setReturnCode(-1);
/*     */       }
/* 148 */       if (!checkM0008(this.m_egParent, this.m_eiParent)) {
/* 149 */         setReturnCode(-1);
/*     */       }
/* 151 */       if (!checkM0005(this.m_eiParent, "POFMEMBERFUPOMG")) {
/* 152 */         setReturnCode(-1);
/*     */       }
/*     */       
/* 155 */       if (getReturnCode() == 0) {
/*     */         
/* 157 */         setFlagValue(this.m_eiParent, FUPPOFSTATUS, 
/*     */ 
/*     */             
/* 160 */             getNextStatusCode(this.m_eiParent));
/*     */         
/* 162 */         if (isChangeRev(this.m_eiParent) || isChangeFinal(this.m_eiParent)) {
/* 163 */           setNow();
/* 164 */           println(processChangeReport(getT1(this.m_eiParent), getNow()));
/*     */         } 
/*     */       } 
/*     */       
/* 168 */       println("<br /><b>" + 
/*     */           
/* 170 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 173 */               getABRDescription(), 
/* 174 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 177 */       log(
/* 178 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 181 */               getABRDescription(), 
/* 182 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/* 184 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 185 */       setReturnCode(-2);
/* 186 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 190 */           .getMessage() + "</font></h3>");
/*     */       
/* 192 */       logError(lockPDHEntityException.getMessage());
/* 193 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 194 */       setReturnCode(-2);
/* 195 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 197 */           .getMessage() + "</font></h3>");
/*     */       
/* 199 */       logError(updatePDHEntityException.getMessage());
/* 200 */     } catch (Exception exception) {
/*     */       
/* 202 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 203 */       println("" + exception);
/*     */ 
/*     */       
/* 206 */       if (getABRReturnCode() != -2) {
/* 207 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/*     */       
/* 211 */       if (getReturnCode() == 0) {
/* 212 */         setReturnCode(0);
/*     */         
/* 214 */         setDGString(getABRReturnCode());
/* 215 */         setDGRptName("FUPPOFQRABR01");
/* 216 */         setDGRptClass("FUPPOFQRABR01");
/* 217 */         printDGSubmitString();
/*     */ 
/*     */         
/* 220 */         buildReportFooter();
/*     */         
/* 222 */         if (!isReadOnly()) {
/* 223 */           clearSoftLock();
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
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 238 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 249 */     return "<br />This needs to be defined <br />";
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
/* 260 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 269 */     return new String("1.18");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 279 */     return "FUPPOFQRABR01.java,v 1.18 2008/01/30 19:39:15 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 289 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\FUPPOFQRABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */