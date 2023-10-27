/*      */ package COM.ibm.eannounce.abr.psg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.CreateActionItem;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANDataFoundation;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.MetaSingleFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.MetaTextAttribute;
/*      */ import COM.ibm.eannounce.objects.SingleFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.TextAttribute;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.math.BigDecimal;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.TreeMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DDABR
/*      */   extends PokBaseABR
/*      */ {
/*   81 */   private MessageFormat mfOut = null;
/*   82 */   private Object[] mfParms = (Object[])new String[10];
/*   83 */   private ResourceBundle msgs = null;
/*   84 */   private StringBuffer rpt = new StringBuffer();
/*   85 */   private StringBuffer traceSb = new StringBuffer();
/*      */   private static final int GB_VALUE = 1024;
/*   87 */   private static final BigDecimal ONETHOUSAND = new BigDecimal(1000);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*   93 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/*      */     try {
/*   97 */       boolean bool = false;
/*   98 */       start_ABRBuild();
/*   99 */       setReturnCode(0);
/*      */       
/*  101 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, getRootEntityType(), "Navigate");
/*  102 */       Iterator<EANMetaAttribute> iterator = entityGroup.getMetaAttribute().values().iterator();
/*  103 */       while (iterator.hasNext()) {
/*  104 */         EANMetaAttribute eANMetaAttribute = iterator.next();
/*  105 */         stringBuffer.append(
/*  106 */             getAttributeValue(
/*  107 */               getRootEntityType(), 
/*  108 */               getRootEntityID(), eANMetaAttribute
/*  109 */               .getAttributeCode()));
/*  110 */         if (iterator.hasNext()) {
/*  111 */           stringBuffer.append(" ");
/*      */         }
/*      */       } 
/*  114 */       this
/*  115 */         .msgs = ResourceBundle.getBundle(
/*  116 */           getClass().getName(), 
/*  117 */           getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*  118 */       this.mfParms = (Object[])new String[10];
/*      */       
/*  120 */       if ("OF".equals(getEntityType())) {
/*  121 */         EANAttribute eANAttribute = this.m_elist.getParentEntityGroup().getEntityItem(0).getAttribute("OFFERINGTYPE");
/*  122 */         if (eANAttribute != null && eANAttribute instanceof EANFlagAttribute) {
/*  123 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/*  124 */           bool = eANFlagAttribute.isSelected("0080");
/*      */         } 
/*      */       } 
/*      */       
/*  128 */       this.rpt.append("<ol>");
/*  129 */       if ("VAR".equals(getEntityType()) || bool) {
/*  130 */         EntityGroup entityGroup1 = this.m_elist.getEntityGroup("DD");
/*  131 */         EntityItem entityItem = null;
/*  132 */         if (entityGroup1.getEntityItemCount() == 0) {
/*      */           
/*  134 */           CreateActionItem createActionItem = new CreateActionItem(null, this.m_db, this.m_prof, "CR" + getEntityType() + "DD");
/*  135 */           EntityItem[] arrayOfEntityItem = new EntityItem[1];
/*      */           
/*  137 */           entityGroup = this.m_elist.getParentEntityGroup();
/*  138 */           arrayOfEntityItem[0] = entityGroup.getEntityItem(0);
/*  139 */           EntityList entityList = new EntityList(this.m_db, this.m_prof, createActionItem, arrayOfEntityItem);
/*  140 */           entityGroup1 = entityList.getEntityGroup("DD");
/*  141 */           if (entityGroup1.getEntityItemCount() == 1) {
/*      */             
/*  143 */             entityItem = entityGroup1.getEntityItem(0);
/*  144 */             EANAttribute eANAttribute = this.m_elist.getParentEntityGroup().getEntityItem(0).getAttribute("OFFERINGPNUMB");
/*  145 */             setText(entityItem, "NAME", eANAttribute.get() + " - DD");
/*      */           } else {
/*  147 */             setReturnCode(-1);
/*  148 */             this.mfOut = new MessageFormat(this.msgs.getString("DD_CR_ERR"));
/*  149 */             this.rpt.append(this.mfOut.format(this.mfParms));
/*      */           } 
/*  151 */         } else if (entityGroup1.getEntityItemCount() == 1) {
/*  152 */           entityItem = entityGroup1.getEntityItem(0);
/*  153 */         } else if (entityGroup1.getEntityItemCount() > 1) {
/*      */           
/*  155 */           this.mfOut = new MessageFormat(this.msgs.getString("DD_ERR"));
/*  156 */           this.rpt.append(this.mfOut.format(this.mfParms));
/*  157 */           setReturnCode(-1);
/*      */         } 
/*  159 */         if (entityItem != null) {
/*  160 */           logMessage("DDABR:setTotalAvailableSlots");
/*  161 */           setTotalAvailableSlots(entityItem);
/*  162 */           logMessage("DDABR:setTotalAvailableBays");
/*  163 */           setTotalAvailableBays(entityItem);
/*  164 */           logMessage("DDABR:setMemoryStandard");
/*  165 */           setMemoryRAMStandard(entityItem);
/*  166 */           logMessage("DDABR:setTotalL2CacheStandard");
/*  167 */           setTotalL2CacheStandard(entityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  172 */           logMessage("DDABR:setNumberOfProcessorsStandard");
/*  173 */           setNumberOfProcessorsStandard(entityItem);
/*  174 */           logMessage("DDABR:setNumberOfInstalledHardDrives");
/*  175 */           setNumberOfInstalledHardDrives(entityItem);
/*  176 */           logMessage("DDABR:setWeight");
/*  177 */           setWeight(entityItem);
/*  178 */           if (entityItem.hasChanges()) {
/*      */             
/*  180 */             entityItem.commit(this.m_db, null);
/*      */             
/*  182 */             EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(0);
/*  183 */             entityItem1.commit(this.m_db, null);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  187 */         this.mfOut = new MessageFormat(this.msgs.getString("SYS_ERR"));
/*  188 */         this.rpt.append(this.mfOut.format(this.mfParms));
/*  189 */         setReturnCode(-1);
/*      */       } 
/*  191 */       this.rpt.append("</ol>");
/*  192 */       this.rpt.append("<!-- DEBUG: " + this.traceSb.toString() + " -->");
/*  193 */     } catch (Throwable throwable) {
/*  194 */       StringWriter stringWriter = new StringWriter();
/*  195 */       setReturnCode(-1);
/*      */       
/*  197 */       this.mfOut = new MessageFormat(this.msgs.getString("EXCEPTION_ERROR"));
/*  198 */       this.mfParms[0] = this.m_abri.getABRCode();
/*  199 */       this.mfParms[1] = throwable.getMessage();
/*  200 */       this.rpt.append(this.mfOut.format(this.mfParms));
/*  201 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*  202 */       this.rpt.append("<!-- ");
/*  203 */       this.rpt.append(stringWriter.getBuffer().toString());
/*  204 */       this.rpt.append(" -->");
/*  205 */       this.rpt.append("</ol>\n");
/*  206 */       this.rpt.append("<!-- DEBUG: " + this.traceSb.toString() + " -->");
/*      */     } finally {
/*  208 */       setDGTitle(stringBuffer.toString());
/*  209 */       setDGRptName(getShortClassName(getClass()));
/*  210 */       setDGRptClass("WWABR");
/*      */       
/*  212 */       if (!isReadOnly()) {
/*  213 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */     
/*  217 */     stringBuffer.append((getReturnCode() == 0) ? " Passed" : " Failed");
/*  218 */     this.mfOut = new MessageFormat(this.msgs.getString("HEADER"));
/*  219 */     this.mfParms[0] = getShortClassName(getClass());
/*  220 */     this.mfParms[1] = stringBuffer.toString();
/*  221 */     this.mfParms[2] = getNow();
/*  222 */     this.mfParms[3] = this.m_prof.getOPName();
/*  223 */     this.mfParms[4] = this.m_prof.getRoleDescription();
/*  224 */     this.mfParms[5] = getDescription();
/*  225 */     this.mfParms[6] = getABRVersion();
/*  226 */     this.rpt.insert(0, this.mfOut.format(this.mfParms));
/*  227 */     println(this.rpt.toString());
/*  228 */     printDGSubmitString();
/*  229 */     buildReportFooter();
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
/*      */   private void setTotalAvailableSlots(EntityItem paramEntityItem) throws MiddlewareRequestException, EANBusinessRuleException {
/*      */     try {
/*  282 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("PSLAVAIL");
/*      */       
/*  284 */       if (entityGroup.getEntityItemCount() > 0)
/*      */       {
/*  286 */         int i = 0;
/*      */         
/*  288 */         Iterator<EntityItem> iterator = entityGroup.getEntityItem().values().iterator();
/*  289 */         while (iterator.hasNext()) {
/*  290 */           EntityItem entityItem = iterator.next();
/*  291 */           EANAttribute eANAttribute = entityItem.getAttribute("SLOTS_AVAIL");
/*      */           
/*  293 */           if (eANAttribute != null && eANAttribute instanceof COM.ibm.eannounce.objects.EANTextAttribute)
/*      */           {
/*  295 */             i += 
/*  296 */               Integer.parseInt(eANAttribute.get().toString());
/*      */           }
/*      */         } 
/*      */         
/*  300 */         setText(paramEntityItem, "TOTAVAILCARDSLOTS", Integer.toString(i));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  317 */     catch (Exception exception) {
/*  318 */       setReturnCode(-1);
/*  319 */       this.mfOut = new MessageFormat(this.msgs.getString("Execption06"));
/*  320 */       this.mfParms[0] = exception.getMessage();
/*  321 */       this.rpt.append(this.mfOut.format(this.mfParms));
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
/*      */   private void setTotalAvailableBays(EntityItem paramEntityItem) throws MiddlewareRequestException, EANBusinessRuleException {
/*      */     try {
/*  340 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("PBYAVAIL");
/*      */       
/*  342 */       if (entityGroup.getEntityItemCount() > 0) {
/*  343 */         int i = 0;
/*      */ 
/*      */         
/*  346 */         Iterator<EntityItem> iterator = entityGroup.getEntityItem().values().iterator();
/*  347 */         while (iterator.hasNext()) {
/*  348 */           EntityItem entityItem = iterator.next();
/*  349 */           EANAttribute eANAttribute = entityItem.getAttribute("TOTAL_BAYS_AVAIL");
/*  350 */           if (eANAttribute != null && eANAttribute instanceof COM.ibm.eannounce.objects.EANTextAttribute)
/*      */           {
/*      */             
/*  353 */             i += 
/*  354 */               Integer.parseInt(eANAttribute
/*  355 */                 .get().toString());
/*      */           }
/*      */         } 
/*      */         
/*  359 */         setText(paramEntityItem, "TOTAVAILBAYS", Integer.toString(i));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  376 */     catch (Exception exception) {
/*  377 */       setReturnCode(-1);
/*  378 */       this.mfOut = new MessageFormat(this.msgs.getString("Execption05"));
/*  379 */       this.mfParms[0] = exception.getMessage();
/*  380 */       this.rpt.append(this.mfOut.format(this.mfParms));
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
/*      */   private void setMemoryRAMStandard(EntityItem paramEntityItem) throws MiddlewareRequestException, EANBusinessRuleException {
/*      */     try {
/*  395 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("MEM");
/*      */       
/*  397 */       if (entityGroup.getEntityItemCount() == 1) {
/*  398 */         EntityItem entityItem = entityGroup.getEntityItem(0);
/*      */         
/*  400 */         EANAttribute eANAttribute = entityItem.getAttribute("MEMCAPACITY");
/*  401 */         if (eANAttribute != null && eANAttribute instanceof COM.ibm.eannounce.objects.EANTextAttribute)
/*      */         {
/*      */           
/*  404 */           int i = Integer.parseInt(eANAttribute.get().toString());
/*      */ 
/*      */           
/*  407 */           i *= getSBBQTY(entityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  413 */           String str = Integer.toString(i);
/*  414 */           EANAttribute eANAttribute1 = entityItem.getAttribute("MEMCAPACITYUNITS");
/*  415 */           if (eANAttribute1 != null && eANAttribute1 instanceof EANFlagAttribute) {
/*      */             
/*  417 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute1;
/*      */ 
/*      */             
/*  420 */             String str1 = eANFlagAttribute.getFirstActiveFlagCode();
/*      */             
/*  422 */             String str2 = eANFlagAttribute.getFlagLongDescription(str1);
/*  423 */             if (i > 1000 && str1.equals("0010")) {
/*      */ 
/*      */ 
/*      */               
/*  427 */               str = Double.toString(i / 1000.0D);
/*  428 */               int j = str.indexOf('.');
/*  429 */               if (j > 0) {
/*  430 */                 if (str.charAt(j + 1) == '0') {
/*      */ 
/*      */                   
/*  433 */                   str = str.substring(0, j);
/*      */                 }
/*      */                 else {
/*      */                   
/*  437 */                   str = str.substring(0, j + 2);
/*      */                 } 
/*      */               }
/*      */               
/*  441 */               setFlagByCode(paramEntityItem, "MEMRAMSTDUNITS", "0040");
/*      */             } else {
/*      */               
/*  444 */               setFlagByDescription(paramEntityItem, "MEMRAMSTDUNITS", str2);
/*      */             } 
/*      */           } 
/*  447 */           setText(paramEntityItem, "MEMRAMSTD", str);
/*      */         }
/*      */       
/*  450 */       } else if (entityGroup.getEntityItemCount() == 0) {
/*      */ 
/*      */         
/*  453 */         setFlagByCode(paramEntityItem, "MEMRAMSTDUNITS", "0030");
/*      */         
/*  455 */         setText(paramEntityItem, "MEMRAMSTD", "0");
/*      */       }
/*  457 */       else if (entityGroup.getEntityItemCount() > 1) {
/*  458 */         setMemoryStandardCR5701(paramEntityItem, entityGroup);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
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
/*      */     }
/*  556 */     catch (Exception exception) {
/*  557 */       setReturnCode(-1);
/*  558 */       this.mfOut = new MessageFormat(this.msgs.getString("Execption01"));
/*  559 */       this.mfParms[0] = exception.getMessage();
/*  560 */       this.rpt.append(this.mfOut.format(this.mfParms));
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
/*      */   private void setMemoryStandardCR5701(EntityItem paramEntityItem, EntityGroup paramEntityGroup) {
/*  589 */     String str1 = "";
/*  590 */     int i = 0;
/*  591 */     String str2 = "";
/*      */     
/*      */     try {
/*  594 */       Iterator<EntityItem> iterator = paramEntityGroup.getEntityItem().values().iterator();
/*  595 */       while (iterator.hasNext()) {
/*  596 */         EntityItem entityItem = iterator.next();
/*      */         
/*  598 */         String str = getAttributeFlagEnabledValue(entityItem, "MEMCAPACITYUNITS");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  603 */         if (str == null) {
/*  604 */           str = "0010";
/*      */         }
/*  606 */         if (str.equals("0020")) {
/*      */           
/*  608 */           this.traceSb.append(entityItem.getEntityType() + ":" + entityItem.getEntityID() + " MEMCAPACITYUNITS is kB, so skipping\n");
/*      */           
/*      */           continue;
/*      */         } 
/*  612 */         EANAttribute eANAttribute = entityItem.getAttribute("MEMCAPACITY");
/*  613 */         if (eANAttribute == null) {
/*      */           
/*  615 */           this.traceSb.append(entityItem.getEntityType() + ":" + entityItem.getEntityID() + " MEMCAPACITY was not set\n");
/*      */           continue;
/*      */         } 
/*  618 */         int j = Integer.parseInt(eANAttribute.get().toString());
/*  619 */         int k = getSBBQTY(entityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  627 */         this.traceSb.append(entityItem.getEntityType() + ":" + entityItem.getEntityID() + " MEMCAPACITYUNITS = [" + str + "] " + 
/*  628 */             getAttributeValue(entityItem.getEntityType(), entityItem.getEntityID(), "MEMCAPACITYUNITS", "") + " MEMCAPACITY: " + j + " SBBQTY: " + k + "\n");
/*      */         
/*  630 */         j *= k;
/*      */         
/*  632 */         if ("0030".equals(str)) {
/*  633 */           j *= 1024;
/*      */         }
/*      */         
/*  636 */         i += j;
/*      */       } 
/*  638 */       this.traceSb.append("Total capacity " + i + " (mb) \n");
/*      */       
/*  640 */       if (i < 1024) {
/*      */         
/*  642 */         str1 = "MB";
/*  643 */         str2 = "" + i;
/*      */       } else {
/*  645 */         float f = i / 1024.0F;
/*      */         
/*  647 */         str1 = "GB";
/*  648 */         i = (int)(f * 10.0D);
/*      */         
/*  650 */         if (i % 10 == 0) {
/*      */           
/*  652 */           str2 = "" + (i / 10);
/*      */         } else {
/*  654 */           f = i / 10.0F;
/*      */           
/*  656 */           str2 = Float.toString(f);
/*      */         } 
/*      */       } 
/*      */       
/*  660 */       this.traceSb.append("Setting MEMRAMSTD to " + str2 + " and MEMRAMSTDUNITS to " + str1 + "\n");
/*      */ 
/*      */       
/*  663 */       setFlagByDescription(paramEntityItem, "MEMRAMSTDUNITS", str1);
/*  664 */       setText(paramEntityItem, "MEMRAMSTD", str2);
/*  665 */     } catch (Exception exception) {
/*  666 */       setReturnCode(-1);
/*  667 */       this.mfOut = new MessageFormat(this.msgs.getString("Execption01"));
/*  668 */       this.mfParms[0] = exception.getMessage();
/*  669 */       this.rpt.append(this.mfOut.format(this.mfParms));
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
/*      */   private void setTotalL2CacheStandard(EntityItem paramEntityItem) throws MiddlewareRequestException, EANBusinessRuleException {
/*  685 */     String str = null;
/*      */ 
/*      */     
/*      */     try {
/*  689 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("PRC");
/*  690 */       if (entityGroup.getEntityItemCount() == 1) {
/*      */         
/*  692 */         EntityItem entityItem = entityGroup.getEntityItem(0);
/*      */         
/*  694 */         EANAttribute eANAttribute = entityItem.getAttribute("INTL2CACHESIZE");
/*  695 */         if (eANAttribute != null && eANAttribute instanceof EANFlagAttribute)
/*      */         {
/*  697 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/*      */           
/*  699 */           str = eANFlagAttribute.getFlagLongDescription(eANFlagAttribute.getFirstActiveFlagCode());
/*  700 */           BigDecimal bigDecimal = new BigDecimal(str);
/*      */ 
/*      */           
/*  703 */           bigDecimal = bigDecimal.multiply(new BigDecimal(
/*  704 */                 getSBBQTY(entityItem)));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  709 */           EANAttribute eANAttribute1 = entityItem.getAttribute("INTL2CACHESIZEUNIT");
/*  710 */           if (eANAttribute1 != null && eANAttribute1 instanceof EANFlagAttribute) {
/*      */             
/*  712 */             EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)eANAttribute1;
/*      */ 
/*      */             
/*  715 */             String str1 = eANFlagAttribute1.getFirstActiveFlagCode();
/*      */ 
/*      */             
/*  718 */             if (bigDecimal.compareTo(ONETHOUSAND) > 0 && str1.equals("0010")) {
/*      */ 
/*      */               
/*  721 */               bigDecimal = bigDecimal.divide(ONETHOUSAND, 4);
/*      */ 
/*      */ 
/*      */               
/*  725 */               setFlagByCode(paramEntityItem, "TOTL2CACHESTDUNITS", "0020");
/*      */             } else {
/*      */               
/*  728 */               setFlagByCode(paramEntityItem, "TOTL2CACHESTDUNITS", str1);
/*      */             } 
/*      */           } 
/*  731 */           setFlagToClosestNumericalMatch(paramEntityItem, "TOT_L2_CACHE_STD", bigDecimal);
/*      */         }
/*      */       
/*  734 */       } else if (entityGroup.getEntityItemCount() == 0) {
/*      */ 
/*      */         
/*  737 */         setFlagByCode(paramEntityItem, "TOTL2CACHESTDUNITS", "0010");
/*      */         
/*  739 */         setFlagByCode(paramEntityItem, "TOT_L2_CACHE_STD", "0010");
/*      */       }
/*  741 */       else if (entityGroup.getEntityItemCount() > 1) {
/*      */ 
/*      */ 
/*      */         
/*  745 */         String str1 = "";
/*  746 */         boolean bool = true;
/*  747 */         Iterator<EntityItem> iterator = entityGroup.getEntityItem().values().iterator();
/*      */         
/*  749 */         EANAttribute eANAttribute = ((EntityItem)iterator.next()).getAttribute("INTL2CACHESIZEUNIT");
/*  750 */         if (eANAttribute != null && eANAttribute instanceof EANFlagAttribute) {
/*      */           
/*  752 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/*      */           
/*  754 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  755 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*  756 */             if (arrayOfMetaFlag[b].isSelected()) {
/*  757 */               str1 = arrayOfMetaFlag[b].getFlagCode();
/*      */             }
/*      */           } 
/*      */         } else {
/*  761 */           bool = false;
/*      */         } 
/*      */         
/*  764 */         while (iterator.hasNext()) {
/*  765 */           eANAttribute = ((EntityItem)iterator.next()).getAttribute("INTL2CACHESIZEUNIT");
/*  766 */           if (eANAttribute != null && eANAttribute instanceof EANFlagAttribute) {
/*      */             
/*  768 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/*      */             
/*  770 */             if (!eANFlagAttribute.isSelected(str1))
/*  771 */               bool = false; 
/*      */             continue;
/*      */           } 
/*  774 */           bool = false;
/*      */         } 
/*      */ 
/*      */         
/*  778 */         if (bool) {
/*      */           
/*  780 */           BigDecimal bigDecimal = new BigDecimal("0");
/*  781 */           iterator = entityGroup.getEntityItem().values().iterator();
/*  782 */           while (iterator.hasNext()) {
/*  783 */             EntityItem entityItem = iterator.next();
/*  784 */             EANAttribute eANAttribute1 = entityItem.getAttribute("INTL2CACHESIZE");
/*  785 */             if (eANAttribute1 != null && eANAttribute1 instanceof EANFlagAttribute) {
/*      */               
/*  787 */               logMessage("eaaINTL2CACHESIZE: " + eANAttribute1);
/*  788 */               EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute1;
/*      */               
/*  790 */               logMessage("fa: " + eANFlagAttribute);
/*      */ 
/*      */               
/*  793 */               BigDecimal bigDecimal1 = new BigDecimal(eANFlagAttribute.getFlagLongDescription(eANFlagAttribute
/*  794 */                     .getFirstActiveFlagCode()));
/*  795 */               bigDecimal1.multiply(new BigDecimal(getSBBQTY(entityItem)));
/*  796 */               logMessage("tmp: " + bigDecimal1);
/*  797 */               bigDecimal = bigDecimal.add(bigDecimal1);
/*  798 */               logMessage("bdINTL2CACHESIZE: " + bigDecimal);
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  809 */           if (bigDecimal.compareTo(ONETHOUSAND) > 0 && str1.equals("0010")) {
/*      */ 
/*      */             
/*  812 */             bigDecimal = bigDecimal.divide(ONETHOUSAND, 4);
/*      */ 
/*      */ 
/*      */             
/*  816 */             setFlagByCode(paramEntityItem, "TOTL2CACHESTDUNITS", "0020");
/*      */           } else {
/*      */             
/*  819 */             setFlagByCode(paramEntityItem, "TOTL2CACHESTDUNITS", str1);
/*      */           } 
/*  821 */           setFlagToClosestNumericalMatch(paramEntityItem, "TOT_L2_CACHE_STD", bigDecimal);
/*      */         } else {
/*      */           
/*  824 */           setReturnCode(-1);
/*  825 */           this.mfOut = new MessageFormat(this.msgs.getString("L2_UNIT_ERR"));
/*  826 */           this.rpt.append(this.mfOut.format(this.mfParms));
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*  837 */     catch (Exception exception) {
/*  838 */       logMessage("Exception e" + exception);
/*  839 */       setReturnCode(-1);
/*  840 */       this.mfOut = new MessageFormat(this.msgs.getString("Execption07"));
/*  841 */       logMessage("mfOut" + this.mfOut);
/*  842 */       this.mfParms[0] = exception.getMessage();
/*  843 */       logMessage("mfParms[0]" + this.mfParms[0]);
/*  844 */       this.rpt.append(this.mfOut.format(this.mfParms));
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
/*      */   private void setNumberOfProcessorsStandard(EntityItem paramEntityItem) throws MiddlewareRequestException, EANBusinessRuleException {
/*      */     try {
/*  935 */       int i = 0;
/*  936 */       EntityGroup entityGroup1 = this.m_elist.getEntityGroup("PRC");
/*      */ 
/*      */ 
/*      */       
/*  940 */       if (entityGroup1.getEntityItemCount() == 0) {
/*  941 */         setText(paramEntityItem, "NUMPROCSTD", Integer.toString(i));
/*      */         return;
/*      */       } 
/*  944 */       Iterator<EntityItem> iterator = entityGroup1.getEntityItem().values().iterator();
/*  945 */       while (iterator.hasNext()) {
/*  946 */         EntityItem entityItem = iterator.next();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  953 */         i += getSBBQTY(entityItem);
/*      */       } 
/*      */ 
/*      */       
/*  957 */       EntityGroup entityGroup2 = this.m_elist.getEntityGroup("MB");
/*  958 */       if (entityGroup2.getEntityItemCount() == 1) {
/*  959 */         EntityItem entityItem = entityGroup2.getEntityItem(0);
/*  960 */         EANAttribute eANAttribute = entityItem.getAttribute("NUMPROCMAX");
/*  961 */         int j = 1;
/*  962 */         if (eANAttribute != null && eANAttribute instanceof COM.ibm.eannounce.objects.EANTextAttribute)
/*      */         {
/*      */           
/*  965 */           j = Integer.parseInt(eANAttribute.get().toString());
/*      */         }
/*  967 */         if (i > j) {
/*      */           
/*  969 */           setReturnCode(-1);
/*  970 */           this.mfOut = new MessageFormat(this.msgs.getString("MAX_PRC_ERR"));
/*  971 */           this.rpt.append(this.mfOut.format(this.mfParms));
/*      */         } else {
/*  973 */           setText(paramEntityItem, "NUMPROCSTD", Integer.toString(i));
/*      */         } 
/*      */       } else {
/*  976 */         setReturnCode(-1);
/*  977 */         this.mfParms[0] = entityGroup2.getLongDescription();
/*  978 */         if (entityGroup2.getEntityItemCount() == 0) {
/*  979 */           this.mfOut = new MessageFormat(this.msgs.getString("MISSING_MSG"));
/*      */         } else {
/*  981 */           this.mfOut = new MessageFormat(this.msgs.getString("TOO_MANY_MSG"));
/*      */         } 
/*  983 */         this.rpt.append(this.mfOut.format(this.mfParms));
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  992 */     catch (Exception exception) {
/*  993 */       setReturnCode(-1);
/*  994 */       this.mfOut = new MessageFormat(this.msgs.getString("Execption03"));
/*  995 */       this.mfParms[0] = exception.getMessage();
/*  996 */       this.rpt.append(this.mfOut.format(this.mfParms));
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
/*      */   private void setNumberOfInstalledHardDrives(EntityItem paramEntityItem) throws MiddlewareRequestException, EANBusinessRuleException {
/*      */     try {
/* 1021 */       int i = 0;
/* 1022 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("HD");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1040 */       Iterator<EntityItem> iterator = entityGroup.getEntityItem().values().iterator();
/* 1041 */       while (iterator.hasNext()) {
/* 1042 */         EntityItem entityItem = iterator.next();
/*      */         
/* 1044 */         EANAttribute eANAttribute = entityItem.getAttribute("HDDCAPACITY");
/* 1045 */         if (eANAttribute != null && eANAttribute instanceof COM.ibm.eannounce.objects.EANTextAttribute)
/*      */         {
/* 1047 */           if (Double.parseDouble(eANAttribute.get().toString()) > 0.0D)
/*      */           {
/* 1049 */             i += getSBBQTY(entityItem);
/*      */           }
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1056 */       if (!setText(paramEntityItem, "NUMINSTHD", Integer.toString(i))) {
/* 1057 */         setReturnCode(-1);
/* 1058 */         this.mfOut = new MessageFormat(this.msgs.getString("HD_ERR"));
/* 1059 */         this.rpt.append(this.mfOut.format(this.mfParms));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1070 */     catch (Exception exception) {
/* 1071 */       setReturnCode(-1);
/* 1072 */       this.mfOut = new MessageFormat(this.msgs.getString("Execption02"));
/* 1073 */       this.mfParms[0] = exception.getMessage();
/* 1074 */       this.rpt.append(this.mfOut.format(this.mfParms));
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
/*      */   private void setWeight(EntityItem paramEntityItem) throws MiddlewareRequestException, EANBusinessRuleException {
/* 1097 */     BigDecimal bigDecimal1 = new BigDecimal("0");
/* 1098 */     BigDecimal bigDecimal2 = new BigDecimal("0");
/* 1099 */     String str1 = null;
/* 1100 */     String str2 = null;
/* 1101 */     boolean bool = false;
/* 1102 */     Iterator<EntityItem> iterator = this.m_elist.getEntityGroup("SBB").getEntityItem().values().iterator();
/* 1103 */     if (!iterator.hasNext()) {
/* 1104 */       this.mfOut = new MessageFormat(this.msgs.getString("NO_SBB"));
/* 1105 */       this.rpt.append(this.mfOut.format(this.mfParms));
/*      */     } else {
/* 1107 */       while (iterator.hasNext()) {
/* 1108 */         EntityItem entityItem1 = iterator.next();
/* 1109 */         EANAttribute eANAttribute1 = entityItem1.getAttribute("WEIGHT_METRIC_SBB");
/* 1110 */         EANAttribute eANAttribute2 = entityItem1.getAttribute("WEIGHT_METUNITS_SBB");
/* 1111 */         EANAttribute eANAttribute3 = entityItem1.getAttribute("WEIGHT_US_SBB");
/* 1112 */         EANAttribute eANAttribute4 = entityItem1.getAttribute("WEIGHT_USUNITS_SBB");
/*      */         
/* 1114 */         EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/*      */         
/* 1116 */         EANAttribute eANAttribute5 = entityItem2.getAttribute(getRootEntityType() + "SBBQTY");
/* 1117 */         BigDecimal bigDecimal = new BigDecimal("1");
/* 1118 */         if (eANAttribute5 != null && eANAttribute5 instanceof COM.ibm.eannounce.objects.EANTextAttribute)
/*      */         {
/* 1120 */           bigDecimal = new BigDecimal(eANAttribute5.get().toString());
/*      */         }
/*      */         
/* 1123 */         if (eANAttribute2 != null && eANAttribute4 != null) {
/*      */           
/* 1125 */           String str3 = null;
/* 1126 */           String str4 = null;
/* 1127 */           if (eANAttribute2 instanceof EANFlagAttribute)
/*      */           {
/*      */             
/* 1130 */             str3 = ((EANFlagAttribute)eANAttribute2).getFirstActiveFlagCode();
/*      */           }
/* 1132 */           if (eANAttribute4 instanceof EANFlagAttribute)
/*      */           {
/*      */             
/* 1135 */             str4 = ((EANFlagAttribute)eANAttribute4).getFirstActiveFlagCode();
/*      */           }
/* 1137 */           if (str1 == null) {
/*      */             
/* 1139 */             str1 = str3;
/* 1140 */             str2 = str4;
/*      */           } 
/*      */           
/* 1143 */           if (str1.equals(str3)) {
/* 1144 */             if (eANAttribute1 != null)
/*      */             {
/* 1146 */               bigDecimal1 = bigDecimal1.add((new BigDecimal(eANAttribute1
/*      */ 
/*      */ 
/*      */                     
/* 1150 */                     .toString()))
/* 1151 */                   .multiply(bigDecimal));
/*      */             }
/*      */           } else {
/*      */             
/* 1155 */             bool = true;
/* 1156 */             this.mfParms[0] = entityItem1
/* 1157 */               .getEntityGroup().getLongDescription();
/* 1158 */             this.mfParms[1] = entityItem1.getEntityGroup().getMetaAttribute("WEIGHT_METUNITS_SBB").getLongDescription();
/* 1159 */             this.mfOut = new MessageFormat(this.msgs.getString("UNITS_MISMATCH_ERR"));
/* 1160 */             this.rpt.append(this.mfOut.format(this.mfParms));
/*      */           } 
/*      */           
/* 1163 */           if (str2.equals(str4)) {
/* 1164 */             if (eANAttribute3 != null)
/*      */             {
/* 1166 */               bigDecimal2 = bigDecimal2.add((new BigDecimal(eANAttribute3
/*      */ 
/*      */ 
/*      */                     
/* 1170 */                     .toString()))
/* 1171 */                   .multiply(bigDecimal));
/*      */             }
/*      */             continue;
/*      */           } 
/* 1175 */           bool = true;
/* 1176 */           this.mfParms[0] = entityItem1
/* 1177 */             .getEntityGroup().getLongDescription();
/* 1178 */           this.mfParms[1] = entityItem1.getEntityGroup().getMetaAttribute("WEIGHT_USUNITS_SBB").getLongDescription();
/* 1179 */           this.mfOut = new MessageFormat(this.msgs.getString("UNITS_MISMATCH_ERR"));
/* 1180 */           this.rpt.append(this.mfOut.format(this.mfParms)); continue;
/*      */         } 
/* 1182 */         if (eANAttribute2 == null && eANAttribute4 == null && eANAttribute1 == null && eANAttribute3 == null) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1189 */         bool = true;
/* 1190 */         this.mfParms[0] = entityItem1.getEntityGroup().getLongDescription();
/* 1191 */         this.mfOut = new MessageFormat(this.msgs.getString("UNITS_POP_ERR"));
/* 1192 */         this.rpt.append(this.mfOut.format(this.mfParms));
/*      */       } 
/*      */ 
/*      */       
/* 1196 */       if (!bool) {
/* 1197 */         if (str1 != null) {
/*      */ 
/*      */ 
/*      */           
/* 1201 */           String str3 = bigDecimal2.toString();
/*      */           
/* 1203 */           String str4 = bigDecimal1.toString();
/* 1204 */           if (str4.indexOf('.') < 0) {
/* 1205 */             str4 = str4 + ".0";
/*      */           }
/* 1207 */           setText(paramEntityItem, "WEIGHT_METRIC", str4);
/*      */           
/* 1209 */           setFlagByCode(paramEntityItem, "WEIGHT_METRICUNITS", str1);
/* 1210 */           if (str3.indexOf('.') < 0) {
/* 1211 */             str3 = str3 + ".0";
/*      */           }
/* 1213 */           setText(paramEntityItem, "WEIGHT_US", str3);
/*      */           
/* 1215 */           setFlagByCode(paramEntityItem, "WEIGHT_USUNITS", str2);
/*      */         } else {
/* 1217 */           this.mfOut = new MessageFormat(this.msgs.getString("NO_DATA"));
/* 1218 */           this.rpt.append(this.mfOut.format(this.mfParms));
/*      */         } 
/*      */       } else {
/* 1221 */         setReturnCode(-1);
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
/*      */   private boolean setText(EntityItem paramEntityItem, String paramString1, String paramString2) throws MiddlewareRequestException, EANBusinessRuleException {
/* 1242 */     boolean bool = false;
/*      */     
/* 1244 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 1245 */     if (eANMetaAttribute != null && eANMetaAttribute instanceof MetaTextAttribute) {
/* 1246 */       TextAttribute textAttribute = new TextAttribute((EANDataFoundation)paramEntityItem, this.m_prof, (MetaTextAttribute)eANMetaAttribute);
/*      */       
/* 1248 */       textAttribute.put(paramString2);
/* 1249 */       paramEntityItem.putAttribute((EANAttribute)textAttribute);
/* 1250 */       bool = true;
/* 1251 */       this.mfParms[0] = eANMetaAttribute.getLongDescription();
/* 1252 */       this.mfParms[1] = paramString2;
/* 1253 */       this.mfOut = new MessageFormat(this.msgs.getString("SET_MSG"));
/* 1254 */       this.rpt.append(this.mfOut.format(this.mfParms));
/*      */     } else {
/* 1256 */       setReturnCode(-1);
/* 1257 */       this.mfParms[0] = paramString1;
/* 1258 */       if (eANMetaAttribute == null) {
/* 1259 */         this.mfOut = new MessageFormat(this.msgs.getString("CODE_ERR"));
/* 1260 */         this.mfParms[1] = paramEntityItem.getLongDescription();
/*      */       } else {
/* 1262 */         this.mfOut = new MessageFormat(this.msgs.getString("TEXT_ERR"));
/*      */       } 
/* 1264 */       this.rpt.append(this.mfOut.format(this.mfParms));
/*      */     } 
/* 1266 */     return bool;
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
/*      */   private boolean setFlagToClosestNumericalMatch(EntityItem paramEntityItem, String paramString, BigDecimal paramBigDecimal) throws MiddlewareRequestException, EANBusinessRuleException {
/* 1285 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*      */     
/* 1287 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString);
/* 1288 */     if (eANMetaAttribute != null && eANMetaAttribute instanceof MetaSingleFlagAttribute) {
/* 1289 */       SingleFlagAttribute singleFlagAttribute = new SingleFlagAttribute((EANDataFoundation)paramEntityItem, this.m_prof, (MetaSingleFlagAttribute)eANMetaAttribute);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1294 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])singleFlagAttribute.get();
/*      */       
/* 1296 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/* 1297 */         treeMap.put(new BigDecimal(arrayOfMetaFlag[b]
/* 1298 */               .getLongDescription()), arrayOfMetaFlag[b]);
/*      */       }
/*      */       
/* 1301 */       MetaFlag metaFlag = (MetaFlag)treeMap.get(paramBigDecimal);
/* 1302 */       if (metaFlag == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1308 */         treeMap.put(paramBigDecimal, paramBigDecimal);
/*      */         
/* 1310 */         ArrayList arrayList = new ArrayList(treeMap.keySet());
/*      */         
/* 1312 */         int i = arrayList.indexOf(paramBigDecimal) - 1;
/* 1313 */         if (i < 0) {
/* 1314 */           setReturnCode(-1);
/* 1315 */           this.mfOut = new MessageFormat(this.msgs.getString("VALUE_ERR"));
/* 1316 */           this.mfParms[0] = paramBigDecimal.toString();
/* 1317 */           this.mfParms[1] = eANMetaAttribute.getLongDescription();
/* 1318 */           this.rpt.append(this.mfOut.format(this.mfParms));
/* 1319 */           return false;
/*      */         } 
/* 1321 */         metaFlag = (MetaFlag)treeMap.get(arrayList.get(i));
/*      */       } 
/* 1323 */       metaFlag.setSelected(true);
/* 1324 */       singleFlagAttribute.put(arrayOfMetaFlag);
/* 1325 */       paramEntityItem.putAttribute((EANAttribute)singleFlagAttribute);
/* 1326 */       this.mfParms[0] = eANMetaAttribute.getLongDescription();
/* 1327 */       this.mfParms[1] = metaFlag.getLongDescription();
/* 1328 */       this.mfOut = new MessageFormat(this.msgs.getString("SET_MSG"));
/* 1329 */       this.rpt.append(this.mfOut.format(this.mfParms));
/* 1330 */       return true;
/*      */     } 
/* 1332 */     setReturnCode(-1);
/* 1333 */     this.mfParms[0] = paramString;
/* 1334 */     if (eANMetaAttribute == null) {
/* 1335 */       this.mfOut = new MessageFormat(this.msgs.getString("CODE_ERR"));
/* 1336 */       this.mfParms[1] = paramEntityItem.getLongDescription();
/*      */     } else {
/* 1338 */       this.mfOut = new MessageFormat(this.msgs.getString("FLAG_ERR"));
/*      */     } 
/* 1340 */     this.rpt.append(this.mfOut.format(this.mfParms));
/* 1341 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean setFlagByDescription(EntityItem paramEntityItem, String paramString1, String paramString2) throws MiddlewareRequestException, EANBusinessRuleException {
/* 1351 */     boolean bool = false;
/*      */     
/* 1353 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 1354 */     if (eANMetaAttribute != null && eANMetaAttribute instanceof MetaSingleFlagAttribute) {
/* 1355 */       SingleFlagAttribute singleFlagAttribute = new SingleFlagAttribute((EANDataFoundation)paramEntityItem, this.m_prof, (MetaSingleFlagAttribute)eANMetaAttribute);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1360 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])singleFlagAttribute.get();
/* 1361 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/* 1362 */         if (arrayOfMetaFlag[b].getLongDescription().equals(paramString2)) {
/* 1363 */           bool = true;
/* 1364 */           arrayOfMetaFlag[b].setSelected(true);
/* 1365 */           this.mfParms[0] = eANMetaAttribute.getLongDescription();
/* 1366 */           this.mfParms[1] = paramString2;
/* 1367 */           this.mfOut = new MessageFormat(this.msgs.getString("SET_MSG"));
/* 1368 */           this.rpt.append(this.mfOut.format(this.mfParms));
/*      */         } 
/*      */       } 
/* 1371 */       if (bool) {
/* 1372 */         singleFlagAttribute.put(arrayOfMetaFlag);
/* 1373 */         paramEntityItem.putAttribute((EANAttribute)singleFlagAttribute);
/*      */       } else {
/* 1375 */         setReturnCode(-1);
/* 1376 */         this.mfOut = new MessageFormat(this.msgs.getString("VALUE_ERR"));
/* 1377 */         this.mfParms[0] = paramString2;
/* 1378 */         this.mfParms[1] = eANMetaAttribute.getLongDescription();
/* 1379 */         this.rpt.append(this.mfOut.format(this.mfParms));
/*      */       } 
/*      */     } else {
/* 1382 */       setReturnCode(-1);
/* 1383 */       this.mfParms[0] = paramString1;
/* 1384 */       if (eANMetaAttribute == null) {
/* 1385 */         this.mfOut = new MessageFormat(this.msgs.getString("CODE_ERR"));
/* 1386 */         this.mfParms[1] = paramEntityItem.getLongDescription();
/*      */       } else {
/* 1388 */         this.mfOut = new MessageFormat(this.msgs.getString("FLAG_ERR"));
/*      */       } 
/* 1390 */       this.rpt.append(this.mfOut.format(this.mfParms));
/*      */     } 
/* 1392 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean setFlagByCode(EntityItem paramEntityItem, String paramString1, String paramString2) throws MiddlewareRequestException, EANBusinessRuleException {
/* 1402 */     boolean bool = false;
/*      */     
/* 1404 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 1405 */     if (eANMetaAttribute != null && eANMetaAttribute instanceof MetaSingleFlagAttribute) {
/* 1406 */       SingleFlagAttribute singleFlagAttribute = new SingleFlagAttribute((EANDataFoundation)paramEntityItem, this.m_prof, (MetaSingleFlagAttribute)eANMetaAttribute);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1411 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])singleFlagAttribute.get();
/* 1412 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/* 1413 */         if (arrayOfMetaFlag[b].getFlagCode().equals(paramString2)) {
/* 1414 */           bool = true;
/* 1415 */           arrayOfMetaFlag[b].setSelected(true);
/* 1416 */           this.mfParms[0] = eANMetaAttribute.getLongDescription();
/* 1417 */           this.mfParms[1] = arrayOfMetaFlag[b].getLongDescription();
/* 1418 */           this.mfOut = new MessageFormat(this.msgs.getString("SET_MSG"));
/* 1419 */           this.rpt.append(this.mfOut.format(this.mfParms));
/*      */         } 
/*      */       } 
/* 1422 */       if (bool) {
/* 1423 */         singleFlagAttribute.put(arrayOfMetaFlag);
/* 1424 */         paramEntityItem.putAttribute((EANAttribute)singleFlagAttribute);
/*      */       } else {
/* 1426 */         setReturnCode(-1);
/* 1427 */         this.mfOut = new MessageFormat(this.msgs.getString("VALUE_ERR"));
/* 1428 */         this.mfParms[0] = paramString2;
/* 1429 */         this.mfParms[1] = eANMetaAttribute.getLongDescription();
/* 1430 */         this.rpt.append(this.mfOut.format(this.mfParms));
/*      */       } 
/*      */     } else {
/* 1433 */       this.mfParms[0] = paramString1;
/* 1434 */       setReturnCode(-1);
/* 1435 */       if (eANMetaAttribute == null) {
/* 1436 */         this.mfOut = new MessageFormat(this.msgs.getString("CODE_ERR"));
/* 1437 */         this.mfParms[1] = paramEntityItem.getLongDescription();
/*      */       } else {
/* 1439 */         this.mfOut = new MessageFormat(this.msgs.getString("FLAG_ERR"));
/*      */       } 
/* 1441 */       this.rpt.append(this.mfOut.format(this.mfParms));
/*      */     } 
/* 1443 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getSBBQTY(EntityItem paramEntityItem) {
/* 1453 */     int i = 0;
/* 1454 */     for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 1455 */       EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/* 1456 */       if (entityItem.getUpLink(0).getEntityType().equals("SBB")) {
/* 1457 */         EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(0);
/* 1458 */         EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/*      */         
/* 1460 */         EANAttribute eANAttribute = entityItem2.getAttribute(getRootEntityType() + "SBBQTY");
/* 1461 */         if (eANAttribute != null && eANAttribute instanceof COM.ibm.eannounce.objects.EANTextAttribute) {
/*      */           
/* 1463 */           i += Integer.parseInt(eANAttribute.get().toString());
/*      */         } else {
/* 1465 */           i++;
/*      */         } 
/*      */       } else {
/* 1468 */         i++;
/*      */       } 
/*      */     } 
/* 1471 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1479 */     return this.msgs.getString("DESCRIPTION");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1488 */     return "$Revision: 1.17 $";
/*      */   }
/*      */   private Locale getLocale(int paramInt) {
/* 1491 */     Locale locale = null;
/* 1492 */     switch (paramInt)
/*      */     { case 1:
/* 1494 */         locale = Locale.US;
/*      */       case 2:
/* 1496 */         locale = Locale.GERMAN;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1516 */         return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\psg\DDABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */