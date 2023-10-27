/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnStatus;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WWTECHCOMPATABR
/*     */   extends PokBaseABR
/*     */ {
/*     */   public void execute_run() {
/*     */     try {
/*  47 */       start_ABRBuild(false);
/*  48 */       buildReportHeader();
/*  49 */       setNow();
/*     */       
/*  51 */       EntityGroup entityGroup = this.m_db.getEntityGroup(this.m_prof, this.m_abri.getEntityType(), "Edit");
/*     */       
/*  53 */       EntityItem entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */       
/*  55 */       println("<b><tr><td class=\"PsgText\" width=\"100%\" >" + 
/*  56 */           getEntityType() + ":</b></td><b><td class=\"PsgText\" width=\"100%\">" + 
/*     */           
/*  58 */           getEntityID() + "</b></td>");
/*  59 */       printNavigateAttributes(entityItem, entityGroup, true);
/*     */       
/*  61 */       setReturnCode(0);
/*  62 */       if (getReturnCode() == 0)
/*     */       {
/*  64 */         for (byte b = 0; b < 10; b++) {
/*  65 */           String str1 = "A";
/*  66 */           String str2 = "2007-09-26-02.35.01.553888";
/*  67 */           String str3 = "SystemEntity" + b;
/*  68 */           byte b1 = b;
/*  69 */           String str4 = "GroupEntity" + b;
/*  70 */           byte b2 = b;
/*  71 */           String str5 = "OSEntity" + b;
/*  72 */           byte b3 = b;
/*  73 */           String str6 = "OS";
/*  74 */           String str7 = "OptionEntity" + b;
/*  75 */           byte b4 = b;
/*  76 */           String str8 = "Yes";
/*  77 */           String str9 = "2";
/*  78 */           String str10 = "From";
/*  79 */           String str11 = "To";
/*  80 */           callGBL7777(str1, str2, str3, b1, str4, b2, str5, b3, str6, str7, b4, str8, str9, str10, str11);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  88 */       println("<br /><b>" + 
/*  89 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*  90 */               getABRDescription(), 
/*  91 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */     }
/*  94 */     catch (LockPDHEntityException lockPDHEntityException) {
/*  95 */       setReturnCode(-2);
/*  96 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*  97 */           .getMessage() + "</font></h3>");
/*  98 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  99 */       setReturnCode(-2);
/* 100 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException.getMessage() + "</font></h3>");
/*     */     }
/* 102 */     catch (Exception exception) {
/*     */       
/* 104 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 105 */       println("" + exception);
/*     */ 
/*     */       
/* 108 */       if (getABRReturnCode() != -2) {
/* 109 */         setReturnCode(-3);
/*     */       }
/* 111 */       exception.printStackTrace();
/*     */       
/* 113 */       StringWriter stringWriter = new StringWriter();
/* 114 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 115 */       String str = stringWriter.toString();
/* 116 */       println(str);
/*     */     }
/*     */     finally {
/*     */       
/* 120 */       setDGString(getABRReturnCode());
/* 121 */       setDGRptName("WWTECHCOMPATABR");
/* 122 */       printDGSubmitString();
/*     */       
/* 124 */       buildReportFooter();
/* 125 */       if (!isReadOnly()) {
/* 126 */         clearSoftLock();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void callGBL7777(String paramString1, String paramString2, String paramString3, int paramInt1, String paramString4, int paramInt2, String paramString5, int paramInt3, String paramString6, String paramString7, int paramInt4, String paramString8, String paramString9, String paramString10, String paramString11) throws SQLException, MiddlewareException {
/* 149 */     ReturnStatus returnStatus = null;
/* 150 */     Connection connection = null;
/* 151 */     CallableStatement callableStatement = null;
/* 152 */     connection = this.m_db.getPDHConnection();
/* 153 */     if (connection == null) {
/* 154 */       this.m_db.connect();
/* 155 */       connection = this.m_db.getPDHConnection();
/*     */     } 
/* 157 */     String str = "CALL opicm.GBL7777(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/* 158 */     callableStatement = connection.prepareCall(str);
/* 159 */     callableStatement.registerOutParameter(1, 4);
/*     */     
/* 161 */     callableStatement.setString(1, paramString1);
/* 162 */     callableStatement.setString(2, paramString2);
/* 163 */     callableStatement.setString(3, paramString3);
/* 164 */     callableStatement.setInt(4, paramInt1);
/* 165 */     callableStatement.setString(5, paramString4);
/* 166 */     callableStatement.setInt(6, paramInt2);
/* 167 */     callableStatement.setString(7, paramString5);
/* 168 */     callableStatement.setInt(8, paramInt3);
/* 169 */     callableStatement.setString(9, paramString6);
/* 170 */     callableStatement.setString(10, paramString7);
/* 171 */     callableStatement.setInt(11, paramInt4);
/* 172 */     callableStatement.setString(12, paramString8);
/* 173 */     callableStatement.setString(13, paramString9);
/* 174 */     callableStatement.setString(14, paramString10);
/* 175 */     callableStatement.setString(15, paramString11);
/*     */ 
/*     */     
/* 178 */     callableStatement.executeUpdate();
/*     */     
/* 180 */     returnStatus.setValue(callableStatement.getInt(1));
/* 181 */     callableStatement.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 197 */     return "<br /><br />";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStyle() {
/* 205 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 215 */     return new String("1.4");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printPassMessage() {
/* 224 */     println("<br /><b>send to Production Management</b><br /><br />");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 234 */     return "WWTECHCOMPATABR.java,v 1.4 2008/01/30 19:39:16 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 241 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\WWTECHCOMPATABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */