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
/*     */ public class XLABR3
/*     */   extends PokBaseABR
/*     */ {
/*  98 */   public static int TARGET_NLSID = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 116 */       start_ABRBuild(false);
/* 117 */       buildReportHeader();
/* 118 */       setNow();
/*     */ 
/*     */       
/* 121 */       EntityGroup entityGroup = this.m_db.getEntityGroup(this.m_prof, this.m_abri.getEntityType(), "Edit");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 128 */       EntityItem entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */       
/* 130 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("BILLINGCODE");
/* 131 */       String str = "";
/* 132 */       if (eANFlagAttribute != null) {
/* 133 */         str = eANFlagAttribute.getFirstActiveFlagCode();
/*     */       }
/* 135 */       if (str.equals("PCD")) {
/* 136 */         str = "";
/*     */       }
/*     */       
/* 139 */       println("<b><tr><td class=\"PsgText\" width=\"100%\" >" + 
/*     */           
/* 141 */           getEntityType() + ":</b></td><b><td class=\"PsgText\" width=\"100%\">" + 
/*     */           
/* 143 */           getEntityID() + "</b></td>");
/*     */       
/* 145 */       printNavigateAttributes(entityItem, entityGroup, true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 150 */       PackageID packageID = new PackageID(getEntityType(), getEntityID(), TARGET_NLSID, "N/A", this.m_strNow, str);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 156 */       TranslationPackage translationPackage = Translation.getETSPackage(this.m_db, this.m_prof, packageID);
/* 157 */       if (translationPackage == null) {
/* 158 */         println("<br><br><b>Failed. ETSPackage is empty");
/*     */       } else {
/* 160 */         Translation.postETSPackage(this.m_db, this.m_prof, packageID, EnterpriseUtil.isLastEnterpriseVersion(this.m_prof));
/* 161 */         setReturnCode(0);
/* 162 */         setControlBlock();
/* 163 */         XLABRQueueUtil.queueTranslationPostProcessABR(this.m_db, this.m_prof, entityItem, this.m_cbOn);
/*     */       } 
/*     */       
/* 166 */       println("<br /><b>" + 
/*     */           
/* 168 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 171 */               getABRDescription(), 
/* 172 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */     }
/* 175 */     catch (MaxTranslationException maxTranslationException) {
/* 176 */       setReturnCode(-1);
/* 177 */       println(maxTranslationException.toHtml());
/* 178 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 179 */       setReturnCode(-2);
/* 180 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 184 */           .getMessage() + "</font></h3>");
/*     */     }
/* 186 */     catch (UpdatePDHEntityException updatePDHEntityException) {
/* 187 */       setReturnCode(-2);
/* 188 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 190 */           .getMessage() + "</font></h3>");
/*     */     }
/* 192 */     catch (Exception exception) {
/*     */       
/* 194 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 195 */       println("" + exception);
/*     */ 
/*     */       
/* 198 */       if (getABRReturnCode() != -2) {
/* 199 */         setReturnCode(-3);
/*     */       }
/* 201 */       exception.printStackTrace();
/*     */       
/* 203 */       StringWriter stringWriter = new StringWriter();
/* 204 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 205 */       String str = stringWriter.toString();
/* 206 */       println(str);
/*     */     }
/*     */     finally {
/*     */       
/* 210 */       setDGString(getABRReturnCode());
/* 211 */       setDGRptName("XLABR3");
/* 212 */       printDGSubmitString();
/*     */       
/* 214 */       buildReportFooter();
/* 215 */       if (!isReadOnly()) {
/* 216 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 226 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 234 */     return "<br /><br />";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStyle() {
/* 242 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 252 */     return new String("$Revision: 1.20 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printPassMessage() {
/* 261 */     println("<br /><b>send to Production Management</b><br /><br />");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 271 */     return "$Id: XLABR3.java,v 1.20 2010/04/23 15:40:47 lucasrg Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 278 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\XLABR3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */