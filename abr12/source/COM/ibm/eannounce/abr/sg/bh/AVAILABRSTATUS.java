/*      */ package COM.ibm.eannounce.abr.sg.bh;
/*      */ 
/*      */ import COM.ibm.eannounce.objects.EANEntity;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class AVAILABRSTATUS
/*      */   extends DQABRSTATUS
/*      */ {
/*  113 */   private static final String[] LSEOABRS = new String[] { "ADSABRSTATUS" };
/*  114 */   private static final String[] MODELABRS = new String[] { "ADSABRSTATUS" };
/*  115 */   private static final String[] SVCMODABRS = new String[] { "ADSABRSTATUS" };
/*  116 */   private static final String[] LSEOBDLABRS = new String[] { "ADSABRSTATUS" };
/*  117 */   private static final String[] SWSABRS = new String[] { "ADSABRSTATUS" };
/*      */   
/*  119 */   private static final String[] LSEOEPIMS = new String[] { "EPIMSABRSTATUS" };
/*  120 */   private static final String[] LSEOBLEPIMS = new String[] { "EPIMSABRSTATUS" };
/*      */   
/*  122 */   private static final String[] RFCABRS = new String[] { "RFCABRSTATUS" };
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isVEneeded(String paramString) {
/*  127 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  419 */     if (doR10processing()) {
/*  420 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  422 */       String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/*  423 */       addDebug("nowRFR " + entityItem.getKey() + "  availAnntypeFlag " + str);
/*      */       
/*  425 */       if (str == null) {
/*  426 */         str = "RFA";
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  525 */       doRFR_ADSXML("LSEO");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  531 */       doRFR_ADSXML("LSEOBUNDLE");
/*      */       
/*  533 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "MODELAVAIL", "MODEL");
/*  534 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*  535 */         EntityItem entityItem1 = vector1.elementAt(b1);
/*      */ 
/*      */         
/*  538 */         if (statusIsRFR(entityItem1)) {
/*      */ 
/*      */           
/*  541 */           addDebug("nowRFR: " + entityItem1.getKey());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  550 */           doRFR_ADSXML("MODEL");
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  556 */       vector1.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  562 */       doRFR_ADSXML(entityItem, "SVCMODAVAIL", "SVCMOD");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  568 */       doRFR_ADSXML("MODELCONVERT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  574 */       doRFR_ADSXML("SWPRODSTRUCT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  580 */       doRFR_ADSXML("PRODSTRUCT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  593 */       Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(entityItem, "SWSTMFAVAIL", "SWSPRODSTRUCT");
/*  594 */       for (byte b2 = 0; b2 < vector2.size(); b2++) {
/*  595 */         EntityItem entityItem1 = vector2.elementAt(b2);
/*  596 */         doRFR_ADSXML(entityItem1.getEntityType());
/*      */       } 
/*  598 */       vector2.clear();
/*      */       
/*  600 */       verifySVCSEORFRAndQueue(entityItem);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doRFR_ADSXML(EntityItem paramEntityItem, String paramString1, String paramString2) {
/*  616 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, paramString1, paramString2);
/*  617 */     for (byte b = 0; b < vector.size(); b++) {
/*  618 */       EntityItem entityItem = vector.elementAt(b);
/*  619 */       doRFR_ADSXML(entityItem);
/*      */     } 
/*  621 */     vector.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  725 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/*  727 */     String str1 = getAttributeFlagEnabledValue(entityItem, "AVAILTYPE");
/*  728 */     addDebug(" " + entityItem.getKey() + " type " + str1);
/*      */ 
/*      */     
/*  731 */     if ("146".equals(str1)) {
/*  732 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("ANNOUNCEMENT");
/*  733 */       for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/*  734 */         EntityItem entityItem1 = entityGroup.getEntityItem(b1);
/*  735 */         String str = getAttributeFlagEnabledValue(entityItem1, "ANNTYPE");
/*  736 */         addDebug(" " + entityItem1.getKey() + " type " + str);
/*      */ 
/*      */ 
/*      */         
/*  740 */         if (statusIsFinal(entityItem1, "ANNSTATUS") && 
/*  741 */           domainInRuleList(entityItem1, "XCC_LIST") && 
/*  742 */           "19".equals(str)) {
/*  743 */           addDebug(" " + entityItem1.getKey() + " is Final and New");
/*      */           
/*  745 */           setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", getQueuedValueForItem(entityItem1, "WWPRTABRSTATUS"), entityItem1);
/*      */           
/*  747 */           setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValueForItem(entityItem1, "QSMRPTABRSTATUS"), entityItem1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  756 */     if ("149".equals(str1)) {
/*  757 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("ANNOUNCEMENT");
/*  758 */       for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/*  759 */         EntityItem entityItem1 = entityGroup.getEntityItem(b1);
/*  760 */         String str = getAttributeFlagEnabledValue(entityItem1, "ANNTYPE");
/*  761 */         addDebug(" " + entityItem1.getKey() + "  type " + str);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  766 */         if (statusIsFinal(entityItem1, "ANNSTATUS") && 
/*  767 */           domainInRuleList(entityItem1, "XCC_LIST") && 
/*  768 */           "14".equals(str)) {
/*  769 */           addDebug(" " + entityItem1.getKey() + " is Final and EOL");
/*      */           
/*  771 */           setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValueForItem(entityItem1, "QSMRPTABRSTATUS"), entityItem1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  779 */     String str2 = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/*  780 */     addDebug("nowFinal " + entityItem.getKey() + "  availAnntypeFlag " + str2);
/*      */     
/*  782 */     if (str2 == null) {
/*  783 */       str2 = "RFA";
/*      */     }
/*      */ 
/*      */     
/*  787 */     boolean bool = false;
/*  788 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "MODELAVAIL", "MODEL"); byte b;
/*  789 */     for (b = 0; b < vector.size(); b++) {
/*  790 */       EntityItem entityItem1 = vector.elementAt(b);
/*  791 */       if (isHardwareOrHIPOModel(entityItem1)) {
/*  792 */         bool = true;
/*      */         break;
/*      */       } 
/*      */     } 
/*  796 */     vector.clear();
/*  797 */     if (!bool) {
/*  798 */       Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*  799 */       addDebug("check if has PRODSTRUCT for  " + entityItem.getKey() + " size " + vector1.size());
/*  800 */       if (vector1.size() > 0) {
/*  801 */         bool = true;
/*      */       }
/*  803 */       vector1.clear();
/*      */     } 
/*      */ 
/*      */     
/*  807 */     if ("RFA".equals(str2)) {
/*  808 */       b = 0;
/*  809 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/*  810 */       addDebug("nowFinal " + entityItem.getKey() + "  annVct " + vector1.size());
/*  811 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*  812 */         EntityItem entityItem1 = vector1.elementAt(b1);
/*  813 */         addDebug("nowFinal " + entityItem1.getKey());
/*      */         
/*  815 */         if (statusIsFinal(entityItem1, "ANNSTATUS")) {
/*      */           
/*  817 */           String str = getAttributeFlagEnabledValue(entityItem1, "ANNTYPE");
/*  818 */           if (bool && isQsmANNTYPE(str)) {
/*  819 */             setFlagValue(this.m_elist.getProfile(), "QSMCREFABRSTATUS", getQueuedValueForItem(entityItem1, "QSMCREFABRSTATUS"), entityItem1);
/*  820 */             setFlagValue(this.m_elist.getProfile(), "QSMFULLABRSTATUS", getQueuedValueForItem(entityItem1, "QSMFULLABRSTATUS"), entityItem1);
/*      */           } 
/*  822 */           b = 1;
/*      */         } 
/*      */       } 
/*      */       
/*  826 */       addDebug("nowFinal hasFinalAnn " + b);
/*  827 */       if (b != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  833 */         verifyFinalAndQueue(entityItem, "LSEOAVAIL", "LSEO", LSEOEPIMS);
/*  834 */         verifyFinalAndQueue(entityItem, "LSEOBUNDLEAVAIL", "LSEOBUNDLE", LSEOBLEPIMS);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  845 */     verifyFinalAndQueue(entityItem, "LSEOAVAIL", "LSEO", LSEOABRS);
/*      */ 
/*      */ 
/*      */     
/*  849 */     verifyFinalAndQueue(entityItem, "LSEOBUNDLEAVAIL", "LSEOBUNDLE", LSEOBDLABRS);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  863 */     verifyModelFinalAndQueue(entityItem, MODELABRS);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  868 */     verifyFinalAndQueue(entityItem, "SVCMODAVAIL", "SVCMOD", SVCMODABRS);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  873 */     verifyFinalAndQueue(entityItem, "SWPRODSTRUCTAVAIL", "SWPRODSTRUCT", MODELABRS);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  879 */     verifyFinalAndQueue(entityItem, "OOFAVAIL", "PRODSTRUCT", MODELABRS);
/*  880 */     verifyFinalAndQueue(entityItem, "OOFAVAIL", "PRODSTRUCT", RFCABRS);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  885 */     verifySVCSEOFinalAndQueue(entityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  890 */     verifyFinalAndQueue(entityItem, "MODELCONVERTAVAIL", "MODELCONVERT", MODELABRS);
/*  891 */     verifyFinalAndQueue(entityItem, "MODELCONVERTAVAIL", "MODELCONVERT", RFCABRS);
/*      */     
/*  893 */     verifyFinalAndQueue(entityItem, "SWSTMFAVAIL", "SWSPRODSTRUCT", SWSABRS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void verifyFinalAndQueue(EntityItem paramEntityItem, String paramString1, String paramString2, String[] paramArrayOfString) {
/*  908 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, paramString1, paramString2);
/*  909 */     for (byte b = 0; b < vector.size(); b++) {
/*  910 */       EntityItem entityItem = vector.elementAt(b);
/*  911 */       if (statusIsFinal(entityItem)) {
/*  912 */         for (byte b1 = 0; b1 < paramArrayOfString.length; b1++) {
/*  913 */           setFlagValue(this.m_elist.getProfile(), paramArrayOfString[b1], getQueuedValueForItem(entityItem, paramArrayOfString[b1]), entityItem);
/*      */         }
/*      */       }
/*      */     } 
/*  917 */     vector.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void verifyModelFinalAndQueue(EntityItem paramEntityItem, String[] paramArrayOfString) throws MiddlewareRequestException, MiddlewareException, SQLException {
/*  951 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELAVAIL", "MODEL");
/*  952 */     for (byte b = 0; b < vector.size(); b++) {
/*  953 */       EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */ 
/*      */       
/*  957 */       addDebug("verifyModelFinalAndQueue " + entityItem.getKey());
/*      */       
/*  959 */       if (statusIsFinal(entityItem)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  972 */         for (byte b1 = 0; b1 < paramArrayOfString.length; b1++)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  977 */           setSinceFirstFinal(entityItem, paramArrayOfString[b1]);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  984 */         if (isHardwareOrHIPOModel(entityItem)) {
/*  985 */           setRFCSinceFirstFinal(entityItem, "RFCABRSTATUS");
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  992 */     vector.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void verifySVCSEOFinalAndQueue(EntityItem paramEntityItem) {
/* 1008 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCSEOAVAIL", "SVCSEO");
/* 1009 */     for (byte b = 0; b < vector.size(); b++) {
/* 1010 */       EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */       
/* 1013 */       if (statusIsFinal(entityItem)) {
/* 1014 */         Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "SVCMODSVCSEO", "SVCMOD");
/* 1015 */         addDebug("verifySVCSEOFinalAndQueue " + entityItem.getKey() + " svcmodVct " + vector1.size());
/* 1016 */         for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 1017 */           EntityItem entityItem1 = vector1.elementAt(b1);
/*      */ 
/*      */           
/* 1020 */           if (statusIsFinal(entityItem1))
/*      */           {
/*      */             
/* 1023 */             setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", 
/* 1024 */                 getQueuedValueForItem(entityItem1, "ADSABRSTATUS"), entityItem1);
/*      */           }
/*      */         } 
/* 1027 */         vector1.clear();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1032 */     vector.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void verifySVCSEORFRAndQueue(EntityItem paramEntityItem) {
/* 1051 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCSEOAVAIL", "SVCSEO");
/* 1052 */     for (byte b = 0; b < vector.size(); b++) {
/* 1053 */       EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */       
/* 1056 */       if (statusIsRFR(entityItem)) {
/* 1057 */         Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "SVCMODSVCSEO", "SVCMOD");
/* 1058 */         addDebug("verifySVCSEORFRAndQueue " + entityItem.getKey() + " svcmodVct " + vector1.size());
/* 1059 */         for (byte b1 = 0; b1 < vector1.size(); b1++)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 1064 */           doRFR_ADSXML(vector1.elementAt(b1));
/*      */         }
/* 1066 */         vector1.clear();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1071 */     vector.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/* 1113 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem, "AVAILTYPE");
/* 1114 */     String str2 = PokUtils.getAttributeFlagValue(paramEntityItem, "AVAILANNTYPE");
/*      */     
/* 1116 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "AVAILANNA", "ANNOUNCEMENT");
/* 1117 */     addDebug(paramEntityItem.getKey() + " availtype " + str1 + " availAnntypeFlag " + str2 + " annVct " + vector.size());
/* 1118 */     boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1123 */     if (str2 == null) {
/* 1124 */       str2 = "RFA";
/*      */     }
/*      */     
/* 1127 */     if ("RFA".equals(str2) || "EPIC".equals(str2)) {
/* 1128 */       if (vector.size() != 1) {
/*      */         
/* 1130 */         String str = "MUST_BE_IN_ONLY1_ERR";
/* 1131 */         if (vector.size() == 0)
/*      */         {
/* 1133 */           str = "MUST_BE_IN_ERR";
/*      */         }
/*      */         
/* 1136 */         this.args[0] = "";
/* 1137 */         this.args[1] = this.m_elist.getEntityGroup("ANNOUNCEMENT").getLongDescription();
/* 1138 */         createMessage(4, str, this.args);
/* 1139 */         vector.clear();
/*      */         return;
/*      */       } 
/*      */       byte b;
/* 1143 */       for (b = 0; b < vector.size(); b++) {
/* 1144 */         EntityItem entityItem = vector.elementAt(b);
/* 1145 */         String str = PokUtils.getAttributeFlagValue(entityItem, "ANNSTATUS");
/* 1146 */         bool = (bool || "0020".equals(str)) ? true : false;
/* 1147 */         addDebug(entityItem.getKey() + " annstatus " + str + " elevatemsg " + bool);
/*      */       } 
/*      */       
/* 1150 */       for (b = 0; b < vector.size(); b++) {
/* 1151 */         EntityItem entityItem = vector.elementAt(b);
/*      */         
/* 1153 */         if ("146".equals(str1) || "171".equals(str1)) {
/* 1154 */           doPlannedAvailChecks(str1, paramEntityItem, entityItem, bool, paramString);
/* 1155 */         } else if ("143".equals(str1)) {
/* 1156 */           doFirstOrderAvailChecks(paramEntityItem, entityItem, bool, paramString);
/* 1157 */         } else if ("149".equals(str1) || "172".equals(str1)) {
/* 1158 */           doLastOrderAvailChecks(str1, paramEntityItem, entityItem, bool, paramString);
/* 1159 */         } else if ("151".equals(str1)) {
/* 1160 */           doEOSAvailChecks(paramEntityItem, entityItem, bool, paramString);
/* 1161 */         } else if ("200".equals(str1)) {
/* 1162 */           doEOMAvailChecks(paramEntityItem, entityItem, bool, paramString);
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1169 */       if (!checkAvailAnnType(paramEntityItem, vector, 4)) {
/* 1170 */         vector.clear();
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1175 */       if ("146".equals(str1) || "171".equals(str1)) {
/* 1176 */         doPlannedAvailChecks(str1, paramEntityItem, (EntityItem)null, bool, paramString);
/* 1177 */       } else if ("143".equals(str1)) {
/* 1178 */         doFirstOrderAvailChecks(paramEntityItem, (EntityItem)null, bool, paramString);
/* 1179 */       } else if ("149".equals(str1) || "172".equals(str1)) {
/* 1180 */         doLastOrderAvailChecks(str1, paramEntityItem, (EntityItem)null, bool, paramString);
/* 1181 */       } else if ("151".equals(str1)) {
/* 1182 */         doEOSAvailChecks(paramEntityItem, (EntityItem)null, bool, paramString);
/* 1183 */       } else if ("200".equals(str1)) {
/* 1184 */         doEOMAvailChecks(paramEntityItem, (EntityItem)null, bool, paramString);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doPlannedAvailChecks(String paramString1, EntityItem paramEntityItem1, EntityItem paramEntityItem2, boolean paramBoolean, String paramString2) throws SQLException, MiddlewareException {
/* 1278 */     if (paramEntityItem2 != null) {
/* 1279 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem2, "ANNTYPE");
/* 1280 */       addDebug("doPlannedAvailChecks " + paramEntityItem2.getKey() + " anntype " + str);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1285 */       if (!"19".equals(str)) {
/*      */         
/* 1287 */         this.args[0] = "";
/* 1288 */         this.args[1] = getLD_Value(paramEntityItem2, "ANNTYPE");
/* 1289 */         this.args[2] = getLD_NDN(paramEntityItem2);
/* 1290 */         createMessage(4, "MUST_NOT_BE_IN_THIS_ERR", this.args);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } else {
/* 1295 */       addDebug("doPlannedAvailChecks no ANNOUNCEMENT");
/*      */     } 
/*      */     
/* 1298 */     int i = getCheck_W_W_E(paramString2);
/* 1299 */     if (paramBoolean) {
/* 1300 */       i = 4;
/*      */     }
/* 1302 */     if ("146".equals(paramString1)) {
/* 1303 */       addHeading(2, paramEntityItem1.getEntityGroup().getLongDescription() + " Planned Avail Checks:");
/* 1304 */     } else if ("171".equals(paramString1)) {
/* 1305 */       addHeading(2, paramEntityItem1.getEntityGroup().getLongDescription() + " MES Planned Avail Checks:");
/*      */     } 
/*      */     
/* 1308 */     ArrayList arrayList = new ArrayList();
/* 1309 */     getCountriesAsList(paramEntityItem1, arrayList, i);
/*      */     
/* 1311 */     if (paramEntityItem2 != null) {
/*      */ 
/*      */       
/* 1314 */       checkDateStatus(paramEntityItem1, paramEntityItem2, "ANNDATE", false, i, i, 1);
/*      */       
/* 1316 */       checkCountryList(paramEntityItem2, arrayList, i);
/*      */     } 
/*      */ 
/*      */     
/* 1320 */     checkNewOfferings(paramEntityItem1, arrayList, i);
/*      */ 
/*      */     
/* 1323 */     arrayList.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkNewOfferings(EntityItem paramEntityItem, ArrayList paramArrayList, int paramInt) throws SQLException, MiddlewareException {
/* 1338 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("MODEL");
/* 1339 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */     
/* 1341 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELAVAIL", "MODEL"); byte b;
/* 1342 */     for (b = 0; b < vector.size(); b++) {
/* 1343 */       EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1351 */       checkDateStatus(paramEntityItem, entityItem, new String[] { "ANNDATE" }, true, new int[] { 4, paramInt }, 4, 1);
/*      */     } 
/*      */     
/* 1354 */     vector.clear();
/*      */ 
/*      */     
/* 1357 */     entityGroup = this.m_elist.getEntityGroup("SVCMOD");
/* 1358 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */     
/* 1360 */     vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCMODAVAIL", "SVCMOD");
/* 1361 */     for (b = 0; b < vector.size(); b++) {
/* 1362 */       EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1367 */       checkDateStatus(paramEntityItem, entityItem, "ANNDATE", true, paramInt, 4, 1);
/*      */     } 
/* 1369 */     vector.clear();
/*      */ 
/*      */     
/* 1372 */     entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/* 1373 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */     
/* 1375 */     vector = PokUtils.getAllLinkedEntities(paramEntityItem, "OOFAVAIL", "PRODSTRUCT");
/* 1376 */     for (b = 0; b < vector.size(); b++) {
/* 1377 */       EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1385 */       checkDateStatus(paramEntityItem, entityItem, new String[] { "ANNDATE" }, true, 4, 4, 1);
/*      */     } 
/*      */     
/* 1388 */     vector.clear();
/*      */     
/* 1390 */     entityGroup = this.m_elist.getEntityGroup("FEATURE");
/* 1391 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 1392 */     vector = getFeatFromAvailRelator(this.m_elist.getEntityGroup("OOFAVAIL"), "PRODSTRUCT", "FEATURE");
/* 1393 */     for (b = 0; b < vector.size(); b++) {
/* 1394 */       EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */       
/* 1397 */       checkCountryList(entityItem, paramArrayList, paramInt);
/*      */     } 
/* 1399 */     vector.clear();
/*      */ 
/*      */     
/* 1402 */     entityGroup = this.m_elist.getEntityGroup("SWPRODSTRUCT");
/* 1403 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */     
/* 1405 */     vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SWPRODSTRUCTAVAIL", "SWPRODSTRUCT");
/* 1406 */     for (b = 0; b < vector.size(); b++) {
/* 1407 */       EntityItem entityItem = vector.elementAt(b);
/*      */       
/* 1409 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 4);
/*      */     } 
/* 1411 */     vector.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1438 */     entityGroup = this.m_elist.getEntityGroup("LSEO");
/* 1439 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 1440 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1441 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */       
/* 1444 */       checkCountryList(entityItem, paramArrayList, paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1449 */       checkDateStatus(paramEntityItem, entityItem, "LSEOPUBDATEMTRGT", true, paramInt, 4, 1);
/*      */     } 
/*      */ 
/*      */     
/* 1453 */     entityGroup = this.m_elist.getEntityGroup("LSEOBUNDLE");
/* 1454 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 1455 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1456 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */       
/* 1459 */       checkCountryList(entityItem, paramArrayList, paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1464 */       checkDateStatus(paramEntityItem, entityItem, "BUNDLPUBDATEMTRGT", true, paramInt, 4, 1);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1484 */     entityGroup = this.m_elist.getEntityGroup("MODELCONVERT");
/* 1485 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 1486 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1487 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1495 */       checkDateStatus(paramEntityItem, entityItem, new String[] { "ANNDATE" }, true, paramInt, 4, 1);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1502 */     entityGroup = this.m_elist.getEntityGroup("SWSPRODSTRUCT");
/* 1503 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */     
/* 1505 */     vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SWSTMFAVAIL", "SWSPRODSTRUCT");
/* 1506 */     for (b = 0; b < vector.size(); b++) {
/* 1507 */       EntityItem entityItem = vector.elementAt(b);
/*      */       
/* 1509 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 4);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Vector getFeatFromAvailRelator(EntityGroup paramEntityGroup, String paramString1, String paramString2) {
/* 1527 */     Vector vector = new Vector(1);
/* 1528 */     if (paramEntityGroup != null) {
/* 1529 */       for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*      */         
/* 1531 */         EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 1532 */         getFeatFromAvailRelator(entityItem, paramString1, paramString2, vector);
/*      */       } 
/*      */     }
/*      */     
/* 1536 */     return vector;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void getFeatFromAvailRelator(EntityItem paramEntityItem, String paramString1, String paramString2, Vector<EANEntity> paramVector) {
/* 1541 */     if (paramEntityItem == null) {
/*      */       return;
/*      */     }
/*      */     byte b;
/* 1545 */     for (b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/*      */       
/* 1547 */       EANEntity eANEntity = paramEntityItem.getUpLink(b);
/* 1548 */       if (eANEntity.getEntityType().equals(paramString1)) {
/*      */         byte b1;
/*      */         
/* 1551 */         for (b1 = 0; b1 < eANEntity.getUpLinkCount(); b1++) {
/*      */           
/* 1553 */           EANEntity eANEntity1 = eANEntity.getUpLink(b1);
/* 1554 */           if (eANEntity1.getEntityType().equals(paramString2) && !paramVector.contains(eANEntity1)) {
/* 1555 */             paramVector.addElement(eANEntity1);
/*      */           }
/*      */         } 
/*      */         
/* 1559 */         for (b1 = 0; b1 < eANEntity.getDownLinkCount(); b1++) {
/*      */           
/* 1561 */           EANEntity eANEntity1 = eANEntity.getDownLink(b1);
/* 1562 */           if (eANEntity1.getEntityType().equals(paramString2) && !paramVector.contains(eANEntity1)) {
/* 1563 */             paramVector.addElement(eANEntity1);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1569 */     for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/*      */       
/* 1571 */       EANEntity eANEntity = paramEntityItem.getDownLink(b);
/* 1572 */       if (eANEntity.getEntityType().equals(paramString1)) {
/*      */         byte b1;
/*      */         
/* 1575 */         for (b1 = 0; b1 < eANEntity.getDownLinkCount(); b1++) {
/*      */           
/* 1577 */           EANEntity eANEntity1 = eANEntity.getDownLink(b1);
/* 1578 */           if (eANEntity1.getEntityType().equals(paramString2) && !paramVector.contains(eANEntity1)) {
/* 1579 */             paramVector.addElement(eANEntity1);
/*      */           }
/*      */         } 
/* 1582 */         for (b1 = 0; b1 < eANEntity.getUpLinkCount(); b1++) {
/*      */           
/* 1584 */           EANEntity eANEntity1 = eANEntity.getUpLink(b1);
/* 1585 */           if (eANEntity1.getEntityType().equals(paramString2) && !paramVector.contains(eANEntity1)) {
/* 1586 */             paramVector.addElement(eANEntity1);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doFirstOrderAvailChecks(EntityItem paramEntityItem1, EntityItem paramEntityItem2, boolean paramBoolean, String paramString) throws SQLException, MiddlewareException {
/* 1655 */     if (paramEntityItem2 != null) {
/* 1656 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem2, "ANNTYPE");
/* 1657 */       addDebug("doFirstOrderAvailChecks " + paramEntityItem2.getKey() + " anntype " + str);
/*      */ 
/*      */       
/* 1660 */       if (!"19".equals(str)) {
/*      */         
/* 1662 */         this.args[0] = "";
/* 1663 */         this.args[1] = getLD_Value(paramEntityItem2, "ANNTYPE");
/* 1664 */         this.args[2] = getLD_NDN(paramEntityItem2);
/* 1665 */         createMessage(4, "MUST_NOT_BE_IN_THIS_ERR", this.args);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } else {
/* 1670 */       addDebug("doFirstOrderAvailChecks  no ANNOUNCEMENT");
/*      */     } 
/*      */     
/* 1673 */     int i = getCheck_W_W_E(paramString);
/* 1674 */     if (paramBoolean) {
/* 1675 */       i = 4;
/*      */     }
/*      */     
/* 1678 */     addHeading(2, paramEntityItem1.getEntityGroup().getLongDescription() + " First Order Avail Checks:");
/* 1679 */     ArrayList arrayList = new ArrayList();
/* 1680 */     getCountriesAsList(paramEntityItem1, arrayList, i);
/*      */     
/* 1682 */     if (paramEntityItem2 != null) {
/*      */ 
/*      */       
/* 1685 */       checkDateStatus(paramEntityItem1, paramEntityItem2, "ANNDATE", false, i, i, 1);
/*      */       
/* 1687 */       checkCountryList(paramEntityItem2, arrayList, i);
/*      */     } 
/*      */ 
/*      */     
/* 1691 */     checkNewOfferings(paramEntityItem1, arrayList, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doLastOrderAvailChecks(String paramString1, EntityItem paramEntityItem1, EntityItem paramEntityItem2, boolean paramBoolean, String paramString2) throws SQLException, MiddlewareException {
/* 1756 */     if (paramEntityItem2 != null) {
/* 1757 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem2, "ANNTYPE");
/* 1758 */       addDebug("doLastOrderAvailChecks " + paramEntityItem2.getKey() + " anntype " + str);
/*      */ 
/*      */ 
/*      */       
/* 1762 */       if (!"14".equals(str)) {
/*      */         
/* 1764 */         this.args[0] = "";
/* 1765 */         this.args[1] = getLD_Value(paramEntityItem2, "ANNTYPE");
/* 1766 */         this.args[2] = getLD_NDN(paramEntityItem2);
/* 1767 */         createMessage(4, "MUST_NOT_BE_IN_THIS_ERR", this.args);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } else {
/* 1772 */       addDebug("doLastOrderAvailChecks no ANNOUNCEMENT");
/*      */     } 
/*      */     
/* 1775 */     int i = getCheck_W_W_E(paramString2);
/* 1776 */     if (paramBoolean)
/*      */     {
/* 1778 */       i = 4;
/*      */     }
/* 1780 */     if ("149".equals(paramString1)) {
/* 1781 */       addHeading(2, paramEntityItem1.getEntityGroup().getLongDescription() + " Last Order Avail Checks:");
/* 1782 */     } else if ("172".equals(paramString1)) {
/* 1783 */       addHeading(2, paramEntityItem1.getEntityGroup().getLongDescription() + " MES Last Order Avail Checks:");
/*      */     } 
/*      */     
/* 1786 */     ArrayList arrayList = new ArrayList();
/* 1787 */     getCountriesAsList(paramEntityItem1, arrayList, i);
/*      */     
/* 1789 */     if (paramEntityItem2 != null) {
/*      */ 
/*      */ 
/*      */       
/* 1793 */       checkDateStatus(paramEntityItem1, paramEntityItem2, "ANNDATE", false, i, i, 1);
/*      */       
/* 1795 */       checkCountryList(paramEntityItem2, arrayList, i);
/*      */     } 
/*      */ 
/*      */     
/* 1799 */     checkEOLOfferings(paramEntityItem1, i, arrayList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doEOSAvailChecks(EntityItem paramEntityItem1, EntityItem paramEntityItem2, boolean paramBoolean, String paramString) throws SQLException, MiddlewareException {
/* 1866 */     if (paramEntityItem2 != null) {
/* 1867 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem2, "ANNTYPE");
/* 1868 */       addDebug("doEOSAvailChecks " + paramEntityItem2.getKey() + " anntype " + str);
/*      */ 
/*      */ 
/*      */       
/* 1872 */       if (!"13".equals(str)) {
/*      */         
/* 1874 */         this.args[0] = "";
/* 1875 */         this.args[1] = getLD_Value(paramEntityItem2, "ANNTYPE");
/* 1876 */         this.args[2] = getLD_NDN(paramEntityItem2);
/* 1877 */         createMessage(4, "MUST_NOT_BE_IN_THIS_ERR", this.args);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } else {
/* 1882 */       addDebug("doEOSAvailChecks NO ANNOUNCEMENT");
/*      */     } 
/*      */ 
/*      */     
/* 1886 */     int i = getCheck_W_W_E(paramString);
/* 1887 */     if (paramBoolean) {
/* 1888 */       i = 4;
/*      */     }
/* 1890 */     addHeading(2, paramEntityItem1.getEntityGroup().getLongDescription() + " End of Service Avail Checks:");
/*      */     
/* 1892 */     ArrayList arrayList = new ArrayList();
/* 1893 */     getCountriesAsList(paramEntityItem1, arrayList, i);
/*      */     
/* 1895 */     if (paramEntityItem2 != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1900 */       checkDateStatus(paramEntityItem1, paramEntityItem2, "ANNDATE", false, i, i, 1);
/* 1901 */       checkCountryList(paramEntityItem2, arrayList, i);
/*      */     } 
/*      */ 
/*      */     
/* 1905 */     checkEOSOfferings(paramEntityItem1, i, arrayList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkEOSOfferings(EntityItem paramEntityItem, int paramInt, ArrayList paramArrayList) throws SQLException, MiddlewareException {
/* 1920 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("MODEL");
/* 1921 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */     
/* 1923 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELAVAIL", "MODEL"); byte b;
/* 1924 */     for (b = 0; b < vector.size(); b++) {
/* 1925 */       EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */       
/* 1928 */       checkDateStatus(paramEntityItem, entityItem, "WTHDRWEFFCTVDATE", true, paramInt, 4, 1);
/*      */     } 
/* 1930 */     vector.clear();
/*      */ 
/*      */     
/* 1933 */     entityGroup = this.m_elist.getEntityGroup("SVCMOD");
/* 1934 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */     
/* 1936 */     vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCMODAVAIL", "SVCMOD");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1941 */     for (b = 0; b < vector.size(); b++) {
/* 1942 */       EntityItem entityItem = vector.elementAt(b);
/*      */       
/* 1944 */       this.args[0] = getLD_NDN(entityItem);
/* 1945 */       this.args[1] = getLD_Value(paramEntityItem, "AVAILTYPE");
/* 1946 */       this.args[2] = paramEntityItem.getEntityGroup().getLongDescription();
/* 1947 */       createMessage(4, "MUST_NOT_BE_IN_THIS_ERR", this.args);
/*      */     } 
/* 1949 */     vector.clear();
/*      */ 
/*      */     
/* 1952 */     entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/* 1953 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */     
/* 1955 */     vector = PokUtils.getAllLinkedEntities(paramEntityItem, "OOFAVAIL", "PRODSTRUCT");
/* 1956 */     for (b = 0; b < vector.size(); b++) {
/* 1957 */       EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */       
/* 1960 */       checkDateStatus(paramEntityItem, entityItem, "WTHDRWEFFCTVDATE", true, paramInt, 4, 1);
/*      */     } 
/* 1962 */     vector.clear();
/*      */ 
/*      */     
/* 1965 */     entityGroup = this.m_elist.getEntityGroup("FEATURE");
/* 1966 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 1967 */     vector = getFeatFromAvailRelator(this.m_elist.getEntityGroup("OOFAVAIL"), "PRODSTRUCT", "FEATURE");
/* 1968 */     for (b = 0; b < vector.size(); b++) {
/* 1969 */       EntityItem entityItem1 = vector.elementAt(b);
/* 1970 */       EntityItem entityItem2 = getDownLinkEntityItem(entityItem1, "PRODSTRUCT");
/*      */       
/* 1972 */       checkCanNotBeEarlier3(entityItem2, paramEntityItem, "EFFECTIVEDATE", entityItem1, "WITHDRAWDATEEFF_T", paramInt);
/*      */       
/* 1974 */       checkCountryList(entityItem1, paramArrayList, paramInt);
/*      */     } 
/* 1976 */     vector.clear();
/*      */ 
/*      */     
/* 1979 */     entityGroup = this.m_elist.getEntityGroup("SWPRODSTRUCT");
/* 1980 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */     
/* 1982 */     vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SWPRODSTRUCTAVAIL", "SWPRODSTRUCT");
/* 1983 */     for (b = 0; b < vector.size(); b++) {
/* 1984 */       EntityItem entityItem = vector.elementAt(b);
/*      */       
/* 1986 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 4);
/*      */     } 
/*      */ 
/*      */     
/* 1990 */     entityGroup = this.m_elist.getEntityGroup("SWFEATURE");
/* 1991 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 1992 */     vector = getFeatFromAvailRelator(this.m_elist.getEntityGroup("SWPRODSTRUCTAVAIL"), "SWPRODSTRUCT", "SWFEATURE");
/* 1993 */     for (b = 0; b < vector.size(); b++) {
/* 1994 */       EntityItem entityItem1 = vector.elementAt(b);
/* 1995 */       EntityItem entityItem2 = getDownLinkEntityItem(entityItem1, "SWPRODSTRUCT");
/*      */       
/* 1997 */       checkCanNotBeEarlier3(entityItem2, paramEntityItem, "EFFECTIVEDATE", entityItem1, "WITHDRAWDATEEFF_T", paramInt);
/*      */     } 
/* 1999 */     vector.clear();
/*      */ 
/*      */     
/* 2002 */     entityGroup = this.m_elist.getEntityGroup("LSEO");
/* 2003 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 2004 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2005 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/* 2007 */       checkCountryList(entityItem, paramArrayList, paramInt);
/*      */ 
/*      */       
/* 2010 */       checkDateStatus(paramEntityItem, entityItem, "LSEOUNPUBDATEMTRGT", true, paramInt, 4, 1);
/*      */     } 
/*      */ 
/*      */     
/* 2014 */     entityGroup = this.m_elist.getEntityGroup("LSEOBUNDLE");
/* 2015 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 2016 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2017 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/* 2019 */       checkCountryList(entityItem, paramArrayList, paramInt);
/*      */ 
/*      */       
/* 2022 */       checkDateStatus(paramEntityItem, entityItem, "BUNDLUNPUBDATEMTRGT", true, paramInt, 4, 1);
/*      */     } 
/*      */ 
/*      */     
/* 2026 */     entityGroup = this.m_elist.getEntityGroup("MODELCONVERT");
/* 2027 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 2028 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2029 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */       
/* 2032 */       checkDateStatus(paramEntityItem, entityItem, "WTHDRWEFFCTVDATE", true, paramInt, 4, 1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkCanNotBeEarlier3(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString1, EntityItem paramEntityItem3, String paramString2, int paramInt) throws SQLException, MiddlewareException {
/* 2056 */     String str1 = getAttrValueAndCheckLvl(paramEntityItem2, paramString1, paramInt);
/* 2057 */     String str2 = getAttrValueAndCheckLvl(paramEntityItem3, paramString2, paramInt);
/* 2058 */     addDebug("checkCanNotBeEarlier3 " + ((paramEntityItem1 == null) ? "" : paramEntityItem1.getKey()) + " " + paramEntityItem2
/* 2059 */         .getKey() + " " + paramString1 + ":" + str1 + " " + paramEntityItem3.getKey() + " " + paramString2 + ":" + str2);
/*      */     
/* 2061 */     boolean bool = checkDates(str1, str2, 1);
/* 2062 */     if (bool) {
/* 2063 */       if (str1.length() > 0 && !Character.isDigit(str1.charAt(0))) {
/* 2064 */         bool = false;
/*      */       }
/* 2066 */       if (str2.length() > 0 && !Character.isDigit(str2.charAt(0))) {
/* 2067 */         bool = false;
/*      */       }
/*      */     } 
/* 2070 */     if (!bool) {
/*      */ 
/*      */       
/* 2073 */       String str3 = "";
/* 2074 */       if (paramEntityItem1 != null) {
/* 2075 */         str3 = getLD_NDN(paramEntityItem1) + " ";
/*      */       }
/*      */       
/* 2078 */       String str4 = getLD_NDN(paramEntityItem2);
/* 2079 */       if (paramEntityItem2.getEntityType().equals(getEntityType()) && paramEntityItem2
/* 2080 */         .getEntityID() == getEntityID())
/*      */       {
/* 2082 */         str4 = "";
/*      */       }
/* 2084 */       this.args[0] = str4;
/* 2085 */       this.args[1] = getLD_Value(paramEntityItem2, paramString1);
/*      */       
/* 2087 */       if (paramEntityItem3.getEntityType().equals(getEntityType()) && paramEntityItem3.getEntityID() == getEntityID()) {
/* 2088 */         this.args[2] = paramEntityItem3.getEntityGroup().getLongDescription();
/*      */       } else {
/* 2090 */         this.args[2] = getLD_NDN(paramEntityItem3);
/*      */       } 
/* 2092 */       this.args[3] = str3;
/* 2093 */       this.args[4] = getLD_Value(paramEntityItem3, paramString2);
/* 2094 */       createMessage(paramInt, "CANNOT_BE_EARLIER_ERR3", this.args);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doEOMAvailChecks(EntityItem paramEntityItem1, EntityItem paramEntityItem2, boolean paramBoolean, String paramString) throws SQLException, MiddlewareException {
/* 2162 */     if (paramEntityItem2 != null) {
/* 2163 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem2, "ANNTYPE");
/* 2164 */       addDebug("doEOMAvailChecks " + paramEntityItem2.getKey() + " anntype " + str);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2169 */       if (!"14".equals(str)) {
/*      */         
/* 2171 */         this.args[0] = "";
/* 2172 */         this.args[1] = getLD_Value(paramEntityItem2, "ANNTYPE");
/* 2173 */         this.args[2] = getLD_NDN(paramEntityItem2);
/* 2174 */         createMessage(4, "MUST_NOT_BE_IN_THIS_ERR", this.args);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } else {
/* 2179 */       addDebug("doEOMAvailChecks NO ANNOUNCEMENT");
/*      */     } 
/*      */ 
/*      */     
/* 2183 */     int i = getCheck_W_W_E(paramString);
/* 2184 */     if (paramBoolean) {
/* 2185 */       i = 4;
/*      */     }
/* 2187 */     addHeading(2, paramEntityItem1.getEntityGroup().getLongDescription() + " End of Marketing Avail Checks:");
/*      */     
/* 2189 */     ArrayList arrayList = new ArrayList();
/* 2190 */     getCountriesAsList(paramEntityItem1, arrayList, i);
/*      */     
/* 2192 */     if (paramEntityItem2 != null) {
/*      */ 
/*      */ 
/*      */       
/* 2196 */       checkDateStatus(paramEntityItem1, paramEntityItem2, "ANNDATE", false, i, i, 1);
/*      */       
/* 2198 */       checkCountryList(paramEntityItem2, arrayList, i);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2203 */     checkEOMOfferings(paramEntityItem1, i, arrayList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkEOMOfferings(EntityItem paramEntityItem, int paramInt, ArrayList paramArrayList) throws SQLException, MiddlewareException {
/* 2217 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("MODEL");
/* 2218 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */     
/* 2220 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELAVAIL", "MODEL"); byte b;
/* 2221 */     for (b = 0; b < vector.size(); b++) {
/* 2222 */       EntityItem entityItem = vector.elementAt(b);
/*      */       
/* 2224 */       checkDateStatus(paramEntityItem, entityItem, "WITHDRAWDATE", true, paramInt, 4, 2);
/*      */     } 
/* 2226 */     vector.clear();
/*      */ 
/*      */     
/* 2229 */     entityGroup = this.m_elist.getEntityGroup("SVCMOD");
/* 2230 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */     
/* 2232 */     vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCMODAVAIL", "SVCMOD");
/* 2233 */     for (b = 0; b < vector.size(); b++) {
/* 2234 */       EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */       
/* 2237 */       checkDateStatus(paramEntityItem, entityItem, "WITHDRAWDATE", true, paramInt, 4, 2);
/*      */     } 
/*      */     
/* 2240 */     vector.clear();
/*      */ 
/*      */     
/* 2243 */     entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/* 2244 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */     
/* 2246 */     vector = PokUtils.getAllLinkedEntities(paramEntityItem, "OOFAVAIL", "PRODSTRUCT");
/* 2247 */     for (b = 0; b < vector.size(); b++) {
/* 2248 */       EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */       
/* 2251 */       checkDateStatus(paramEntityItem, entityItem, "WITHDRAWDATE", true, paramInt, 4, 2);
/*      */     } 
/* 2253 */     vector.clear();
/*      */ 
/*      */     
/* 2256 */     entityGroup = this.m_elist.getEntityGroup("FEATURE");
/* 2257 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 2258 */     vector = getFeatFromAvailRelator(this.m_elist.getEntityGroup("OOFAVAIL"), "PRODSTRUCT", "FEATURE");
/* 2259 */     for (b = 0; b < vector.size(); b++) {
/* 2260 */       EntityItem entityItem1 = vector.elementAt(b);
/* 2261 */       EntityItem entityItem2 = getDownLinkEntityItem(entityItem1, "PRODSTRUCT");
/*      */       
/* 2263 */       checkCanNotBeLater(entityItem2, paramEntityItem, "EFFECTIVEDATE", entityItem1, "WITHDRAWANNDATE_T", paramInt);
/*      */       
/* 2265 */       checkCountryList(entityItem1, paramArrayList, paramInt);
/*      */     } 
/* 2267 */     vector.clear();
/*      */ 
/*      */     
/* 2270 */     entityGroup = this.m_elist.getEntityGroup("SWPRODSTRUCT");
/* 2271 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */     
/* 2273 */     vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SWPRODSTRUCTAVAIL", "SWPRODSTRUCT");
/* 2274 */     for (b = 0; b < vector.size(); b++) {
/* 2275 */       EntityItem entityItem = vector.elementAt(b);
/*      */       
/* 2277 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 4);
/*      */     } 
/*      */ 
/*      */     
/* 2281 */     entityGroup = this.m_elist.getEntityGroup("SWFEATURE");
/* 2282 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 2283 */     vector = getFeatFromAvailRelator(this.m_elist.getEntityGroup("SWPRODSTRUCTAVAIL"), "SWPRODSTRUCT", "SWFEATURE");
/* 2284 */     for (b = 0; b < vector.size(); b++) {
/* 2285 */       EntityItem entityItem1 = vector.elementAt(b);
/* 2286 */       EntityItem entityItem2 = getDownLinkEntityItem(entityItem1, "SWPRODSTRUCT");
/*      */       
/* 2288 */       checkCanNotBeLater(entityItem2, paramEntityItem, "EFFECTIVEDATE", entityItem1, "WITHDRAWANNDATE_T", paramInt);
/*      */     } 
/* 2290 */     vector.clear();
/*      */ 
/*      */     
/* 2293 */     entityGroup = this.m_elist.getEntityGroup("LSEO");
/* 2294 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 2295 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2296 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/* 2298 */       checkCountryList(entityItem, paramArrayList, paramInt);
/*      */ 
/*      */ 
/*      */       
/* 2302 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 4);
/*      */     } 
/*      */ 
/*      */     
/* 2306 */     entityGroup = this.m_elist.getEntityGroup("LSEOBUNDLE");
/* 2307 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 2308 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2309 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/* 2311 */       checkCountryList(entityItem, paramArrayList, paramInt);
/*      */ 
/*      */ 
/*      */       
/* 2315 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 4);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2329 */     entityGroup = this.m_elist.getEntityGroup("MODELCONVERT");
/* 2330 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 2331 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2332 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */       
/* 2335 */       checkDateStatus(paramEntityItem, entityItem, "WITHDRAWDATE", true, paramInt, 4, 2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkEOLOfferings(EntityItem paramEntityItem, int paramInt, ArrayList paramArrayList) throws SQLException, MiddlewareException {
/* 2351 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("MODEL");
/* 2352 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */     
/* 2354 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELAVAIL", "MODEL"); byte b;
/* 2355 */     for (b = 0; b < vector.size(); b++) {
/* 2356 */       EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */ 
/*      */       
/* 2360 */       checkDateStatus(paramEntityItem, entityItem, "WTHDRWEFFCTVDATE", true, paramInt, 4, 2);
/*      */     } 
/* 2362 */     vector.clear();
/*      */ 
/*      */ 
/*      */     
/* 2366 */     entityGroup = this.m_elist.getEntityGroup("SVCMOD");
/* 2367 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */     
/* 2369 */     vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCMODAVAIL", "SVCMOD");
/*      */     
/* 2371 */     for (b = 0; b < vector.size(); b++) {
/* 2372 */       EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2379 */       checkDateStatus(paramEntityItem, entityItem, "WTHDRWEFFCTVDATE", true, paramInt, 4, 2);
/*      */     } 
/*      */     
/* 2382 */     vector.clear();
/*      */ 
/*      */ 
/*      */     
/* 2386 */     entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/* 2387 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */     
/* 2389 */     vector = PokUtils.getAllLinkedEntities(paramEntityItem, "OOFAVAIL", "PRODSTRUCT");
/* 2390 */     for (b = 0; b < vector.size(); b++) {
/* 2391 */       EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */       
/* 2394 */       checkDateStatus(paramEntityItem, entityItem, "WTHDRWEFFCTVDATE", true, paramInt, 4, 2);
/*      */     } 
/* 2396 */     vector.clear();
/*      */ 
/*      */ 
/*      */     
/* 2400 */     entityGroup = this.m_elist.getEntityGroup("FEATURE");
/* 2401 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 2402 */     vector = getFeatFromAvailRelator(this.m_elist.getEntityGroup("OOFAVAIL"), "PRODSTRUCT", "FEATURE");
/* 2403 */     for (b = 0; b < vector.size(); b++) {
/* 2404 */       EntityItem entityItem1 = vector.elementAt(b);
/* 2405 */       EntityItem entityItem2 = getDownLinkEntityItem(entityItem1, "PRODSTRUCT");
/*      */       
/* 2407 */       checkCanNotBeLater(entityItem2, paramEntityItem, "EFFECTIVEDATE", entityItem1, "WITHDRAWDATEEFF_T", paramInt);
/*      */       
/* 2409 */       checkCountryList(entityItem1, paramArrayList, paramInt);
/*      */     } 
/* 2411 */     vector.clear();
/*      */ 
/*      */     
/* 2414 */     entityGroup = this.m_elist.getEntityGroup("SWPRODSTRUCT");
/* 2415 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/*      */     
/* 2417 */     vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SWPRODSTRUCTAVAIL", "SWPRODSTRUCT");
/* 2418 */     for (b = 0; b < vector.size(); b++) {
/* 2419 */       EntityItem entityItem = vector.elementAt(b);
/*      */       
/* 2421 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 4);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2426 */     entityGroup = this.m_elist.getEntityGroup("SWFEATURE");
/* 2427 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 2428 */     vector = getFeatFromAvailRelator(this.m_elist.getEntityGroup("SWPRODSTRUCTAVAIL"), "SWPRODSTRUCT", "SWFEATURE");
/* 2429 */     for (b = 0; b < vector.size(); b++) {
/* 2430 */       EntityItem entityItem1 = vector.elementAt(b);
/* 2431 */       EntityItem entityItem2 = getDownLinkEntityItem(entityItem1, "SWPRODSTRUCT");
/*      */       
/* 2433 */       checkCanNotBeLater(entityItem2, paramEntityItem, "EFFECTIVEDATE", entityItem1, "WITHDRAWDATEEFF_T", paramInt);
/*      */     } 
/* 2435 */     vector.clear();
/*      */ 
/*      */ 
/*      */     
/* 2439 */     entityGroup = this.m_elist.getEntityGroup("LSEO");
/* 2440 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 2441 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2442 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */       
/* 2445 */       checkCountryList(entityItem, paramArrayList, paramInt);
/*      */ 
/*      */       
/* 2448 */       checkDateStatus(paramEntityItem, entityItem, "LSEOUNPUBDATEMTRGT", true, paramInt, 4, 2);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2453 */     entityGroup = this.m_elist.getEntityGroup("LSEOBUNDLE");
/* 2454 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 2455 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2456 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */       
/* 2459 */       checkCountryList(entityItem, paramArrayList, paramInt);
/*      */ 
/*      */       
/* 2462 */       checkDateStatus(paramEntityItem, entityItem, "BUNDLUNPUBDATEMTRGT", true, paramInt, 4, 2);
/*      */     } 
/*      */ 
/*      */     
/* 2466 */     entityGroup = this.m_elist.getEntityGroup("MODELCONVERT");
/* 2467 */     addHeading(3, entityGroup.getLongDescription() + " Checks:");
/* 2468 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2469 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */       
/* 2472 */       checkDateStatus(paramEntityItem, entityItem, "WTHDRWEFFCTVDATE", true, paramInt, 4, 2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkDateStatus(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3) throws SQLException, MiddlewareException {
/* 2494 */     checkDateStatus(paramEntityItem1, paramEntityItem2, new String[] { paramString }, paramBoolean, paramInt1, paramInt2, paramInt3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkDateStatus(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String[] paramArrayOfString, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3) throws SQLException, MiddlewareException {
/* 2514 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 2515 */       if (paramInt3 == 1) {
/* 2516 */         checkCanNotBeEarlier(paramEntityItem1, "EFFECTIVEDATE", paramEntityItem2, paramArrayOfString[b], paramInt1);
/*      */       } else {
/* 2518 */         checkCanNotBeLater(paramEntityItem1, "EFFECTIVEDATE", paramEntityItem2, paramArrayOfString[b], paramInt1);
/*      */       } 
/*      */     } 
/* 2521 */     if (paramBoolean) {
/* 2522 */       checkStatusVsDQ(paramEntityItem2, "STATUS", paramEntityItem1, paramInt2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkDateStatus(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String[] paramArrayOfString, boolean paramBoolean, int[] paramArrayOfint, int paramInt1, int paramInt2) throws SQLException, MiddlewareException {
/* 2529 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 2530 */       if (paramInt2 == 1) {
/* 2531 */         checkCanNotBeEarlier(paramEntityItem1, "EFFECTIVEDATE", paramEntityItem2, paramArrayOfString[b], paramArrayOfint[b]);
/*      */       } else {
/* 2533 */         checkCanNotBeLater(paramEntityItem1, "EFFECTIVEDATE", paramEntityItem2, paramArrayOfString[b], paramArrayOfint[b]);
/*      */       } 
/*      */     } 
/* 2536 */     if (paramBoolean) {
/* 2537 */       checkStatusVsDQ(paramEntityItem2, "STATUS", paramEntityItem1, paramInt1);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkCountryList(EntityItem paramEntityItem, ArrayList<?> paramArrayList, int paramInt) throws SQLException, MiddlewareException {
/* 2551 */     ArrayList arrayList = new ArrayList();
/* 2552 */     getCountriesAsList(paramEntityItem, arrayList, paramInt);
/* 2553 */     if (!arrayList.containsAll(paramArrayList)) {
/* 2554 */       addDebug(paramEntityItem.getKey() + " ctrylist " + arrayList + " availctry " + paramArrayList);
/*      */       
/* 2556 */       this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 2557 */       this.args[1] = getLD_NDN(paramEntityItem);
/* 2558 */       createMessage(paramInt, "COUNTRY_MISMATCH_ERR", this.args);
/*      */     } 
/* 2560 */     arrayList.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 2569 */     return "AVAIL ABR.";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 2581 */     return "1.22";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\AVAILABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */