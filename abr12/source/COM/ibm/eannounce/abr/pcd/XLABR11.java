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
/*     */ public class XLABR11
/*     */   extends PokBaseABR
/*     */ {
/* 107 */   public static int TARGET_NLSID = 11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 124 */       start_ABRBuild(false);
/* 125 */       buildReportHeader();
/* 126 */       setNow();
/*     */ 
/*     */       
/* 129 */       EntityGroup entityGroup = this.m_db.getEntityGroup(this.m_prof, this.m_abri.getEntityType(), "Edit");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 136 */       EntityItem entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */       
/* 138 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("BILLINGCODE");
/* 139 */       String str = "";
/* 140 */       if (eANFlagAttribute != null) {
/* 141 */         str = eANFlagAttribute.getFirstActiveFlagCode();
/*     */       }
/* 143 */       if (str.equals("PCD")) {
/* 144 */         str = "";
/*     */       }
/*     */       
/* 147 */       println("<b><tr><td class=\"PsgText\" width=\"100%\" >" + 
/*     */           
/* 149 */           getEntityType() + ":</b></td><b><td class=\"PsgText\" width=\"100%\">" + 
/*     */           
/* 151 */           getEntityID() + "</b></td>");
/*     */       
/* 153 */       printNavigateAttributes(entityItem, entityGroup, true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 158 */       PackageID packageID = new PackageID(getEntityType(), getEntityID(), TARGET_NLSID, "N/A", this.m_strNow, str);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 164 */       TranslationPackage translationPackage = Translation.getETSPackage(this.m_db, this.m_prof, packageID);
/* 165 */       if (translationPackage == null) {
/* 166 */         println("<br><br><b>Failed. ETSPackage is empty");
/*     */       } else {
/* 168 */         Translation.postETSPackage(this.m_db, this.m_prof, packageID, EnterpriseUtil.isLastEnterpriseVersion(this.m_prof));
/* 169 */         setReturnCode(0);
/* 170 */         setControlBlock();
/* 171 */         XLABRQueueUtil.queueTranslationPostProcessABR(this.m_db, this.m_prof, entityItem, this.m_cbOn);
/*     */       } 
/*     */       
/* 174 */       println("<br /><b>" + 
/*     */           
/* 176 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 179 */               getABRDescription(), 
/* 180 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */     }
/* 183 */     catch (MaxTranslationException maxTranslationException) {
/* 184 */       setReturnCode(-1);
/* 185 */       println(maxTranslationException.toHtml());
/* 186 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 187 */       setReturnCode(-2);
/* 188 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 192 */           .getMessage() + "</font></h3>");
/*     */     }
/* 194 */     catch (UpdatePDHEntityException updatePDHEntityException) {
/* 195 */       setReturnCode(-2);
/* 196 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 198 */           .getMessage() + "</font></h3>");
/*     */     }
/* 200 */     catch (Exception exception) {
/*     */       
/* 202 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 203 */       println("" + exception);
/*     */ 
/*     */       
/* 206 */       if (getABRReturnCode() != -2) {
/* 207 */         setReturnCode(-3);
/*     */       }
/* 209 */       exception.printStackTrace();
/*     */       
/* 211 */       StringWriter stringWriter = new StringWriter();
/* 212 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 213 */       String str = stringWriter.toString();
/* 214 */       println(str);
/*     */     }
/*     */     finally {
/*     */       
/* 218 */       setDGString(getABRReturnCode());
/* 219 */       setDGRptName("XLABR11");
/* 220 */       printDGSubmitString();
/*     */       
/* 222 */       buildReportFooter();
/* 223 */       if (!isReadOnly()) {
/* 224 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 234 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 242 */     return "<br /><br />";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStyle() {
/* 250 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 260 */     return new String("$Revision: 1.23 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printPassMessage() {
/* 269 */     println("<br /><b>send to Production Management</b><br /><br />");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 279 */     return "$Id: XLABR11.java,v 1.23 2010/04/23 15:40:48 lucasrg Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 286 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\XLABR11.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */