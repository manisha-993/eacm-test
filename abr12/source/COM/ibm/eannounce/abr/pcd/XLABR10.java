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
/*     */ public class XLABR10
/*     */   extends PokBaseABR
/*     */ {
/*  95 */   public static int TARGET_NLSID = 10;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 112 */       start_ABRBuild(false);
/* 113 */       buildReportHeader();
/* 114 */       setNow();
/*     */ 
/*     */       
/* 117 */       EntityGroup entityGroup = this.m_db.getEntityGroup(this.m_prof, this.m_abri.getEntityType(), "Edit");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 124 */       EntityItem entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */       
/* 126 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("BILLINGCODE");
/* 127 */       String str = "";
/* 128 */       if (eANFlagAttribute != null) {
/* 129 */         str = eANFlagAttribute.getFirstActiveFlagCode();
/*     */       }
/* 131 */       if (str.equals("PCD")) {
/* 132 */         str = "";
/*     */       }
/* 134 */       println("<b><tr><td class=\"PsgText\" width=\"100%\" >" + 
/*     */           
/* 136 */           getEntityType() + ":</b></td><b><td class=\"PsgText\" width=\"100%\">" + 
/*     */           
/* 138 */           getEntityID() + "</b></td>");
/*     */       
/* 140 */       printNavigateAttributes(entityItem, entityGroup, true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 145 */       PackageID packageID = new PackageID(getEntityType(), getEntityID(), TARGET_NLSID, "N/A", this.m_strNow, str);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 151 */       TranslationPackage translationPackage = Translation.getETSPackage(this.m_db, this.m_prof, packageID);
/* 152 */       if (translationPackage == null) {
/* 153 */         println("<br><br><b>Failed. ETSPackage is empty");
/*     */       } else {
/* 155 */         Translation.postETSPackage(this.m_db, this.m_prof, packageID, EnterpriseUtil.isLastEnterpriseVersion(this.m_prof));
/* 156 */         setReturnCode(0);
/* 157 */         setControlBlock();
/* 158 */         XLABRQueueUtil.queueTranslationPostProcessABR(this.m_db, this.m_prof, entityItem, this.m_cbOn);
/*     */       } 
/*     */       
/* 161 */       println("<br /><b>" + 
/*     */           
/* 163 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 166 */               getABRDescription(), 
/* 167 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/* 169 */     } catch (MaxTranslationException maxTranslationException) {
/* 170 */       setReturnCode(-1);
/* 171 */       println(maxTranslationException.toHtml());
/* 172 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 173 */       setReturnCode(-2);
/* 174 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 178 */           .getMessage() + "</font></h3>");
/*     */     }
/* 180 */     catch (UpdatePDHEntityException updatePDHEntityException) {
/* 181 */       setReturnCode(-2);
/* 182 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 184 */           .getMessage() + "</font></h3>");
/*     */     }
/* 186 */     catch (Exception exception) {
/*     */       
/* 188 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 189 */       println("" + exception);
/*     */ 
/*     */       
/* 192 */       if (getABRReturnCode() != -2) {
/* 193 */         setReturnCode(-3);
/*     */       }
/* 195 */       exception.printStackTrace();
/*     */       
/* 197 */       StringWriter stringWriter = new StringWriter();
/* 198 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 199 */       String str = stringWriter.toString();
/* 200 */       println(str);
/*     */     }
/*     */     finally {
/*     */       
/* 204 */       setDGString(getABRReturnCode());
/* 205 */       setDGRptName("XLABR10");
/* 206 */       printDGSubmitString();
/*     */       
/* 208 */       buildReportFooter();
/* 209 */       if (!isReadOnly()) {
/* 210 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 220 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 228 */     return "<br /><br />";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStyle() {
/* 236 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 246 */     return new String("$Revision: 1.19 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printPassMessage() {
/* 255 */     println("<br /><b>send to Production Management</b><br /><br />");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 265 */     return "$Id: XLABR10.java,v 1.19 2010/04/23 15:40:47 lucasrg Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 272 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\XLABR10.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */