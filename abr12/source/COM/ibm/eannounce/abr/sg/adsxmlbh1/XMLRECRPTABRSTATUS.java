//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2010  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.OPICMList;

import com.ibm.transform.oim.eacm.util.PokUtils;
/*
The ABR will be queued for an instance of “XML Reconciliation Reports” (XMLRECPT) by setting XMLRECRPTABRSTATUS = “Queued” (0020). The value of this attribute is changed to “In Process” (0050) by Task Master at the start of execution of this ABR. The following is used in the selection criterion of entries from the XMLMSGLOG.
•	T1 = XMLRECRPTLRDTS or, if empty use “1980-01-01”
•	T2 = VALFROM of XMLRECRPTABRSTATUS for the current value of “In Process”

The selection from the XMLMSGLOG table from a query point of view should be:
•	Order by:  MSGTYPE, SENDMSGDTS
•	Group on:  MSGTYPE

The selection criterion of entries from the XMLMSGLOG table is:
•	T1 < SENDMSGDTS <=  T2
•	XMLRECPT.MQPROPFILE = XMLMSGLOG.MQPROPFILE

An XML Report Message is created for the result of the preceding selection criteria.

The XML Report Message is sent to the system (MQ Series Queue) identified in the Property File named in the MQPROPFILE. 

Upon successful completion of this ABR (i.e. after the reconciliation report XML is placed on the MQ Series queue), set XMLRECRPTLRDTS = T2.

Each MSGTYPE produces one or more instances <MSGELEMENT> and each row selected from the XML Message Log for the MSGTYPE produces one or more instances of <ENTITYLIST>.


 
IX.	XML Report Message

<RECONCILE_MSGS xmlns="http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/RECONCILE_MSGS">
<DTSOFMSG></DTSOFMSG>
<FROMMSGDTS></FROMMSGDTS>
<TOMSGDTS></TOMSGDTS>
<MSGLIST>
     <MSGELEMENT>
          <SETUPENTITYTYPE></SETUPENTITYTYPE>
          <SETUPENTITYID></SETUPENTITYID>
          <DETUPDTS></DETUPDTS>
          <MSGTYPE></MSGTYPE>
          <MSGCOUNT></MSGCOUNT>
          <ENTITYLIST>
               <ENTITYELEMENT>
                    <ENTITYTYPE></ENTITYTYPE>
                    <ENTITYID></ENTITYID>
                    <DTSOFMSG></DTSOFMSG>
               </ENTITYELEMENT>
          </ENTITYLIST>
     </MSGELEMENT>
</MSGLIST>
</RECONCILE_MSGS>

 */  
//$Log: XMLRECRPTABRSTATUS.java,v $
//Revision 1.12  2015/09/10 09:27:19  wangyul
//Fix Defect 1392225 - SQL error got when I triggered the reconcile report for CHIS on lpar5
//
//Revision 1.11  2014/01/17 11:24:00  wangyulo
//the ABR changes needed to comply with V17 standards
//
//Revision 1.10  2013/12/11 08:08:23  guobin
//xsd validation for generalarea, reconcile, wwcompat and price XML
//
//Revision 1.9  2013/05/24 05:51:12  liuweimi
//RCQ00241437 - send an 'empty' reconcile XML when no msgs sent - config using meta
//
//Revision 1.8  2013/02/19 13:51:53  wangyulo
//update for defect 894771 -- RCQ00241437-WI BH W1 - Support XML Reconcile report when 0 XML records sent - DCUT
//
//Revision 1.7  2012/05/21 12:31:29  wangyulo
//fix the defect 724568-- reconcile msg ENTITYLISTS should be empty for GA price and wwcompat
//

public class XMLRECRPTABRSTATUS extends PokBaseABR {
	
	private static final int XMLMSGLOG_ROW_LIMIT;
	static{
		String rowlimit = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("XMLRECRPTABRSTATUS", "_XMLGENCOUNT","3000");
		XMLMSGLOG_ROW_LIMIT = Integer.parseInt(rowlimit);
	}
	private static final int MAXFILE_SIZE=5000000;
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = {'\n'};
	static final String NEWLINE = new String(FOOL_JTEST);
	private Object[] args = new String[10];

