/*     */ package COM.ibm.eannounce.abr.pcd;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.EnterpriseUtil;
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.XLABRQueueUtil;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.translation.MaxTranslationException;
/*     */ import COM.ibm.opicmpdh.translation.PackageID;
/*     */ import COM.ibm.opicmpdh.translation.Translation;
/*     */ import COM.ibm.opicmpdh.translation.TranslationPackage;
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
/*     */ public class XLABR7
/*     */   extends PokBaseABR
/*     */ {
/* 113 */   public static int TARGET_NLSID = 7;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 131 */       start_ABRBuild(false);
/* 132 */       buildReportHeader();
/* 133 */       setNow();
/*     */ 
/*     */       
/* 136 */       EntityGroup entityGroup = this.m_db.getEntityGroup(this.m_prof, this.m_abri.getEntityType(), "Edit");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 143 */       EntityItem entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */       
/* 145 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("BILLINGCODE");
/* 146 */       String str = "";
/* 147 */       if (eANFlagAttribute != null) {
/* 148 */         str = eANFlagAttribute.getFirstActiveFlagCode();
/*     */       }
/* 150 */       if (str.equals("PCD")) {
/* 151 */         str = "";
/*     */       }
/*     */       
/* 154 */       println("<b><tr><td class=\"PsgText\" width=\"100%\" >" + 
/*     */           
/* 156 */           getEntityType() + ":</b></td><b><td class=\"PsgText\" width=\"100%\">" + 
/*     */           
/* 158 */           getEntityID() + "</b></td>");
/*     */       
/* 160 */       printNavigateAttributes(entityItem, entityGroup, true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 165 */       PackageID packageID = new PackageID(getEntityType(), getEntityID(), TARGET_NLSID, "N/A", this.m_strNow, str);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 171 */       TranslationPackage translationPackage = Translation.getETSPackage(this.m_db, this.m_prof, packageID);
/* 172 */       if (translationPackage == null) {
/* 173 */         println("<br><br><b>Failed. ETSPackage is empty");
/*     */       } else {
/* 175 */         Translation.postETSPackage(this.m_db, this.m_prof, packageID, EnterpriseUtil.isLastEnterpriseVersion(this.m_prof));
/* 176 */         setReturnCode(0);
/* 177 */         setControlBlock();
/* 178 */         XLABRQueueUtil.queueTranslationPostProcessABR(this.m_db, this.m_prof, entityItem, this.m_cbOn);
/*     */       } 
/*     */       
/* 181 */       println("<br /><b>" + 
/*     */           
/* 183 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 186 */               getABRDescription(), 
/* 187 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */     }
/* 190 */     catch (MaxTranslationException maxTranslationException) {
/* 191 */       setReturnCode(-1);
/* 192 */       println(maxTranslationException.toHtml());
/* 193 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 194 */       setReturnCode(-2);
/* 195 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 199 */           .getMessage() + "</font></h3>");
/*     */     }
/* 201 */     catch (UpdatePDHEntityException updatePDHEntityException) {
/* 202 */       setReturnCode(-2);
/* 203 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 205 */           .getMessage() + "</font></h3>");
/*     */     }
/* 207 */     catch (Exception exception) {
/*     */       
/* 209 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 210 */       println("" + exception);
/*     */ 
/*     */       
/* 213 */       if (getABRReturnCode() != -2) {
/* 214 */         setReturnCode(-3);
/*     */       }
/* 216 */       exception.printStackTrace();
/*     */       
/* 218 */       StringWriter stringWriter = new StringWriter();
/* 219 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 220 */       String str = stringWriter.toString();
/* 221 */       println(str);
/*     */     }
/*     */     finally {
/*     */       
/* 225 */       setDGString(getABRReturnCode());
/* 226 */       setDGRptName("XLABR7");
/* 227 */       printDGSubmitString();
/*     */       
/* 229 */       buildReportFooter();
/* 230 */       if (!isReadOnly()) {
/* 231 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 241 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 249 */     return "<br /><br />";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStyle() {
/* 257 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 267 */     return new String("$Revision: 1.25 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printPassMessage() {
/* 276 */     println("<br /><b>send to Production Management</b><br /><br />");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 286 */     return "$Id: XLABR7.java,v 1.25 2010/04/23 15:40:47 lucasrg Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 293 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\XLABR7.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */