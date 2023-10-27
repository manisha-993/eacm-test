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
/*     */ public class XLABR6
/*     */   extends PokBaseABR
/*     */ {
/*  95 */   public static int TARGET_NLSID = 6;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 113 */       start_ABRBuild(false);
/* 114 */       buildReportHeader();
/* 115 */       setNow();
/*     */ 
/*     */       
/* 118 */       EntityGroup entityGroup = this.m_db.getEntityGroup(this.m_prof, this.m_abri.getEntityType(), "Edit");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 125 */       EntityItem entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */       
/* 127 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("BILLINGCODE");
/* 128 */       String str = "";
/* 129 */       if (eANFlagAttribute != null) {
/* 130 */         str = eANFlagAttribute.getFirstActiveFlagCode();
/*     */       }
/* 132 */       if (str.equals("PCD")) {
/* 133 */         str = "";
/*     */       }
/*     */       
/* 136 */       println("<b><tr><td class=\"PsgText\" width=\"100%\" >" + 
/*     */           
/* 138 */           getEntityType() + ":</b></td><b><td class=\"PsgText\" width=\"100%\">" + 
/*     */           
/* 140 */           getEntityID() + "</b></td>");
/*     */       
/* 142 */       printNavigateAttributes(entityItem, entityGroup, true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 147 */       PackageID packageID = new PackageID(getEntityType(), getEntityID(), TARGET_NLSID, "N/A", this.m_strNow, str);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 153 */       TranslationPackage translationPackage = Translation.getETSPackage(this.m_db, this.m_prof, packageID);
/* 154 */       if (translationPackage == null) {
/* 155 */         println("<br><br><b>Failed. ETSPackage is empty");
/*     */       } else {
/* 157 */         Translation.postETSPackage(this.m_db, this.m_prof, packageID, EnterpriseUtil.isLastEnterpriseVersion(this.m_prof));
/* 158 */         setReturnCode(0);
/* 159 */         setControlBlock();
/* 160 */         XLABRQueueUtil.queueTranslationPostProcessABR(this.m_db, this.m_prof, entityItem, this.m_cbOn);
/*     */       } 
/*     */       
/* 163 */       println("<br /><b>" + 
/*     */           
/* 165 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 168 */               getABRDescription(), 
/* 169 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */     }
/* 172 */     catch (MaxTranslationException maxTranslationException) {
/* 173 */       setReturnCode(-1);
/* 174 */       println(maxTranslationException.toHtml());
/* 175 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 176 */       setReturnCode(-2);
/* 177 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 181 */           .getMessage() + "</font></h3>");
/*     */     }
/* 183 */     catch (UpdatePDHEntityException updatePDHEntityException) {
/* 184 */       setReturnCode(-2);
/* 185 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 187 */           .getMessage() + "</font></h3>");
/*     */     }
/* 189 */     catch (Exception exception) {
/*     */       
/* 191 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 192 */       println("" + exception);
/*     */ 
/*     */       
/* 195 */       if (getABRReturnCode() != -2) {
/* 196 */         setReturnCode(-3);
/*     */       }
/* 198 */       exception.printStackTrace();
/*     */       
/* 200 */       StringWriter stringWriter = new StringWriter();
/* 201 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 202 */       String str = stringWriter.toString();
/* 203 */       println(str);
/*     */     }
/*     */     finally {
/*     */       
/* 207 */       setDGString(getABRReturnCode());
/* 208 */       setDGRptName("XLABR6");
/* 209 */       printDGSubmitString();
/*     */       
/* 211 */       buildReportFooter();
/* 212 */       if (!isReadOnly()) {
/* 213 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 223 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 231 */     return "<br /><br />";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStyle() {
/* 239 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 249 */     return new String("$Revision: 1.19 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printPassMessage() {
/* 258 */     println("<br /><b>send to Production Management</b><br /><br />");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 268 */     return "$Id: XLABR6.java,v 1.19 2010/04/23 15:40:47 lucasrg Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 275 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\XLABR6.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */