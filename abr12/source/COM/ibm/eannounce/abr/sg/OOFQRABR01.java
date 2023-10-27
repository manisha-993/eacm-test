/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OOFQRABR01
/*     */   extends PokBaseABR
/*     */ {
/* 129 */   public static final String ABR = new String("OOFQRABR01");
/*     */   
/* 131 */   private EntityGroup m_egParent = null;
/* 132 */   private EntityItem m_eiParent = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 141 */     String str = null;
/*     */     
/*     */     try {
/* 144 */       start_ABRBuild();
/* 145 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 146 */       this.m_eiParent = this.m_egParent.getEntityItem(0);
/*     */       
/* 148 */       if (this.m_egParent == null) {
/* 149 */         logMessage(ABR + ":" + 
/*     */ 
/*     */             
/* 152 */             getVersion() + ":ERROR:1: m_egParent cannot be established.");
/*     */         
/* 154 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/* 157 */       if (this.m_eiParent == null) {
/* 158 */         logMessage(ABR + ":" + 
/*     */ 
/*     */             
/* 161 */             getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/*     */         
/* 163 */         setReturnCode(-1);
/*     */         
/*     */         return;
/*     */       } 
/* 167 */       logMessage(ABR + ":" + 
/*     */ 
/*     */           
/* 170 */           getVersion() + ":Request to Work on Entity:" + this.m_eiParent
/*     */           
/* 172 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 174 */           .getEntityID());
/*     */       
/* 176 */       buildReportHeader();
/* 177 */       setControlBlock();
/* 178 */       setDGTitle(setDGName(this.m_eiParent, ABR));
/*     */       
/* 180 */       logMessage(ABR + ":" + 
/*     */ 
/*     */           
/* 183 */           getVersion() + ":Setup Complete:" + this.m_eiParent
/*     */           
/* 185 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 187 */           .getEntityID());
/*     */ 
/*     */       
/* 190 */       setReturnCode(0);
/*     */       
/* 192 */       displayHeader(this.m_egParent, this.m_eiParent);
/*     */       
/* 194 */       if (!checkA0001(this.m_eiParent)) {
/* 195 */         setReturnCode(-1);
/*     */       }
/* 197 */       if (!checkA0002(this.m_eiParent)) {
/* 198 */         setReturnCode(-1);
/*     */       }
/* 200 */       if (!checkS0001(this.m_eiParent)) {
/* 201 */         setReturnCode(-1);
/*     */       }
/*     */       
/* 204 */       if (!checkS0002(this.m_eiParent)) {
/* 205 */         setReturnCode(-1);
/*     */       }
/* 207 */       if (!checkM0002(this.m_eiParent)) {
/* 208 */         setReturnCode(-1);
/*     */       }
/* 210 */       if (!checkM0003(this.m_eiParent)) {
/* 211 */         setReturnCode(-1);
/*     */       }
/* 213 */       if (!checkM0007(this.m_egParent, this.m_eiParent)) {
/* 214 */         setReturnCode(-1);
/*     */       }
/* 216 */       if (!checkM0008(this.m_egParent, this.m_eiParent)) {
/* 217 */         setReturnCode(-1);
/*     */       }
/* 219 */       if (!checkH0003(this.m_eiParent)) {
/* 220 */         setReturnCode(-1);
/*     */       }
/* 222 */       if (!checkH0006(this.m_eiParent)) {
/* 223 */         setReturnCode(-1);
/*     */       }
/* 225 */       if (!checkV0001(this.m_eiParent)) {
/* 226 */         setReturnCode(-1);
/*     */       }
/*     */       
/* 229 */       if (!checkFCFormat(this.m_eiParent)) {
/* 230 */         setReturnCode(-1);
/*     */       }
/*     */       
/* 233 */       if ((getFlagCode(this.m_eiParent, "OOFCAT").equals("100") && 
/* 234 */         getFlagCode(this.m_eiParent, "OOFSUBCAT").equals("500") && 
/* 235 */         getFlagCode(this.m_eiParent, "OOFGRP").equals("405") && 
/* 236 */         getFlagCode(this.m_eiParent, "OOFSUBGRP").equals("405")) || (
/* 237 */         getFlagCode(this.m_eiParent, "OOFCAT").equals("100") && 
/* 238 */         getFlagCode(this.m_eiParent, "OOFSUBCAT").equals("506") && 
/* 239 */         getFlagCode(this.m_eiParent, "OOFGRP").equals("405") && 
/* 240 */         getFlagCode(this.m_eiParent, "OOFSUBGRP").equals("405")) || (
/* 241 */         getFlagCode(this.m_eiParent, "OOFCAT").equals("101") && 
/* 242 */         getFlagCode(this.m_eiParent, "OOFSUBCAT").equals("500") && 
/* 243 */         getFlagCode(this.m_eiParent, "OOFGRP").equals("405") && 
/* 244 */         getFlagCode(this.m_eiParent, "OOFSUBGRP").equals("405")) || (
/* 245 */         getFlagCode(this.m_eiParent, "OOFCAT").equals("100") && 
/* 246 */         getFlagCode(this.m_eiParent, "OOFSUBCAT").equals("501") && 
/* 247 */         getFlagCode(this.m_eiParent, "OOFGRP").equals("600") && 
/* 248 */         getFlagCode(this.m_eiParent, "OOFSUBGRP").equals("405")) || (
/* 249 */         getFlagCode(this.m_eiParent, "OOFCAT").equals("100") && 
/* 250 */         getFlagCode(this.m_eiParent, "OOFSUBCAT").equals("501") && 
/* 251 */         getFlagCode(this.m_eiParent, "OOFGRP").equals("601") && 
/* 252 */         getFlagCode(this.m_eiParent, "OOFSUBGRP").equals("405")) || (
/* 253 */         getFlagCode(this.m_eiParent, "OOFCAT").equals("100") && 
/* 254 */         getFlagCode(this.m_eiParent, "OOFSUBCAT").equals("501") && 
/* 255 */         getFlagCode(this.m_eiParent, "OOFGRP").equals("602") && 
/* 256 */         getFlagCode(this.m_eiParent, "OOFSUBGRP").equals("405")) || (
/* 257 */         getFlagCode(this.m_eiParent, "OOFCAT").equals("100") && 
/* 258 */         getFlagCode(this.m_eiParent, "OOFSUBCAT").equals("501") && 
/* 259 */         getFlagCode(this.m_eiParent, "OOFGRP").equals("603") && 
/* 260 */         getFlagCode(this.m_eiParent, "OOFSUBGRP").equals("405")) || (
/* 261 */         getFlagCode(this.m_eiParent, "OOFCAT").equals("100") && 
/* 262 */         getFlagCode(this.m_eiParent, "OOFSUBCAT").equals("501") && 
/* 263 */         getFlagCode(this.m_eiParent, "OOFGRP").equals("604") && 
/* 264 */         getFlagCode(this.m_eiParent, "OOFSUBGRP").equals("405")) || (
/* 265 */         getFlagCode(this.m_eiParent, "OOFCAT").equals("100") && 
/* 266 */         getFlagCode(this.m_eiParent, "OOFSUBCAT").equals("501") && 
/* 267 */         getFlagCode(this.m_eiParent, "OOFGRP").equals("405") && 
/* 268 */         getFlagCode(this.m_eiParent, "OOFSUBGRP").equals("405"))) {
/*     */         
/* 270 */         if (!checkV0002(this.m_eiParent)) {
/* 271 */           setReturnCode(-1);
/*     */         }
/*     */       } else {
/* 274 */         println("<br><br><b><I>Not Checking V0002:</I> FEATURECODE is not a valid attribute. </b>");
/*     */       } 
/* 276 */       if (getReturnCode() == 0) {
/*     */         
/* 278 */         setFlagValue(this.m_eiParent, 
/*     */             
/* 280 */             getStatusAttributeCode(this.m_eiParent), 
/* 281 */             getNextStatusCode(this.m_eiParent));
/* 282 */         if (isChangeRev(this.m_eiParent) || isChangeFinal(this.m_eiParent)) {
/* 283 */           setNow();
/* 284 */           println(processChangeReport(getT1(this.m_eiParent), getNow()));
/*     */         } 
/*     */       } 
/* 287 */       println("<br /><b>" + 
/*     */           
/* 289 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 292 */               getABRDescription(), 
/* 293 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 296 */       log(
/* 297 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 300 */               getABRDescription(), 
/* 301 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/* 303 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 304 */       setReturnCode(-2);
/* 305 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 309 */           .getMessage() + "</font></h3>");
/*     */       
/* 311 */       logError(lockPDHEntityException.getMessage());
/* 312 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 313 */       setReturnCode(-2);
/* 314 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 316 */           .getMessage() + "</font></h3>");
/*     */       
/* 318 */       logError(updatePDHEntityException.getMessage());
/* 319 */     } catch (Exception exception) {
/*     */       
/* 321 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 322 */       println("" + exception);
/* 323 */       exception.printStackTrace();
/*     */       
/* 325 */       StringWriter stringWriter = new StringWriter();
/* 326 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 327 */       str = stringWriter.toString();
/* 328 */       println(str);
/*     */       
/* 330 */       logMessage("Error in " + this.m_abri
/* 331 */           .getABRCode() + ":" + exception.getMessage());
/* 332 */       logMessage("" + exception);
/*     */ 
/*     */       
/* 335 */       if (getABRReturnCode() != -2) {
/* 336 */         setReturnCode(-3);
/*     */       
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 342 */       setDGString(getABRReturnCode());
/* 343 */       setDGRptName("OOFQRABR01");
/* 344 */       setDGRptClass("OOFQRABR01");
/* 345 */       printDGSubmitString();
/*     */ 
/*     */       
/* 348 */       buildReportFooter();
/*     */       
/* 350 */       if (!isReadOnly()) {
/* 351 */         clearSoftLock();
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
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 366 */     return null;
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
/* 377 */     return "<br /><br />";
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
/* 388 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 398 */     return "OOFQRABR01.java,v 1.25 2006/03/03 19:23:29 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 408 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\OOFQRABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */