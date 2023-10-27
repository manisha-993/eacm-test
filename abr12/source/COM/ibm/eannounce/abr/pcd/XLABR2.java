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
/*     */ public class XLABR2
/*     */   extends PokBaseABR
/*     */ {
/* 107 */   public static int TARGET_NLSID = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 125 */       start_ABRBuild(false);
/* 126 */       buildReportHeader();
/* 127 */       setNow();
/*     */ 
/*     */       
/* 130 */       EntityGroup entityGroup = this.m_db.getEntityGroup(this.m_prof, this.m_abri.getEntityType(), "Edit");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 137 */       EntityItem entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */       
/* 139 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("BILLINGCODE");
/* 140 */       String str = "";
/* 141 */       if (eANFlagAttribute != null) {
/* 142 */         str = eANFlagAttribute.getFirstActiveFlagCode();
/*     */       }
/* 144 */       if (str.equals("PCD")) {
/* 145 */         str = "";
/*     */       }
/*     */       
/* 148 */       println("<b><tr><td class=\"PsgText\" width=\"100%\" >" + 
/*     */           
/* 150 */           getEntityType() + ":</b></td><b><td class=\"PsgText\" width=\"100%\">" + 
/*     */           
/* 152 */           getEntityID() + "</b></td>");
/*     */       
/* 154 */       printNavigateAttributes(entityItem, entityGroup, true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 159 */       PackageID packageID = new PackageID(getEntityType(), getEntityID(), TARGET_NLSID, "N/A", this.m_strNow, str);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 165 */       TranslationPackage translationPackage = Translation.getETSPackage(this.m_db, this.m_prof, packageID);
/* 166 */       if (translationPackage == null) {
/* 167 */         println("<br><br><b>Failed. ETSPackage is empty");
/*     */       } else {
/* 169 */         Translation.postETSPackage(this.m_db, this.m_prof, packageID, EnterpriseUtil.isLastEnterpriseVersion(this.m_prof));
/* 170 */         setReturnCode(0);
/* 171 */         setControlBlock();
/* 172 */         XLABRQueueUtil.queueTranslationPostProcessABR(this.m_db, this.m_prof, entityItem, this.m_cbOn);
/*     */       } 
/*     */       
/* 175 */       println("<br /><b>" + 
/*     */           
/* 177 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 180 */               getABRDescription(), 
/* 181 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */     }
/* 184 */     catch (MaxTranslationException maxTranslationException) {
/* 185 */       setReturnCode(-1);
/* 186 */       println(maxTranslationException.toHtml());
/* 187 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 188 */       setReturnCode(-2);
/* 189 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 193 */           .getMessage() + "</font></h3>");
/*     */     }
/* 195 */     catch (UpdatePDHEntityException updatePDHEntityException) {
/* 196 */       setReturnCode(-2);
/* 197 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 199 */           .getMessage() + "</font></h3>");
/*     */     }
/* 201 */     catch (Exception exception) {
/*     */       
/* 203 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 204 */       println("" + exception);
/*     */ 
/*     */       
/* 207 */       if (getABRReturnCode() != -2) {
/* 208 */         setReturnCode(-3);
/*     */       }
/* 210 */       exception.printStackTrace();
/*     */       
/* 212 */       StringWriter stringWriter = new StringWriter();
/* 213 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 214 */       String str = stringWriter.toString();
/* 215 */       println(str);
/*     */     }
/*     */     finally {
/*     */       
/* 219 */       setDGString(getABRReturnCode());
/* 220 */       setDGRptName("XLABR2");
/* 221 */       printDGSubmitString();
/*     */       
/* 223 */       buildReportFooter();
/* 224 */       if (!isReadOnly()) {
/* 225 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 235 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 243 */     return "<br /><br />";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStyle() {
/* 251 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 261 */     return new String("$Revision: 1.24 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printPassMessage() {
/* 270 */     println("<br /><b>send to Production Management</b><br /><br />");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 280 */     return "$Id: XLABR2.java,v 1.24 2010/04/23 15:40:47 lucasrg Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 287 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\XLABR2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */