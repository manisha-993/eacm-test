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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class COFCOFQRABR01
/*     */   extends PokBaseABR
/*     */ {
/*  96 */   public static final String ABR = new String("COFCOFQRABR01");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public static final String COFCOFMGMTGRP = new String("COFCOFMGMTGRP");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String COFCOFSTATUS = "COFCOFSTATUS";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   private EntityGroup m_egParent = null;
/* 114 */   private EntityItem m_eiParent = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*     */     try {
/* 123 */       start_ABRBuild();
/*     */       
/* 125 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 126 */       this.m_eiParent = this.m_egParent.getEntityItem(0);
/*     */       
/* 128 */       if (this.m_egParent == null) {
/* 129 */         logMessage(ABR + ":" + 
/*     */ 
/*     */             
/* 132 */             getVersion() + ":ERROR:1: m_egParent cannot be established.");
/*     */         
/* 134 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/* 137 */       if (this.m_eiParent == null) {
/* 138 */         logMessage(ABR + ":" + 
/*     */ 
/*     */             
/* 141 */             getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/*     */         
/* 143 */         setReturnCode(-1);
/*     */         
/*     */         return;
/*     */       } 
/* 147 */       logMessage(ABR + ":" + 
/*     */ 
/*     */           
/* 150 */           getVersion() + ":Request to Work on Entity:" + this.m_eiParent
/*     */           
/* 152 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 154 */           .getEntityID());
/* 155 */       buildReportHeader();
/* 156 */       setControlBlock();
/* 157 */       setDGTitle(setDGName(this.m_eiParent, ABR));
/*     */       
/* 159 */       logMessage(ABR + ":" + 
/*     */ 
/*     */           
/* 162 */           getVersion() + ":Setup Complete:" + this.m_eiParent
/*     */           
/* 164 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 166 */           .getEntityID());
/*     */ 
/*     */       
/* 169 */       setReturnCode(0);
/*     */       
/* 171 */       displayHeader(this.m_egParent, this.m_eiParent);
/*     */       
/* 173 */       if (!checkS0002(this.m_eiParent)) {
/* 174 */         setReturnCode(-1);
/*     */       }
/* 176 */       if (!checkM0007(this.m_egParent, this.m_eiParent)) {
/* 177 */         setReturnCode(-1);
/*     */       }
/* 179 */       if (!checkM0008(this.m_egParent, this.m_eiParent)) {
/* 180 */         setReturnCode(-1);
/*     */       }
/* 182 */       if (!checkM0005(this.m_eiParent, "COFMEMBERCOFOMG")) {
/* 183 */         setReturnCode(-1);
/*     */       }
/* 185 */       if (!checkM0001(this.m_eiParent)) {
/* 186 */         setReturnCode(-1);
/*     */       }
/*     */       
/* 189 */       if (getReturnCode() == 0) {
/*     */         
/* 191 */         setFlagValue(this.m_eiParent, "COFCOFSTATUS", 
/*     */ 
/*     */             
/* 194 */             getNextStatusCode(this.m_eiParent));
/*     */         
/* 196 */         if (isChangeRev(this.m_eiParent) || isChangeFinal(this.m_eiParent)) {
/* 197 */           setNow();
/* 198 */           println(processChangeReport(getT1(this.m_eiParent), getNow()));
/*     */         } 
/*     */       } 
/*     */       
/* 202 */       println("<br /><b>" + 
/*     */           
/* 204 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 207 */               getABRDescription(), 
/* 208 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 211 */       log(
/* 212 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 215 */               getABRDescription(), 
/* 216 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/* 218 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 219 */       setReturnCode(-2);
/* 220 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 224 */           .getMessage() + "</font></h3>");
/*     */       
/* 226 */       logError(lockPDHEntityException.getMessage());
/* 227 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 228 */       setReturnCode(-2);
/* 229 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 231 */           .getMessage() + "</font></h3>");
/*     */       
/* 233 */       logError(updatePDHEntityException.getMessage());
/* 234 */     } catch (Exception exception) {
/*     */       
/* 236 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 237 */       println("" + exception);
/*     */ 
/*     */       
/* 240 */       if (getABRReturnCode() != -2) {
/* 241 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/*     */       
/* 245 */       if (getReturnCode() == 0) {
/* 246 */         setReturnCode(0);
/*     */         
/* 248 */         setDGString(getABRReturnCode());
/* 249 */         setDGRptName("COFCOFQRABR01");
/* 250 */         setDGRptClass("COFCOFQRABR01");
/* 251 */         printDGSubmitString();
/*     */ 
/*     */         
/* 254 */         buildReportFooter();
/*     */         
/* 256 */         if (!isReadOnly()) {
/* 257 */           clearSoftLock();
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
/* 272 */     return null;
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
/* 283 */     return "<br />This needs to be defined <br />";
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
/* 294 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 303 */     return new String("1.17");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 313 */     return "COFCOFQRABR01.java,v 1.17 2008/01/30 19:39:14 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 323 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\COFCOFQRABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */