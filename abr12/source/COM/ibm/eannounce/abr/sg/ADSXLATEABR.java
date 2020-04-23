// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;

import java.util.*;
import java.sql.*;
import java.io.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
/**********************************************************************************
* This is an extract and feed of Meta Data with the source being MetaDescription table.

The following attributes (DESCRIPTIONTYPE) are to be included:
-	OS Level (OSLEVEL)
-	Warranty Period (WARRPRIOD)
-	Warranty Type (WARRTYPE)

*
1	<XLATE_UPDATE>	1	METADESCRIPTION
1	<DTSOFMSG>	</DTSOFMSG>	2	setup entity	ABR Queued	DTS of ABR Queued
1..N	<XLATEELEMENT>		2	METADESCRIPTION
1	<DTSOFUPDATE>	</DTSOFUPDATE>	3	METADESCRIPTION	VALFROM
1	<ATTRIBUTECODE>	</ATTRIBUTECODE>	3	METADESCRIPTION	AttributeCode	WARRPRIOD | WARRTYPE | OSLEVEL
1	<NLSID>	</NLSID>	3	METADESCRIPTION	NLSID
1	<DESCRIPTIONCLASS>	</DESCRIPTIONCLASS>	3	METADESCRIPTION	DESCRIPTIONCLASS
1	<LONGDESCRIPTION>	</LONGDESCRIPTION>	3	METADESCRIPTION	LONGDESCRIPTION
		</XLATEELEMENT>	2
		</XLATE_UPDATE>	1

*/
// ADSXLATEABR.java,v
// Revision 1.4  2008/05/28 13:46:07  wendy
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
public class ADSXLATEABR extends XMLMQAdapter
{
	private static final String ADSATTRIBUTE = "ADSATTRIBUTE";
	private static final String XLATE_SQL ="select descriptionclass,valfrom,"+
		"longdescription, nlsid from opicm.metadescription "+
		"where valto>current timestamp and enterprise=? and descriptiontype=? and Valfrom between ? and ? and nlsid in( ";

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

		EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute(ADSATTRIBUTE);
		if (metaAttr==null) {
			throw new MiddlewareException(ADSATTRIBUTE+" not in meta for Periodic ABR "+rootEntity.getKey());
		}

		String adsattrval = PokUtils.getAttributeValue(rootEntity, ADSATTRIBUTE,", ", "", false);
		abr.addDebug("ADSXLATEABR.processThis checking "+ADSATTRIBUTE+" "+adsattrval+" between "+t1DTS+" and "+t2DTS);
		// find xlated
		Vector xlateVct = getXlated(abr,profileT2.getEnterprise(),adsattrval, t1DTS, t2DTS);
		if (xlateVct.size()==0){
			//NO_CHANGES_FND=No Changes found for {0}
			abr.addXMLGenMsg("NO_CHANGES_FND","XLATE");
		}else{
			abr.addDebug("ADSXLATEABR.processThis found "+xlateVct.size()+" XLATE");

			Vector mqVct = getMQPropertiesFN();
			if (mqVct==null){
				abr.addDebug("ADSXLATEABR: No MQ properties files, nothing will be generated.");
				//NOT_REQUIRED = Not Required for {0}.
				abr.addXMLGenMsg("NOT_REQUIRED", "XLATE");
			}else{
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.newDocument();  // Create

				Element parent = (Element) document.createElement("XLATE_UPDATE");
				// create the root
				document.appendChild(parent);
				Element elem = (Element) document.createElement("DTSOFMSG");
				elem.appendChild(document.createTextNode(profileT2.getEndOfDay()));
				parent.appendChild(elem);

				// create one XLATEELEMENT for each one found
				for (int i=0; i<xlateVct.size(); i++){
					XlateInfo ga = (XlateInfo)xlateVct.elementAt(i);
					Element xlated = (Element) document.createElement("XLATEELEMENT");
					parent.appendChild(xlated);

					elem = (Element) document.createElement("DTSOFUPDATE");
					elem.appendChild(document.createTextNode(ga.valfrom));
					xlated.appendChild(elem);

					elem = (Element) document.createElement("ATTRIBUTECODE");
					elem.appendChild(document.createTextNode(adsattrval));
					xlated.appendChild(elem);

					elem = (Element) document.createElement("NLSID");
					elem.appendChild(document.createTextNode(""+ga.nlsid));
					xlated.appendChild(elem);

					elem = (Element) document.createElement("DESCRIPTIONCLASS");
					elem.appendChild(document.createTextNode(ga.descriptionclass));
					xlated.appendChild(elem);

					elem = (Element) document.createElement("LONGDESCRIPTION");
					elem.appendChild(document.createTextNode(ga.longdesc));
					xlated.appendChild(elem);

					// release memory
					ga.dereference();
				}

				String xml = abr.transformXML(this, document);
				abr.addDebug("ADSXLATEABR: Generated MQ xml:"+ADSABRSTATUS.NEWLINE+xml+ADSABRSTATUS.NEWLINE);
				abr.notify(this, "XLATE", xml);
			}

			// release memory
			xlateVct.clear();
		}
    }

	private Vector getXlated(ADSABRSTATUS abr,String enterprise, String attrcode, String t1DTS, String t2DTS)
 	throws java.sql.SQLException
	{
		Vector rootVct = new Vector();
        ResultSet result=null;
		Connection connection=null;
		PreparedStatement statement = null;
        try {
            connection = setupConnection();
            StringBuffer sqlSb = new StringBuffer(XLATE_SQL);
            Vector nlsidVct = new Vector(1);
            // add nlsids
			String nlsids = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getNLSIDs(abr.getABRAttrCode());
			StringTokenizer st1 = new StringTokenizer(nlsids,",");
			while (st1.hasMoreTokens()) {
				String nlsid = st1.nextToken();
				nlsidVct.add(nlsid);
				sqlSb.append("?");
				if (st1.hasMoreTokens()){
					sqlSb.append(",");
				}
			}
			sqlSb.append(") order by descriptionclass");

			statement = connection.prepareStatement(sqlSb.toString());

            //statement.clearParameters();
            statement.setString(1, enterprise);//"enterprise"
            statement.setString(2, attrcode);//"attrcode"
            statement.setString(3, t1DTS);//"time1"
            statement.setString(4, t2DTS);//"time2"
            for (int i=0; i<nlsidVct.size(); i++){
				statement.setInt(5+i, Integer.parseInt(nlsidVct.elementAt(i).toString()));
			}

            result = statement.executeQuery();
            while(result.next()) {
				String desc = result.getString(1);
				String valfrom = result.getString(2);
				String longdesc = result.getString(3);
				int nlsid = result.getInt(4);
				abr.addDebug("getXlated desc:"+desc+" valfrom:"+valfrom+" longdesc:"+longdesc+" nlsid:"+nlsid);
				rootVct.add(new XlateInfo(desc, valfrom, longdesc, nlsid));
            }

            nlsidVct.clear();
        }
        finally{
			try {
				if (statement!=null) {
					statement.close();
					statement=null;
				}
			}catch(Exception e){
				System.err.println("getXlated(), unable to close statement. "+ e);
				abr.addDebug("getXlated unable to close statement. "+e);
			}
            if (result!=null){
                result.close();
            }
            closeConnection(connection);
        }
		return rootVct;
	}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "XLATEMETA"; }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "1.4";
    }

    private static class XlateInfo{
		String descriptionclass = XMLElem.CHEAT;
		String valfrom = XMLElem.CHEAT;
		String longdesc = XMLElem.CHEAT;
		int nlsid =1;
		XlateInfo(String desc, String vfrom, String ldesc, int nid){
			if (desc != null){
				descriptionclass = desc.trim();
			}
			if (vfrom != null){
				valfrom = vfrom.trim();
			}
			if (ldesc != null){
				longdesc = ldesc.trim();
			}
			nlsid = nid;
		}
		void dereference(){
			descriptionclass = null;
			valfrom = null;
			longdesc = null;
		}
	}
}
