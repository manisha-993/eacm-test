//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;
import java.io.StringReader;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.Attribute;
import COM.ibm.opicmpdh.objects.ControlBlock;
import COM.ibm.opicmpdh.objects.SingleFlag;
import COM.ibm.opicmpdh.objects.Text;

import com.ibm.transform.oim.eacm.util.PokUtils;
import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

/**********************************************************************************
 * From "SG FS Inbound Feed Leads 20090108.doc"

LXABRSTATUS_class=COM.ibm.eannounce.abr.sg.LXABRSTATUS
LXABRSTATUS_enabled=true
LXABRSTATUS_idler_class=D
LXABRSTATUS_keepfile=true
LXABRSTATUS_read_only=false
LXABRSTATUS_CAT1=LEADS
LXABRSTATUS_SUBSCRVE=LXSPECBID
LXABRSTATUS_vename=dummy
 */
//LXABRSTATUS.java,v
//LXABRSTATUS.java,v
//Revision 1.10  2010/03/09 19:53:04  wendy
//design chg for PROJCDNAM
//
//Revision 1.8  2010/03/01 17:15:32  wendy
//Expanded to support Blue Harmony (BH) IDLs.
//
//Revision 1.7  2009/04/29 11:42:36  wendy
//Added debug info
//
//Revision 1.6  2009/04/17 00:57:20  wendy
//Add to deref
//
//Revision 1.5  2009/04/16 20:34:46  wendy
//Spec chg
//
//Revision 1.4  2009/04/07 12:19:37  wendy
//Enhance error msgs
//
//Revision 1.3  2009/04/02 19:44:31  wendy
//Output factsheet header when possible
//
//Revision 1.2  2009/02/23 20:47:17  wendy
//Add method to get node value, returning a default if doesn't exist
//
//Revision 1.1  2009/01/20 19:41:40  wendy
//CQ00016138-RQ: STG - HVEC EACM Inbound Feed from LEADS - New Feed
//CQ00002984-RQ: STG - EACM Inbound Feed from LEADS - New Feed
//
public class LXABRSTATUS extends PokBaseABR {
	private static final Hashtable ABR_TBL;
	static{
		ABR_TBL = new Hashtable();
		ABR_TBL.put("LEADSADDSEO", "COM.ibm.eannounce.abr.sg.LXLEADSADDSEOABR");
		ABR_TBL.put("LEADSADDBUNDLE", "COM.ibm.eannounce.abr.sg.LXLEADSADDBUNDLEABR");
		ABR_TBL.put("LEADSADDVAR", "COM.ibm.eannounce.abr.psg.LXLEADSADDVARABR");
	}
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = {'\n'};
	static final String NEWLINE = new String(FOOL_JTEST);
	public static final String TITLE="Special Bid from LEADS";

	private ResourceBundle rsBundle = null;
	private String navName = TITLE;
	private LXABRInterface lxAbr=null;
	private Vector vctReturnsEntityKeys = new Vector();
	
	protected static final String PROJCDNAM_ATTR = "PROJCDNAM";
	
	private String projcdname = null;//expanded to support Blue Harmony (BH) IDLs
	protected String getPROJCDNAME(){ return projcdname;}
	
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#dereference()
	 */
	public void dereference(){
		super.dereference();
		rptSb = null;

		rsBundle = null;
		navName = null;
		if (lxAbr != null){
			lxAbr.dereference();
			lxAbr = null;
		}
		vctReturnsEntityKeys.clear();
		vctReturnsEntityKeys = null;
	}
	
	/**********************************
	 * get the resource bundle
	 */
	private ResourceBundle getBundle() {
		return rsBundle;
	}

	/**********************************
	 *  Execute ABR.
	 *
	 */
	public void execute_run()
	{
		/* 
         The Report should identify:
            Userid:
			Role:
			Workgroup:
			Date:
			Description: 
			Return code:
		 */
		// must split because too many arguments for messageformat, max of 10.. this was 11
		String HEADER = "<head>"+
		EACustom.getMetaTags(getDescription()) + NEWLINE +
		EACustom.getCSS() + NEWLINE +
		EACustom.getTitle("{0} {1}") + NEWLINE +
		"</head>" + NEWLINE + "<body id=\"ibm-com\">" +
		EACustom.getMastheadDiv() + NEWLINE +
		"<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
		String HEADER2 = "<table>"+NEWLINE +
		"<tr><th>Userid: </th><td>{0}</td></tr>"+NEWLINE +
		"<tr><th>Role: </th><td>{1}</td></tr>"+NEWLINE +
		"<tr><th>Workgroup: </th><td>{2}</td></tr>"+NEWLINE +
		"<tr><th>Date: </th><td>{3}</td></tr>"+NEWLINE +
		"<tr><th>Description: </th><td>{4}</td></tr>"+NEWLINE +
		"<tr><th>Return code: </th><td>{5}</td></tr>"+NEWLINE +
		"</table>"+NEWLINE+
		"<!-- {6} -->" + NEWLINE;

		MessageFormat msgf;
		String abrversion="";

		Object[] args = new String[10];
		Document document = null;
		EntityItem rootEntity = null;
		Element rootElem = null;

		try {
			//Default set to pass
			setReturnCode(PASS);

			start_ABRBuild(); // pull VE 
		
			// get properties file for the base class
			rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));

			setControlBlock(); // needed for attribute updates

			rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

			addDebug("execute: "+rootEntity.getKey());

//fixme remove this.. avoid msgs to userid for testing
//			setCreateDGEntity(false);

			String attCode = "LXMSG";
			// get the xml from the entity
			String xml = PokUtils.getAttributeValue(rootEntity, attCode, "", "", false);			
			addDebug(attCode+" : "+xml);			
			
			// parse it to determine what type of LEADS XML msg we are loading
			DocumentBuilderFactory factory =
				DocumentBuilderFactory.newInstance();

			DocumentBuilder builder = factory.newDocumentBuilder();
			StringReader sr = new StringReader(xml);
			InputSource is = new InputSource(sr);
			
			document = builder.parse( is );
			rootElem = document.getDocumentElement();
			String lxtype = rootElem.getNodeName();
			
			findPrjcdnam(rootEntity, rootElem);
			
