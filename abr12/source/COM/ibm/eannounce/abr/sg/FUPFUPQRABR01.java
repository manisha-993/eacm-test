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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FUPFUPQRABR01
/*     */   extends PokBaseABR
/*     */ {
/*  88 */   public static final String ABR = new String("FUPFUPQRABR01");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public static final String FUPFUPMGMTGRP = new String("FUPFUPMGMTGRP");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public static final String FUPFUPSTATUS = new String("FUPFUPSTATUS");
/*     */   
/* 101 */   private EntityGroup m_egParent = null;
/* 102 */   private EntityItem m_eiParent = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*     */     try {
/* 111 */       start_ABRBuild();
/*     */       
/* 113 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 114 */       this.m_eiParent = this.m_egParent.getEntityItem(0);
/*     */       
/* 116 */       if (this.m_egParent == null) {
/* 117 */         logMessage(ABR + ":" + 
/*     */ 
/*     */             
/* 120 */             getVersion() + ":ERROR:1: m_egParent cannot be established.");
/*     */         
/* 122 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/* 125 */       if (this.m_eiParent == null) {
/* 126 */         logMessage(ABR + ":" + 
/*     */ 
/*     */             
/* 129 */             getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/*     */         
/* 131 */         setReturnCode(-1);
/*     */         
/*     */         return;
/*     */       } 
/* 135 */       logMessage(ABR + ":" + 
/*     */ 
/*     */           
/* 138 */           getVersion() + ":Request to Work on Entity:" + this.m_eiParent
/*     */           
/* 140 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 142 */           .getEntityID());
/* 143 */       buildReportHeader();
/* 144 */       setControlBlock();
/* 145 */       setDGTitle(setDGName(this.m_eiParent, ABR));
/*     */       
/* 147 */       logMessage(ABR + ":" + 
/*     */ 
/*     */           
/* 150 */           getVersion() + ":Setup Complete:" + this.m_eiParent
/*     */           
/* 152 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 154 */           .getEntityID());
/*     */ 
/*     */       
/* 157 */       setReturnCode(0);
/*     */       
/* 159 */       displayHeader(this.m_egParent, this.m_eiParent);
/*     */       
/* 161 */       if (!checkS0002(this.m_eiParent)) {
/* 162 */         setReturnCode(-1);
/*     */       }
/* 164 */       if (!checkM0007(this.m_egParent, this.m_eiParent)) {
/* 165 */         setReturnCode(-1);
/*     */       }
/* 167 */       if (!checkM0008(this.m_egParent, this.m_eiParent)) {
/* 168 */         setReturnCode(-1);
/*     */       }
/* 170 */       if (!checkM0005(this.m_eiParent, "POFMEMBERFUPOMG")) {
/* 171 */         setReturnCode(-1);
/*     */       }
/*     */       
/* 174 */       if (getReturnCode() == 0) {
/*     */         
/* 176 */         setFlagValue(this.m_eiParent, FUPFUPSTATUS, 
/*     */ 
/*     */             
/* 179 */             getNextStatusCode(this.m_eiParent));
/*     */         
/* 181 */         if (isChangeRev(this.m_eiParent) || isChangeFinal(this.m_eiParent)) {
/* 182 */           setNow();
/* 183 */           println(processChangeReport(getT1(this.m_eiParent), getNow()));
/*     */         } 
/*     */       } 
/*     */       
/* 187 */       println("<br /><b>" + 
/*     */           
/* 189 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 192 */               getABRDescription(), 
/* 193 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 196 */       log(
/* 197 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 200 */               getABRDescription(), 
/* 201 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/* 203 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 204 */       setReturnCode(-2);
/* 205 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 209 */           .getMessage() + "</font></h3>");
/*     */       
/* 211 */       logError(lockPDHEntityException.getMessage());
/* 212 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 213 */       setReturnCode(-2);
/* 214 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 216 */           .getMessage() + "</font></h3>");
/*     */       
/* 218 */       logError(updatePDHEntityException.getMessage());
/* 219 */     } catch (Exception exception) {
/*     */       
/* 221 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 222 */       println("" + exception);
/*     */ 
/*     */       
/* 225 */       if (getABRReturnCode() != -2) {
/* 226 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/*     */       
/* 230 */       if (getReturnCode() == 0) {
/* 231 */         setReturnCode(0);
/*     */         
/* 233 */         setDGString(getABRReturnCode());
/* 234 */         setDGRptName("FUPFUPQRABR01");
/* 235 */         setDGRptClass("FUPFUPQRABR01");
/* 236 */         printDGSubmitString();
/*     */ 
/*     */         
/* 239 */         buildReportFooter();
/*     */         
/* 241 */         if (!isReadOnly()) {
/* 242 */           clearSoftLock();
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
/* 257 */     return null;
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
/* 268 */     return "<br />This needs to be defined <br />";
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
/* 279 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 288 */     return new String("1.22");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 298 */     return "FUPFUPQRABR01.java,v 1.22 2008/01/30 19:39:17 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 308 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\FUPFUPQRABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */