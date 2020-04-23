// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.ln.adsxmlbh1;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import java.util.*;
import java.sql.*;
import java.io.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;

/**********************************************************************************
*
*
XVII. General Area

select genareacode,genareaname_fc,genareaname,
sleorg,genareatype,genareaparent_fc,genareaparent,wwcoacode,isactive 
from price.generalarea 
where genareatype='Country' and nlsid=1 and Valfrom BETWEEN ? AND ?"

Deleted rows are when IsActive<>1

1 <GENERALAREA_UPDATE>		1
1 <DTSOFMSG>	</DTSOFMSG>	1	VALFROM for the ABR attribute when Queued
0..N <GENAREAELEMENT>		2
1 <ACTIVITY>	</ACTIVITY>	2	Activity
1 <GENAREACODE>	</GENAREACODE>	3	
1 <GENAREANAME_FC> </GENAREANAME_FC>	3	GENERALAREA	GENAREACODE
1 <GENAREANAME>	</GENAREANAME>	3	GENERALAREA	GENAREANAME
1 <SLEORG>	</SLEORG>	3	GENERALAREA	SLEORG
1 <GENAREATYPE> </GENAREATYPE>  3       GENERALAREA     GENAREATYPE
1 <GENAREAPARENT_FC> </GENAREAPARENT_FC> 3      GENAREAPARENT_FC     GENAREAPARENT_FC
1 <GENAREAPARENT> <GENAREAPARENT> 3     GENAREAPARENT   GENAREAPARENT 
1 <WWCOACODE>    </WWCOACODE>     3	WWCOACODE	WWCOACODE 
	</GENAREAELEMENT>	2
	</GENERALAREA_UPDATE>	1	GENERALAREA


*/
// $Log: ADSGENAREAABR.java,v $
// Revision 1.1  2015/02/04 14:55:48  wangyul
// RCQ00337765-RQ change the XML mapping to pull DIV from PROJ for Lenovo
//
// Revision 1.10  2013/12/11 08:22:40  guobin
// xsd validation for generalarea, reconcile, wwcompat and price XML
//
// Revision 1.9  2011/12/14 02:21:47  guobin
// Update the Version V Mod M for the ADSABR
//
// Revision 1.8  2011/10/17 13:40:37  guobin
// Support both 0.5 and 1.0 XML together
//
// Revision 1.7  2011/05/20 05:56:39  guobin
// change getMQPropertiesFN method
//
// Revision 1.6  2010/10/29 15:18:05  rick
// changing MQCID again.
//
// Revision 1.5  2010/10/12 19:24:55  rick
// setting new MQCID value
//
// Revision 1.4  2010/10/09 02:23:53  rick
// multiple q for IDL support
//
//Revision 1.3  2010/06/29 00:13:53  rick
//adding getStatusAttr method
//
// Revision 1.2  2010/06/24 20:28:31  rick
// BH 1.0 changes
//
//
//Revision 1.6  2010/01/07 18:04:21  wendy
//cvs failure again
//
// Revision 1.4  2008/05/28 13:46:09  wendy
// updates for spec "SG FS ABR ADS System Feed 20080528c.doc"
//
// Revision 1.3  2008/05/27 14:28:58  wendy
// Clean up RSA warnings
//
// Revision 1.2  2008/05/03 23:30:27  wendy
// Changed to support generation of large XML files
//
// Revision 1.1  2008/04/29 14:31:38  wendy
// Init for
//  -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
//  -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
//  -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
//  -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
//  -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//
public class ADSGENAREAABR extends XMLMQAdapter
{
	private static final String GENAREA_SQL = "select genareacode,genareaname_fc,genareaname,"+
                "sleorg,genareatype,genareaparent_fc,genareaparent,wwcoacode,isactive "+
                " from price.generalarea "+
		"where genareatype='Country' and nlsid=1 and Valfrom BETWEEN ? AND ?";
	

    /**********************************
    * create xml and write to queue
    */
    public void processThis(ADSABRSTATUS abr, Profile profileT1, Profile profileT2, EntityItem rootEntity)
    throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    ParserConfigurationException,
    java.rmi.RemoteException,
    COM.ibm.eannounce.objects.EANBusinessRuleException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    IOException,
    javax.xml.transform.TransformerException,
	MissingResourceException
    {
		String t1DTS = profileT1.getValOn();
		String t2DTS = profileT2.getValOn();
        String classname05 = "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSGENAREA05ABR";
		abr.addDebug("ADSGENAREAABR.processThis checking between "+t1DTS+" and "+t2DTS);
		boolean requestV05 = false;
		boolean requestV10 = false;
		String key05 = "05";
		String key10 = "10";
		Hashtable VersionmqVct = getMQPropertiesVN(rootEntity, abr);
		requestV05 = VersionmqVct.containsKey(key05);
		requestV10 = VersionmqVct.containsKey(key10);
		if (requestV05){
			try {
				XMLMQ mqAbr = (XMLMQ) Class.forName(classname05).newInstance();
				mqAbr.processThis(abr, profileT1, profileT2, rootEntity);
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw new IOException("Can not instance " + classname05 + " class!");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new IOException("Can not access " + classname05 + " class!");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new IOException("Can not find " + classname05 + " class!");
			}
		} 
		if (requestV10){
			Vector genVct = getGenarea(abr,	t1DTS, t2DTS);
			if (genVct.size()==0){
				//NO_CHANGES_FND=No Changes found for {0}
				abr.addXMLGenMsg("NO_CHANGES_FND","GENERALAREA");
			}else{
				abr.addDebug("ADSGENAREAABR.processThis found "+genVct.size()+" GENERALAREA");
				//Vector mqVct = (Vector)VersionmqVct.get(key10);
				Vector mqVct = getPeriodicMQ(rootEntity);
				if (mqVct==null){
					abr.addDebug("ADSGENAREAABR: No MQ properties files, nothing will be generated.");
					//NOT_REQUIRED = Not Required for {0}.
					abr.addXMLGenMsg("NOT_REQUIRED", "GENERALAREA");
				}else{
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document document = builder.newDocument();  // Create
					String nodeName = "GENERALAREA_UPDATE";
					String xmlns = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + nodeName;

					// Element parent = (Element) document.createElement("GENERALAREA_UPDATE");
					Element parent = (Element) document.createElementNS(xmlns,nodeName);
					// create the root
					document.appendChild(parent);
					parent.setAttributeNS("http://www.w3.org/2000/xmlns/","xmlns",xmlns);
					parent.appendChild(document.createComment("GENERALAREA_UPDATE Version "+XMLVERSION10+" Mod "+XMLMOD10));
					
	                Element elem = (Element) document.createElement("DTSOFMSG");
					elem.appendChild(document.createTextNode(profileT2.getEndOfDay()));
					parent.appendChild(elem);

					// create one GENAREAELEMENT for each one found
					for (int i=0; i<genVct.size(); i++){
						GenAreaInfo ga = (GenAreaInfo)genVct.elementAt(i);
						Element genarea = (Element) document.createElement("GENAREAELEMENT");
						parent.appendChild(genarea);

						elem = (Element) document.createElement("ACTIVITY");
						elem.appendChild(document.createTextNode(ga.isactive?XMLElem.UPDATE_ACTIVITY:XMLElem.DELETE_ACTIVITY));
						genarea.appendChild(elem);

						elem = (Element) document.createElement("GENAREACODE");
						elem.appendChild(document.createTextNode(ga.genareacode));
						genarea.appendChild(elem);

						elem = (Element) document.createElement("GENAREANAME_FC");
						elem.appendChild(document.createTextNode(ga.genareaname_fc));
						genarea.appendChild(elem);

						elem = (Element) document.createElement("GENAREANAME");
						elem.appendChild(document.createTextNode(ga.genareaname));
						genarea.appendChild(elem);
						
						elem = (Element) document.createElement("SLEORG");
						elem.appendChild(document.createTextNode(ga.sleorg));
						genarea.appendChild(elem);

	                                        elem = (Element) document.createElement("GENAREATYPE");
						elem.appendChild(document.createTextNode(ga.genareatype));
						genarea.appendChild(elem);

	                                        elem = (Element) document.createElement("GENAREAPARENT_FC");
						elem.appendChild(document.createTextNode(ga.genareaparent_fc));
						genarea.appendChild(elem);

	                                        elem = (Element) document.createElement("GENAREAPARENT");
						elem.appendChild(document.createTextNode(ga.genareaparent));
						genarea.appendChild(elem);

	                                        elem = (Element) document.createElement("WWCOACODE");
						elem.appendChild(document.createTextNode(ga.wwcoacode));
						genarea.appendChild(elem);

						// release memory
						ga.dereference();
					}

					String xml = abr.transformXML(this, document);
					
					//new added 
					boolean ifpass = false;
					String entitytype = rootEntity.getEntityType();
					String ifNeed = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS" ,"_"+entitytype+"_XSDNEEDED","NO");
					if ("YES".equals(ifNeed.toUpperCase())) {
					   String xsdfile = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS","_"+entitytype+"_XSDFILE","NONE");
					    if ("NONE".equals(xsdfile)) {
					    	abr.addError("there is no xsdfile for "+entitytype+" defined in the propertyfile ");
					    } else {
					    	long rtm = System.currentTimeMillis();
					    	Class cs = this.getClass();
					    	StringBuffer debugSb = new StringBuffer();
					    	ifpass = ABRUtil.validatexml(cs,debugSb,xsdfile,xml);
					    	if (debugSb.length()>0){
					    		String s = debugSb.toString();
								if (s.indexOf("fail") != -1)
									abr.addError(s);
								abr.addOutput(s);
					    	}
					    	long ltm = System.currentTimeMillis();
							abr.addDebugComment(D.EBUG_DETAIL, "Time for validation: "+Stopwatch.format(ltm-rtm));
					    	if (ifpass) {
					    		abr.addDebug("the xml for "+entitytype+" passed the validation");
					    	}
					    }
					} else {
						abr.addOutput("the xml for "+entitytype+" doesn't need to be validated");
						ifpass = true;
					}

					//new added end

					//add flag(new added)
					if (xml != null && ifpass) {					
					abr.addDebug("ADSGENAREAABR: Generated MQ xml:"+ADSABRSTATUS.NEWLINE+xml+ADSABRSTATUS.NEWLINE);
					abr.notify(this, "GENERALAREA", xml, mqVct);
					}
			}
				// release memory
				genVct.clear();
			}
		}
		if (!requestV05 && !requestV10){
			abr.addError("Error: Invalid Version. Can not find request Version and Mod!");
		}
    }