			// find class to instantiate based on xml
			// Load the specified ABR class in preparation for execution
			String clsname = (String) ABR_TBL.get(lxtype);
			addDebug("creating instance of LXABR  = '" + clsname + "'");				
			if (clsname!=null){
				// list domains, search is reliant on it
				listDomains();
				
				lxAbr = (LXABRInterface) Class.forName(clsname).newInstance();
				abrversion = getShortClassName(lxAbr.getClass())+" "+lxAbr.getVersion();
				// pass the root element to class and check the data
				lxAbr.validateData(this, rootElem);
				
				// execute and create entities
				if (getReturnCode()==PASS){
					lxAbr.execute();
				}
			}else{
				addError(getShortClassName(getClass())+" does not support "+lxtype);
			}			
		}
		catch(Throwable exc)  {
			try{
				if (document != null){
					// set up a transformer
					TransformerFactory transfac = TransformerFactory.newInstance();
					Transformer trans = transfac.newTransformer();
					trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					trans.setOutputProperty(OutputKeys.INDENT, "yes");		
					trans.setOutputProperty(OutputKeys.METHOD, "xml");
					trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

					//create string from xml tree
					java.io.StringWriter sw = new java.io.StringWriter();
					StreamResult result = new StreamResult(sw);
					DOMSource source = new DOMSource(document);
					trans.transform(source, result);
					rptSb.append("<pre>Error executing: <br />"+
							PokUtils.convertToHTML(sw.toString())
							+"</pre>");
				}
			}catch(Exception e){				
			}

			java.io.StringWriter exBuf = new java.io.StringWriter();
			String Error_EXCEPTION="<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
			String Error_STACKTRACE="<pre>{0}</pre>";
			msgf = new MessageFormat(Error_EXCEPTION);
			setReturnCode(INTERNAL_ERROR);
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			// Put exception into document
			args[0] = exc.getMessage();
			rptSb.append(msgf.format(args) + NEWLINE);
			msgf = new MessageFormat(Error_STACKTRACE);
			args[0] = exBuf.getBuffer().toString();
			rptSb.append(msgf.format(args) + NEWLINE);
			logError("Exception: "+exc.getMessage());
			logError(exBuf.getBuffer().toString());
		}
		finally {	
			try{
				// copy the FACTSHEETNUM into the root entity so user can search on it
				if (rootEntity!=null && rootElem!=null){
					String factSheetNum = getNodeValue(rootElem,"FACTSHEETNUM", null);
					if (factSheetNum!=null){
						String curval = PokUtils.getAttributeValue(rootEntity, "LXFACTSHEETNO", "", "", false);
						if (!curval.equals(factSheetNum)){
							setTextValue(m_prof, "LXFACTSHEETNO", factSheetNum,rootEntity);
						}else{
							addDebug("LXFACTSHEETNO already set to "+factSheetNum+" for "+rootEntity.getKey());
						}
					}
				}
				//may have flag value in here too, so update here
				updatePDH(m_prof);
			}catch(Exception e){
				e.printStackTrace();
			}
			setDGTitle(navName);
			setDGRptName(getShortClassName(getClass()));
			setDGRptClass(getABRCode());
			// make sure the lock is released
			if(!isReadOnly()) {
				clearSoftLock();
			}
		}
		//Print everything up to </html>
		//Insert Header into beginning of report
		msgf = new MessageFormat(HEADER);
		if (lxAbr!=null){
			args[0] = getShortClassName(lxAbr.getClass());
			navName = lxAbr.getTitle();
			String lxheader = lxAbr.getHeader();
			if (lxheader!=null &&lxheader.length()>0){
				rptSb.insert(0,"<p>"+lxheader+"</p>\n");
			}
		}else{
			args[0] = getShortClassName(getClass());
		}
		args[1] = navName;
		String header1 = msgf.format(args);
		
		msgf = new MessageFormat(HEADER2);
		args[0] = m_prof.getOPName();
		args[1] = m_prof.getRoleDescription();
		args[2] = m_prof.getWGName();
		args[3] = getNow();
		if (lxAbr!=null){
			args[4] = lxAbr.getDescription();
		}else{
			args[4] = "";
		}
		args[5] = (this.getReturnCode()==PokBaseABR.PASS?"Passed":"Failed");
		args[6] = abrversion+" "+getABRVersion();

		rptSb.insert(0, header1+msgf.format(args) + NEWLINE);

		println(EACustom.getDocTypeHtml()); //Output the doctype and html
		println(rptSb.toString()); // Output the Report
		printDGSubmitString();        

		println(EACustom.getTOUDiv());
		buildReportFooter(); // Print </html>
	}

	/**
	 * Wayne Kehrli: 1) if in XML and not in LEADSXML - take value from XML and put it in LEADSXML
 					 2) if after step 1, if LEADSXML is not empty, use it - otherwise, use default
	 * @param rootEntity
	 * @param rootElem
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private void findPrjcdnam(EntityItem rootEntity, Element rootElem) throws SQLException, MiddlewareException{
		// get the PROJCDNAM - expanded to support Blue Harmony (BH) IDLs.
		projcdname = PokUtils.getAttributeFlagValue(rootEntity, PROJCDNAM_ATTR);
		addDebug("findPrjcdnam from "+rootEntity.getKey()+" "+PROJCDNAM_ATTR+" : "+projcdname);

		if (projcdname==null){ // was not in the LEADSXML entity
			String projcdnameDesc = getNodeValue(rootElem,PROJCDNAM_ATTR, null); // get it from the XML 
			 
			addDebug("findPrjcdnam from XML "+PROJCDNAM_ATTR+" desc : "+projcdnameDesc);
			if (projcdnameDesc!=null){
				PDGUtility m_utility = new PDGUtility();
				// find flag code corresponding to the xml PROJCDNAM desc
				String[] flagArray = m_utility.getFlagCodeForExactDesc(m_db, m_prof, PROJCDNAM_ATTR, projcdnameDesc);
				if (flagArray != null && flagArray.length > 0) {
					projcdname = flagArray[0];
					addDebug("findPrjcdnam "+PROJCDNAM_ATTR+" desc : "+projcdnameDesc+" flagcode "+projcdname);
					// save it in the LEADSXML entity
					setFlagValue(m_prof, PROJCDNAM_ATTR, projcdname,rootEntity);
				}else{
					addDebug("findPrjcdnam NO match found for "+PROJCDNAM_ATTR+" desc : "+projcdnameDesc);
				}
				m_utility.dereference();
			}
		}
	}
	/**
	 * domains are key to search and create, list them for debug
	 */
	private void listDomains(){
		try{
			EntityGroup eg = m_db.getEntityGroup(m_prof, "WG", "Edit");
			EntityItem wgItem = new EntityItem(eg, m_prof, "WG", m_prof.getWGID());
			java.sql.ResultSet rs = null;
			StringBuffer sb = new StringBuffer();
			try {
				ReturnStatus returnStatus = new ReturnStatus( -1);
	            ReturnDataResultSet rdrs = null;
			
                rs = m_db.callGBL7065(returnStatus, m_prof.getEnterprise(), m_prof.getWGID(), 
                		m_prof.getValOn(), m_prof.getEffOn());
                rdrs = new ReturnDataResultSet(rs);
                for (int ii = 0; ii < rdrs.size(); ii++) {
                	// This is the AttributeCode
                	String strAttributeCode = rdrs.getColumn(ii, 0);
                	if ("PDHDOMAIN".equals(strAttributeCode)){
                		sb.append(rdrs.getColumn(ii, 1)+"|");// get the value
                	}
                }
            }
            finally {
				if (rs !=null){
                	rs.close();
                	rs = null;
				}
                m_db.commit();
                m_db.freeStatement();
                m_db.isPending();
            }
            
			addDebug("Running with "+wgItem.getKey()+" for domains "+sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/******************************************************************************
	 * Check for one and only one tag for this element
	 * @param elem
	 * @param tagName
	 * @return
	 */
	public String getNodeValue(Element elem, String tagName){
		String value = null;
		
		NodeList nlist = elem.getElementsByTagName(tagName);
		//ERROR_INVALIDXML = Invalid xml tag {0}.
		if (nlist==null || nlist.getLength()!=1){
			addError("ERROR_INVALIDXML", tagName);
		}else {
			Node node = nlist.item(0);
			if (node.hasChildNodes()){
				value = node.getFirstChild().getNodeValue();
			}else{
				addDebug("getNodeValue: "+tagName+" has no child nodes");
			}
			addDebug("getNodeValue: "+tagName+" "+value);
			if (value==null){
				//ERROR_MISSINGXML = Missing xml tag {0}.
				addError("ERROR_MISSINGXML", tagName);
			}	
		}
		
		return value;
	}
	/******************************************************************************
	 * Check for one parent and minNum children
	 * @param nlist
	 * @param tagName
	 * @param childTagName
	 * @param minNum
	 */
	public void verifyChildNodes(NodeList nlist, String tagName, String childTagName, int minNum){
		for (int x=0; x<nlist.getLength(); x++){
			NodeList childlist = nlist.item(x).getChildNodes(); 
			//ERROR_INVALIDXMLSTRUCTURE = Invalid XML. Tag {0} must have {1} or more {2} tags.
			if (childlist.getLength()<minNum){ // must have at least minNum
				addError("ERROR_INVALIDXMLSTRUCTURE", new Object[]{tagName,""+minNum, childTagName});
			}
		}			
	}
	/******************************************************************************
	 * Get tag value, if doesnt exist use default
	 * @param elem
	 * @param tagName
	 * @param defaultVal
	 * @return
	 */
	public String getNodeValue(Element elem, String tagName, String defaultVal){
		String value = defaultVal;
		
		NodeList nlist = elem.getElementsByTagName(tagName);
		if (nlist==null || nlist.getLength()!=1){
		}else {
			Node node = nlist.item(0);
			if (node.hasChildNodes()){
				value = node.getFirstChild().getNodeValue();
			}else{
				addDebug("getNodeValue: "+tagName+" has no child nodes");
			}
			addDebug("getNodeValue: "+tagName+" "+value);
			if (value==null){
				value = defaultVal;
			}	
		}
		
		return value;
	}
	/**********************************
	 * add msg to report output
	 * @param msg
	 */
	public void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}

	/**********************************
	 * used for output
	 *
	 * @param resrcCode
	 * @param args
	 */
	public void addOutput(String resrcCode, Object args[])
	{
		String msg = getBundle().getString(resrcCode);
		if (args != null){
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		addOutput(msg);
	}
	/**********************************
	 * get message using resource
	 *
	 * @param resrcCode
	 * @param args
	 * @return
	 */
	public String getResourceMsg(String resrcCode, Object args[])
	{
		String msg = getBundle().getString(resrcCode);
		if (args != null){
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		return msg;
	}
	/**********************************
	 * add debug info as html comment
	 * @param msg
	 */
	public void addDebug(String msg) { rptSb.append("<!-- "+msg+" -->"+NEWLINE);}

	/**********************************
	 * add error info and fail abr
	 * @param msg
	 */
	public void addError(String msg) {
		addOutput(msg);
		setReturnCode(FAIL);
	}

	/**********************************
	 * used for error output
	 * @param errCode
	 * @param arg
	 */
	public void addError(String errCode, Object arg)
	{
		addError(errCode, new Object[]{arg});
	}
	/**********************************
	 * used for error output
	 * @param errCode
	 * @param args
	 */
	public void addError(String errCode, Object args[])
	{
		setReturnCode(FAIL);
		String msg = getBundle().getString(errCode);
		// get message to output
		if (args!=null){
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}
		addOutput(msg);
	}

	/***********************************************
	 *  Get the version
	 *
	 *@return java.lang.String
	 */
	public String getABRVersion()
	{
		return "1.10";
	}
	/***********************************************
	 *  Get ABR description
	 *
	 *@return java.lang.String
	 */
	public String getDescription()
	{
		return "LXABRSTATUS";
	}

	/***********************************************
	 * Sets the specified Text Attribute on the specified entity
	 *
	 *@param    profile Profile
	 *@param _sAttributeCode
	 *@param _sAttributeValue
	 *@param eitem
	 */
	private void setTextValue(Profile profile, String _sAttributeCode, String _sAttributeValue,
			EntityItem eitem)
	{
		logMessage(getDescription()+" ***** "+eitem.getKey()+" "+_sAttributeCode+" set to: " + _sAttributeValue);
		addDebug("setTextValue entered for "+eitem.getKey()+" "+_sAttributeCode+" set to: " + _sAttributeValue);

		// if meta does not have this attribute, there is nothing to do
		EANMetaAttribute metaAttr = eitem.getEntityGroup().getMetaAttribute(_sAttributeCode);
		if (metaAttr==null) {
			addDebug("setTextValue: "+_sAttributeCode+" was not in meta for "+eitem.getEntityType()+", nothing to do");
			logMessage(getDescription()+" ***** "+_sAttributeCode+" was not in meta for "+
					eitem.getEntityType()+", nothing to do");
			return;
		}

		if( _sAttributeValue != null ) {
			if (m_cbOn==null){
				setControlBlock(); // needed for attribute updates
			}
			ControlBlock cb = m_cbOn;
			if (_sAttributeValue.length()==0){ // deactivation is now needed
				EANAttribute att = eitem.getAttribute(_sAttributeCode);
				String efffrom = att.getEffFrom();
				cb = new ControlBlock(efffrom, efffrom, efffrom, efffrom, profile.getOPWGID());
				_sAttributeValue = att.toString();
				addDebug("setTextValue deactivating "+_sAttributeCode);
			}
			Vector vctAtts = null;
			// look at each key to see if this item is there yet
			for (int i=0; i<vctReturnsEntityKeys.size(); i++){
				ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
				if (rek.getEntityID() == eitem.getEntityID() &&
						rek.getEntityType().equals(eitem.getEntityType())){
					vctAtts = rek.m_vctAttributes;
					break;
				}
			}
			if (vctAtts ==null){
				ReturnEntityKey rek = new ReturnEntityKey(eitem.getEntityType(),
						eitem.getEntityID(), true);
				vctAtts = new Vector();
				rek.m_vctAttributes = vctAtts;
				vctReturnsEntityKeys.addElement(rek);
			}
			COM.ibm.opicmpdh.objects.Text sf = new COM.ibm.opicmpdh.objects.Text(profile.getEnterprise(),
					eitem.getEntityType(), eitem.getEntityID(), _sAttributeCode, _sAttributeValue, 1, cb);
			vctAtts.addElement(sf);
		}
	}
	/***********************************************
	 *  Sets the specified Flag Attribute on the specified Entity
	 *
	 *@param    profile Profile
	 *@param    strAttributeCode The Flag Attribute Code
	 *@param    strAttributeValue The Flag Attribute Value
	 */
	private void setFlagValue(Profile profile, String strAttributeCode, String strAttributeValue,
			EntityItem item)
	{
		logMessage(getDescription()+" ***** "+item.getKey()+" "+strAttributeCode+" set to: " + strAttributeValue);
		addDebug("setFlagValue entered "+item.getKey()+" for "+strAttributeCode+" set to: " +
				strAttributeValue);

		if (strAttributeValue!=null && strAttributeValue.trim().length()==0) {
			addDebug("setFlagValue: "+strAttributeCode+" was blank for "+item.getKey()+", it will be ignored");
			return;
		}

		// if meta does not have this attribute, there is nothing to do
		EANMetaAttribute metaAttr = item.getEntityGroup().getMetaAttribute(strAttributeCode);
		if (metaAttr==null) {
			addDebug("setFlagValue: "+strAttributeCode+" was not in meta for "+item.getEntityType()+", nothing to do");
			logMessage(getDescription()+" ***** "+strAttributeCode+" was not in meta for "+
					item.getEntityType()+", nothing to do");
			return;
		}

		if(strAttributeValue != null)
		{
			//get the current value
			String curval = PokUtils.getAttributeFlagValue(item,strAttributeCode);
			if (strAttributeValue.equals(curval)){ 
				addDebug("setFlagValue: "+strAttributeCode+" was already set to "+curval+" for "+item.getKey()+", nothing to do");
				logMessage("setFlagValue: "+strAttributeCode+" was already set to "+curval+" for "+item.getKey()+", nothing to do");
				return;
			}

			if (m_cbOn==null){
				setControlBlock(); // needed for attribute updates
			}
			Vector vctAtts = null;
			// look at each key to see if root is there yet
			for (int i=0; i<vctReturnsEntityKeys.size(); i++){
				ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
				if (rek.getEntityID() == item.getEntityID() &&
						rek.getEntityType().equals(item.getEntityType())){
					vctAtts = rek.m_vctAttributes;
					break;
				}
			}
			if (vctAtts ==null){
				ReturnEntityKey rek = new ReturnEntityKey(item.getEntityType(),item.getEntityID(), true);
				vctAtts = new Vector();
				rek.m_vctAttributes = vctAtts;
				vctReturnsEntityKeys.addElement(rek);
			}

			SingleFlag sf = new SingleFlag (profile.getEnterprise(), item.getEntityType(), item.getEntityID(),
					strAttributeCode, strAttributeValue, 1, m_cbOn);

			// look at each attr to see if this is there yet
			for (int i=0; i<vctAtts.size(); i++){
				Attribute attr = (Attribute)vctAtts.elementAt(i);
				if (attr.getAttributeCode().equals(strAttributeCode)){
					sf = null;
					break;
				}
			}
			if(sf != null){
				vctAtts.addElement(sf);
			}else{
				addDebug("setFlagValue:  "+item.getKey()+" "+strAttributeCode+" was already added for updates ");
			}
		}
	}

	/***********************************************
	 * Update the PDH with the values in the vector, do all at once
	 *
	 *@param    profile Profile
	 */
	private void updatePDH(Profile profile)
	throws java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	java.rmi.RemoteException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
	COM.ibm.eannounce.objects.EANBusinessRuleException
	{
		logMessage(getDescription()+" updating PDH");
		addDebug("updatePDH entered for vctReturnsEntityKeys: "+vctReturnsEntityKeys);
		if(vctReturnsEntityKeys.size()>0)
		{
			try
			{
				m_db.update(profile, vctReturnsEntityKeys, false, false);

				for (int i=0; i<vctReturnsEntityKeys.size(); i++){
					ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
					// must commit text chgs.. not sure why
					for (int ii=0; ii<rek.m_vctAttributes.size(); ii++){
						Attribute attr = (Attribute)rek.m_vctAttributes.elementAt(ii);
						if (attr instanceof Text){
							EntityGroup egrp = null;
							if (rek.getEntityType().equals(getEntityType())){ // is root
								egrp = m_elist.getParentEntityGroup();
							}
							else{
								egrp = m_elist.getEntityGroup(rek.getEntityType());
							}

							EntityItem item = egrp.getEntityItem(rek.getEntityType()+rek.getEntityID());
							// must commit changes, not really sure why though
							item.commit(m_db, null);
						}
					}
				}
			}
			finally {
				vctReturnsEntityKeys.clear();
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending("finally after updatePDH");
			}
		}
	}	    
}
