// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

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
//$Log: ADSXLATEABR.java,v $
//Revision 1.12  2013/01/16 16:08:11  wangyulo
//correct the MQCID
//
//Revision 1.11  2012/07/17 16:56:38  wangyulo
//add the switch to turn on or turn off to generate the userxml file for the periodic ABR
//
//Revision 1.10  2011/12/14 02:27:04  guobin
//Update the Version V Mod M for the ADSABR
//
//Revision 1.9  2011/08/11 08:01:18  guobin
//update the xlateVct value of ADSXLATEABR
//
//Revision 1.8  2011/05/20 05:56:59  guobin
//change getMQPropertiesFN method
//
//Revision 1.7  2011/04/14 05:20:39  guobin
//update for the NLSID and adsattribute
//
//Revision 1.6  2011/02/28 08:52:53  guobin
//update for XLATE_UPDATE element tag
//
//Revision 1.5  2011/02/25 14:42:32  guobin
//add the log and print the querySql
//
//Revision 1.4  2011/02/17 09:36:46  guobin
//add the comments for the ADSXLATEABR.java
//
// Init for
//updates for spec "SG FS ABR ADS System Feed 20080528c.doc"
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
	private static final String ADSNLSID = "ADSNLSID";
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

		//String adsattrval = PokUtils.getAttributeValue(rootEntity, ADSATTRIBUTE,", ", "", false);		
		String adsattrval = getShortDescription(rootEntity, ADSATTRIBUTE,"short");
		String nlsid = getShortDescription(rootEntity, ADSNLSID,"flag");
				
		abr.addDebug("ADSXLATEABR.processThis checking rootEntity ="+rootEntity.getKey() + ",Attribute of " + ADSATTRIBUTE+"="+adsattrval+" between "+t1DTS+" and "+t2DTS);
		abr.addDebug("ADSXLATEABR.processThis checking rootEntity ="+rootEntity.getKey() + ",Attribute of " + ADSNLSID+"="+nlsid);
		
		// find xlated
		Vector xlateVct = new Vector();
		if(!nlsid.equals("")){
			xlateVct = getXlated(abr,profileT2.getEnterprise(),adsattrval, t1DTS, t2DTS,nlsid);
		}
		
		if (xlateVct.size()==0){
			//NO_CHANGES_FND=No Changes found for {0}
			abr.addXMLGenMsg("NO_CHANGES_FND","XLATE");
		}else{
			abr.addDebug("ADSXLATEABR.processThis found "+xlateVct.size()+" XLATE");

//			Vector mqVct = getMQPropertiesFN(rootEntity,abr);
			Vector mqVct = getPeriodicMQ(rootEntity);
			
			if (mqVct==null){
				abr.addDebug("ADSXLATEABR: No MQ properties files, nothing will be generated.");
				//NOT_REQUIRED = Not Required for {0}.
				abr.addXMLGenMsg("NOT_REQUIRED", "XLATE");
			}else{
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.newDocument();  // Create

				Element parent = (Element) document.createElementNS("http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/XLATE_UPDATE","XLATE_UPDATE");
				parent.appendChild(document.createComment("XLATE_UPDATE Version "+XMLVERSION10+" Mod "+XMLMOD10));
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
				if(!ADSABRSTATUS.USERXML_OFF_LOG){
					abr.addDebug("ADSXLATEABR: Generated MQ xml:"+ADSABRSTATUS.NEWLINE+xml+ADSABRSTATUS.NEWLINE);
				}
				abr.notify(this, "XLATE", xml,mqVct);
			}

			// release memory
			xlateVct.clear();
		}
    }

    /**
     * get the description of the item
     * @param item
     * @param code
     * @return
     */
	private String getShortDescription(EntityItem item, String code,String type) {
		String value=""; 
		EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute(code);
        if (fAtt!=null && fAtt.toString().length()>0){
            // Get the selected Flag codes.
            MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mfArray.length; i++){
                // get selection            	
                if (mfArray[i].isSelected())
                {
                	if (sb.length()>0) {
                        sb.append(","); 
                    }
                	if(type.equals("short")) {
                		sb.append(mfArray[i].getShortDescription());
                	}
                	else if(type.equals("flag")) {
                		sb.append(mfArray[i].getFlagCode());                	
                	}
                	else{
                		sb.append(mfArray[i].toString());
                	}
                }                
            }//
            value = sb.toString();
        }
        return value;
	}

	private Vector getXlated(ADSABRSTATUS abr,String enterprise, String attrcode, String t1DTS, String t2DTS, String snlsid)
 	throws java.sql.SQLException
	{
		Vector rootVct = new Vector();
        ResultSet result=null;
		Connection connection=null;
		PreparedStatement statement = null;
        try {
            connection = setupConnection();
            StringBuffer sqlSb = new StringBuffer(XLATE_SQL);
            
            sqlSb.append(snlsid);
			sqlSb.append(") order by descriptionclass");

			statement = connection.prepareStatement(sqlSb.toString());
			
			StringBuffer sqlPrint = new StringBuffer();
			sqlPrint.append(" select descriptionclass,valfrom,longdescription, nlsid from opicm.metadescription");
			sqlPrint.append(" where valto>current timestamp and enterprise='"+enterprise+"'");
			sqlPrint.append(" and descriptiontype='"+attrcode+ "'");
			sqlPrint.append(" and Valfrom between '"+t1DTS+ "'");
			sqlPrint.append(" and '"+t2DTS+"'");
			sqlPrint.append(" and nlsid in(");

            //statement.clearParameters();
            statement.setString(1, enterprise);//"enterprise"
            statement.setString(2, attrcode);//"attrcode"
            statement.setString(3, t1DTS);//"time1"
            statement.setString(4, t2DTS);//"time2"
            sqlPrint.append(snlsid);
            sqlPrint.append(") order by descriptionclass");
            abr.addDebug("ADSXLATEABR.processThis sqlPrint= " + ADSABRSTATUS.NEWLINE + sqlPrint.toString()+ ADSABRSTATUS.NEWLINE);
            
            result = statement.executeQuery();
            while(result.next()) {
				String desc = result.getString(1);
				String valfrom = result.getString(2);
				String longdesc = result.getString(3);
				int nlsid = result.getInt(4);
				if(!ADSABRSTATUS.USERXML_OFF_LOG){
					abr.addDebug("getXlated desc:"+desc+" valfrom:"+valfrom+" longdesc:"+longdesc+" nlsid:"+nlsid);
				}
				rootVct.add(new XlateInfo(desc, valfrom, longdesc, nlsid));
            }
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
    public String getMQCID() { return "XLATE_UPDATE"; }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "1.2";
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