	private Vector getGenarea(ADSABRSTATUS abr,String t1DTS, String t2DTS)
 	throws java.sql.SQLException
	{
		Vector rootVct = new Vector();
        ResultSet result=null;
		Connection connection=null;
		PreparedStatement statement = null;
        try {
            connection = setupConnection();
			statement = connection.prepareStatement(GENAREA_SQL);

            //statement.clearParameters();
            statement.setString(1, t1DTS);//"time1"
            statement.setString(2, t2DTS);//"time2"

            result = statement.executeQuery();
            while(result.next()) {
				String code = result.getString(1);
				String name_fc = result.getString(2);
				String name = result.getString(3);
				String org = result.getString(4);
                                String type = result.getString(5);
                                String parent_fc = result.getString(6);
                                String parent = result.getString(7); 
                                String wwcoa = result.getString(8); 
				int active = result.getInt(9);

				abr.addDebug("getGenarea code:"+code+" name_fc:"+name_fc+" name:"+name+" org:"+org+
                                " type:"+type+" parent_fc:"+parent_fc+" parent:"+parent+" wwcoa:"+wwcoa+
                                " active:"+active);
				rootVct.add(new GenAreaInfo(code, name_fc, name, org, type, parent_fc, parent, wwcoa, active));

            }
        }
        finally{
			try {
				if (statement!=null) {
					statement.close();
					statement=null;
				}
			}catch(Exception e){
				System.err.println("getGenarea(), unable to close statement. "+ e);
				abr.addDebug("getGenarea unable to close statement. "+e);
			}
            if (result!=null){
                result.close();
            }
            closeConnection(connection);
        }
		return rootVct;
	}

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion() {
        return "1.6";//"1.4";
    }

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "GENERALAREA_UPDATE"; }
    
    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr() { return "ADSABRSTATUS";}

    private static class GenAreaInfo{
		String genareacode = XMLElem.CHEAT;
		String genareaname_fc = XMLElem.CHEAT;
                String genareaname = XMLElem.CHEAT;
		String sleorg = XMLElem.CHEAT;
                String genareatype = XMLElem.CHEAT;
                String genareaparent_fc = XMLElem.CHEAT; 
                String genareaparent = XMLElem.CHEAT;
                String wwcoacode = XMLElem.CHEAT; 
		boolean isactive = false;
		GenAreaInfo(String code, String name_fc, String name, String org, String type, String parent_fc,
                            String parent, String wwcoa, int active){
			if (code != null){
				genareacode = code.trim();
			}
			if (name_fc != null){
				genareaname_fc = name_fc.trim();
			}
			if (name != null){
				genareaname = name.trim();
			}
			if (org != null){
				sleorg = org.trim();
			}
                        if (type != null){
				genareatype = type.trim();
			}
                        if (parent_fc != null){
				genareaparent_fc = parent_fc.trim();
                        }
                        if (parent != null){
				genareaparent = parent.trim();
                        }
                        if (wwcoa != null){
				wwcoacode = wwcoa.trim();
                        }
			isactive = (active==1);

		}
		void dereference(){
			genareacode = null;
			genareaname_fc = null;
			genareaname = null;
			sleorg = null;
                        genareatype = null;
                        genareaparent_fc = null;
                        wwcoacode = null; 
		}
	}
}
