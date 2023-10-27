/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLRelatorSearchElem
/*     */   extends XMLSearchElem
/*     */ {
/*  53 */   String fsql = "select tf.entityid as FEATUREID from opicm.text tf inner join opicm.flag df on df.entityid = tf.entityid and df.entitytype = tf.entitytype and df.attributecode = 'PDHDOMAIN' and  df.effto>current timestamp and df.valto>current timestamp where tf.attributecode = 'FEATURECODE' and  tf.ATTRIBUTEVALUE = ? and  df.ATTRIBUTEVALUE = ? and tf.effto>current timestamp and tf.valto>current timestamp with ur";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   String psql = "select r.entity1id as FEATUREID,r.entity2id as MODELID from opicm.flag mf inner join opicm.text mt on mt.entityid = mf.entityid and mf.entitytype = mt.entitytype and mf.ATTRIBUTECODE='MACHTYPEATR' and mt.ATTRIBUTECODE='MODELATR' inner join opicm.flag dm on dm.entityid = mt.entityid and dm.entitytype = mt.entitytype and dm.attributecode = 'PDHDOMAIN' and  dm.effto>current timestamp and dm.valto>current timestamp inner join opicm.relator r on r.entitytype='PRODSTRUCT'  AND r.entity2id = mf.entityid inner join opicm.text ff on ff.ATTRIBUTECODE='FEATURECODE'  and ff.entityid=r.entity1id inner join opicm.flag df on df.entityid = ff.entityid and df.entitytype = ff.entitytype and df.attributecode = 'PDHDOMAIN' and  df.effto>current timestamp and df.valto>current timestamp where   mf.ATTRIBUTEVALUE=?  AND mt.ATTRIBUTEVALUE= ? and ff.ATTRIBUTEVALUE =?  and df.attributevalue = ? and dm.attributevalue=? and mf.effto>current timestamp and mf.valto>current timestamp and mt.effto>current timestamp and mt.valto>current timestamp and r.effto>current timestamp and r.valto>current timestamp and ff.effto>current timestamp and ff.valto>current timestamp with ur";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String upNodeName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLRelatorSearchElem(String paramString1, String paramString2, String paramString3, String paramString4) {
/*  86 */     super(paramString1, paramString3, paramString4, true);
/*  87 */     this.upNodeName = paramString2;
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
/*     */   public void addElements(Database paramDatabase, EntityList paramEntityList, Document paramDocument, Element paramElement, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 111 */     Element element = paramDocument.createElement(this.nodeName);
/* 112 */     addXMLAttrs(element);
/*     */     
/* 114 */     String str1 = "@@";
/* 115 */     String str2 = "@@";
/*     */     
/* 117 */     boolean bool = false;
/* 118 */     String str3 = null;
/* 119 */     String str4 = null;
/* 120 */     String str5 = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     Connection connection = paramDatabase.getPDHConnection();
/* 141 */     PreparedStatement preparedStatement = null;
/* 142 */     String str6 = PokUtils.getAttributeFlagValue(paramEntityItem, "PDHDOMAIN");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     if ("FCTRANSACTION".equals(paramEntityItem.getEntityType()) && "FROMMODELENTITYID".equals(this.nodeName)) {
/* 148 */       String str = PokUtils.getAttributeValue(paramEntityItem, "FROMMODEL", ", ", "@@", false);
/*     */       
/* 150 */       if ("@@".equals(str)) {
/* 151 */         bool = true;
/*     */ 
/*     */ 
/*     */         
/* 155 */         preparedStatement = connection.prepareStatement(this.fsql);
/* 156 */         str5 = PokUtils.getAttributeValue(paramEntityItem, "FROMFEATURECODE", ", ", "@@", false);
/* 157 */         preparedStatement.setString(1, str5);
/* 158 */         preparedStatement.setString(2, str6);
/* 159 */         ABRUtil.append(paramStringBuffer, "sql=" + this.fsql + ":" + str5 + "\n");
/*     */       } else {
/* 161 */         str3 = PokUtils.getAttributeValue(paramEntityItem, "FROMMACHTYPE", ", ", "@@", false);
/* 162 */         str4 = PokUtils.getAttributeValue(paramEntityItem, "FROMMODEL", ", ", "@@", false);
/* 163 */         str5 = PokUtils.getAttributeValue(paramEntityItem, "FROMFEATURECODE", ", ", "@@", false);
/*     */       } 
/*     */     } 
/*     */     
/* 167 */     if ("FCTRANSACTION".equals(paramEntityItem.getEntityType()) && "TOMODELENTITYID".equals(this.nodeName)) {
/* 168 */       String str = PokUtils.getAttributeValue(paramEntityItem, "TOMODEL", ", ", "@@", false);
/* 169 */       if ("@@".equals(str)) {
/*     */ 
/*     */         
/* 172 */         bool = true;
/* 173 */         preparedStatement = connection.prepareStatement(this.fsql);
/* 174 */         str5 = PokUtils.getAttributeValue(paramEntityItem, "TOFEATURECODE", ", ", "@@", false);
/* 175 */         preparedStatement.setString(1, str5);
/* 176 */         preparedStatement.setString(2, str6);
/* 177 */         ABRUtil.append(paramStringBuffer, "sql=" + this.fsql + ":" + str5 + "\n");
/*     */       } else {
/* 179 */         str3 = PokUtils.getAttributeValue(paramEntityItem, "TOMACHTYPE", ", ", "@@", false);
/* 180 */         str4 = PokUtils.getAttributeValue(paramEntityItem, "TOMODEL", ", ", "@@", false);
/* 181 */         str5 = PokUtils.getAttributeValue(paramEntityItem, "TOFEATURECODE", ", ", "@@", false);
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     if (!bool) {
/* 186 */       preparedStatement = connection.prepareStatement(this.psql);
/*     */       
/* 188 */       preparedStatement.setString(1, str3);
/* 189 */       preparedStatement.setString(2, str4);
/* 190 */       preparedStatement.setString(3, str5);
/* 191 */       preparedStatement.setString(4, str6);
/* 192 */       preparedStatement.setString(5, str6);
/* 193 */       ABRUtil.append(paramStringBuffer, "sql=" + this.psql + ":" + str3 + ":" + str4 + ":" + str5 + "\n");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 198 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 199 */     if (resultSet.next()) {
/*     */       try {
/* 201 */         str2 = resultSet.getString("FEATUREID");
/* 202 */         ABRUtil.append(paramStringBuffer, "FEATUREID=" + str2 + "\n");
/* 203 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/*     */       try {
/* 207 */         str1 = resultSet.getString("MODELID");
/* 208 */         ABRUtil.append(paramStringBuffer, "MODELID=" + str1 + "\n");
/*     */       }
/* 210 */       catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     element.appendChild(paramDocument.createTextNode(str1));
/* 217 */     paramElement.appendChild(element);
/*     */     
/* 219 */     element = paramDocument.createElement(this.upNodeName);
/* 220 */     element.appendChild(paramDocument.createTextNode(str2));
/* 221 */     paramElement.appendChild(element);
/*     */ 
/*     */     
/* 224 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 225 */       XMLElem xMLElem = this.childVct.elementAt(b);
/* 226 */       xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramEntityItem, paramStringBuffer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 254 */     Element element = paramDocument.createElement(this.nodeName);
/*     */     
/* 256 */     addXMLAttrs(element);
/*     */     
/* 258 */     String str1 = "@@";
/* 259 */     String str2 = "@@";
/* 260 */     boolean bool = false;
/* 261 */     EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 262 */     if (paramDiffEntity.isDeleted()) {
/* 263 */       entityItem = paramDiffEntity.getPriorEntityItem();
/*     */     }
/* 265 */     String str3 = PokUtils.getAttributeFlagValue(entityItem, "PDHDOMAIN");
/*     */     
/* 267 */     String str4 = null;
/* 268 */     String str5 = null;
/* 269 */     String str6 = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 279 */     Connection connection = paramDatabase.getPDHConnection();
/* 280 */     PreparedStatement preparedStatement = null;
/*     */ 
/*     */ 
/*     */     
/* 284 */     if ("FCTRANSACTION".equals(paramDiffEntity.getEntityType()) && "FROMMODELENTITYID".equals(this.nodeName)) {
/* 285 */       String str = PokUtils.getAttributeValue(entityItem, "FROMMODEL", ", ", "@@", false);
/*     */       
/* 287 */       if ("@@".equals(str)) {
/* 288 */         bool = true;
/*     */ 
/*     */ 
/*     */         
/* 292 */         preparedStatement = connection.prepareStatement(this.fsql);
/* 293 */         str6 = PokUtils.getAttributeValue(entityItem, "FROMFEATURECODE", ", ", "@@", false);
/* 294 */         preparedStatement.setString(1, str6);
/* 295 */         preparedStatement.setString(2, str3);
/*     */         
/* 297 */         System.out.println("sql=1" + this.fsql + ":" + str4 + ":" + str5 + ":" + str6 + "\n");
/*     */       } else {
/* 299 */         str4 = PokUtils.getAttributeValue(entityItem, "FROMMACHTYPE", ", ", "@@", false);
/* 300 */         str5 = PokUtils.getAttributeValue(entityItem, "FROMMODEL", ", ", "@@", false);
/* 301 */         str6 = PokUtils.getAttributeValue(entityItem, "FROMFEATURECODE", ", ", "@@", false);
/*     */       } 
/*     */     } 
/*     */     
/* 305 */     if ("FCTRANSACTION".equals(paramDiffEntity.getEntityType()) && "TOMODELENTITYID".equals(this.nodeName)) {
/* 306 */       String str = PokUtils.getAttributeValue(entityItem, "TOMODEL", ", ", "@@", false);
/* 307 */       if ("@@".equals(str)) {
/*     */ 
/*     */         
/* 310 */         bool = true;
/* 311 */         preparedStatement = connection.prepareStatement(this.fsql);
/* 312 */         str6 = PokUtils.getAttributeValue(entityItem, "TOFEATURECODE", ", ", "@@", false);
/* 313 */         preparedStatement.setString(1, str6);
/* 314 */         preparedStatement.setString(2, str3);
/*     */       } else {
/*     */         
/* 317 */         str4 = PokUtils.getAttributeValue(entityItem, "TOMACHTYPE", ", ", "@@", false);
/* 318 */         str5 = PokUtils.getAttributeValue(entityItem, "TOMODEL", ", ", "@@", false);
/* 319 */         str6 = PokUtils.getAttributeValue(entityItem, "TOFEATURECODE", ", ", "@@", false);
/*     */       } 
/*     */     } 
/*     */     
/* 323 */     if (!bool) {
/* 324 */       preparedStatement = connection.prepareStatement(this.psql);
/*     */       
/* 326 */       preparedStatement.setString(1, str4);
/* 327 */       preparedStatement.setString(2, str5);
/* 328 */       preparedStatement.setString(3, str6);
/* 329 */       preparedStatement.setString(4, str3);
/* 330 */       preparedStatement.setString(5, str3);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 335 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 336 */     if (resultSet.next()) {
/*     */       try {
/* 338 */         str2 = resultSet.getString("FEATUREID");
/* 339 */         ABRUtil.append(paramStringBuffer, "FEATUREID=" + str2 + "\n");
/* 340 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/*     */       try {
/* 344 */         str1 = resultSet.getString("MODELID");
/* 345 */         ABRUtil.append(paramStringBuffer, "MODELID=" + str1 + "\n");
/*     */       }
/* 347 */       catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 354 */     element.appendChild(paramDocument.createTextNode(str1));
/* 355 */     paramElement.appendChild(element);
/*     */     
/* 357 */     element = paramDocument.createElement(this.upNodeName);
/* 358 */     element.appendChild(paramDocument.createTextNode(str2));
/* 359 */     paramElement.appendChild(element);
/*     */ 
/*     */     
/* 362 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 363 */       XMLElem xMLElem = this.childVct.elementAt(b);
/* 364 */       xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityItem[] SearchFeature(EntityItem paramEntityItem, Database paramDatabase, String paramString, StringBuffer paramStringBuffer) throws MiddlewareBusinessRuleException, MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException, EANBusinessRuleException, IOException {
/* 396 */     EntityItem[] arrayOfEntityItem = null;
/* 397 */     Profile profile = paramEntityItem.getProfile();
/*     */     
/* 399 */     String str1 = "SRDFEATURE";
/* 400 */     String str2 = "FEATURE";
/* 401 */     Vector<String> vector1 = new Vector();
/* 402 */     Vector<String> vector2 = new Vector();
/*     */     
/* 404 */     vector1.add("FEATURECODE");
/* 405 */     String str3 = PokUtils.getAttributeValue(paramEntityItem, paramString, ", ", "@@", false);
/* 406 */     ABRUtil.append(paramStringBuffer, "searchFeature from/to featurecode=" + str3 + "\n");
/* 407 */     vector2.add(str3);
/*     */     try {
/* 409 */       arrayOfEntityItem = ABRUtil.doSearch(paramDatabase, profile, str1, str2, false, vector1, vector2, paramStringBuffer);
/*     */     }
/* 411 */     catch (Exception exception) {
/* 412 */       ABRUtil.append(paramStringBuffer, "doSearch error=" + exception.getMessage() + "\n");
/* 413 */       exception.printStackTrace();
/*     */     } 
/* 415 */     return arrayOfEntityItem;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLRelatorSearchElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */