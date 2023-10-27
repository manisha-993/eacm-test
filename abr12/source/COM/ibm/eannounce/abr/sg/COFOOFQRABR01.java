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
/*     */ public class COFOOFQRABR01
/*     */   extends PokBaseABR
/*     */ {
/* 104 */   public static final Hashtable c_hshEntities = new Hashtable<>();
/*     */   
/*     */   static {
/* 107 */     c_hshEntities.put("AVAIL", "HI");
/* 108 */     c_hshEntities.put("ORDEROF", "HI");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public static final String ABR = new String("COFOOFQRABR01");
/*     */   
/* 119 */   private EntityGroup m_egParent = null;
/* 120 */   private EntityItem m_eiParent = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 128 */     StringWriter stringWriter = null;
/* 129 */     String str = null;
/*     */     
/*     */     try {
/* 132 */       start_ABRBuild();
/* 133 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 134 */       this
/* 135 */         .m_eiParent = (this.m_egParent == null) ? null : this.m_egParent.getEntityItem(0);
/*     */       
/* 137 */       if (this.m_egParent == null) {
/* 138 */         logMessage(ABR + ":" + 
/*     */ 
/*     */             
/* 141 */             getVersion() + ":ERROR:1: m_egParent cannot be established.");
/*     */         
/* 143 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/* 146 */       if (this.m_eiParent == null) {
/* 147 */         logMessage(ABR + ":" + 
/*     */ 
/*     */             
/* 150 */             getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/*     */         
/* 152 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/* 155 */       logMessage(ABR + ":" + 
/*     */ 
/*     */           
/* 158 */           getVersion() + ":Request to Work on Entity:" + this.m_eiParent
/*     */           
/* 160 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 162 */           .getEntityID());
/* 163 */       buildReportHeader();
/* 164 */       setControlBlock();
/* 165 */       setDGTitle(setDGName(this.m_eiParent, ABR));
/*     */       
/* 167 */       logMessage(ABR + ":" + 
/*     */ 
/*     */           
/* 170 */           getVersion() + ":Setup Complete:" + this.m_eiParent
/*     */           
/* 172 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 174 */           .getEntityID());
/*     */ 
/*     */       
/* 177 */       displayHeader(this.m_egParent, this.m_eiParent);
/* 178 */       setReturnCode(0);
/*     */       
/* 180 */       if (!checkM0007(this.m_egParent, this.m_eiParent)) {
/* 181 */         setReturnCode(-1);
/*     */       }
/* 183 */       if (!checkM0008(this.m_egParent, this.m_eiParent)) {
/* 184 */         setReturnCode(-1);
/*     */       }
/* 186 */       if (!checkM0004Child(this.m_eiParent)) {
/* 187 */         setReturnCode(-1);
/*     */       }
/*     */       
/* 190 */       if (!checkA0002(this.m_eiParent)) {
/* 191 */         setReturnCode(-1);
/*     */       }
/* 193 */       if (!checkA0003(this.m_eiParent)) {
/* 194 */         setReturnCode(-1);
/*     */       }
/* 196 */       if (!checkS0002(this.m_eiParent, c_hshEntities)) {
/* 197 */         setReturnCode(-1);
/*     */       }
/*     */       
/* 200 */       if ((getFlagCode(this.m_eiParent, "COFOOFMGCAT").equals("100") && 
/* 201 */         getFlagCode(this.m_eiParent, "COFOOFMGSUBCAT").equals("452") && 
/* 202 */         getFlagCode(this.m_eiParent, "COFOOFMGGRP").equals("400") && 
/* 203 */         getFlagCode(this.m_eiParent, "COFOOFMGSUBGRP").equals("405")) || (
/* 204 */         getFlagCode(this.m_eiParent, "COFOOFMGCAT").equals("100") && 
/* 205 */         getFlagCode(this.m_eiParent, "COFOOFMGSUBCAT").equals("453") && 
/* 206 */         getFlagCode(this.m_eiParent, "COFOOFMGGRP").equals("400") && 
/* 207 */         getFlagCode(this.m_eiParent, "COFOOFMGSUBGRP").equals("405")) || (
/* 208 */         getFlagCode(this.m_eiParent, "COFOOFMGCAT").equals("100") && 
/* 209 */         getFlagCode(this.m_eiParent, "COFOOFMGSUBCAT").equals("208") && 
/* 210 */         getFlagCode(this.m_eiParent, "COFOOFMGGRP").equals("304") && 
/* 211 */         getFlagCode(this.m_eiParent, "COFOOFMGSUBGRP").equals("400")) || (
/* 212 */         getFlagCode(this.m_eiParent, "COFOOFMGCAT").equals("100") && 
/* 213 */         getFlagCode(this.m_eiParent, "COFOOFMGSUBCAT").equals("208") && 
/* 214 */         getFlagCode(this.m_eiParent, "COFOOFMGGRP").equals("304") && 
/* 215 */         getFlagCode(this.m_eiParent, "COFOOFMGSUBGRP").equals("402")) || (
/* 216 */         getFlagCode(this.m_eiParent, "COFOOFMGCAT").equals("100") && 
/* 217 */         getFlagCode(this.m_eiParent, "COFOOFMGSUBCAT").equals("208") && 
/* 218 */         getFlagCode(this.m_eiParent, "COFOOFMGGRP").equals("304") && 
/* 219 */         getFlagCode(this.m_eiParent, "COFOOFMGSUBGRP").equals("404")))
/*     */       {
/* 221 */         if (!checkA0001(this.m_eiParent)) {
/* 222 */           setReturnCode(-1);
/*     */         }
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 228 */       if (getReturnCode() == 0) {
/* 229 */         setFlagValue(this.m_eiParent, 
/*     */             
/* 231 */             getStatusAttributeCode(this.m_eiParent), 
/* 232 */             getNextStatusCode(this.m_eiParent));
/*     */         
/* 234 */         if (isChangeRev(this.m_eiParent) || isChangeFinal(this.m_eiParent)) {
/* 235 */           setNow();
/* 236 */           println(processChangeReport(getT1(this.m_eiParent), getNow()));
/*     */         } 
/*     */       } 
/*     */       
/* 240 */       println("<br /><b>" + 
/*     */           
/* 242 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 245 */               getABRDescription(), 
/* 246 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/* 248 */       log(
/* 249 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 252 */               getABRDescription(), 
/* 253 */               (getReturnCode() == 0) ? "Passed" : "Failed" }));
/* 254 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 255 */       setReturnCode(-2);
/* 256 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 260 */           .getMessage() + "</font></h3>");
/*     */       
/* 262 */       logError(lockPDHEntityException.getMessage());
/* 263 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 264 */       setReturnCode(-2);
/* 265 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 267 */           .getMessage() + "</font></h3>");
/*     */       
/* 269 */       logError(updatePDHEntityException.getMessage());
/* 270 */     } catch (Exception exception) {
/*     */       
/* 272 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 273 */       println("" + exception);
/* 274 */       exception.printStackTrace();
/*     */       
/* 276 */       stringWriter = new StringWriter();
/* 277 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 278 */       str = stringWriter.toString();
/* 279 */       println(str);
/* 280 */       logMessage("Error in " + this.m_abri
/* 281 */           .getABRCode() + ":" + exception.getMessage());
/* 282 */       logMessage("" + exception);
/*     */       
/* 284 */       if (getABRReturnCode() != -2) {
/* 285 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/*     */       
/* 289 */       setDGString(getABRReturnCode());
/* 290 */       setDGRptName("COFOOFQRABR01");
/* 291 */       setDGRptClass("COFOOFQRABR01");
/* 292 */       printDGSubmitString();
/*     */       
/* 294 */       buildReportFooter();
/* 295 */       if (!isReadOnly()) {
/* 296 */         clearSoftLock();
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
/* 310 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 320 */     return "<br /><br />";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStyle() {
/* 330 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 340 */     return new String("1.18");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 350 */     return "COFOOFQRABR01.java,v 1.18 2006/03/03 19:23:27 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 360 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\COFOOFQRABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */