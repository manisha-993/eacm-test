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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OOFOOFQRABR01
/*     */   extends PokBaseABR
/*     */ {
/* 110 */   public static final String ABR = new String("OOFOOFQRABR01");
/* 111 */   private EntityGroup m_egParent = null;
/* 112 */   private EntityItem m_eiParent = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*     */     try {
/* 122 */       start_ABRBuild();
/*     */       
/* 124 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 125 */       this.m_eiParent = this.m_egParent.getEntityItem(0);
/*     */       
/* 127 */       if (this.m_egParent == null) {
/* 128 */         logMessage(ABR + ":" + 
/*     */ 
/*     */             
/* 131 */             getVersion() + ":ERROR:1: m_egParent cannot be established.");
/*     */         
/* 133 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/* 136 */       if (this.m_eiParent == null) {
/* 137 */         logMessage(ABR + ":" + 
/*     */ 
/*     */             
/* 140 */             getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/*     */         
/* 142 */         setReturnCode(-1);
/*     */         
/*     */         return;
/*     */       } 
/* 146 */       logMessage(ABR + ":" + 
/*     */ 
/*     */           
/* 149 */           getVersion() + ":Request to Work on Entity:" + this.m_eiParent
/*     */           
/* 151 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 153 */           .getEntityID());
/*     */       
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
/* 182 */       if (!checkM0005(this.m_eiParent, "OOFMEMBEROOFOMG")) {
/* 183 */         setReturnCode(-1);
/*     */       }
/*     */       
/* 186 */       if (getReturnCode() == 0) {
/*     */         
/* 188 */         setFlagValue(this.m_eiParent, 
/*     */             
/* 190 */             getStatusAttributeCode(this.m_eiParent), 
/* 191 */             getNextStatusCode(this.m_eiParent));
/*     */         
/* 193 */         if (isChangeRev(this.m_eiParent) || isChangeFinal(this.m_eiParent)) {
/* 194 */           setNow();
/* 195 */           println(processChangeReport(getT1(this.m_eiParent), getNow()));
/*     */         } 
/*     */       } 
/*     */       
/* 199 */       println("<br /><b>" + 
/*     */           
/* 201 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 204 */               getABRDescription(), 
/* 205 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 208 */       log(
/* 209 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 212 */               getABRDescription(), 
/* 213 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/* 215 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 216 */       setReturnCode(-2);
/* 217 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 221 */           .getMessage() + "</font></h3>");
/*     */       
/* 223 */       logError(lockPDHEntityException.getMessage());
/* 224 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 225 */       setReturnCode(-2);
/* 226 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 228 */           .getMessage() + "</font></h3>");
/*     */       
/* 230 */       logError(updatePDHEntityException.getMessage());
/* 231 */     } catch (Exception exception) {
/*     */       
/* 233 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 234 */       println("" + exception);
/*     */ 
/*     */       
/* 237 */       if (getABRReturnCode() != -2) {
/* 238 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/*     */       
/* 242 */       if (getReturnCode() == 0) {
/* 243 */         setReturnCode(0);
/*     */         
/* 245 */         setDGString(getABRReturnCode());
/* 246 */         setDGRptName("OOFOOFQRABR01");
/* 247 */         setDGRptClass("OOFOOFQRABR01");
/* 248 */         printDGSubmitString();
/*     */ 
/*     */         
/* 251 */         buildReportFooter();
/*     */         
/* 253 */         if (!isReadOnly()) {
/* 254 */           clearSoftLock();
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
/* 269 */     return null;
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
/* 280 */     return "<br />This needs to be defined <br />";
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
/* 291 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 300 */     return new String("1.24");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 310 */     return "OOFOOFQRABR01.java,v 1.24 2006/03/03 19:23:29 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 320 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\OOFOOFQRABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */