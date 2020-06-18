/*
 * Created on July 12, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * $Log: QueueABR.java,v $
 * Revision 1.6  2008/08/28 14:33:17  wendy
 * Added support for specifying search attributes
 *
 * Revision 1.5  2008/02/01 22:10:06  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.4  2007/11/01 19:30:09  wendy
 * Use enterprise to filter profile
 *
 * Revision 1.3  2006/03/03 00:31:18  joan
 * fixes
 *
 * Revision 1.2  2006/03/01 22:09:14  joan
 * fixes
 *
 * Revision 1.1  2006/02/14 17:00:09  joan
 * new file
 *
 */

package COM.ibm.eannounce.objects;

//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
import java.io.*;
import java.util.*;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.ProfileSet;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.LoginException;
import COM.ibm.opicmpdh.middleware.VersionException;
import COM.ibm.opicmpdh.transactions.*;


/**
 * Use this to ultimately run all our Catalog processes.
 */
public final class QueueABR {

    static {
        D.isplay("QueueABR:" + getVersion());
    }

  	/**
  	* Values for these variables are
  	* retrieved from the dgentity.properties
  	* file
  	*/
  	private static String m_strEnterprise, m_strRoleCode=null;

  	//private static int m_iOPWGID;                      //Used for everything connected to middleware

  	private static String m_strTimeStampNow;
  	//private static String m_strTimeEOD;
  	//private static String m_strTimeStampForever = "9999-12-31-00.00.00.000000";

  	private static Database m_dbPDH;
  	//private static Connection conPDH;

  	private static String m_strLoginID;
  	private static String m_strLoginPwd;
  	private static String m_strOPICMVersion;
  	private static String m_strSai;
  	private static String m_strEntityType;
  	private static String m_strAtts;
  	private static String m_strSearchAttr;
  	//Date Time Cast we use to print in the log entries
  	//private static SimpleDateFormat c_sdfTimestamp = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");

  	private static ProfileSet psLogin = null;
  	private static Profile pLogin  = null;

    //private static final SimpleDateFormat SDF_LOG_TIMESTAMP = new SimpleDateFormat("yyyyMMddHHmm");


    public static void main(String[] args) {
        QueueABR.queueABRs();
    }

    /**
     * queueABRs
     */
    private static void queueABRs() {
	    //java.io.BufferedReader  aspectFileStream = null;
    	//Get connection to PDH
    	try {
			readPropertyFile();
      		/**
      		*   This database object instance will pick up
      		*   the connection parameters from the
      		*   middleware.server.properties file
      		*/
      		D.ebug("Connecting to the pdh");
      		m_dbPDH = new Database();
      		m_dbPDH.connect();

      		m_dbPDH.getNow();
      		DatePackage dpNow = m_dbPDH.getDates();
      		m_strTimeStampNow = dpNow.getNow();
      		//m_strTimeEOD = dpNow.getEndOfDay();
      		//m_strTimeStampForever = dpNow.getForever();

      		D.ebug("Timestamp is "+m_strTimeStampNow);
      		D.ebug("login Info: " + m_strLoginID + ", " + m_strLoginPwd  + ", " + m_strOPICMVersion);

      		try {
      			psLogin = m_dbPDH.login(m_strLoginID, (m_strLoginPwd.equals("null")?null:m_strLoginPwd), m_strOPICMVersion);
      		} catch (LoginException le) {
        		D.ebug("Error during login...aborting...check error file");
        		D.ebug(D.EBUG_ERR,"ERROR during login...aborting run!"+le.getMessage());
        		le.printStackTrace();
        		System.exit(-1);
      		} catch (VersionException ve) {
        		D.ebug("Error during login...aborting...check error file");
        		D.ebug(D.EBUG_ERR,"ERROR during login...aborting run!"+ve.getMessage());
        		ve.printStackTrace();
        		System.exit(-1);
      		}

      		D.ebug("Logged in via ldap....Profile set is "+ psLogin.size());

			// find enterprise and set it as active profile
			for (int i = 0,c=psLogin.size(); i < c; i++)
			{
				// connection items contain connId, valOn, effOn, enterprise, etc..
				// set role to be used, like PSG Div Admin
				Profile pf = psLogin.elementAt(i);
				D.ebug(" profile["+i+"] openid: "+pf.getOPWGID()+" role: <"+pf.getRoleCode()+
					"> enterprise: <"+pf.getEnterprise()+"> looking for enterprise: <"+m_strEnterprise+"> role:<"+
					m_strRoleCode+">");
				if (m_strRoleCode!=null && !pf.getRoleCode().equals(m_strRoleCode)){
					continue;
				}
				if (pf.getEnterprise().equals(m_strEnterprise))
				{
					Vector nlsVct = null;
					psLogin.setActiveProfile(i);
					pf.setValOn(m_strTimeStampNow);
					pf.setEffOn(m_strTimeStampNow);

					// find nls and set it as active nls item
					nlsVct = psLogin.getActiveProfile().getReadLanguages();
					for (int ii=0, ci=nlsVct.size(); ii<ci; ii++)
					{
						NLSItem nlsObj = (NLSItem)nlsVct.elementAt(ii);
						if (nlsObj.getNLSID() ==1) {
							pf.setReadLanguage(nlsObj); }
					}
					pLogin = pf;
					break;
				}
			}

			if (pLogin == null){
        		D.ebug("Error during login...aborting...cannot find profile");
        		System.exit(-1);
			}

      		D.ebug("Timestamp is now "+m_strTimeStampNow);

      		D.ebug("role is "+pLogin.getRoleCode() );
      		//m_iOPWGID =  pLogin.getOPWGID();
			PDGUtility util = new PDGUtility();

			// check if any attributes need to be set for the search action
			// format is ATTR[value1,value2]:ATTR2[value3]
			String actionAttrs = "";
			if (m_strSearchAttr != null){
				StringTokenizer stAttr = new StringTokenizer(m_strSearchAttr,":");
				StringBuffer sb = new StringBuffer();
				while (stAttr.hasMoreTokens()) {
					String attr = "";
					String values = "";
					String strSrchAtt = stAttr.nextToken();
					int valueId = strSrchAtt.indexOf("[");
					if (valueId != -1){
						attr = strSrchAtt.substring(0,valueId);
						values = strSrchAtt.substring(valueId+1);
					}

					if (values.endsWith("]")){
						values = values.substring(0,values.length()-1);
					}
					StringTokenizer stValues = new StringTokenizer(values,",");

					while (stValues.hasMoreTokens()) {
						sb.append("map_"+attr+"="+stValues.nextToken()+";");
					}
				}
				actionAttrs = sb.toString();
			}

			EntityItem[] aei = util.dynaSearch(m_dbPDH, pLogin, null, m_strSai, m_strEntityType, actionAttrs);
			OPICMList attList = new OPICMList();
			StringTokenizer st = new StringTokenizer(m_strAtts, ",");
			while (st.hasMoreTokens()) {
				String strAtt = st.nextToken();
				attList.put(strAtt, strAtt + "=0020");
			}

			if (attList.size() > 0) {
				for (int i=0; i < aei.length; i++) {
					EntityItem ei = aei[i];
					util.updateAttribute(m_dbPDH, pLogin, ei, attList);
				}
			}
    	} catch(Exception e) {
    	    D.ebug("Exception in QueueABR"+e.getMessage());
    	    e.printStackTrace();
      		System.exit(1);
    	}
    }

	/******************************************
	 * readPropertyFile reads the property file aspect.properties
	 */
	private static void readPropertyFile()  {
	    D.ebug("Reading queueabr.properties");
	    Properties properties = new Properties();
	    try {
			File file = new File("./" + "queueabr.properties");
			if ( !file.exists() || !file.canRead( ) ) {
				System.out.println("Can't read " + file.getAbsolutePath() );
				System.exit(1);
			}

		    properties.load(new FileInputStream(file));
    	} catch( IOException e)   {
      		System.out.println("Error reading queueabr.properties");
      		System.exit(1);
    	}
    	m_strLoginID = properties.getProperty("OPICMID");
    	m_strLoginPwd = properties.getProperty("OPICMPWD");
    	m_strOPICMVersion = properties.getProperty("OPICMVERSION");
    	m_strEnterprise=properties.getProperty("ENTERPRISE");
    	m_strRoleCode=properties.getProperty("ROLECODE");
    	m_strSai=properties.getProperty("SEARCHACTION");
    	m_strEntityType=properties.getProperty("ENTITYTYPE");
    	m_strAtts = properties.getProperty("ATT");
    	m_strSearchAttr = properties.getProperty("SRCHATT");
  	} //readPropertyFile


    /*private static final void log(String _strMsg) {
        D.ebug(D.EBUG_DETAIL, _strMsg);
    }*/

    /**
     *  Return the version of this class
     *
     *@return    The version value
     */
    public final static String getVersion() {
        return "$Id: QueueABR.java,v 1.6 2008/08/28 14:33:17 wendy Exp $";
    }

}
