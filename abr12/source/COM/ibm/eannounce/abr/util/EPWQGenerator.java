/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.EPIMSWWPRTBASE;
/*     */ import COM.ibm.eannounce.objects.BlobAttribute;
/*     */ import COM.ibm.eannounce.objects.CreateActionItem;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANDataFoundation;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.MetaBlobAttribute;
/*     */ import COM.ibm.eannounce.objects.MetaSingleFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.MetaTextAttribute;
/*     */ import COM.ibm.eannounce.objects.SingleFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.TextAttribute;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EPWQGenerator
/*     */ {
/*     */   public static final String COPYRIGHT = "(C) Copyright IBM Corp. 2007  All Rights Reserved.";
/*     */   public static final String VERSION = "$Revision: 1.3 $";
/*     */   private EPIMSWWPRTBASE epimsAbr;
/*     */   private static final String CREATEACTION_NAME = "CREPWAITINGQUEUE";
/*     */   private static final String EPWQ_TYPE = "EPWAITINGQUEUE";
/*     */   public static final String EPFEATURECODE = "EPFEATURECODE";
/*     */   public static final String EPMACHTYPE = "EPMACHTYPE";
/*     */   public static final String EPMODELATR = "EPMODELATR";
/*     */   public static final String EPONEID = "EPONEID";
/*     */   public static final String EPONET = "EPONET";
/*     */   public static final String EPSALESORG = "EPSALESORG";
/*     */   public static final String EPSTATUS = "EPSTATUS";
/*     */   public static final String EPWAITEID = "EPWAITEID";
/*     */   public static final String EPWAITET = "EPWAITET";
/*  50 */   private Hashtable updateTbl = new Hashtable<>();
/*  51 */   private CreateActionItem cai = null;
/*  52 */   private EntityItem[] wgArray = new EntityItem[1];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EPWQGenerator(EPIMSWWPRTBASE paramEPIMSWWPRTBASE) {
/*  72 */     this.epimsAbr = paramEPIMSWWPRTBASE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAttribute(String paramString1, String paramString2) {
/*  82 */     this.updateTbl.put(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/*  90 */     this.updateTbl.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init() throws SQLException, MiddlewareException {
/*  97 */     if (this.cai == null) {
/*  98 */       Profile profile = this.epimsAbr.getProfile();
/*  99 */       this.cai = new CreateActionItem(null, this.epimsAbr.getDB(), profile, "CREPWAITINGQUEUE");
/* 100 */       this.wgArray[0] = new EntityItem(null, profile, "WG", profile.getWGID());
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
/*     */   public void createEntity() throws SQLException, MiddlewareException, MiddlewareRequestException, EANBusinessRuleException, MiddlewareShutdownInProgressException, RemoteException {
/* 115 */     Profile profile = this.epimsAbr.getProfile();
/*     */     
/* 117 */     init();
/*     */     
/* 119 */     EntityList entityList = new EntityList(this.epimsAbr.getDB(), profile, this.cai, this.wgArray);
/*     */     
/* 121 */     EntityGroup entityGroup = entityList.getEntityGroup("EPWAITINGQUEUE");
/* 122 */     if (entityGroup.getEntityItemCount() == 1) {
/*     */       
/* 124 */       StringBuffer stringBuffer = new StringBuffer();
/*     */       
/* 126 */       EntityItem entityItem = entityGroup.getEntityItem(0);
/*     */ 
/*     */       
/* 129 */       for (Enumeration<String> enumeration = this.updateTbl.keys(); enumeration.hasMoreElements(); ) {
/*     */         
/* 131 */         String str1 = enumeration.nextElement();
/* 132 */         String str2 = (String)this.updateTbl.get(str1);
/* 133 */         setText(entityItem, str1, str2);
/* 134 */         stringBuffer.append(str1 + ":" + str2 + " ");
/*     */       } 
/*     */ 
/*     */       
/* 138 */       entityItem.commit(this.epimsAbr.getDB(), null);
/*     */       
/* 140 */       this.epimsAbr.addDebug("EPWQGenerator.createEntity() created Entity: " + entityItem.getKey() + " for " + stringBuffer);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 145 */       this.epimsAbr.getDB().debug(0, "EPWQGenerator.createEntity() ERROR: Can not create EPWAITINGQUEUE entity");
/* 146 */       this.epimsAbr.addError("EPWQGenerator.createEntity() ERROR: Can not create EPWAITINGQUEUE entity");
/*     */     } 
/* 148 */     entityList.dereference();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setText(EntityItem paramEntityItem, String paramString1, String paramString2) throws MiddlewareRequestException, EANBusinessRuleException {
/* 188 */     EANAttribute eANAttribute = createAttr(paramEntityItem, paramString1);
/* 189 */     if (eANAttribute != null)
/*     */     {
/* 191 */       eANAttribute.put(paramString2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EANAttribute createAttr(EntityItem paramEntityItem, String paramString) throws MiddlewareRequestException {
/*     */     BlobAttribute blobAttribute;
/* 203 */     TextAttribute textAttribute = null;
/* 204 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/* 205 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString);
/* 206 */     if (eANMetaAttribute == null)
/*     */     
/* 208 */     { this.epimsAbr.addError("EPWQGenerator.createAttr() MetaAttribute cannot be found to Create EPWAITINGQUEUE." + paramString);
/* 209 */       this.epimsAbr.getDB().debug(0, "EPWQGenerator.createAttr() MetaAttribute cannot be found to Create EPWAITINGQUEUE." + paramString); }
/*     */     else
/*     */     { TextAttribute textAttribute1; SingleFlagAttribute singleFlagAttribute; BlobAttribute blobAttribute1;
/* 212 */       switch (eANMetaAttribute.getAttributeType().charAt(0))
/*     */       
/*     */       { 
/*     */         case 'T':
/* 216 */           textAttribute1 = new TextAttribute((EANDataFoundation)paramEntityItem, null, (MetaTextAttribute)eANMetaAttribute);
/* 217 */           paramEntityItem.putAttribute((EANAttribute)textAttribute1);
/* 218 */           textAttribute = textAttribute1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 241 */           return (EANAttribute)textAttribute;case 'U': singleFlagAttribute = new SingleFlagAttribute((EANDataFoundation)paramEntityItem, null, (MetaSingleFlagAttribute)eANMetaAttribute); paramEntityItem.putAttribute((EANAttribute)singleFlagAttribute); return (EANAttribute)singleFlagAttribute;case 'B': blobAttribute1 = new BlobAttribute((EANDataFoundation)paramEntityItem, null, (MetaBlobAttribute)eANMetaAttribute); paramEntityItem.putAttribute((EANAttribute)blobAttribute1); blobAttribute = blobAttribute1; return (EANAttribute)blobAttribute; }  this.epimsAbr.addError("EPWQGenerator.createAttr() MetaAttribute Type=" + eANMetaAttribute.getAttributeType() + " is not supported yet " + "EPWAITINGQUEUE" + "." + paramString); }  return (EANAttribute)blobAttribute;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\EPWQGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */