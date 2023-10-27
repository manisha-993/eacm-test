/*     */ package COM.ibm.eannounce.abr.pcd;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANUtility;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.pmsync.PMComparisonReportWriter;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnRelatorKey;
/*     */ import COM.ibm.opicmpdh.transactions.OPICMBlobValue;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ECABR1
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String ABR = "ECABR1";
/*     */   private static final String EC_ENTTYPE = "EC";
/*     */   private static final String EC_BLOB_ATTCODE = "ECBLOB1";
/*     */   private static final String REPORT_FILENAME = "PMComparisonReport.html";
/*     */   
/*     */   public void execute_run() {
/*     */     try {
/*  94 */       start_ABRBuild();
/*  95 */       genComparisonReport();
/*  96 */     } catch (Exception exception) {
/*  97 */       logError("ECABR1.execute_run(): " + exception.getMessage());
/*  98 */       exception.printStackTrace(System.err);
/*     */     } finally {
/*     */       
/* 101 */       setDGTitle("PM Comparison Report");
/* 102 */       setDGRptName("ECABR1");
/* 103 */       printDGSubmitString();
/*     */ 
/*     */       
/* 106 */       if (!isReadOnly()) {
/* 107 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void genComparisonReport() throws Exception {
/*     */     try {
/* 118 */       PMComparisonReportWriter pMComparisonReportWriter = new PMComparisonReportWriter(getEntityList(), getABRItem().getFileName());
/* 119 */       getABRItem().setPrintWriter((PrintWriter)pMComparisonReportWriter);
/* 120 */       pMComparisonReportWriter.genComparisonReport();
/* 121 */       getRootEntityItem().put("EC:ECBLOB1", new OPICMBlobValue(
/*     */ 
/*     */             
/* 124 */             getProfile().getReadLanguage().getNLSID(), "PMComparisonReport.html", 
/*     */             
/* 126 */             getReportBytes()));
/* 127 */       getRootEntityItem().commit(getDatabase(), null);
/*     */ 
/*     */       
/* 130 */       Vector<ReturnRelatorKey> vector = new Vector();
/* 131 */       vector.addElement(new ReturnRelatorKey("DIVEC", -1, "DIV", 1, "EC", 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 138 */             getRootEntityItem().getEntityID(), true));
/*     */ 
/*     */       
/* 141 */       EANUtility.link(
/* 142 */           getDatabase(), 
/* 143 */           getProfile(), vector, "NODUPES", 2, 1, false);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 149 */     catch (Exception exception) {
/* 150 */       logError("ECABR1.genComparisonReport(): " + exception.getMessage());
/* 151 */       exception.printStackTrace(System.err);
/* 152 */       setReturnCode(-2);
/* 153 */       throw exception;
/*     */     } 
/* 155 */     setReturnCode(0);
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] getReportBytes() throws Exception {
/* 160 */     FileInputStream fileInputStream = new FileInputStream(getABRItem().getFileName());
/* 161 */     byte[] arrayOfByte = new byte[fileInputStream.available()];
/* 162 */     fileInputStream.read(arrayOfByte);
/* 163 */     fileInputStream.close();
/* 164 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   private EntityList getEntityList() {
/* 168 */     if (this.m_elist == null) {
/* 169 */       logError("ECABR1.getEntityList() is null!!!");
/*     */     }
/* 171 */     return this.m_elist;
/*     */   }
/*     */   
/*     */   private EntityItem getRootEntityItem() {
/* 175 */     return getEntityList().getParentEntityGroup().getEntityItem(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 183 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 193 */     return "$Id: ECABR1.java,v 1.11 2008/01/30 20:00:36 wendy Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 201 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\ECABR1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */