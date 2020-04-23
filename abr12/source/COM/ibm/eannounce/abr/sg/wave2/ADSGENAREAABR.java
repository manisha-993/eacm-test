// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.wave2;

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

select genareaname_fc as descriptionclass,
genareacode,
genareaname, sleorg, isactive
from price.generalarea
where genareatype='Country' and nlsid=1 and valfrom> '1979-01-01 00:00:00.000000'

Deleted rows are when IsActive<>1

1 <GENERALAREA_UPDATE>		1
0..N <GENAREAELEMENT>		2
1 <DTSOFMSG>	</DTSOFMSG>	2				VALFROM for the ABR attribute when Queued
1 <ACTIVITY>	</ACTIVITY>		2	Activity
1 <DESCRIPTIONCLASS>	</DESCRIPTIONCLASS>	3	METADESCRIPTION	DESCRIPTIONCLASS		where DESCRIPTIONTYPE='COUNTRYLIST'
1 <GENAREACODE>	</GENAREACODE>	3	GENERALAREA	GENAREACODE
1 <GENAREANAME>	</GENAREANAME>	3	GENERALAREA	GENAREANAME
1 <SLEORG>	</SLEORG>	3	GENERALAREA	SLEORG
	</GENAREAELEMENT>	2
	</GENERALAREA_UPDATE>	1	GENERALAREA


*/
// ADSGENAREAABR.java,v
//ADSGENAREAABR.java,v
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
	private static final String GENAREA_SQL ="select genareaname_fc as descriptionclass,genareacode,"+
		"genareaname, sleorg, isactive from price.generalarea "+
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

		abr.addDebug("ADSGENAREAABR.processThis checking between "+t1DTS+" and "+t2DTS);
		// find GENAREA
		Vector genVct = getGenarea(abr,	t1DTS, t2DTS);
		if (genVct.size()==0){
			//NO_CHANGES_FND=No Changes found for {0}
			abr.addXMLGenMsg("NO_CHANGES_FND","GENERALAREA");
		}else{
			abr.addDebug("ADSGENAREAABR.processThis found "+genVct.size()+" GENERALAREA");

			Vector mqVct = getMQPropertiesFN();
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

				// create one GENAREAELEMENT for each one found
				for (int i=0; i<genVct.size(); i++){
					GenAreaInfo ga = (GenAreaInfo)genVct.elementAt(i);
					Element genarea = (Element) document.createElement("GENAREAELEMENT");
					parent.appendChild(genarea);

					Element elem = (Element) document.createElement("DTSOFMSG");
					elem.appendChild(document.createTextNode(profileT2.getEndOfDay()));
					genarea.appendChild(elem);

					elem = (Element) document.createElement("ACTIVITY");
					elem.appendChild(document.createTextNode(ga.isactive?XMLElem.UPDATE_ACTIVITY:XMLElem.DELETE_ACTIVITY));
					genarea.appendChild(elem);

					elem = (Element) document.createElement("DESCRIPTIONCLASS");
					elem.appendChild(document.createTextNode(ga.descriptionclass));
					genarea.appendChild(elem);

					elem = (Element) document.createElement("GENAREACODE");
					elem.appendChild(document.createTextNode(ga.genareacode));
					genarea.appendChild(elem);

					elem = (Element) document.createElement("GENAREANAME");
					elem.appendChild(document.createTextNode(ga.genareaname));
					genarea.appendChild(elem);
					
					elem = (Element) document.createElement("SLEORG");
					elem.appendChild(document.createTextNode(ga.sleorg));
					genarea.appendChild(elem);
					// release memory
					ga.dereference();
				}

				String xml = abr.transformXML(this, document);
				abr.addDebug("ADSGENAREAABR: Generated MQ xml:"+ADSABRSTATUS.NEWLINE+xml+ADSABRSTATUS.NEWLINE);
				abr.notify(this, "GENERALAREA", xml);
			}

			// release memory
			genVct.clear();
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
				String desc = result.getString(1);
				String code = result.getString(2);
				String name = result.getString(3);
				String org = result.getString(4);
				int active = result.getInt(5);
				abr.addDebug("getGenarea desc:"+desc+" code:"+code+" name:"+name+" org:"+org+" active:"+active);
				rootVct.add(new GenAreaInfo(desc, code, name, org, active));
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
    public String getMQCID() { return "GENERALAREA"; }

    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr() { return "ADSABRSTATUS";}

    private static class GenAreaInfo{
		String descriptionclass = XMLElem.CHEAT;
		String genareacode = XMLElem.CHEAT;
		String genareaname = XMLElem.CHEAT;
		String sleorg = XMLElem.CHEAT;
		boolean isactive = false;
		GenAreaInfo(String desc, String code, String name, String org, int active){
			if (desc != null){
				descriptionclass = desc.trim();
			}
			if (code != null){
				genareacode = code.trim();
			}
			if (name != null){
				genareaname = name.trim();
			}
			if (org != null){
				sleorg = org.trim();
			}
			isactive = (active==1);
		}
		void dereference(){
			descriptionclass = null;
			genareacode = null;
			genareaname = null;
			sleorg = null;
		}
	}
}
