//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008,2010  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package COM.ibm.eannounce.abr.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXValidator;
import org.dom4j.util.XMLErrorHandler;

import COM.ibm.eannounce.objects.BlobAttribute;
import COM.ibm.eannounce.objects.CreateActionItem;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.LongTextAttribute;
import COM.ibm.eannounce.objects.MetaBlobAttribute;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.eannounce.objects.MetaLongTextAttribute;
import COM.ibm.eannounce.objects.MetaMultiFlagAttribute;
import COM.ibm.eannounce.objects.MetaSingleFlagAttribute;
import COM.ibm.eannounce.objects.MetaStatusAttribute;
import COM.ibm.eannounce.objects.MetaTextAttribute;
import COM.ibm.eannounce.objects.MetaXMLAttribute;
import COM.ibm.eannounce.objects.MultiFlagAttribute;
import COM.ibm.eannounce.objects.PDGUtility;
import COM.ibm.eannounce.objects.SingleFlagAttribute;
import COM.ibm.eannounce.objects.StatusAttribute;
import COM.ibm.eannounce.objects.TextAttribute;
import COM.ibm.eannounce.objects.XMLAttribute;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;

/**********************************************************************************
 * Utilities for ABRs
 */ 
//$Log: ABRUtil.java,v $
//Revision 1.14  2015/02/04 14:50:58  wangyul
//RCQ00337765-RQ change the XML mapping to pull DIV from PROJ for Lenovo
//
//Revision 1.13  2015/01/26 15:53:39  wangyul
//fix the issue PR24222 -- SPF ADS abr string buffer
//
//Revision 1.12  2015/01/08 08:02:28  wangyul
//add run info in the utility class
//
//Revision 1.11  2013/12/11 08:42:03  guobin
//xsd validation for generalarea, reconcile, wwcompat and price XML
//
//Revision 1.10  2011/06/06 15:52:42  wendy
//protect against invalid search arguments
//
//Revision 1.9  2010/02/04 13:38:55  wendy
//Add support for 'S' type
//
//Revision 1.8  2010/01/13 17:33:35  wendy
//Add support for S type attr
//
//Revision 1.7  2009/08/05 17:29:20  wendy
//Make PDGUtility local instead of static, cant reuse after deref
//
//Revision 1.6  2009/05/28 17:14:56  wendy
//Support executing a script
//
//Revision 1.5  2009/05/06 12:34:24  wendy
//support create without parent relator
//
//Revision 1.4  2009/02/13 12:58:57  wendy
//Accept empty string to deactivate attr
//
//Revision 1.3  2009/02/12 20:41:47  wendy
//Support attribute deactivation
//
//Revision 1.2  2009/02/04 21:22:46  wendy
//CQ00016165 - Automated QSM feed from ePIMS HW to support the late change request from BIDS
//
//Revision 1.1  2009/01/20 19:37:50  wendy
//CQ00016138-RQ: STG - HVEC EACM Inbound Feed from LEADS - New Feed
//CQ00002984-RQ: STG - EACM Inbound Feed from LEADS - New Feed
//
public class ABRUtil {
	private static final int BUFFER_SIZE = 8192;

	/**********************************************************************************
	 * search for entity(s) using search action, attributes and values passed in
	 *
	 * @param db
	 * @param prof
	 * @param searchAction
	 * @param srchType
	 * @param useListSrch
	 * @param attrVct
	 * @param valVct
	 * @param debugSb
	 * @return
	 * @throws java.sql.SQLException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
	 * @throws COM.ibm.eannounce.objects.SBRException
	 */
	public static EntityItem[] doSearch(Database db, Profile prof, String searchAction, String srchType, 
			boolean useListSrch, Vector attrVct, Vector valVct, StringBuffer debugSb)
	throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
	COM.ibm.eannounce.objects.SBRException
	{
		if(attrVct==null){
			throw new IllegalArgumentException("AttributeCode vector cannot be null");
		}
		if(valVct==null){
			throw new IllegalArgumentException("AttributeValue vector cannot be null");
		}
		if(valVct.size()!=attrVct.size()){
			throw new IllegalArgumentException("AttributeValue vector must have the same number of elements as the AttributeCode vector");
		}
		PDGUtility pdgUtility = new PDGUtility();
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<attrVct.size(); i++){
			if (sb.length()>0){
				sb.append(";");
			}
			Object objValue = valVct.elementAt(i);
			Object objAttr = attrVct.elementAt(i);
			if(objValue==null){
				throw new IllegalArgumentException("AttributeValue vector cannot have a null value. Value["+i+"] is null");
			}
			if(objAttr==null){
				throw new IllegalArgumentException("AttributeCode vector cannot have a null Attribute. Attribute["+i+"] is null");
			}
			String value = valVct.elementAt(i).toString();
			String attr = attrVct.elementAt(i).toString();
			sb.append("map_"+attr+"="+value);
		}

		ABRUtil.append(debugSb,"ABRUtil.doSearch: Using "+searchAction+", useListSrch:"+useListSrch+" to search for "+srchType+" using "+sb.toString()+"\n");

		EntityItem[] aei = null;
		if (!useListSrch){
			aei = pdgUtility.dynaSearch(db, prof, null, searchAction, srchType, sb.toString());
		}else{
			EntityList list = pdgUtility.dynaSearchIIForEntityList(db, prof, null, searchAction,
					srchType, sb.toString());
			// group will be null if no matches are found
			EntityGroup psgrp = list.getEntityGroup(srchType);
			if (psgrp !=null && psgrp.getEntityItemCount()>0){
				aei = psgrp.getEntityItemsAsArray();
			}else{
				ABRUtil.append(debugSb,"ABRUtil.doSearch: No "+srchType+" found\n");
			}
		}
		return aei;
	}	

	/*****************************************************
	 * Create Entity
	 * 
	 * @param dbCurrent
	 * @param profile
	 * @param actionName
	 * @param parentItem
	 * @param childType
	 * @param attrCodeVct
	 * @param attrValTbl
	 * @param debugSb
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws EANBusinessRuleException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 */
	public static EntityItem createEntity(Database dbCurrent, Profile profile, String actionName, EntityItem parentItem,  
			String childType, Vector attrCodeVct, Hashtable attrValTbl, StringBuffer debugSb) 
	throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, 
	RemoteException, MiddlewareShutdownInProgressException
	{
		ABRUtil.append(debugSb,"ABRUtil.createEntity: Using "+actionName+", child:"+childType+" parent: "+parentItem.getKey()+"\n");
		EntityItem childItem = null;
		// create the entity
		CreateActionItem cai = new CreateActionItem(null,dbCurrent, profile, actionName);
		EntityList list = new EntityList(dbCurrent, profile, cai, 
				new EntityItem[] {parentItem}); 
		EntityGroup eGrp = list.getEntityGroup(childType);
		if (eGrp!= null && eGrp.getEntityItemCount() == 1)	{
			EntityItem relator = null;
			// write the attributes
			childItem = eGrp.getEntityItem(0);

			for (int i=0; i<attrCodeVct.size(); i++){
				String attrCode = (String)attrCodeVct.elementAt(i);
				// get the meta attribute
				EANMetaAttribute ma = eGrp.getMetaAttribute(attrCode);
				if (ma==null) {
					ABRUtil.append(debugSb,"ABRUtil.createEntity: MetaAttribute cannot be found "+eGrp.getEntityType()+"."+attrCode+"\n");
					continue;
				}
				Object value = attrValTbl.get(attrCode);
				if (value==null){
					ABRUtil.append(debugSb,"ABRUtil.createEntity: Value not found for "+attrCode+"\n");
					continue;
				}
				switch (ma.getAttributeType().charAt(0))
				{
				case 'T':
				case 'L':
				case 'X':
				{   
					// save the Text attributes
					setText(childItem,attrCode, value.toString(), debugSb); 
					break;
				}
				case 'S':
				case 'U':
				{
					String tmp = null;
					if (value instanceof Vector){
						tmp = ((Vector)value).firstElement().toString(); // just use first value
						ABRUtil.append(debugSb,"ABRUtil.createEntity: Vector passed in for "+
								ma.getAttributeType()+" type for "+
								attrCode+" using "+tmp+" vct was "+value+"\n");
					}else {
						tmp = value.toString();
					}
					setUniqueFlag(childItem,attrCode, tmp,debugSb); 
					break;
				}
				case 'F':
				{
					Vector tmp = null;
					if (value instanceof String){
						tmp = new Vector();
						tmp.addElement(value);
					}else {
						tmp = (Vector)value;
					}

					setMultiFlag(childItem,attrCode,tmp,debugSb); // make sure flagcodes are passed in 
					break;
				}
				default:
				{
					ABRUtil.append(debugSb,"ABRUtil.createEntity: MetaAttribute Type="+ma.getAttributeType()+
							" is not supported yet "+eGrp.getEntityType()+"."+attrCode+"\n");
					// could not get anything
					break;
				}			         
				}				
			}

			// must commit new entity and the relator to the PDH
			childItem.commit(dbCurrent, null);
			// Commit the relator too
			relator = (EntityItem) childItem.getUpLink(0);
			if (relator != null && !relator.getEntityGroup().isAssoc()){
				relator.commit(dbCurrent, null);
			}

			ABRUtil.append(debugSb,"ABRUtil.createEntity() created Entity: "+childItem.getKey()+
					(relator==null?"":" and Relator: "+relator.getKey())+"\n");
		}


		return childItem;
	}	

	/********************************************************************************
	 * Create unique flag attribute and set it
	 * @param item
	 * @param attrCode
	 * @param flagCode
	 * @param debugSb
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	 * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
	 */
	public static void setUniqueFlag(EntityItem item,String attrCode, String flagCode,
			StringBuffer debugSb) throws
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
			COM.ibm.eannounce.objects.EANBusinessRuleException
	{
		if (flagCode!=null && flagCode.trim().length()==0){
			flagCode = null;
		}
		if (flagCode==null) { // will be deactivated
			EANAttribute attr =item.getAttribute(attrCode);
			if (attr==null || attr.toString().trim().length()==0){ // nothing to do
				return;
			}
		}
		EANFlagAttribute flagAttr = (EANFlagAttribute)createAttr(item,attrCode,debugSb);
		if (flagAttr !=null){
			if (flagCode==null){
				flagAttr.put(null);
				ABRUtil.append(debugSb,"ABRUtil.setUniqueFlag: deactivating "+attrCode+"\n");
			}else{
				MetaFlag[] mfa = (MetaFlag[]) flagAttr.get();
				// reset all first just in case default turned one on 
				for (int i = 0; i < mfa.length; i++){
					mfa[i].setSelected(false);
				}

				boolean flagFound = false;
				for (int i = 0; i < mfa.length; i++){
					if (mfa[i].getFlagCode().equals(flagCode) || // match flag code or flag description
							mfa[i].toString().equals(flagCode))
					{
						mfa[i].setSelected(true);
						flagAttr.put(mfa);
						flagFound = true;
						ABRUtil.append(debugSb,"ABRUtil.setUniqueFlag: setting "+attrCode+" to "+mfa[i]+
								"["+mfa[i].getFlagCode()+"]\n");
						break;
					}
				}
				if (!flagFound){
					StringBuffer sb = new StringBuffer("FlagCode "+flagCode+
							" could not be found in "+attrCode+" MetaFlags [ ");
					for (int i = 0; i < mfa.length; i++){
						sb.append(mfa[i].getFlagCode()+" ");
					}
					sb.append("]");
					throw new COM.ibm.opicmpdh.middleware.MiddlewareRequestException(sb.toString());
				}
			}
		}
	}
	
	//new added validation
	 /**********************************
     * use dom4j validate xml file(new added)
     */
    public static boolean validatexml(Class cs, StringBuffer debugSb, String xsdfile, String xmlfile) {

    	String packagePath ="/COM/ibm/eannounce/abr/sg/adsxmlbh1";
	    boolean ifpass = true;
        try {
            XMLErrorHandler errorHandler = new XMLErrorHandler();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true);
            factory.setNamespaceAware(true);
            SAXParser parser = factory.newSAXParser();
            Document xmldoc = DocumentHelper.parseText(xmlfile);
            //new added(2013-10-11)
            //abr.addDebug("validatexml begin:");
            xsdfile =xsdfile.indexOf("/")>-1 ? packagePath + xsdfile.substring(xsdfile.indexOf("/")): packagePath + "/" + xsdfile.trim();
          //  xsdfile =packagePath + xsdfile.substring(xsdfile.indexOf("/"));
            InputStream inputStream=cs.getResourceAsStream(xsdfile);
//            abr.addDebug("validatexml inputStream is" + (inputStream!=null?" not null.":" is null"));
            ABRUtil.append(debugSb,"validatexml packagePath="+ packagePath+"\n");
            ABRUtil.append(debugSb,"111 validatexml xsdfile="+ xsdfile+"\n");
            
            
//            URL url = this.getClass().getResource(xsdfile);
//            
//            File file = new File(url.getPath());
//            abr.addDebug("validatexml url="+ url);
//            abr.addDebug("validatexml url.getPath="+ url.getPath());
            
            //new added end
            parser.setProperty(
                    "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                    "http://www.w3.org/2001/XMLSchema");
            //parser.setProperty(
                //    "http://java.sun.com/xml/jaxp/properties/schemaSource",
                //    "file:" + xsdfile);
          //new added(2013-10-11)
            parser.setProperty(
                             "http://java.sun.com/xml/jaxp/properties/schemaSource",
                             inputStream);
            //new added end
            SAXValidator validator = new SAXValidator(parser.getXMLReader());
            validator.setErrorHandler(errorHandler);
            validator.validate(xmldoc);

            if (errorHandler.getErrors().hasContent()) {
                String st = errorHandler.getErrors().asXML();
              //  abr.addError("the validation for this xml failed because: "+st);
                ABRUtil.append(debugSb,"the validation for this xml failed because: "+st+"\n");
                ifpass = false;
            } else {
            	ABRUtil.append(debugSb,"the validation for this xml successfully"+"\n");
            }
        } catch (Exception ex) {
        	//abr.addError("Error:the validation for xml failed,because:"+ex.getMessage());
        	ABRUtil.append(debugSb,"Error:the validation for xml failed,because:"+ex.getMessage()+"\n");
            ifpass = false;
        }
        return ifpass;
    }

	//new added end

	/********************************************************************************
	 * Create multi flag attribute and set it
	 * @param item
	 * @param attrCode
	 * @param flagCodeVct
	 * @param debugSb
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	 * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
	 */
	public static void setMultiFlag(EntityItem item,String attrCode, Vector flagCodeVct,
			StringBuffer debugSb) throws
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
			COM.ibm.eannounce.objects.EANBusinessRuleException
	{
		if (flagCodeVct==null || flagCodeVct.size()==0){ // will be deactivated
			EANAttribute attr =item.getAttribute(attrCode);
			if (attr==null || attr.toString().trim().length()==0){ // nothing to do
				return;
			}
		}		
		EANFlagAttribute flagAttr = (EANFlagAttribute)createAttr(item,attrCode,debugSb);
		if (flagAttr !=null) {
			if (flagCodeVct==null || flagCodeVct.size()==0){
				flagAttr.put(null);
				ABRUtil.append(debugSb,"ABRUtil.setMultiFlag: deactivating "+attrCode+"\n");
			}else{
				MetaFlag[] mfa = (MetaFlag[]) flagAttr.get();
				// reset all first just in case default turned one on
				for (int i = 0; i < mfa.length; i++){
					mfa[i].setSelected(false);
				}

				int matchCnt = 0;
				for (int i = 0; i < mfa.length; i++){
					if (flagCodeVct.contains(mfa[i].getFlagCode()) ||  // match on flag code
							flagCodeVct.contains(mfa[i].toString()))   // or flag description
					{
						mfa[i].setSelected(true);
						flagAttr.put(mfa);
						matchCnt++;
						ABRUtil.append(debugSb,"ABRUtil.setMultiFlag: setting "+attrCode+" to "+mfa[i]+
								"["+mfa[i].getFlagCode()+"]\n");
					}
				}
				if (matchCnt!=flagCodeVct.size()){
					StringBuffer sb = new StringBuffer("One or more FlagCodes in "+flagCodeVct+
							" could not be found in "+attrCode+" MetaFlags [ ");
					for (int i = 0; i < mfa.length; i++){
						sb.append(mfa[i].getFlagCode()+" ");
					}
					sb.append("]");
					throw new COM.ibm.opicmpdh.middleware.MiddlewareRequestException(sb.toString());
				}
			}
		}
	}

	/********************************************************************************
	 * Create text attribute and set it
	 * @param item
	 * @param attrCode
	 * @param value
	 * @param debugSb
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	 * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
	 */
	public static void setText(EntityItem item,String attrCode, String value, StringBuffer debugSb)   throws
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
	COM.ibm.eannounce.objects.EANBusinessRuleException
	{
		if (value!=null && value.trim().length()==0){
			value = null;
		}
		if (value==null) { // will be deactivated
			EANAttribute attr =item.getAttribute(attrCode);
			if (attr==null || attr.toString().trim().length()==0){ // nothing to do
				return;
			}
		}
		EANAttribute textAttr = createAttr(item,attrCode, debugSb);
		if (textAttr != null) {
			textAttr.put(value);
			if (value!= null){
				ABRUtil.append(debugSb,"ABRUtil.setText: setting "+attrCode+" to "+value+"\n");
			}else{
				ABRUtil.append(debugSb,"ABRUtil.setText: deactivating "+attrCode+"\n");	
			}
		}
	}

	/********************************************************************************
	 * Create specified attribute
	 * @param item
	 * @param attrCode
	 * @param debugSb
	 * @return
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	 */
	private static EANAttribute createAttr(EntityItem item, String attrCode, StringBuffer debugSb) throws
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		EANAttribute attr =item.getAttribute(attrCode);
		if (attr==null){
			EntityGroup eGrp = item.getEntityGroup();
			EANMetaAttribute ma = eGrp.getMetaAttribute(attrCode);
			if (ma==null){
				ABRUtil.append(debugSb,"ABRUtil.createAttr: MetaAttribute cannot be found to Create "+item.getEntityType()+"."+attrCode+"\n");
			}
			else {
				switch (ma.getAttributeType().charAt(0))
				{
				case 'T':
				{
					TextAttribute ta = new TextAttribute(item, null, (MetaTextAttribute) ma);
					item.putAttribute(ta);
					attr=ta;
					break;
				}
				case 'U':
				{
					SingleFlagAttribute sfa = new SingleFlagAttribute(item, null, (MetaSingleFlagAttribute) ma);
					item.putAttribute(sfa);
					attr=sfa;
					break;
				}
		        case 'S':
		        {
		        	StatusAttribute sa = new StatusAttribute(item, null, (MetaStatusAttribute) ma);
		        	item.putAttribute(sa);
		        	attr=sa;
		        	break;
		        }
				case 'B':
				{
					BlobAttribute ba = new BlobAttribute(item, null, (MetaBlobAttribute) ma);
					item.putAttribute(ba);
					attr=ba;
					break;
				}
				case 'L':
				{
					LongTextAttribute ta = new LongTextAttribute(item, null, (MetaLongTextAttribute) ma);
					item.putAttribute(ta);
					attr=ta;
					break;
				} 
				case 'X':
				{
					XMLAttribute xa = new XMLAttribute(item, null, (MetaXMLAttribute) ma);
					item.putAttribute(xa);
					attr=xa;
					break;
				} 
				case 'F':
				{
					MultiFlagAttribute mfa = new MultiFlagAttribute(item, null, (MetaMultiFlagAttribute) ma);
					item.putAttribute(mfa);
					attr = mfa;
					break;
				}
				default:
				{
					ABRUtil.append(debugSb,"ABRUtil.createAttr: MetaAttribute Type="+ma.getAttributeType()+" is not supported yet "+item.getEntityType()+"."+attrCode+"\n");
					// could not get anything
					break;
				}
				}
			}
		}
		return attr;
	}	
	
	/**********************************************************************************
	 *  Get Locale based on NLSID
	 *
	 *@return java.util.Locale
	 */
	public static Locale getLocale(int nlsID)
	{
		Locale locale = null;
		switch (nlsID)
		{
		case 1:
			locale = Locale.US;
			break;
		case 2:
			locale = Locale.GERMAN;
			break;
		case 3:
			locale = Locale.ITALIAN;
			break;
		case 4:
			locale = Locale.JAPANESE;
			break;
		case 5:
			locale = Locale.FRENCH;
			break;
		case 6:
			locale = new Locale("es", "ES");
			break;
		case 7:
			locale = Locale.UK;
			break;
		default:
			locale = Locale.US;
		break;
		}
		return locale;
	}

	/***************************************************
	 * Download the file from the specified host 
	 * and return contents as string
	 * 
	 * @param strURL = username:password@ftp.whatever.com/file.zip;type=a
	 * @return String
	 * @throws IOException
	 */	
	public static String ftpRead(String strURL) throws IOException
	{
		BufferedInputStream is = null;
		String ftpData = null;
			
		if (!strURL.startsWith("ftp://")){
			strURL ="ftp://"+strURL;
		}		
		try {
			URLConnection connection = (new URL(strURL)).openConnection();
			if (connection != null) {
				connection.setDoInput(true);
				is = new BufferedInputStream(connection.getInputStream(), BUFFER_SIZE);
				if (is != null) {
					byte[] buffer = new byte[0];
					byte[] chunk = new byte[BUFFER_SIZE];
					int count;
					// get the data
					while ((count = is.read(chunk)) >= 0) {
						byte[] t = new byte [buffer.length + count];
						System.arraycopy(buffer, 0, t, 0, buffer.length);
						System.arraycopy(chunk, 0, t, buffer.length,count);
						buffer = t;
					}
					ftpData = new String(buffer);
				}
			}
		}finally{
			if (is != null){
				try{
					is.close();					
				}catch(IOException ioe){
					//ignore
				}
				is = null;
			}
		}
		
		return ftpData;
	}

	/***************************************************
	 * Download the file from the specified host and store in file
	 * 
	 * @param strURL = username:password@ftp.whatever.com/file.zip;type=i
	 * @throws IOException
	 */
	public static void ftpReadToFile(String strURL, String localfile) throws IOException
	{
		BufferedInputStream is = null;
		FileOutputStream out = null;
			
		if (!strURL.startsWith("ftp://")){
			strURL ="ftp://"+strURL;
		}
		try {
			URLConnection connection = (new URL(strURL)).openConnection();
			if (connection != null) {
				connection.setDoInput(true);
				is = new BufferedInputStream(connection.getInputStream(), BUFFER_SIZE);
				if (is != null) {
					out = new FileOutputStream(localfile);
					int i = 0;
					byte[] bytesIn = new byte[BUFFER_SIZE];
					while ((i = is.read(bytesIn)) >= 0) {
					    out.write(bytesIn, 0, i);
					}
				}
			}
		}finally{
			if (is != null){
				try{
					is.close();					
				}catch(IOException ioe){
					//ignore
				}
				is = null;
			}
			if (out != null){
				try{
					out.close();					
				}catch(IOException ioe){
					//ignore
				}
				out = null;
			}
		}
	}	
	/*******************************************************
	 * Write this string data to the specified host and file
	 * @param strURL
	 * @param sb
	 * @throws IOException
	 */
	
	/***************************************************
	 * java.net.URL and java.net.URLConnection are the public APIs to access ftp resources, eg:-

String loc = "ftp://user:password@server/etc/motd;type=a";  specifying the type this way doesnt seem to work
"ftp://username:password@ftp.whatever.com/file.zip;type=i"
URLConnection uc = (new URL(loc)).openConnection();

If you want to download the file then call URLConnection's getInputStream() method. 
If you want to upload you call getOutputStream() method and write to the output stream. 
	 * @param strURL
	 * @return
	 * @throws IOException
	 */
	public static void ftpWrite(String strURL, StringBuffer sb) throws IOException
	{
		BufferedOutputStream bos=null;
		if (!strURL.startsWith("ftp://")){
			strURL ="ftp://"+strURL;
		}			
		
		try {
			URLConnection connection = (new URL(strURL)).openConnection();
			if (connection != null) {
				connection.setDoOutput(true);

				// get an output stream, buffering improves performance
                bos = new BufferedOutputStream(connection.getOutputStream());

                // write fields to stream
                bos.write(sb.toString().getBytes("UTF8"));
                bos.flush();
			}
		}finally{
			if (bos != null){
				try{
					bos.close();
				}catch(IOException ioe){
					// ignore
				}
				bos = null;
			}
		}
	}
	
	/***************************************************
	 * java.net.URL and java.net.URLConnection are the public APIs to access ftp resources, eg:-

String loc = "ftp://user:password@server/etc/motd;type=a";
"ftp://username:password@ftp.whatever.com/file.zip;type=i"
URLConnection uc = (new URL(loc)).openConnection();

If you want to download the file then call URLConnection's getInputStream() method. 
If you want to upload you call getOutputStream() method and write to the output stream. 
          
	 * @param strURL
	 * @return
	 * @throws IOException
	 */
	public static void ftpWriteFile(String strURL, String localFileName) throws IOException
	{
		BufferedOutputStream bos=null;
		FileInputStream is=null;
		if (!strURL.startsWith("ftp://")){
			strURL ="ftp://"+strURL;
		}			
		try {
			URLConnection connection = (new URL(strURL)).openConnection();
			if (connection != null) {
				connection.setDoOutput(true);
				// get an output stream, buffering improves performance
                bos = new BufferedOutputStream(connection.getOutputStream());

                // write file to stream
                is=  new FileInputStream(localFileName);
                byte[] buf= new byte[BUFFER_SIZE];
                while (true) {
                	int c= is.read(buf);
                	if (c<= 0) {
                		break;
                	}
                	bos.write(buf, 0, c);
                }
                bos.flush();
			}
		}finally{
			if (is != null){
				try{
					is.close();
				}catch(IOException ioe){
					// ignore
				}
				is = null;
			}
			if (bos != null){
				try{
					bos.close();
				}catch(IOException ioe){
					// ignore
				}
				bos = null;
			}
		}
	}	
	/**
	 * run a script from within java
	 * @param command - should be fully qualified path and can have parameters
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static String runScript(String command) throws IOException, InterruptedException{
		StringBuffer sb = new StringBuffer();
		Runtime rtime = Runtime.getRuntime();// Get the current RunTime environment.   
		Process shell = rtime.exec(command);// Submit the script   
		shell.waitFor();// Wait till the script finishes.   
		int rc = shell.exitValue();// get the exit code of the script.   
		if ( rc == 0 ) {// Display the normal o/p if script completed successfully.   
			sb.append(displayOutput(shell.getInputStream()));   
		} else {// Display the normal and the error o/p if script failed.    
			sb.append(command + " exited with RC = " + rc);   
			sb.append(displayOutput(shell.getErrorStream()));   
			sb.append(displayOutput(shell.getInputStream()));   
		}   
		return sb.toString();
	}
	/** This method diplays the contents of the InputStream class passed to it as an argument. */  
	private static String displayOutput(InputStream is) 
	{   	
		StringBuffer sb = new StringBuffer();
		try {
			String s;   
			BufferedReader br = new BufferedReader(new InputStreamReader(is));  
			while ((s = br.readLine()) != null)  {   
				sb.append(s+"\n");   
			}
		} catch (IOException e) {
			e.printStackTrace();
		}   
		return sb.toString();
	}   
	
	/** show the Total memory and free memory */
	public static String showMemory() {
		System.gc();
		Runtime runtime = Runtime.getRuntime();
		return ("TotMem:" + runtime.totalMemory() + ", FreeMem:" + runtime.freeMemory() +", UsedMem:" + ( runtime.totalMemory() - runtime.freeMemory())/1000/1000 +"M" );
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void append(StringBuffer sb, String msg){
		int debuglevel = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRDebugLevel("ADSABRSTATUS");
		if(debuglevel==D.EBUG_SPEW){
			sb.append(msg);			
		}else{
			
		}		
	}

    public static void main(String[] args)
    {
    	String readloc = "opicmadm:mice8chs@eacm.lexington.ibm.com/bala/checkdel.sql;type=a";
    	String writeloc = "opicmadm:mice8chs@eacm.lexington.ibm.com/bala/ADSmetaissues.txt;type=i";
    	String writeloc2 = "opicmadm:mice8chs@eacm.lexington.ibm.com/bala/chqisoproblems.txt;type=a";
    	StringBuffer sb = new StringBuffer();
    	sb.append("as of 05/07/08\n");
    	sb.append("----------------------------------------------\n");
    	sb.append("IX. Catalog Category Navigation \n");
    	sb.append("    - cant test because CATNAVIMG relator seems to be missing.  The VE is incomplete when forced to run.\n");
    	sb.append("    gbl8104 doesnt find any changes because the meta is incomplete.\n");
    	sb.append("<!-- EntityList for 1980-01-01-00.00.00.000000 extract ADSCATNAV contains the following entities: \n");
    	sb.append("CATNAV : 1 parent items. IDs( 1265233)\n");
    	sb.append("CATNAVIMG : 0 entity items. \n");
    	sb.append(" -->\n");
        try {
        	// read a file
        	String read = ftpRead(readloc);
        	System.err.println("ftpRead returned:"+read+":");
        	ftpReadToFile(readloc,"\\dev\\abr12\\source\\ftpd.txt");
        	ftpWrite(writeloc, sb);
        	ftpWriteFile(writeloc2, "\\dev\\abr12\\source\\chqisoproblems.txt");
        }catch(Exception ex){
        	System.err.println(ex.getMessage());
        	ex.printStackTrace(System.err);
        }
    }
}
