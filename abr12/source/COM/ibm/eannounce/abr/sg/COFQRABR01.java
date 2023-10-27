/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class COFQRABR01
/*     */   extends PokBaseABR
/*     */ {
/* 118 */   public static final Hashtable c_hshEntities = new Hashtable<>();
/*     */   static {
/* 120 */     c_hshEntities.put("BPEXHIBIT", "HI");
/* 121 */     c_hshEntities.put("CATINCL", "HI");
/* 122 */     c_hshEntities.put("CHANNEL", "HI");
/* 123 */     c_hshEntities.put("COMMERCIALOF", "HI");
/* 124 */     c_hshEntities.put("CRYPTO", "HI");
/* 125 */     c_hshEntities.put("ENVIRINFO", "HI");
/* 126 */     c_hshEntities.put("IVOCAT", "HI");
/* 127 */     c_hshEntities.put("ORDERINFO", "HI");
/* 128 */     c_hshEntities.put("ORGANUNIT", "HI");
/* 129 */     c_hshEntities.put("PACKAGING", "HI");
/* 130 */     c_hshEntities.put("PRICEFININFO", "HI");
/* 131 */     c_hshEntities.put("PUBLICATION", "HI");
/* 132 */     c_hshEntities.put("PUBTABLE", "HI");
/* 133 */     c_hshEntities.put("RULES", "HI");
/* 134 */     c_hshEntities.put("SHIPINFO", "HI");
/* 135 */     c_hshEntities.put("TECHCAPABILITY", "HI");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public static final String ABR = new String("COFQRABR01");
/*     */   
/* 144 */   private EntityGroup m_egParent = null;
/* 145 */   private EntityItem m_eiParent = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 153 */     EntityGroup entityGroup1 = null;
/* 154 */     EntityGroup entityGroup2 = null;
/*     */     
/* 156 */     String str = null;
/*     */ 
/*     */     
/*     */     try {
/* 160 */       start_ABRBuild();
/* 161 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 162 */       this
/* 163 */         .m_eiParent = (this.m_egParent == null) ? null : this.m_egParent.getEntityItem(0);
/*     */       
/* 165 */       if (this.m_egParent == null) {
/* 166 */         logMessage(ABR + ":" + 
/*     */ 
/*     */             
/* 169 */             getVersion() + ":ERROR:1: m_egParent cannot be established.");
/*     */         
/* 171 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/* 174 */       if (this.m_eiParent == null) {
/* 175 */         logMessage(ABR + ":" + 
/*     */ 
/*     */             
/* 178 */             getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/*     */         
/* 180 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/* 183 */       logMessage(ABR + ":" + 
/*     */ 
/*     */           
/* 186 */           getVersion() + ":Request to Work on Entity:" + this.m_eiParent
/*     */           
/* 188 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 190 */           .getEntityID());
/* 191 */       buildReportHeader();
/* 192 */       setControlBlock();
/* 193 */       setDGTitle(setDGName(this.m_eiParent, ABR));
/*     */       
/* 195 */       logMessage(ABR + ":" + 
/*     */ 
/*     */           
/* 198 */           getVersion() + ":Setup Complete:" + this.m_eiParent
/*     */           
/* 200 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 202 */           .getEntityID());
/*     */ 
/*     */       
/* 205 */       displayHeader(this.m_egParent, this.m_eiParent);
/* 206 */       setReturnCode(0);
/*     */       
/* 208 */       if (!checkM0007(this.m_egParent, this.m_eiParent)) {
/* 209 */         setReturnCode(-1);
/*     */       }
/* 211 */       if (!checkM0008(this.m_egParent, this.m_eiParent)) {
/* 212 */         setReturnCode(-1);
/*     */       }
/* 214 */       if (!checkM0001(this.m_eiParent)) {
/* 215 */         setReturnCode(-1);
/*     */       }
/* 217 */       if (!checkM0004GrandChild(this.m_eiParent)) {
/* 218 */         setReturnCode(-1);
/*     */       }
/* 220 */       if (!checkS0001(this.m_eiParent)) {
/* 221 */         setReturnCode(-1);
/*     */       }
/* 223 */       if (!checkS0002(this.m_eiParent, c_hshEntities)) {
/* 224 */         setReturnCode(-1);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 230 */       if (getReturnCode() == 0) {
/* 231 */         setFlagValue(this.m_eiParent, 
/*     */             
/* 233 */             getStatusAttributeCode(this.m_eiParent), 
/* 234 */             getNextStatusCode(this.m_eiParent));
/* 235 */         entityGroup1 = this.m_elist.getEntityGroup("COFCOFMGMTGRP"); byte b;
/* 236 */         for (b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/* 237 */           EntityItem entityItem = entityGroup1.getEntityItem(b);
/* 238 */           setFlagValue(entityItem, 
/*     */               
/* 240 */               getStatusAttributeCode(entityItem), 
/* 241 */               getNextStatusCode(entityItem));
/*     */         } 
/*     */ 
/*     */         
/* 245 */         entityGroup2 = this.m_elist.getEntityGroup("COFOOFMGMTGRP");
/* 246 */         for (b = 0; b < entityGroup2.getEntityItemCount(); b++) {
/* 247 */           EntityItem entityItem = entityGroup2.getEntityItem(b);
/* 248 */           setFlagValue(entityItem, 
/*     */               
/* 250 */               getStatusAttributeCode(entityItem), 
/* 251 */               getNextStatusCode(entityItem));
/*     */         } 
/*     */ 
/*     */         
/* 255 */         if (isChangeRev(this.m_eiParent) || isChangeFinal(this.m_eiParent)) {
/* 256 */           setNow();
/* 257 */           println(processChangeReport(getT1(this.m_eiParent), getNow()));
/*     */         } 
/*     */       } 
/*     */       
/* 261 */       println("<br /><b>" + 
/*     */           
/* 263 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 266 */               getABRDescription(), 
/* 267 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/* 269 */       log(
/* 270 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 273 */               getABRDescription(), 
/* 274 */               (getReturnCode() == 0) ? "Passed" : "Failed" }));
/* 275 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 276 */       setReturnCode(-2);
/* 277 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 281 */           .getMessage() + "</font></h3>");
/*     */       
/* 283 */       logError(lockPDHEntityException.getMessage());
/* 284 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 285 */       setReturnCode(-2);
/* 286 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 288 */           .getMessage() + "</font></h3>");
/*     */       
/* 290 */       logError(updatePDHEntityException.getMessage());
/* 291 */     } catch (Exception exception) {
/*     */       
/* 293 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 294 */       println("" + exception);
/* 295 */       exception.printStackTrace();
/*     */       
/* 297 */       StringWriter stringWriter = new StringWriter();
/* 298 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 299 */       str = stringWriter.toString();
/* 300 */       println(str);
/* 301 */       logMessage("Error in " + this.m_abri
/* 302 */           .getABRCode() + ":" + exception.getMessage());
/* 303 */       logMessage("" + exception);
/*     */       
/* 305 */       if (getABRReturnCode() != -2) {
/* 306 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/*     */       
/* 310 */       setDGString(getABRReturnCode());
/* 311 */       setDGRptName("COFQRABR01");
/* 312 */       setDGRptClass("COFQRABR01");
/* 313 */       printDGSubmitString();
/*     */       
/* 315 */       buildReportFooter();
/* 316 */       if (!isReadOnly()) {
/* 317 */         clearSoftLock();
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
/* 331 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 341 */     return "<br /><br />";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStyle() {
/* 351 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 361 */     return new String("1.19");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 371 */     return "COFQRABR01.java,v 1.19 2006/03/03 19:23:27 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 381 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\COFQRABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */