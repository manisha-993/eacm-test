/*     */ package COM.ibm.eannounce.abr.sg.translation;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.D;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ALREQ
/*     */   extends PokBaseABR
/*     */ {
/*  33 */   private static final Map ENTITY_PROCESS_MAP = new HashMap<>();
/*     */   
/*  35 */   private final StringBuffer exceptionReportBuffer = new StringBuffer();
/*     */   
/*     */   private int requestNLSID;
/*     */   
/*     */   private String requestEntityType;
/*     */   
/*  41 */   private XLATEGRPHandler relatorHandler = new XLATEGRPHandler();
/*     */   
/*  43 */   private int handledEntityCount = 0;
/*     */   
/*     */   static {
/*  46 */     ENTITY_PROCESS_MAP.put("XCATNAV", new CATNAV_AddTranslationProcess());
/*  47 */     ENTITY_PROCESS_MAP.put("XFB", new FB_AddTranslationProcess());
/*  48 */     ENTITY_PROCESS_MAP.put("XFEATURE", new FEATURE_AddTranslationProcess());
/*  49 */     ENTITY_PROCESS_MAP.put("XIPSCFEAT", new IPSCFEAT_AddTranslationProcess());
/*     */     
/*  51 */     ENTITY_PROCESS_MAP.put("XLSEO", new LSEO_AddTranslationProcess());
/*  52 */     ENTITY_PROCESS_MAP.put("XLSEOBUNDLE", new LSEOBUNDLE_AddTranslationProcess());
/*     */     
/*  54 */     ENTITY_PROCESS_MAP.put("XMM", new MM_AddTranslationProcess());
/*  55 */     ENTITY_PROCESS_MAP.put("XMODEL", new MODEL_AddTranslationProcess());
/*  56 */     ENTITY_PROCESS_MAP.put("XSVCMOD", new SVCMOD_AddTranslationProcess());
/*  57 */     ENTITY_PROCESS_MAP.put("XSWFEATURE", new SWFEATURE_AddTranslationProcess());
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*     */     try {
/*  63 */       start_ABRBuild(false);
/*  64 */       setNow();
/*  65 */       setControlBlock();
/*  66 */       buildRptHeader();
/*  67 */       processAbr();
/*     */       
/*  69 */       print("<h2>Added translation groups: ");
/*  70 */       print("" + this.relatorHandler.translationGroupCount);
/*  71 */       print("</h2>");
/*  72 */       println("<br/>");
/*     */       
/*  74 */       print("<h2>Handled entities: ");
/*  75 */       print("" + this.handledEntityCount);
/*  76 */       print("</h2>");
/*  77 */       println("<br/>");
/*     */       
/*  79 */       if (this.exceptionReportBuffer.length() > 0) {
/*  80 */         println("<br/>");
/*  81 */         println("<h1>Exceptions:</h1>");
/*  82 */         println(this.exceptionReportBuffer.toString());
/*     */       } 
/*     */       
/*  85 */       setReturnCode(0);
/*  86 */     } catch (Exception exception) {
/*  87 */       setReturnCode(-1);
/*  88 */       println("<h3>Uncaught exception</h3>");
/*  89 */       exception.printStackTrace(getPrintWriter());
/*     */     } finally {
/*  91 */       setDGString(getABRReturnCode());
/*  92 */       setDGRptName(getShortClassName(getClass()));
/*  93 */       setDGRptClass(getABRCode());
/*     */       
/*  95 */       if (!isReadOnly()) {
/*  96 */         clearSoftLock();
/*     */       }
/*  98 */       printDGSubmitString();
/*  99 */       println(EACustom.getTOUDiv());
/* 100 */       buildReportFooter();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processAbr() throws Exception {
/* 110 */     D.ebug("Processing ABR for " + this.m_abri.getEntityType());
/* 111 */     EntityGroup entityGroup = this.m_db.getEntityGroup(this.m_prof, this.m_abri.getEntityType(), "Edit");
/*     */ 
/*     */     
/* 114 */     EntityItem entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */ 
/*     */     
/* 117 */     String str1 = PokUtils.getAttributeValue(entityItem, "XENTITYTYPE", null, null);
/* 118 */     EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem.getAttribute("XENTITYTYPE");
/* 119 */     String str2 = eANFlagAttribute1.getFirstActiveFlagCode();
/* 120 */     if (str2 == null) {
/* 121 */       throw new IllegalArgumentException("XENTITYTYPE cannot be null");
/*     */     }
/* 123 */     EANFlagAttribute eANFlagAttribute2 = (EANFlagAttribute)entityItem.getAttribute("NLSWRITE");
/* 124 */     String str3 = eANFlagAttribute2.getFirstActiveFlagCode();
/*     */     
/* 126 */     if (str3 == null) {
/* 127 */       throw new IllegalArgumentException("NLSWRITE cannot be null");
/*     */     }
/*     */     try {
/* 130 */       this.requestNLSID = Integer.parseInt(str3);
/* 131 */     } catch (Exception exception) {
/* 132 */       throw new IllegalArgumentException("NLSWRITE " + str3 + " is not valid!");
/*     */     } 
/*     */     
/* 135 */     String str4 = PokUtils.getAttributeValue(entityItem, "XTGTDATE", null, null);
/* 136 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/* 137 */     final Date xTargetDate = simpleDateFormat.parse(str4);
/*     */     
/* 139 */     println("<h2>Translation Add Language request</h2>");
/* 140 */     println("<ul>");
/* 141 */     println("<li>XENTITYTYPE=" + str1 + " (" + str2 + ")</li>");
/* 142 */     println("<li>NLSWRITE=" + str3 + "</li>");
/* 143 */     println("<li>XTGTDATE=" + str4 + "</li>");
/* 144 */     println("</ul>");
/* 145 */     println("<br/>");
/*     */     
/* 147 */     D.ebug("xEntityType = " + str2);
/* 148 */     D.ebug("xTgtDate / xTargetDate  = " + str4 + " / " + date);
/* 149 */     D.ebug("xNLSWrite / nslid = " + str3 + " / " + this.requestNLSID);
/*     */ 
/*     */ 
/*     */     
/* 153 */     final AddTranslationProcess translationProcess = (AddTranslationProcess)ENTITY_PROCESS_MAP.get(str2);
/*     */     
/* 155 */     if (addTranslationProcess == null) {
/* 156 */       throw new IllegalArgumentException(str2 + " is not mapped");
/*     */     }
/*     */     
/* 159 */     D.ebug("AddTranslationProcess  for " + str2 + " is " + addTranslationProcess.getClass());
/*     */ 
/*     */     
/* 162 */     if (str2.charAt(0) == 'X') {
/* 163 */       this.requestEntityType = str2.substring(1);
/*     */     } else {
/* 165 */       this.requestEntityType = str2;
/*     */     } 
/* 167 */     D.ebug("Working with EntityType: " + this.requestEntityType);
/* 168 */     this.handledEntityCount = 0;
/*     */ 
/*     */     
/* 171 */     EntityHandler.withAllEntitiesDo(this.m_db, this.m_prof, this.m_cbOn, this.requestEntityType, this.requestNLSID, new EntityHandler.Callback()
/*     */         {
/*     */           
/*     */           public void process(EntityHandler param1EntityHandler)
/*     */           {
/*     */             try {
/* 177 */               if (translationProcess.isValid(param1EntityHandler, xTargetDate))
/*     */               {
/* 179 */                 D.ebug("Creating relators for: " + param1EntityHandler.getEntityType() + " - ID: " + param1EntityHandler
/* 180 */                     .getEntityID());
/*     */                 
/* 182 */                 translationProcess.createRelators(ALREQ.this.relatorHandler, param1EntityHandler);
/* 183 */                 ALREQ.this.handledEntityCount++;
/*     */               }
/*     */             
/* 186 */             } catch (Exception exception) {
/* 187 */               ALREQ.this.handleException(exception, param1EntityHandler.getEntityType(), param1EntityHandler.getEntityID());
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 193 */     this.m_db.commit();
/*     */   }
/*     */   
/*     */   public String getABRVersion() {
/* 197 */     return "1.0";
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 201 */     return "ALREQ - Add Translation ABR";
/*     */   }
/*     */   
/*     */   private void handleException(Exception paramException, String paramString, int paramInt) {
/* 205 */     this.exceptionReportBuffer.append("<p style='color:#c00'>Exception: ");
/* 206 */     this.exceptionReportBuffer.append(paramException.getMessage());
/* 207 */     this.exceptionReportBuffer.append("<p>Entity:");
/* 208 */     this.exceptionReportBuffer.append(paramString);
/* 209 */     this.exceptionReportBuffer.append(" - ");
/* 210 */     this.exceptionReportBuffer.append(paramInt);
/* 211 */     this.exceptionReportBuffer.append("</p>");
/* 212 */     this.exceptionReportBuffer.append("</p>");
/* 213 */     paramException.printStackTrace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildRptHeader() throws SQLException, MiddlewareException {
/* 222 */     String str1 = getVersion();
/*     */     
/* 224 */     String[] arrayOfString = { getABRDescription(), str1, getEnterprise() };
/* 225 */     String str2 = buildMessage("<b>IAB0001I: ABRName:</b> %1# <b>Version:</b> %2# <b>Enterprise:</b> %3#", arrayOfString);
/*     */     
/* 227 */     println(EACustom.getDocTypeHtml());
/* 228 */     println("<head>");
/* 229 */     println(EACustom.getMetaTags(getClass().getName() + ".java"));
/* 230 */     println(EACustom.getCSS());
/* 231 */     println(EACustom.getTitle(getShortClassName(getClass())));
/* 232 */     println("</head>");
/* 233 */     println("<body id=\"ibm-com\">");
/* 234 */     println(EACustom.getMastheadDiv());
/*     */     
/* 236 */     println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + str2 + "</em></p>");
/* 237 */     println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" + 
/*     */ 
/*     */         
/* 240 */         getABRVersion() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" + 
/*     */ 
/*     */ 
/*     */         
/* 244 */         getValOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" + 
/*     */ 
/*     */ 
/*     */         
/* 248 */         getEffOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" + 
/*     */ 
/*     */ 
/*     */         
/* 252 */         getNow() + "</td></tr>" + "\n" + "</table>");
/* 253 */     println("<h3>Description: </h3>" + getDescription() + "\n" + "<hr />" + "\n");
/*     */   }
/*     */ 
/*     */   
/*     */   private class XLATEGRPHandler
/*     */     extends RelatorHandler
/*     */   {
/* 260 */     int translationGroupCount = 0;
/*     */     
/*     */     public XLATEGRPHandler() {
/* 263 */       super("XLATEGRP");
/*     */     }
/*     */     
/*     */     public EntityHandler createParentEntity() throws MiddlewareException {
/* 267 */       D.ebug("RelatorHandler: Inserting new XLATEGRP");
/* 268 */       EntityHandler entityHandler = new EntityHandler(ALREQ.this.m_db, ALREQ.this.m_prof, ALREQ.this.m_cbOn, "XLATEGRP", -1, ALREQ.this.requestNLSID);
/* 269 */       entityHandler.insert();
/* 270 */       D.ebug("RelatorHandler: XLATEGRP new ID = " + entityHandler.getEntityID());
/* 271 */       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
/* 272 */       String str = "Add Lang. Package " + ALREQ.this.requestEntityType + " " + simpleDateFormat.format(new Date());
/* 273 */       entityHandler.setTextAttribute("XLATENAME", str);
/*     */       
/* 275 */       entityHandler.setFlagAttribute("BILLINGCODE", "SGA");
/* 276 */       entityHandler.setFlagAttribute("FAMILY", "F00050");
/* 277 */       entityHandler.setFlagAttribute("PDHDOMAIN", "0050");
/* 278 */       entityHandler.setFlagAttribute("XLATEABR01", "0020");
/* 279 */       entityHandler.setFlagAttribute("XLATEGRPSTATUS", "D0010");
/* 280 */       entityHandler.setFlagAttribute("XLSTATUS" + ALREQ.this.requestNLSID, "XL20");
/* 281 */       entityHandler.setFlagAttribute("XLLANGUAGES", "" + ALREQ.this.requestNLSID);
/*     */       
/* 283 */       ALREQ.this.println("<h3>Added XLATEGRP: " + str + "</h3>");
/*     */       
/* 285 */       this.translationGroupCount++;
/*     */       
/*     */       try {
/* 288 */         ALREQ.this.m_db.commit();
/* 289 */       } catch (SQLException sQLException) {
/* 290 */         sQLException.printStackTrace();
/*     */       } 
/*     */       
/* 293 */       return entityHandler;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\translation\ALREQ.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */