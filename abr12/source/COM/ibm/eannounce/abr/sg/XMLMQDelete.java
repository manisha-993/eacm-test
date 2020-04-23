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
* this is the periodic abr that looks for deleted entities
*
*/
// XMLMQDelete.java,v
// Revision 1.3  2008/05/27 14:28:58  wendy
// Clean up RSA warnings
//
// Revision 1.2  2008/05/03 23:29:32  wendy
// Changed to support generation of large XML files
//
// Revision 1.1  2008/04/29 14:30:47  wendy
// Init for
//  -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
//  -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
//  -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
//  -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
//  -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//
public abstract class XMLMQDelete extends XMLMQAdapter
{
	private static final String DELETED_SQL_ENTITY = "select valfrom, entityid, entitytype from opicm.entity where "+
		"Enterprise = ? AND EntityType = ? and Valfrom BETWEEN ? AND ? and "+
		"effto<current timestamp and  Valto>current timestamp ";

	private static final String DELETED_SQL_RELATOR = "select valfrom, entityid, entitytype from opicm.relator where "+
		"Enterprise = ? AND EntityType = ? and Valfrom BETWEEN ? AND ? and "+
		"effto<current timestamp and  Valto>current timestamp";

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
		String ADSTYPE = PokUtils.getAttributeFlagValue(rootEntity, "ADSTYPE");
		String t1DTS = profileT1.getValOn();
		String t2DTS = profileT2.getValOn();

		// look at ADSTYPE
		String etype = "";
		if (ADSTYPE != null){
			etype = (String)ADSABRSTATUS.ADSTYPES_TBL.get(ADSTYPE);
		}

		abr.addDebug("XMLMQDelete.processThis checking for deleted "+etype+" between "+t1DTS+" and "+t2DTS);
		// find deleted entities with their DTS
		Hashtable rootIds = getDeletedRoots(abr,profileT2.getEnterprise(), etype,t1DTS, t2DTS);
		if (rootIds.size()==0){
			//NO_CHANGES_FND=No Changes found for {0}
			abr.addXMLGenMsg("NO_CHANGES_FND",etype);
		}else{
			abr.addDebug("XMLMQDelete.processThis checking found "+rootIds.size()+" deleted "+etype);

			Profile profile = profileT2.getNewInstance(abr.getDB());
			profile.setEndOfDay(t2DTS); // used for notification time


			Vector mqVct = getMQPropertiesFN();
			if (mqVct==null){
				abr.addDebug("XMLMQDelete: No MQ properties files, nothing will be generated.");
				//NOT_REQUIRED = Not Required for {0}.
				abr.addXMLGenMsg("NOT_REQUIRED", "Deleted "+etype);
			}else{
				// pull VE and check PDHDOMAIN
				for (Enumeration e = rootIds.keys(); e.hasMoreElements();) {
					Integer eid = (Integer)e.nextElement();
					String rootInfo = "Deleted "+etype+eid;
					String valfrom = (String)rootIds.get(eid);
					// subtract 20 seconds from valfrom
					ISOCalendar isoCalendar = new ISOCalendar(valfrom);
					String adjValFrom = isoCalendar.getAdjustedDate(); // bogus override.. should be a better way!!

					abr.addDebug("XMLMQDelete.processThis checking deleted "+etype+eid+" valfrom "+valfrom+
						" adjust valfrom "+adjValFrom);
					profile.setValOnEffOn(adjValFrom, adjValFrom);

					// pull ve
					EntityList rootlist = abr.getDB().getEntityList(profile,
						new ExtractActionItem(null, abr.getDB(), profile,getVeName()),
						new EntityItem[] { new EntityItem(null, profile, etype, eid.intValue())});

					EntityItem item = rootlist.getParentEntityGroup().getEntityItem(0);
					if (abr.domainNeedsChecks(item)){
						profile.setValOn(valfrom);

						// do one at a time
						String xml = getXMLForItem(abr,rootlist);
						abr.addDebug("XMLMQDelete: Generated MQ xml:"+ADSABRSTATUS.NEWLINE+xml+ADSABRSTATUS.NEWLINE);
						abr.notify(this, rootInfo, xml);
					}else{
						abr.addXMLGenMsg("DOMAIN_NOT_LISTED",item.getKey());
					}
					rootlist.dereference();
				}
			}

			rootIds.clear();
		}
    }

    private String getXMLForItem(ADSABRSTATUS abr,EntityList xmllist)
	throws
		java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException,
		ParserConfigurationException,
		java.rmi.RemoteException,
		COM.ibm.eannounce.objects.EANBusinessRuleException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		IOException,
		javax.xml.transform.TransformerException
    {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();  // Create
		XMLElem xmlMap = getXMLMap(); // get list of xml elements

		Element root = null;

		StringBuffer debugSb = new StringBuffer();
		xmlMap.addElements(abr.getDB(),xmllist, document, root,null,debugSb);

		abr.addDebug("XMLMQDelete: GenXML debug: "+ADSABRSTATUS.NEWLINE+debugSb.toString());
		String xml = abr.transformXML(this, document);
		return xml;
	}

	private Hashtable getDeletedRoots(ADSABRSTATUS abr,String enterprise, String etype,	String t1DTS, String t2DTS)
 	throws java.sql.SQLException
	{
		Hashtable rootTbl = new Hashtable();
        ResultSet result=null;
		Connection connection=null;
		PreparedStatement delStatement = null;
        try {
            connection = setupConnection();
            //right now opicm.entity has deleted relators as active
            if (etype.equals("PRODSTRUCT") || etype.equals("SWPRODSTRUCT") || etype.equals("SVCPRODSTRUCT")) {
				delStatement = connection.prepareStatement(DELETED_SQL_RELATOR);
			}else{
				delStatement = connection.prepareStatement(DELETED_SQL_ENTITY);
			}

            //delStatement.clearParameters();
            delStatement.setString(1, enterprise);//"enterprise"
            delStatement.setString(2, etype);//"entitytype"
            delStatement.setString(3, t1DTS);//"time1"
            delStatement.setString(4, t2DTS);//"time2"

            result = delStatement.executeQuery();
            while(result.next()) {
				String valfrom = result.getString(1);
				int eid = result.getInt(2);
				rootTbl.put(new Integer(eid),valfrom);
            }
        }
        finally{
			try {
				if (delStatement!=null) {
					delStatement.close();
					delStatement=null;
				}
			}catch(Exception e){
				System.err.println("getDeletedRoots(), unable to close statement. "+ e);
				abr.addDebug("XMLMQDelete.getDeletedRoots unable to close statement. "+e);
			}
            if (result!=null){
                result.close();
            }
            closeConnection(connection);
        }
		return rootTbl;
	}

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()    {
        return "1.3";
    }

	// used to modify entity deletion date
	private static class ISOCalendar
	{
		private GregorianCalendar calendar = new GregorianCalendar(); // doesnt' seem like any DateFormat will parse an ISO date
		private String microSecStr;
		private String isoDate;
		private static final int MAGIC_NUMBER = 20;  // amount to subtract from entity deletion dts
		ISOCalendar(String theDate)
		{
			isoDate = theDate;
			calendar.set(Calendar.YEAR,Integer.parseInt(isoDate.substring(0,4)));
			calendar.set(Calendar.MONTH,Integer.parseInt(isoDate.substring(5,7))-1); // months are counted from 0
			calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(isoDate.substring(8,10)));
			calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(isoDate.substring(11,13)));
			calendar.set(Calendar.MINUTE,Integer.parseInt(isoDate.substring(14,16)));
			calendar.set(Calendar.SECOND,Integer.parseInt(isoDate.substring(17,19)));
			calendar.set(Calendar.MILLISECOND,0); // really want to set MICROSECOND but it doesn't support it
			microSecStr = isoDate.substring(20);
		}

		String getAdjustedDate()
		{
			// must manipulate date, subtract X seconds?
			calendar.add(Calendar.SECOND, -MAGIC_NUMBER);
			// build new date string
			StringBuffer datesb = new StringBuffer(calendar.get(Calendar.YEAR)+"-");
			int dts = calendar.get(Calendar.MONTH)+1; // months are counted from 0
			if (dts<10) datesb.append("0");
			datesb.append(dts+"-");
			dts = calendar.get(Calendar.DAY_OF_MONTH);
			if (dts<10) datesb.append("0");
			datesb.append(dts+"-");
			dts = calendar.get(Calendar.HOUR_OF_DAY);
			if (dts<10) datesb.append("0");
			datesb.append(dts+".");
			dts = calendar.get(Calendar.MINUTE);
			if (dts<10) datesb.append("0");
			datesb.append(dts+".");
			dts = calendar.get(Calendar.SECOND);
			if (dts<10) datesb.append("0");
			datesb.append(dts+"."+microSecStr);

			return datesb.toString();
		}
	}
}