    private StringBuffer xmlgenSb = new StringBuffer();
	private ResourceBundle rsBundle = null;
	private Hashtable metaTbl = new Hashtable();
	private String navName = "";
	private PrintWriter dbgPw=null;
	private String dbgfn = null;
	private int dbgLen=0; 
    private int abr_debuglvl=D.EBUG_ERR;
	private Vector vctReturnsEntityKeys = new Vector();
	private String actionTaken="";
    private PrintWriter userxmlPw=null;
    private String userxmlfn = null;
    private int userxmlLen=0;
    private StringBuffer userxmlSb= new StringBuffer(); 
    private String t2DTS = "&nbsp;";  // T2
    private String t1DTS = "&nbsp;";   // T1
    protected static final String STATUS_PROCESS = "0050";
    protected String attrXMLABRPROPFILE = "MQPROPFILE";
    protected static final String CHEAT = "@@";
    protected static final Hashtable SETUP_MSGTYPE_TBL;
    static {
    	SETUP_MSGTYPE_TBL = new Hashtable();
    	SETUP_MSGTYPE_TBL.put("ADSXMLSETUP","GENERALAREA_UPDATE");
    	SETUP_MSGTYPE_TBL.put("XMLPRODPRICESETUP","PRODUCT_PRICE_UPDATE");
    	SETUP_MSGTYPE_TBL.put("XMLCOMPATSETUP","WWCOMPAT_UPDATE");
    	SETUP_MSGTYPE_TBL.put("XMLXLATESETUP","XLATE_UPDATE");
    }
	
	
	private void setupPrintWriter(){
		String fn = m_abri.getFileName();
		int extid = fn.lastIndexOf(".");
		dbgfn = fn.substring(0,extid+1)+"dbg";
		userxmlfn = fn.substring(0,extid+1)+"userxml";
		try {
			dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(dbgfn, true), "UTF-8"));
		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, "trouble creating debug PrintWriter " + x);
		}
		 try {
	            userxmlPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(userxmlfn, true), "UTF-8"));
	        } catch (Exception x) {
	            D.ebug(D.EBUG_ERR, "trouble creating xmlgen PrintWriter " + x);
	        }
	}
	private void closePrintWriter() {
		if (dbgPw != null){
			dbgPw.flush();
			dbgPw.close();
			dbgPw = null;
		}
       if (userxmlPw != null){
            userxmlPw.flush();
            userxmlPw.close();
            userxmlPw = null;
        }
	}
	
	  protected void addUserXML(String s){
	        if (userxmlPw != null){
	            userxmlLen+=s.length();
	            userxmlPw.println(s);
	            userxmlPw.flush();
	        }else{
	            userxmlSb.append(ADSABRSTATUS.convertToHTML(s)+NEWLINE);
	        }
	    }

	/**********************************
	 *  Execute ABR.
	 */
	public void execute_run()
	{
		/*
        The Report should identify:
            USERID (USERTOKEN)
            Role
            Workgroup
            Date/Time
            EntityType LongDescription
			Any errors or list LSEO created or changed
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
		"<tr><th>Prior feed Date/Time: </th><td>{4}</td></tr>"+NEWLINE +
		"<tr><th>Description: </th><td>{5}</td></tr>"+NEWLINE +
		"<tr><th>Return code: </th><td>{6}</td></tr>"+NEWLINE +
		"<tr><th>Action Taken: </th><td>{7}</td></tr>"+NEWLINE+
		"</table>"+NEWLINE+
		"<!-- {8} -->" + NEWLINE;

		MessageFormat msgf;
		String abrversion="";

		println(EACustom.getDocTypeHtml()); //Output the doctype and html

		try
		{
			start_ABRBuild(false); // pull dummy VE

		    abr_debuglvl = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRDebugLevel(m_abri.getABRCode());
		       
			setupPrintWriter();

			//get properties file for the base class
			rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));			
			  //get the root entity using current timestamp, need this to get the timestamps or info for VE pulls
	        m_elist = m_db.getEntityList(m_prof,
	                new ExtractActionItem(null, m_db, m_prof,"dummy"),
	                new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });
			long startTime = System.currentTimeMillis();			
			// get root from VE
			EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
			// debug display list of groups
			addDebug("DEBUG: "+getShortClassName(getClass())+" entered for " +rootEntity.getKey()+
					" extract: "+m_abri.getVEName()+" using DTS: "+m_prof.getValOn()+NEWLINE + PokUtils.outputList(m_elist));

			//Default set to pass
			setReturnCode(PASS);
//			fixme remove this.. avoid msgs to userid for testing
//			setCreateDGEntity(false);

			//NAME is navigate attributes
			navName = getNavigationName(rootEntity);

            addDebug("getT1 entered for Periodic XMLRECRPTABR "+rootEntity.getKey());
            // get it from the attribute
            EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute("XMLRECRPTLRDTS");
            if (metaAttr==null) {
                throw new MiddlewareException("XMLRECRPTLRDTS not in meta for Periodic ABR "+rootEntity.getKey());
            }
            //TODO defect 636891 The xml report mismatch with the XMLRECPT.MQPROPFILE,
            metaAttr = rootEntity.getEntityGroup().getMetaAttribute("MQPROPFILE");
            if (metaAttr==null) {
                throw new MiddlewareException("MQPROPFILE not in meta for Periodic ABR "+rootEntity.getKey());
            }
            String propfile = PokUtils.getAttributeFlagValue(rootEntity, attrXMLABRPROPFILE);   	
	    	if (propfile == null) {
	    	    addError("XMLRECRPTABR: No MQ properties files, nothing will be generated.");
	    	    
			    //NOT_REQUIRED = Not Required for {0}.
			    addXMLGenMsg("NOT_REQUIRED", "XMLRECRPTABRSTATUS");     	        
	    	} 	      
            t1DTS = PokUtils.getAttributeValue(rootEntity, "XMLRECRPTLRDTS", ", ", m_strEpoch, false);
            boolean istimestamp = isTimestamp(t1DTS);
            if (istimestamp && getReturnCode()==PASS){
            	AttributeChangeHistoryGroup statusHistory = getSTATUSHistory("XMLRECRPTABRSTATUS");
                setT2DTS(statusHistory);
                processThis(rootEntity, propfile);
            } else if (!istimestamp){
            	addError("Invalid DateTime Stamp for XMLRECRPTLRDTS, please put format: yyyy-MM-dd-HH.mm.ss.SSSSSS ");
            }
            if (getReturnCode()==PASS) {
                PDGUtility pdgUtility = new PDGUtility();
                OPICMList attList = new OPICMList();
                attList.put("XMLRECRPTLRDTS","XMLRECRPTLRDTS=" + t2DTS);
                pdgUtility.updateAttribute(m_db, m_prof, rootEntity, attList);
            }
            addDebug("Total Time: "+Stopwatch.format(System.currentTimeMillis()-startTime));
			
		}catch(Throwable exc) {
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
		finally	{
			setDGTitle(navName);
			setDGRptName(getShortClassName(getClass()));
			setDGRptClass(getABRCode());
			// make sure the lock is released
			if(!isReadOnly()) {
				clearSoftLock();
			}
			closePrintWriter();
		}

		//Print everything up to </html>
		//Insert Header into beginning of report
		msgf = new MessageFormat(HEADER);
		args[0] = getDescription();
		args[1] = navName;
		String header1 = msgf.format(args);
		msgf = new MessageFormat(HEADER2);
		args[0] = m_prof.getOPName();
		args[1] = m_prof.getRoleDescription();
		args[2] = m_prof.getWGName();
		args[3] = getNow();
		args[4] = t1DTS;
		args[5] = navName;
		args[6] = (this.getReturnCode()==PokBaseABR.PASS?"Passed":"Failed");
		args[7] = actionTaken+"<br />"+xmlgenSb.toString();
		args[8] = abrversion+" "+getABRVersion();

		restoreXtraContent();
		 //XML_MSG= XML Message
		 //XML_MSG= XML Message
        String info = header1+msgf.format(args)+"<pre>"+
		rsBundle.getString("XML_MSG")+"<br />"+
	    userxmlSb.toString()+"</pre>" + NEWLINE;
		rptSb.insert(0, info + NEWLINE);

		println(rptSb.toString()); // Output the Report
		printDGSubmitString();
		println(EACustom.getTOUDiv());
		buildReportFooter(); // Print </html>

		metaTbl.clear();
	}
	
	/**
	 * 
	 * @param rootEntity
	 * @throws Exception 
	 */
	 public void processThis (EntityItem rootEntity, String propfile) throws Exception{
		 StringBuffer strbSQL;
		 addDebug("XMLRECRPTABR.processThis checking between "+t1DTS+" and "+t2DTS);
		 strbSQL = new StringBuffer();
		 strbSQL.append("select SETUPENTITYTYPE, SETUPENTITYID, SETUPDTS, MSGTYPE, ENTITYTYPE, ENTITYID, DTSOFMSG, MSGCOUNT from cache.xmlmsglog ");
		 strbSQL.append(" where sendmsgdts between '" + t1DTS + "' and '" + t2DTS + "'");
		 if (propfile != null){
			 strbSQL.append(" and locate('"+ propfile + "', MQPROPFILE)>0");
		 }
		 strbSQL.append(" and msgstatus = 'S' order by setupdts, msgtype, setupentityid");
		 TreeMap rootmap = new TreeMap();
	        ResultSet result=null;
			PreparedStatement statement = null;			
	        try {
	        	statement = m_db.getODSConnection().prepareStatement(new String(strbSQL));
	        	result = statement.executeQuery();
	        	int chunkcounter = 0;
	        	int counter=0;
	        	while (result.next()) {
	        		counter++;
	        		String setupentitytype = result.getString(1);
					int setupentityid = result.getInt(2);
					String setupdts = result.getString(3);
					String msgtype = result.getString(4);
					String entitytype = result.getString(5);
					int entityid = result.getInt(6);
					String dtsofmsg = result.getString(7);
					int msgcount = result.getInt(8);
					if (setupentitytype ==null)
						setupentitytype = CHEAT;
					if (setupdts ==null){
						setupdts = CHEAT;
					}else{
						setupdts =  setupdts.replace(' ','-').replace(':','.');
					}	
					if (msgtype ==null)
						msgtype = CHEAT;
					if (entitytype ==null)
						entitytype = CHEAT;
					if (dtsofmsg ==null){
						dtsofmsg = CHEAT;
					} else {
						dtsofmsg = dtsofmsg.replace(' ','-').replace(':','.');
					}
					String key = setupentitytype + setupdts + msgtype + Integer.toString(setupentityid);
					if (!rootmap.containsKey(key)){
						XMLMSGInfo xmlmsginfo = new XMLMSGInfo(setupentitytype,setupentityid,setupdts,msgtype,msgcount);
						xmlmsginfo.getEntitylist_xml().add(new String[]{entitytype,("0".equals(Integer.toString(entityid))?CHEAT:Integer.toString(entityid)),dtsofmsg});
						rootmap.put(key, xmlmsginfo);
					} else{
						//TODO set MSGCOUNT
						XMLMSGInfo xmlmsginfo = (XMLMSGInfo)rootmap.get(key);			
						xmlmsginfo.setMsgcount_xml(xmlmsginfo.getMsgcount_xml() + msgcount);
						xmlmsginfo.getEntitylist_xml().add(new String[]{entitytype,("0".equals(Integer.toString(entityid))?CHEAT:Integer.toString(entityid)),dtsofmsg});
					}
					if (counter>=XMLMSGLOG_ROW_LIMIT){
						addDebug("Chunking size is " +  XMLMSGLOG_ROW_LIMIT + ". Start to run chunking "  + ++chunkcounter  + " times.");
						sentToMQ(rootmap, rootEntity);
						counter = 0;
					}
	            }
	            //TODO
	            if (counter>0){
	            	sentToMQ(rootmap, rootEntity);
	            }else if((XMLMSGLOG_ROW_LIMIT*chunkcounter+counter)==0){
	            	sentToMQ(rootmap, rootEntity); 
	            }
	            
	            
	            addOutput("The total number is " + (XMLMSGLOG_ROW_LIMIT*chunkcounter+ counter) + " entities");	
				
	        } catch (RuntimeException rx) {
	        	addXMLGenMsg("FAILED", rootEntity.getKey());
				addDebug("RuntimeException on ? " + rx);
			    rx.printStackTrace();
			    throw rx;
			} catch (Exception x) {
				addXMLGenMsg("FAILED", rootEntity.getKey());
				addDebug("Exception on ? " + x);
			    x.printStackTrace();
			    throw x;
			}finally{
				 if (statement != null)
					try {
						statement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
	 }
	 private void sentToMQ(TreeMap xmlmsgMap,EntityItem rootEntity) throws DOMException, MissingResourceException, ParserConfigurationException, TransformerException{
		 	String val = PokUtils.getAttributeFlagValue(rootEntity, attrXMLABRPROPFILE);
	    	Vector vct = new Vector();    	
	    	if (val != null) {
	    		// parse the string into substrings    	        
	    	    StringTokenizer st = new StringTokenizer(val,PokUtils.DELIMITER);    	           
	    	    while(st.hasMoreTokens())
	            {
	    	        vct.addElement(st.nextToken());
	            }                       	        
	    	}
//	    	If XMLRECPT attribute XMLRECRPTOPTION has a value of “Not Zero Report” (NRZERO), then do not send a report when <MSGCOUNT> was set to zero.
	    	String xmlrecrptoption = PokUtils.getAttributeFlagValue(rootEntity, "XMLRECRPTOPTION");
	    	addDebug("XMLRECRPTOPTION = " + xmlrecrptoption);
	    	if(xmlmsgMap.size()==0 && "XRZERO".equals(xmlrecrptoption)){
	    		//do nothing
	    		addDebug("don't send anything when xmlmsgMap.size is 0 and XMLRECRPTOPTION = " + xmlrecrptoption);
	    	}
	    	else if (xmlmsgMap.size()==0 && !("XRZERO".equals(xmlrecrptoption))){				
				//NO_CHANGES_FND=No Changes found for {0}
				//addOutput("No message log found for ADSWWCOMPATXMLABR");	    		
				processMQZero(xmlmsgMap, vct);
				addDebug("send zero report when xmlmsgMap.size is 0 and XMLRECRPTOPTION = " + xmlrecrptoption);
			}else{
				addDebug("send normal report when xmlmsgMap.size > 0 and XMLRECRPTOPTION = " + xmlrecrptoption);
				processMQ(xmlmsgMap, vct);
		    }
					
	    	// release memory		
			xmlmsgMap.clear();
			
		}
	 /**
		 * @param abr
		 * @param profileT2
		 * @param compatVct
		 * @param mqVct
		 * @throws ParserConfigurationException
		 * @throws DOMException
		 * @throws TransformerException
		 * @throws MissingResourceException
		 */
		private void processMQZero(TreeMap xmlmsgMap, Vector mqVct) throws ParserConfigurationException, DOMException, TransformerException, MissingResourceException {
			if (mqVct==null){
				addDebug("XMLRECRPTABR: No MQ properties files, nothing will be generated.");
				//NOT_REQUIRED = Not Required for {0}.
				addXMLGenMsg("NOT_REQUIRED", "XMLRECRPTABRSTATUS");
			}else{
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document document = builder.newDocument();  // Create
					String nodeName = "RECONCILE_MSGS";
					String xmlns = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + nodeName;

					// Element parent = (Element) document.createElement("WWCOMPAT_UPDATE");
					Element parent = (Element) document.createElementNS(xmlns,nodeName);
					parent.appendChild(document.createComment("RECONCILE_MSGS Version "+XMLMQAdapter.XMLVERSION10+" Mod "+XMLMQAdapter.XMLMOD10));
					// create the root
					document.appendChild(parent);
					parent.setAttributeNS("http://www.w3.org/2000/xmlns/","xmlns",xmlns);

			        Element elem = (Element) document.createElement("DTSOFMSG");
					elem.appendChild(document.createTextNode(getNow()));
					parent.appendChild(elem);
				    elem = (Element) document.createElement("FROMMSGDTS");
					elem.appendChild(document.createTextNode(t1DTS));
					parent.appendChild(elem);
					elem = (Element) document.createElement("TOMSGDTS");
					elem.appendChild(document.createTextNode(t2DTS));
					parent.appendChild(elem);
					Element msglist = (Element) document.createElement("MSGLIST");
					parent.appendChild(msglist);					
						Element msglog = (Element) document.createElement("MSGELEMENT");
						msglist.appendChild(msglog);
	
						elem = (Element) document.createElement("SETUPENTITYTYPE");
						elem.appendChild(document.createTextNode(CHEAT));
						msglog.appendChild(elem);
	
						elem = (Element) document.createElement("SETUPENTITYID");
						elem.appendChild(document.createTextNode(CHEAT));
						msglog.appendChild(elem);
	
						elem = (Element) document.createElement("SETUPDTS");
						elem.appendChild(document.createTextNode(CHEAT));
						msglog.appendChild(elem);
	
						elem = (Element) document.createElement("MSGTYPE");
						elem.appendChild(document.createTextNode(CHEAT));
						msglog.appendChild(elem);
						
						elem = (Element) document.createElement("MSGCOUNT");
						elem.appendChild(document.createTextNode("0"));
						msglog.appendChild(elem);
	
						Element entitylist = (Element) document.createElement("ENTITYLIST");
						msglog.appendChild(entitylist);

				String xml = transformXML(document);
//				new added
				boolean ifpass = false;
//				String entitytype = rootEntity.getEntityType();
				String entitytype = "XMLRECPT";
				String ifNeed = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS" ,"_"+entitytype+"_XSDNEEDED","NO");
				if ("YES".equals(ifNeed.toUpperCase())) {
				   String xsdfile = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS","_"+entitytype+"_XSDFILE","NONE");
				    if ("NONE".equals(xsdfile)) {
				    	addError("there is no xsdfile for "+entitytype+" defined in the propertyfile ");
				    } else {
				    	long rtm = System.currentTimeMillis();
				    	Class cs = this.getClass();
				    	StringBuffer debugSb = new StringBuffer();
				    	ifpass = ABRUtil.validatexml(cs,debugSb,xsdfile,xml);
				    	if (debugSb.length()>0){
				    		String s = debugSb.toString();
							if (s.indexOf("fail") != -1)
								addError(s);
							addOutput(s);
				    	}
				    	long ltm = System.currentTimeMillis();
						addDebug(D.EBUG_DETAIL, "Time for validation: "+Stopwatch.format(ltm-rtm));
				    	if (ifpass) {
				    		addDebug("the xml for "+entitytype+" passed the validation");
				    	}
				    }
				} else {
					addOutput("the xml for "+entitytype+" doesn't need to be validated");
					ifpass = true;
				}

				//new added end
				//add flag(new added)
				if (xml != null && ifpass) {
				//addDebug("XMLRECRPTABR: Generated MQ xml:"+ADSABRSTATUS.NEWLINE+xml+ADSABRSTATUS.NEWLINE);
				notify("XMLRECPT", xml, mqVct);
				}
				document = null;
			}

		}
	 
	 /**
		 * @param abr
		 * @param profileT2
		 * @param compatVct
		 * @param mqVct
		 * @throws ParserConfigurationException
		 * @throws DOMException
		 * @throws TransformerException
		 * @throws MissingResourceException
		 */
		private void processMQ(TreeMap xmlmsgMap, Vector mqVct) throws ParserConfigurationException, DOMException, TransformerException, MissingResourceException {
			if (mqVct==null){
				addDebug("XMLRECRPTABR: No MQ properties files, nothing will be generated.");
				//NOT_REQUIRED = Not Required for {0}.
				addXMLGenMsg("NOT_REQUIRED", "XMLRECRPTABRSTATUS");
			}else{
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document document = builder.newDocument();  // Create
					String nodeName = "RECONCILE_MSGS";
					String xmlns = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + nodeName;

					// Element parent = (Element) document.createElement("WWCOMPAT_UPDATE");
					Element parent = (Element) document.createElementNS(xmlns,nodeName);
					parent.appendChild(document.createComment("RECONCILE_MSGS Version "+XMLMQAdapter.XMLVERSION10+" Mod "+XMLMQAdapter.XMLMOD10));
					// create the root
					document.appendChild(parent);
					parent.setAttributeNS("http://www.w3.org/2000/xmlns/","xmlns",xmlns);

			        Element elem = (Element) document.createElement("DTSOFMSG");
					elem.appendChild(document.createTextNode(getNow()));
					parent.appendChild(elem);
				    elem = (Element) document.createElement("FROMMSGDTS");
					elem.appendChild(document.createTextNode(t1DTS));
					parent.appendChild(elem);
					elem = (Element) document.createElement("TOMSGDTS");
					elem.appendChild(document.createTextNode(t2DTS));
					parent.appendChild(elem);
					Element msglist = (Element) document.createElement("MSGLIST");
					parent.appendChild(msglist);					
					Collection xmlmsglogs = xmlmsgMap.values();
					Iterator itr = xmlmsglogs.iterator();
					while (itr.hasNext()){
						XMLMSGInfo xmlmsginfo = (XMLMSGInfo)itr.next();
						Element msglog = (Element) document.createElement("MSGELEMENT");
						msglist.appendChild(msglog);

						elem = (Element) document.createElement("SETUPENTITYTYPE");
						elem.appendChild(document.createTextNode(xmlmsginfo.setupentitytype_xml));
						msglog.appendChild(elem);

						elem = (Element) document.createElement("SETUPENTITYID");
						elem.appendChild(document.createTextNode(xmlmsginfo.setupentityid_xml));
						msglog.appendChild(elem);

						elem = (Element) document.createElement("SETUPDTS");
						elem.appendChild(document.createTextNode(xmlmsginfo.setupdts_xml));
						msglog.appendChild(elem);

						elem = (Element) document.createElement("MSGTYPE");
						elem.appendChild(document.createTextNode(xmlmsginfo.msgtype_xml));
						msglog.appendChild(elem);
						
						elem = (Element) document.createElement("MSGCOUNT");
						elem.appendChild(document.createTextNode(Integer.toString(xmlmsginfo.getMsgcount_xml())));
						msglog.appendChild(elem);

						Element entitylist = (Element) document.createElement("ENTITYLIST");
						msglog.appendChild(entitylist);
						if(!SETUP_MSGTYPE_TBL.containsKey(xmlmsginfo.setupentitytype_xml)){
							for (int i = 0; i < xmlmsginfo.entitylist_xml.size(); i++){
								Element entityelement = (Element) document.createElement("ENTITYELEMENT");
								entitylist.appendChild(entityelement);
								String[] entitymsg = (String[])xmlmsginfo.entitylist_xml.elementAt(i);
								elem = (Element) document.createElement("ENTITYTYPE");
								elem.appendChild(document.createTextNode(entitymsg[0]));
								entityelement.appendChild(elem);
								elem = (Element) document.createElement("ENTITYID");
								elem.appendChild(document.createTextNode(entitymsg[1]));
								entityelement.appendChild(elem);
								elem = (Element) document.createElement("DTSOFMSG");
								elem.appendChild(document.createTextNode(entitymsg[2]));
								entityelement.appendChild(elem);
	
							}
						}
						// release memory
						xmlmsginfo.dereference();
				}

				String xml = transformXML(document);
//				new added
				boolean ifpass = false;
//				String entitytype = rootEntity.getEntityType();
				String entitytype = "XMLRECPT";
				String ifNeed = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS" ,"_"+entitytype+"_XSDNEEDED","NO");
				if ("YES".equals(ifNeed.toUpperCase())) {
				   String xsdfile = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS","_"+entitytype+"_XSDFILE","NONE");				    if ("NONE".equals(xsdfile)) {
				    	addError("there is no xsdfile for "+entitytype+" defined in the propertyfile ");
				    } else {
				    	long rtm = System.currentTimeMillis();
				    	Class cs = this.getClass();
				    	StringBuffer debugSb = new StringBuffer();
				    	ifpass = ABRUtil.validatexml(cs,debugSb,xsdfile,xml);
				    	if (debugSb.length()>0){
				    		String s = debugSb.toString();
							if (s.indexOf("fail") != -1)
								addError(s);
							addOutput(s);
				    	}
				    	long ltm = System.currentTimeMillis();
						addDebug(D.EBUG_DETAIL, "Time for validation: "+Stopwatch.format(ltm-rtm));
				    	if (ifpass) {
				    		addDebug("the xml for "+entitytype+" passed the validation");
				    	}
				    }
				} else {
					addOutput("the xml for "+entitytype+" doesn't need to be validated");
					ifpass = true;
				}

				//new added end


				//add flag(new added)
				if (xml != null && ifpass) {
				//addDebug("XMLRECRPTABR: Generated MQ xml:"+ADSABRSTATUS.NEWLINE+xml+ADSABRSTATUS.NEWLINE);
				notify("XMLRECPT", xml, mqVct);
				}
				document = null;
			}

		}
		
		 /**********************************
	     * generate the xml
	     */
	    protected String transformXML(Document document) throws
	    ParserConfigurationException,
	    javax.xml.transform.TransformerException
	    {
	        //set up a transformer
	        TransformerFactory transfac = TransformerFactory.newInstance();
	        Transformer trans = transfac.newTransformer();
	        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        // OIDH can't handle whitespace.. trans.setOutputProperty(OutputKeys.INDENT, "yes");
	        trans.setOutputProperty(OutputKeys.INDENT, "no");
	        trans.setOutputProperty(OutputKeys.METHOD, "xml");
	        trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

	        //create string from xml tree
	        java.io.StringWriter sw = new java.io.StringWriter();
	        StreamResult result = new StreamResult(sw);
	        DOMSource source = new DOMSource(document);
	        trans.transform(source, result);
	        String xmlString = XMLElem.removeCheat(sw.toString());

	        //(ADSABRSTATUS)mqAbr.addDebug
	        // transform again for user to see in rpt
	        trans.setOutputProperty(OutputKeys.INDENT, "yes");
	        sw = new java.io.StringWriter();
	        result = new StreamResult(sw);
	        trans.transform(source, result);
	        addUserXML(XMLElem.removeCheat(sw.toString()));

	        return xmlString;
	    }
	    /**********************************
	     * feed the xml
	     */

	    // RQK change to receive mqVct as a parameter

	    protected void notify(String rootInfo, String xml, Vector mqVct)
	    throws MissingResourceException
	    {
	        MessageFormat msgf = null;
	        // Vector mqVct = mqAbr.getMQPropertiesFN();
	        int sendCnt=0;
	        boolean hasFailure = false;

	        // write to each queue, only one now, but leave this just in case
	        for (int i=0; i<mqVct.size(); i++){

	            String mqProperties = (String)mqVct.elementAt(i);
	            addDebug("in notify looking at prop file "+mqProperties);
	            try {
	                ResourceBundle rsBundleMQ = ResourceBundle.getBundle(mqProperties,
	                        getLocale(getProfile().getReadLanguage().getNLSID()));
	                Hashtable ht = MQUsage.getMQSeriesVars(rsBundleMQ);
	                boolean bNotify = ((Boolean)ht.get(MQUsage.NOTIFY)).booleanValue();
	                ht.put(MQUsage.MQCID,getMQCID()); //add to hashtable for CID to MQ
	                ht.put(MQUsage.XMLTYPE,"ADS"); //add to hashtable to indicate ADS msg
					Hashtable userProperties = MQUsage.getUserProperties(rsBundleMQ, getMQCID());
	                if (bNotify) {
	                    try{
							addDebug("User infor " + userProperties);
	                    	MQUsage.putToMQQueueWithRFH2("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xml, ht,userProperties);
	                        //SENT_SUCCESS = XML was generated and sent successfully for {0} {1}.
	                        msgf = new MessageFormat(rsBundle.getString("SENT_SUCCESS"));
	                        args[0] = mqProperties;
	                        args[1] = rootInfo;
	                        addOutput(msgf.format(args));
	                        sendCnt++;
	                        if (!hasFailure){  // dont overwrite a failed notice
	                            //xmlgen = rsBundle.getString("SUCCESS");//"Success";
	                            addXMLGenMsg("SUCCESS", rootInfo);
	                            addDebug("sent successfully to prop file "+mqProperties);
	                        }
	                    }catch (com.ibm.mq.MQException ex) {
	                        //MQ_ERROR = Error: An MQSeries error occurred for {0}: Completion code {1} Reason code {2}.
	                        //FAILED = Failed sending {0}
	                        addXMLGenMsg("FAILED", rootInfo);
	                        hasFailure = true;
	                        msgf = new MessageFormat(rsBundle.getString("MQ_ERROR"));
	                        args[0] = mqProperties+" "+rootInfo;
	                        args[1] = ""+ex.completionCode;
	                        args[2] = ""+ex.reasonCode;
	                        addError(msgf.format(args));
	                        ex.printStackTrace(System.out);
	                        addDebug("failed sending to prop file "+mqProperties);
	                    } catch (java.io.IOException ex) {
	                        //MQIO_ERROR = Error: An error occurred when writing to the MQ message buffer for {0}: {1}
	                        addXMLGenMsg("FAILED", rootInfo);
	                        hasFailure = true;
	                        msgf = new MessageFormat(rsBundle.getString("MQIO_ERROR"));
	                        args[0] = mqProperties+" "+rootInfo;
	                        args[1] = ex.toString();
	                        addError(msgf.format(args));
	                        ex.printStackTrace(System.out);
	                        addDebug("failed sending to prop file "+mqProperties);
	                    }
	                }else{
	                    //NO_NOTIFY = XML was generated but NOTIFY was false in the {0} properties file.
	                    msgf = new MessageFormat(rsBundle.getString("NO_NOTIFY"));
	                    args[0] = mqProperties;
	                    addError(msgf.format(args));
	                    //{0} "Not sent";
	                    addXMLGenMsg("NOT_SENT", rootInfo);
	                    addDebug("not sent to prop file "+mqProperties+ " because Notify not true");

	                }
	            } catch (MissingResourceException mre) {
	                addXMLGenMsg("FAILED",mqProperties + " " + rootInfo);
	                hasFailure = true;
	                addError("Prop file "+mqProperties + " "+rootInfo + " not found");
	            }

	        } // end mq loop
	        if (sendCnt>0 && sendCnt!=mqVct.size()){ // some went but not all
	            addXMLGenMsg("ALL_NOT_SENT", rootInfo);// {0} "Not sent to all";
	        }
	    }
	  /**********************************************************************************
     * Get the history of the ABR (XMLRECRPTABRSTATUS) in VALFROM order
     *  The current value should be “In Process?? (0050)
     *  TQ = VALFROM of this row.
     *  T2 = TQ
     * @throws MiddlewareException 
     */
    private void setT2DTS(AttributeChangeHistoryGroup xmlrecrptabrstatus) throws MiddlewareException {
      
            if (xmlrecrptabrstatus != null && xmlrecrptabrstatus.getChangeHistoryItemCount() > 1) {
                // get the historyitem count.
                int i = xmlrecrptabrstatus.getChangeHistoryItemCount();
                // Find the time stamp for "Queued" Status. Notic: last
                // chghistory is the current one(in process),-2 is queued.
                AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) xmlrecrptabrstatus.getChangeHistoryItem(i - 1);
                if (achi != null) {
                    addDebug("getT2Time [" + (i - 1) + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
                            + " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
                    if (achi.getFlagCode().equals(STATUS_PROCESS)) {
                        t2DTS = achi.getChangeDate();
                    } else {
                        addDebug("getT2Time for the value of " + achi.getFlagCode()
                                + "is not Queued, set getNow() to t2DTS and find the prior &DTFS!");
                        t2DTS = getNow();
                    }
                }
            } else {
                t2DTS = getNow();
                addDebug("getT2Time for ADSABRSTATUS changedHistoryGroup has no history, set getNow to t2DTS");
            }
        
    }
    /**
     * set instance variable ADSABRSTATUSHistory
     * @param mqAbr
     * @return
     * @throws MiddlewareException
     */
    private AttributeChangeHistoryGroup getSTATUSHistory(String attCode) throws MiddlewareException {
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
        EANAttribute att = rootEntity.getAttribute(attCode);
        if (att != null) {
            return new AttributeChangeHistoryGroup(m_db, m_prof, att);
        } else {
            addDebug( attCode + " of "+rootEntity.getKey()+ "  was null");
            return null;
        }
    }
    /**
     * CHECK whether is DateTime Stamp
     * @param dateString
     * @return
     */
    public static boolean isTimestamp(String dateString){
		boolean isValid = false;
		DateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSSSSS",Locale.ENGLISH);
		try {
			dateFormat.parse(dateString);
			isValid = true;
		} catch (ParseException e) {
			isValid = false;
		}
		return isValid;
	}
    /**
     *  restoreXtraContent
     *
     */
    private void restoreXtraContent(){
        // if written to file and still small enough, restore debug and xmlgen to the abr rpt and delete the file
        if (userxmlLen+rptSb.length()<MAXFILE_SIZE){
            // read the file in and put into the stringbuffer
            InputStream is = null;
            FileInputStream fis = null;
            BufferedReader rdr = null;
            try{
                fis = new FileInputStream(userxmlfn);
                is = new BufferedInputStream(fis);

                String s=null;
                rdr = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // append lines until done
                while((s=rdr.readLine()) !=null){
                    userxmlSb.append(ADSABRSTATUS.convertToHTML(s)+NEWLINE);
                }
                // remove the file
                File f1 = new File(userxmlfn);
                if (f1.exists()) {
                    f1.delete();
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                if (is!=null){
                    try{
                        is.close();
                    }catch(Exception x){
                        x.printStackTrace();
                    }
                }
                if (fis!=null){
                    try{
                        fis.close();
                    }catch(Exception x){
                        x.printStackTrace();
                    }
                }
            }
        }else{
            userxmlSb.append("XML generated was too large for this file");
        }
        // if written to file and still small enough, restore debug and xmlgen to the abr rpt and delete the file
        if (dbgLen+userxmlSb.length()+rptSb.length()<MAXFILE_SIZE){
            // read the file in and put into the stringbuffer
            InputStream is = null;
            FileInputStream fis = null;
            BufferedReader rdr = null;
            try{
                fis = new FileInputStream(dbgfn);
                is = new BufferedInputStream(fis);

                String s=null;
                StringBuffer sb = new StringBuffer();
                rdr = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // append lines until done
                while((s=rdr.readLine()) !=null){
                    sb.append(s+NEWLINE);
                }
                rptSb.append("<!-- "+sb.toString()+" -->"+NEWLINE);

                // remove the file
                File f1 = new File(dbgfn);
                if (f1.exists()) {
                    f1.delete();
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                if (is!=null){
                    try{
                        is.close();
                    }catch(Exception x){
                        x.printStackTrace();
                    }
                }
                if (fis!=null){
                    try{
                        fis.close();
                    }catch(Exception x){
                        x.printStackTrace();
                    }
                }
            }
        }
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
    /******************************************
     * build xml generation msg
     */
    protected void addXMLGenMsg(String rsrc, String info)
    {
        MessageFormat msgf = new MessageFormat(rsBundle.getString(rsrc));
        Object args[] = new Object[]{info};
        xmlgenSb.append(msgf.format(args)+"<br />");
    }

	/******
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#dereference()
	 */
	public void dereference(){
		super.dereference();

		rsBundle = null;
		rptSb = null;
		args = null;

		metaTbl = null;
		navName = null;
		vctReturnsEntityKeys.clear();
		vctReturnsEntityKeys = null;

		dbgPw=null;
		dbgfn = null;
	}
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
	 */
	public String getABRVersion() {
		return "$Revision: 1.12 $";
	}
	  /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "RECONCILE_MSGS"; }


	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getDescription()
	 */
	public String getDescription() {
		return "ADSIDLSTATUS";
	}
	/**********************************
	 * add msg to report output
	 * @param msg
	 */
	protected void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}

	/**********************************
	 * add debug info as html comment
	 * @param msg
	 */
	protected void addDebug(String msg) { 
		dbgLen+=msg.length();
		dbgPw.println(msg);
		dbgPw.flush();
		//rptSb.append("<!-- "+msg+" -->"+NEWLINE);
	}
	/**********************
	 * support conditional msgs
	 * @param level
	 * @param msg
	 */
	protected void addDebug(int level,String msg) { 
		if (level <= abr_debuglvl) {
			addDebug(msg);
		}
	}
	/**********************************
	 * used for error output
	 * Prefix with LD(EntityType) NDN(EntityType) of the EntityType that the ABR is checking
	 * (root EntityType)
	 *
	 * The entire message should be prefixed with 'Error: '
	 *
	 */
	protected void addError(String errCode, Object args[]){
		setReturnCode(FAIL);

		//ERROR_PREFIX = Error:  reduce size of output, do not prepend root info
		addMessage(rsBundle.getString("ERROR_PREFIX"), errCode, args);
	} 
    /**********************************
     * add error info and fail abr
     */
    protected void addError(String msg) {
        addOutput(msg);
        setReturnCode(FAIL);
    }


	/**********************************
	 * used for warning or error output
	 *
	 */
	private void addMessage(String msgPrefix, String errCode, Object args[])
	{
		String msg = rsBundle.getString(errCode);
		// get message to output
		if (args!=null){
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		addOutput(msgPrefix+" "+msg);
	}

	/**********************************************************************************
	 *  Get Name based on navigation attributes for specified entity
	 *
	 *@return java.lang.String
	 */
	private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException
	{
		StringBuffer navName = new StringBuffer();

		// NAME is navigate attributes
		// check hashtable to see if we already got this meta
		EANList metaList = (EANList)metaTbl.get(theItem.getEntityType());
		if (metaList==null)	{
			EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
			metaList = eg.getMetaAttribute();  // iterator does not maintain navigate order
			metaTbl.put(theItem.getEntityType(), metaList);
		}
		for (int ii=0; ii<metaList.size(); ii++){
			EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(),", ", "", false));
			if (ii+1<metaList.size()){
				navName.append(" ");
			}
		}

		return navName.toString().trim();
	}

	 private static class XMLMSGInfo{
			
         String setupentitytype_xml = XMLElem.CHEAT;
         String setupentityid_xml = XMLElem.CHEAT;
         String setupdts_xml = XMLElem.CHEAT;
         String msgtype_xml = XMLElem.CHEAT; 
         int  msgcount_xml;
         Vector entitylist_xml = new Vector ();


         XMLMSGInfo(
                     String setupentitytype,
                     int    setupentityid,
                     String setupdts,
                     String msgtype,
                     int msgcount
                     )

                 {
		        	 if (setupentitytype != null){
		        		 setupentitytype_xml = setupentitytype.trim();
		 			}
		 			
		 			if (setupentityid != 0){
		 				setupentityid_xml = Integer.toString(setupentityid);
		 			}
		 						
		 			if (setupdts != null){
		 				setupdts_xml = setupdts.trim();
		 			}
		 			
		             if (msgtype != null){
		            	 msgtype_xml = msgtype.trim();
		 			}
		             msgcount_xml = msgcount;
		            	 
            }
         
         void dereference(){
        	 setupentitytype_xml = null; 		
        	 setupentityid_xml = null;
        	 setupdts_xml = null;
        	 msgtype_xml = null;
        	 if (entitylist_xml!=null)
        	 entitylist_xml.clear();
        	 entitylist_xml = null;
         }

		public Vector getEntitylist_xml() {
			return entitylist_xml;
		}

		public void setEntitylist_xml(Vector entitylist_xml) {
			this.entitylist_xml = entitylist_xml;
		}

		public int getMsgcount_xml() {
			return msgcount_xml;
		}

		public void setMsgcount_xml(int msgcount_xml) {
			this.msgcount_xml = msgcount_xml;
		}
		
		String getKey(){
			return setupentitytype_xml+setupentityid_xml+setupdts_xml+msgtype_xml;
		}
	 }
}

