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

import java.util.*;
import java.sql.*;
import java.io.*;

import javax.xml.parsers.*;

import com.ibm.eacm.AES256Utils;

/**********************************************************************************
* base class for ADS feeds in ADSABRSTATUS abr
*
*/
// XMLMQAdapter.java,v
// Revision 1.2  2008/04/29 14:29:11  wendy
// Add CID support
//
// Revision 1.1  2008/04/25 12:11:37  wendy
// Init for
//  -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
//  -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
//  -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
//  -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
//  -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//
public abstract class XMLMQAdapter implements XMLMQ
{
    /**********************************
    * get the name(s) of the MQ properties file to use
    */
    public Vector getMQPropertiesFN() {
        Vector vct = new Vector(1);
        vct.add(ADSABRSTATUS.ADSMQSERIES);
        return vct;
    }

    /**********************************
    * check if xml should be created for this
    */
    public boolean createXML(EntityItem rootItem) { return true;}

    /**********************************
    * get xml object mapping
    */
    public XMLElem getXMLMap() {return null;}

    /**********************************
    * get the name of the VE to use
    */
    public String getVeName() {return "dummy";}

    /**********************************
    * get the role code to use for this ABR
    */
    public String getRoleCode() { return "BHFEED"; }

    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr(){ return "";}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public abstract String getMQCID();

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion(){ return "";}

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
    {}

    /********************************************************************************
    * setup the connection and preparedstatements
    */
    protected Connection setupConnection()
        throws java.sql.SQLException
    {
        Connection connection = null;
		try {
			connection = DriverManager.getConnection(
                MiddlewareServerProperties.getPDHDatabaseURL(),
                MiddlewareServerProperties.getPDHDatabaseUser(),
                AES256Utils.decrypt(MiddlewareServerProperties.getPDHDatabasePassword()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        connection.setAutoCommit(false);

        return connection;
    }
    /********************************************************************************
    * close the connection and preparedstatements
    */
    protected void closeConnection(Connection connection) throws java.sql.SQLException
    {
        if(connection != null) {
            try {
                connection.rollback();
            }
            catch (Throwable ex) {
                System.err.println("XMLMQAdapter.closeConnection(), unable to rollback. "+ ex);
            }
            finally {
                connection.close();
                connection = null;
            }
        }
    }
}
