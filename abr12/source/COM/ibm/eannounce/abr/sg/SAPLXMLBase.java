// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;

import java.util.*;
import java.text.*;
import java.io.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

/**********************************************************************************
* SAPLXMLBase class classes will be derived from this for each entity type
* SAPLABRSTATUS will launch the correct class
* From "SG FS ABR Data Quality 20070830.doc" and "SG FS ABR SAPL 20070830.doc"
* This is now queued from DQ ABRs
* must use SG enterprise and GFS role to access SAPL attributes bertilc@us.ibm.com on xea6
*
*/
// SAPLXMLBase.java,v
// Revision 1.3  2008/01/30 19:39:16  wendy
// Cleanup RSA warnings
//
// Revision 1.2  2008/01/09 18:12:00  wendy
// Added support for LSCC - RQ0117074421
//
// Revision 1.1  2007/09/13 12:40:28  wendy
// Init for RQ0426071527 - XCC GX
//
public abstract class SAPLXMLBase
{
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
    private static final Hashtable SAPL_TRANS_TBL;

	protected static final String XMLNS_WSNT ="http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd";
	protected static final String XMLNS_EBI = "http://ibm.com/esh/ebi";

  	protected final static String ESHMQSERIES = "ESHMQSERIES";
  	protected final static String OIDHMQSERIES = "OIDHMQSERIES";
  	private String failedStr = "Failed";  // hang onto this so dont have to get over and over
	private String xmlgen = "";  // set to Success or Failed or left blank if not required
	private boolean checkSAPLattr = true; // some roots do not have SAPL attribute at all //RQ0117074421

	protected SAPLABRSTATUS saplAbr = null;
/*
SAPL	10	Not Ready
SAPL	20	Ready
SAPL	30	Send
SAPL	40	Sent
SAPL	70	Update
SAPL	80	Update Sent
SAPL	90	N/A
*/
	static {
/*
Current Value	Set Value	Comment
Ready			Sent		First Time - ABR is successful
Send			Sent		First Time - Failed to Receive - Manual Trigger
Sent			Update Sent	Received Once - Changed Data - Auto Trigger
Update Sent		Update		Received Once - send again
*/
		SAPL_TRANS_TBL = new Hashtable();
		SAPL_TRANS_TBL.put("20", "40");  // Ready->Sent
		SAPL_TRANS_TBL.put("30", "40");  // Send->Sent
		SAPL_TRANS_TBL.put("40", "80");  // Sent->Update Sent
		SAPL_TRANS_TBL.put("80", "70");  // Update Sent->Update
	}

	protected SAPLXMLBase() {} //RQ0117074421
	protected SAPLXMLBase(boolean hasSAPLattr) {checkSAPLattr = hasSAPLattr;} //RQ0117074421

    /**
     *  Execute ABR.
     *
     */
    public boolean execute_run(SAPLABRSTATUS sapl) throws Exception
    {
		saplAbr = sapl;
        failedStr = saplAbr.getBundle().getString("FAILED"); // "Failed"
        return checkSaplAndSendXML();
    }
    public String getXMLGenMsg() { return xmlgen; }

	/**********************************
	* get the name(s) of the MQ properties file to use, could be more than one
	*/
	protected abstract Vector getMQPropertiesFN();

	/**********************************
	* get the name of the VE to use for the feed
	*/
	protected abstract String getSaplVeName();

	/**********************************
	* get SAPL xml objects
	*/
	protected abstract Vector getSAPLXMLMap();

	/**********************************
	* stringbuffer used for report output
	*/
	protected void addOutput(String msg) { saplAbr.addOutput(msg);}

	/**********************************
	* stringbuffer used for report output
	*/
	protected void addDebug(String msg) { saplAbr.addDebug(msg);}

	/**********************************
	* If the attribute STATUS is 'Final' and the attribute SAPL is any one of the 'Current Value'
	* in the table below, then XML is generated and placed on a MQ-Series queue for OIDH.
	* After the XML is successfully placed on the MQ-Series queue (OIDH or ESH or both), the ABR updates
	* attribute SAPL to the 'Set Value' based on its 'Current Value' as follows:
	*
	*Current Value	Set Value	Comment
	*Ready			Sent		First Time - ABR is successful
	*Send			Sent		First Time - Failed to Receive - Manual Trigger
	*Sent			Update		Received Once - Changed Data - Auto Trigger
	*Update 		Update		Received Once - send again
	*/
	private boolean checkSaplAndSendXML() throws
		java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		ParserConfigurationException,
		javax.xml.transform.TransformerException,
		COM.ibm.eannounce.objects.EANBusinessRuleException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		IOException
	{
		boolean sendOk = false;
		if (checkSAPLattr){ //RQ0117074421
			EntityItem rootEntity = saplAbr.getEntityList().getParentEntityGroup().getEntityItem(0);
			// if meta does not have this attribute, there is nothing to do
			EANMetaAttribute metaAttr = saplAbr.getEntityList().getParentEntityGroup().getMetaAttribute("SAPL");
			if (metaAttr==null) {
				addDebug("SAPL was not in meta, no xml generation done");
			}else{
				// check value of SAPL attribute
				String curVal = PokUtils.getAttributeFlagValue(rootEntity, "SAPL");
				if (curVal==null){
					curVal = "10"; // default to 'Not Ready'
				}

				// if current val is not in table then don't process
				String setValue = (String)SAPL_TRANS_TBL.get(curVal);
				if (setValue==null){
					Object[] args = new String[1];
					addDebug("SAPL current value is not in list ["+curVal+"]");
					//SAPL_WRONG_VALUE = Status was &quot;Final&quot; but SAPL was &quot;{0}&quot; so XML was not generated.
					MessageFormat msgf = new MessageFormat(saplAbr.getBundle().getString("SAPL_WRONG_VALUE"));
					args[0] = PokUtils.getAttributeValue(rootEntity, "SAPL",", ", "", false);
					addOutput("<p>"+msgf.format(args)+ "</p>");
					xmlgen = failedStr;
				}else{
					sendOk = doSaplXmlFeed();
				}
			}
		}else{ // LSCC does not have SAPL attribute
			sendOk = doSaplXmlFeed();
		}

		return sendOk;
	}
	/**********************************
	* PRODSTRUCTSAPLXML needs to pull 2 VEs.  MODELPROJA->PROJ is not returned when PRODSTRUCT is root
	* TIR72PGQ9
	*/
	protected void mergeLists(EntityList theList) throws
		java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
	{}

	/**********************************
	* build and feed the xml
	* After the XML is successfully placed on the MQ-Series queue (OIDH or ESH or both),
	* the ABR updates attribute SAPL to the 'Set Value' based on its 'Current Value'
	*/
	private boolean doSaplXmlFeed() throws
		java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		ParserConfigurationException,
		javax.xml.transform.TransformerException,
		COM.ibm.eannounce.objects.EANBusinessRuleException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		IOException
	{
		boolean sendOk = false;
		Object[] args = new String[3];
		MessageFormat msgf = null;
		try{
			Vector mqVct = getMQPropertiesFN();
			Vector saplVct = getSAPLXMLMap(); // get list of sap elements
			if (mqVct==null){
				addDebug("No MQ properties files, nothing will be generated.");
			}else if (saplVct==null){
				addDebug("No SAPL XML Mappings, nothing will be generated.");
			}else{
				String xml = generateXML();
				int sendCnt=0;
				addDebug("Generated xml:"+NEWLINE+xml+NEWLINE);

				// write to each queue
				for (int i=0; i<mqVct.size(); i++){
					String mqProperties = (String)mqVct.elementAt(i);
					ResourceBundle rsBundleMQ = ResourceBundle.getBundle(mqProperties,
						SAPLABRSTATUS.getLocale(saplAbr.getEntityList().getProfile().getReadLanguage().getNLSID()));
					Hashtable ht = MQUsage.getMQSeriesVars(rsBundleMQ);
					boolean bNotify = ((Boolean)ht.get(MQUsage.NOTIFY)).booleanValue();

					if (bNotify) {
						try{
/*

2007-04-30-13.49.17.199000 pokxea6@taskmaster.sg  putToMQ method:::1414:pokxopi1
.pok.ibm.com:EACM.CLIENT:POKXOPI1:RDX.OIDH.EACM.DATA.OUT
2007-04-30-13.49.17.199000 pokxea6@taskmaster.sg  putToMQ method creating a connection to the queue manager
An error occurred whilst writing to the message buffer: java.io.UTFDataFormatException
java.io.UTFDataFormatException
        at java.io.DataOutputStream.writeUTF(DataOutputStream.java:349)
        at com.ibm.mq.MQMessage.writeUTF(MQMessage.java:1584)
        at COM.ibm.eannounce.objects.MQUsage.putToMQQueue(MQUsage.java:195)
        at COM.ibm.eannounce.abr.sg.SAPLXMLBase.doSaplXmlFeed(SAPLXMLBase.java:489)
        at COM.ibm.eannounce.abr.sg.SAPLXMLBase.checkSaplAndSendXML(SAPLXMLBase.java:445)
        at COM.ibm.eannounce.abr.sg.SAPLXMLBase.execute_run(SAPLXMLBase.java:281)
        at COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask.execute(AbstractTask.java:467)
        at COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask.run(AbstractTask.java:765)
        at COM.ibm.opicmpdh.middleware.taskmaster.Runner.run(Runner.java:54)


DataOutputStream.writeUTF has a calculated length limit of 65535
this snippet is from 1.4.0 java source code
        int strlen = str.length();
        int utflen = 0;
        int c, count = 0;

        for (int i = 0 ; i < strlen ; i++) {
            c = str.charAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                utflen++;
            } else if (c > 0x07FF) {
                utflen += 3;
            } else {
                utflen += 2;
            }
        }

        if (utflen > 65535)
            throw new UTFDataFormatException();
*/
							MQUsage.putToMQQueue("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xml, ht);
							//SENT_SUCCESS = XML was generated and sent successfully for {0}.
							msgf = new MessageFormat(saplAbr.getBundle().getString("SENT_SUCCESS"));
							args[0] = mqProperties;
							addOutput("<p>"+msgf.format(args)+ "</p>");
							sendCnt++;
							if (!xmlgen.equals(failedStr)){  // dont overwrite a failed notice
								xmlgen = saplAbr.getBundle().getString("SUCCESS");//"Success";
							}
						}catch (com.ibm.mq.MQException ex) {
							//MQ_ERROR = Error: An MQSeries error occurred for {0}: Completion code {1} Reason code {2}.
							xmlgen = failedStr;
							msgf = new MessageFormat(saplAbr.getBundle().getString("MQ_ERROR"));
							args[0] = mqProperties;
							args[1] = ""+ex.completionCode;
							args[2] = ""+ex.reasonCode;
							addOutput("<p>"+msgf.format(args)+ "</p>");
							ex.printStackTrace(System.out);
						} catch (java.io.IOException ex) {
							//MQIO_ERROR = Error: An error occurred when writing to the MQ message buffer for {0}: {1}
							xmlgen = failedStr;
							msgf = new MessageFormat(saplAbr.getBundle().getString("MQIO_ERROR"));
							args[0] = mqProperties;
							args[1] = ex.toString();
							addOutput("<p>"+msgf.format(args)+ "</p>");
							ex.printStackTrace(System.out);
						}
					}else{
						//NO_NOTIFY = XML was generated but NOTIFY was false in the {0} properties file.
						msgf = new MessageFormat(saplAbr.getBundle().getString("NO_NOTIFY"));
						args[0] = mqProperties;
						addOutput("<p>"+msgf.format(args)+ "</p>");
						xmlgen = saplAbr.getBundle().getString("NOT_SENT");//"Not sent";
					}
				}
				if (sendCnt==mqVct.size()){ // all mq msgs sent ok
					sendOk = true;
				}else{
					if (sendCnt>0){ // some went but not all
						xmlgen = saplAbr.getBundle().getString("ALL_NOT_SENT");//"Not sent to all";
					}
					if (checkSAPLattr){
						addDebug("SAPL was not updated because all MQ msgs were not sent successfully");
					}
				}
			}
		}catch(IOException ioe){
			// only get this if a required node was not populated
			//REQ_ERROR = Error: {0}
			msgf = new MessageFormat(saplAbr.getBundle().getString("REQ_ERROR"));
			args[0] = ioe.getMessage();
			addOutput("<p>"+msgf.format(args)+ "</p>");
			xmlgen = failedStr;
		}catch(java.sql.SQLException x){
			xmlgen = failedStr;
			throw x;
		}catch(COM.ibm.opicmpdh.middleware.MiddlewareRequestException x){
			xmlgen = failedStr;
			throw x;
		}catch(COM.ibm.opicmpdh.middleware.MiddlewareException x){
			xmlgen = failedStr;
			throw x;
		}catch(ParserConfigurationException x){
			xmlgen = failedStr;
			throw x;
		}catch(javax.xml.transform.TransformerException x){
			xmlgen = failedStr;
			throw x;
		}
		return sendOk;
	}

	/**********************************
	* generate the xml based on new extract using this profile
	* The 'Profile' of the user that caused the ABR to run will provide Role authority
	* and NLSID Read (OPWG.NLSREAD).The XML Tags are always in US English (NLSID=1) and
	* the data is US English unless otherwise noted (e.g. MKTGNAME). If the content of a
	* Tag is NLSID sensitive, then NLSREAD is used to obtain the additional values. 'Fall Back'
	* to US English should not be supported. All countries should be handled (see below).
	*/
	private String generateXML() throws
		java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		ParserConfigurationException,
		javax.xml.transform.TransformerException,
		COM.ibm.eannounce.objects.EANBusinessRuleException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		IOException
	{
		EntityList eList = saplAbr.getEntityList();
		Profile profile = saplAbr.getEntityList().getProfile();
		//String saplVeName = getSaplVeName();
		Vector saplVct = getSAPLXMLMap(); // get list of sap elements

		// get VE for xml
		// prodstuctsaplxml needs to merge to VEs
		mergeLists(eList);

		//Wayne Kehrli	Base message is NLSID=1
        profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();  // Create

        Element root = null;

		StringBuffer debugSb = new StringBuffer();
		for(int i=0; i<saplVct.size(); i++){
			SAPLElem sapl = (SAPLElem)saplVct.elementAt(i);
			//rptSb.append("<pre>"+sapl+"</pre>\n");
			sapl.addElements(saplAbr.getDB(),eList, document, root,debugSb);
		}
		addDebug("GenXML debug: "+NEWLINE+debugSb.toString());

		//Output the XML

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
		String xmlString = SAPLElem.removeCheat(sw.toString());

		if (eList!=saplAbr.getEntityList()){ // got a new one, so deref here
			eList.dereference();
		}

		return xmlString;
	}

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public abstract String getVersion();
}
