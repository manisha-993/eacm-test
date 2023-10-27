/*     */ package COM.ibm.eannounce.darule;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.StringUtils;
/*     */ import COM.ibm.eannounce.darule.parser.AttributeRule;
/*     */ import COM.ibm.eannounce.darule.parser.AttributeRuleContext;
/*     */ import COM.ibm.eannounce.darule.parser.AttributeRuleParser;
/*     */ import COM.ibm.eannounce.darule.parser.EntityAttributeRule;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DARuleSubstitution
/*     */   extends DARuleItem
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/* 101 */   private static transient AttributeRuleParser ruleParser = new AttributeRuleParser();
/*     */   
/* 103 */   private static transient AttributeRuleContext ruleContext = new AttributeRuleContext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DARuleSubstitution(EntityItem paramEntityItem) {
/* 110 */     super(paramEntityItem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDerivedValue(Database paramDatabase, Profile paramProfile, EntityItem paramEntityItem, String paramString, StringBuffer paramStringBuffer) throws Exception {
/* 119 */     String str = paramString;
/*     */     
/* 121 */     if (isApplicable(paramEntityItem, paramStringBuffer)) {
/*     */       EntityItem entityItem;
/* 123 */       String str1 = getDAVEName();
/* 124 */       String str2 = getRule();
/* 125 */       String str3 = getRuleConcatenationString();
/* 126 */       DARuleEngineMgr.addDebugComment(3, paramStringBuffer, "DASubstitutionRule - RootItem " + paramEntityItem
/* 127 */           .getKey() + "\nDAVENAME: " + str1 + "\nRULE: " + str2 + "\nCONCATSTRING: " + str3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 133 */       EntityList entityList = null;
/*     */       
/* 135 */       if (str1 == null) {
/*     */         
/* 137 */         EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/* 138 */         entityItem = paramEntityItem;
/*     */       } else {
/* 140 */         entityList = paramDatabase.getEntityList(paramProfile, new ExtractActionItem(null, paramDatabase, paramProfile, str1), new EntityItem[] { new EntityItem(paramEntityItem) });
/*     */         
/* 142 */         DARuleEngineMgr.addDebugComment(3, paramStringBuffer, "DASubstitutionRule VE\n" + 
/* 143 */             PokUtils.outputList(entityList));
/* 144 */         EntityGroup entityGroup = entityList.getParentEntityGroup();
/* 145 */         entityItem = entityGroup.getEntityItem(paramEntityItem.getKey());
/*     */       } 
/*     */       
/* 148 */       List list = parseAndValidate(str2, entityItem, entityList, paramStringBuffer);
/*     */       
/* 150 */       str = derive(list, entityItem, str2, str3, (str1 == null), paramStringBuffer);
/*     */ 
/*     */       
/* 153 */       if (entityList != null) {
/* 154 */         entityList.dereference();
/*     */       }
/*     */     } 
/*     */     
/* 158 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String[] getDerivedValues(Database paramDatabase, Profile paramProfile, EntityItem[] paramArrayOfEntityItem, String[] paramArrayOfString, StringBuffer paramStringBuffer) throws Exception {
/* 167 */     String[] arrayOfString = new String[paramArrayOfEntityItem.length];
/* 168 */     String str1 = getDAVEName();
/* 169 */     String str2 = getRule();
/* 170 */     String str3 = getRuleConcatenationString();
/* 171 */     EntityList entityList = null;
/* 172 */     EntityGroup entityGroup = null;
/* 173 */     DARuleEngineMgr.addDebugComment(3, paramStringBuffer, "DASubstitutionRule - IDL\nDAVENAME: " + str1 + "\nRULE: " + str2 + "\nCONCATSTRING: " + str3);
/*     */ 
/*     */     
/* 176 */     List list = null;
/* 177 */     for (byte b = 0; b < paramArrayOfEntityItem.length; b++) {
/* 178 */       EntityItem entityItem = paramArrayOfEntityItem[b];
/*     */       
/* 180 */       if (isApplicable(entityItem, paramStringBuffer)) {
/*     */         EntityItem entityItem1;
/* 182 */         if (str1 == null) {
/*     */           
/* 184 */           entityItem1 = entityItem;
/* 185 */           entityGroup = entityItem1.getEntityGroup();
/* 186 */           DARuleEngineMgr.addDebugComment(3, paramStringBuffer, "DASubstitutionRule: No VE defined");
/*     */         }
/*     */         else {
/*     */           
/* 190 */           if (entityGroup == null) {
/* 191 */             EntityItem[] arrayOfEntityItem = new EntityItem[paramArrayOfEntityItem.length];
/* 192 */             for (byte b1 = 0; b1 < paramArrayOfEntityItem.length; b1++) {
/* 193 */               arrayOfEntityItem[b1] = new EntityItem(paramArrayOfEntityItem[b1]);
/*     */             }
/* 195 */             entityList = paramDatabase.getEntityList(paramProfile, new ExtractActionItem(null, paramDatabase, paramProfile, str1), arrayOfEntityItem);
/*     */             
/* 197 */             DARuleEngineMgr.addDebugComment(3, paramStringBuffer, "DASubstitutionRule VE\n" + 
/* 198 */                 PokUtils.outputList(entityList));
/* 199 */             entityGroup = entityList.getParentEntityGroup();
/*     */           } 
/* 201 */           entityItem1 = entityGroup.getEntityItem(entityItem.getKey());
/*     */         } 
/*     */ 
/*     */         
/* 205 */         if (list == null) {
/* 206 */           list = parseAndValidate(str2, entityItem1, entityList, paramStringBuffer);
/*     */         }
/*     */ 
/*     */         
/* 210 */         arrayOfString[b] = derive(list, entityItem1, str2, str3, (str1 == null), paramStringBuffer);
/*     */       
/*     */       }
/* 213 */       else if (paramArrayOfString != null) {
/* 214 */         arrayOfString[b] = paramArrayOfString[b];
/*     */       } else {
/* 216 */         arrayOfString[b] = null;
/*     */       } 
/*     */     } 
/*     */     
/* 220 */     if (entityList != null) {
/* 221 */       entityList.dereference();
/*     */     }
/*     */     
/* 224 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getDAVEName() {
/* 232 */     return PokUtils.getAttributeValue(getDARULEEntity(), "DAVENAME", null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getRule() {
/* 241 */     return PokUtils.getAttributeValue(getDARULEEntity(), "RULEPASS", null, null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getRuleConcatenationString() {
/* 249 */     return PokUtils.getAttributeValue(getDARULEEntity(), "RULEMULTIPLE", null, "", false);
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
/*     */   private List parseAndValidate(String paramString, EntityItem paramEntityItem, EntityList paramEntityList, StringBuffer paramStringBuffer) throws InvalidDARuleException {
/* 261 */     if (paramString == null) {
/* 262 */       throw createInvalidException("The rule cannot be null");
/*     */     }
/*     */     
/* 265 */     List<AttributeRule> list = null;
/*     */     try {
/* 267 */       list = ruleParser.parse(paramString);
/* 268 */     } catch (Exception exception) {
/* 269 */       throw createInvalidException("Unable to parse the rule: " + exception.getMessage());
/*     */     } 
/*     */     
/* 272 */     if (list == null) {
/* 273 */       throw createInvalidException("Unable to parse the rule (parse result is null)");
/*     */     }
/*     */     
/* 276 */     ArrayList<String> arrayList = new ArrayList();
/*     */     
/* 278 */     for (byte b = 0; b < list.size(); b++) {
/* 279 */       AttributeRule attributeRule = ((AttributeRule)list.get(b)).getLastRule();
/* 280 */       if (attributeRule instanceof EntityAttributeRule) {
/* 281 */         EntityGroup entityGroup; EntityAttributeRule entityAttributeRule = (EntityAttributeRule)attributeRule;
/*     */ 
/*     */         
/* 284 */         if (paramEntityList != null) {
/* 285 */           DARuleEngineMgr.addDebugComment(3, paramStringBuffer, "DASubstitutionRule EntityGroup from VE for " + entityAttributeRule
/*     */               
/* 287 */               .getEntityType());
/* 288 */           entityGroup = paramEntityList.getEntityGroup(entityAttributeRule.getEntityType());
/* 289 */           if (entityGroup == null) {
/*     */ 
/*     */             
/* 292 */             String str = entityAttributeRule.getEntityType() + " was not found in VE: " + getDAVEName();
/* 293 */             arrayList.add(str);
/*     */             
/*     */             continue;
/*     */           } 
/*     */         } else {
/* 298 */           DARuleEngineMgr.addDebugComment(3, paramStringBuffer, "DASubstitutionRule EntityGroup from RootItem for " + entityAttributeRule
/*     */               
/* 300 */               .getEntityType());
/* 301 */           entityGroup = paramEntityItem.getEntityGroup();
/*     */         } 
/*     */         
/* 304 */         EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(entityAttributeRule
/* 305 */             .getAttributeCode());
/* 306 */         if (eANMetaAttribute == null) {
/*     */ 
/*     */           
/* 309 */           String str = entityAttributeRule.getEntityType() + " don't have the attribute: " + entityAttributeRule.getAttributeCode() + " (Check if your role have access to that attribute)";
/*     */           
/* 311 */           arrayList.add(str);
/* 312 */           StringBuffer stringBuffer = new StringBuffer();
/* 313 */           for (byte b1 = 0; b1 < entityGroup.getMetaAttributeCount(); b1++) {
/* 314 */             EANMetaAttribute eANMetaAttribute1 = entityGroup.getMetaAttribute(b1);
/* 315 */             stringBuffer.append("\nMetaAttribute:" + b1 + ":" + eANMetaAttribute1.dump(true));
/*     */           } 
/* 317 */           DARuleEngineMgr.addDebugComment(3, paramStringBuffer, str + stringBuffer
/* 318 */               .toString());
/*     */         }  continue;
/*     */       } 
/* 321 */       arrayList.add("The rule's last step must be 'ENTITY.ATTRIBUTE'. Check the syntax: " + attributeRule
/* 322 */           .getKey());
/*     */       continue;
/*     */     } 
/* 325 */     if (arrayList.size() > 0) {
/* 326 */       throw createInvalidException(StringUtils.concatenate(arrayList, "<br>\n"));
/*     */     }
/*     */     
/* 329 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String derive(List<AttributeRule> paramList, EntityItem paramEntityItem, String paramString1, String paramString2, boolean paramBoolean, StringBuffer paramStringBuffer) throws InvalidDARuleException {
/* 340 */     if (paramString2 == null) {
/* 341 */       paramString2 = "";
/*     */     }
/*     */     
/* 344 */     ruleContext.setDaRuleItem(getDARULEEntity());
/*     */     
/* 346 */     String str = paramString1;
/* 347 */     boolean bool = true;
/* 348 */     for (byte b = 0; b < paramList.size(); b++) {
/* 349 */       AttributeRule attributeRule = paramList.get(b);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 355 */       if (paramBoolean && 
/* 356 */         attributeRule instanceof COM.ibm.eannounce.darule.parser.RelatorAttributeRule) {
/* 357 */         throw createInvalidException("This DARULE have no DAVENAME and has a relator in the rule");
/*     */       }
/*     */ 
/*     */       
/* 361 */       String str1 = attributeRule.getKey();
/*     */ 
/*     */       
/* 364 */       ruleContext.reset(paramEntityItem);
/*     */ 
/*     */       
/*     */       try {
/* 368 */         attributeRule.executeRule(ruleContext);
/* 369 */       } catch (Exception exception) {
/* 370 */         throw createInvalidException(exception.getMessage());
/*     */       } 
/*     */ 
/*     */       
/* 374 */       String str2 = "";
/*     */ 
/*     */       
/* 377 */       if (ruleContext.getResults().size() > 0) {
/*     */         
/* 379 */         str2 = StringUtils.concatenate(ruleContext.getResults(), paramString2);
/* 380 */         DARuleEngineMgr.addDebugComment(3, paramStringBuffer, paramEntityItem.getKey() + " " + str1 + " - Results: " + ruleContext
/* 381 */             .getResults().size() + " : " + str2);
/*     */       } else {
/* 383 */         DARuleEngineMgr.addDebugComment(3, paramStringBuffer, "DA Substitution Rule - No results - " + str1 + "\n" + ruleContext
/*     */             
/* 385 */             .getLog()
/* 386 */             .toString());
/*     */       } 
/* 388 */       str = StringUtils.replace(str, str1, str2);
/*     */ 
/*     */       
/* 391 */       if (str2.length() > 0) {
/* 392 */         bool = false;
/*     */       }
/*     */     } 
/* 395 */     ruleContext.dereference();
/*     */     
/* 397 */     return bool ? null : str;
/*     */   }
/*     */   
/*     */   private InvalidDARuleException createInvalidException(String paramString) {
/* 401 */     Vector<EntityItem> vector = new Vector();
/* 402 */     vector.add(getDARULEEntity());
/* 403 */     return new InvalidDARuleException(paramString, vector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 412 */     return "$Revision: 1.18 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\darule\DARuleSubstitution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */