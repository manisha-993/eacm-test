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
import java.io.*;

import javax.xml.parsers.*;

/**********************************************************************************
* used for BH feed and ADSABRSTATUS
*
*/
// XMLMQ.java,v
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
public interface XMLMQ
{
    /**********************************
    * get the name(s) of the MQ properties file to use
    */
    Vector getMQPropertiesFN();

    /**********************************
    * get xml object mapping
    */
    XMLElem getXMLMap();

    /**********************************
    * check if xml should be created for this
    */
    boolean createXML(EntityItem rootItem);

    /**********************************
    * get the name of the VE to use
    */
    String getVeName();

    /**********************************
    * get the role code to use for this ABR
    */
    String getRoleCode();

    /**********************************
    * get the status attribute to use for this ABR
    */
    String getStatusAttr();

    /**********************************
    *
	A.	MQ-Series CID

	MQ-Series supports an attribute of the message called the Correlation ID (-CID).
	Each message type will have a unique CID that is passed via MQ-Series.
	Tab				RootEntity		CID
	Category		CATNAV			CATNAV
	Deletes			FCTRANSACTION	FCTRANSACTION
	Deletes			FEATURE			FEATURE
	Deletes			MODEL			MODEL
	Deletes			MODELCONVERT	MODELCONVERT
	Deletes			PRODSTRUCT		PRODSTRUCT
	Deletes			SWFEATURE		SWFEATURE
	Deletes			SWPRODSTRUCT	SWPRODSTRUCT
	FEATURE			FEATURE			FEATURE
	FEATURE			SVCFEATURE		SVCFEATURE
	FEATURE			SWFEATURE		SWFEATURE
	FEATUREconvert	FCTRANSACTION	FCTRANSACTION
	GENAREA			GENERALAREA		GENERALAREA
	MODEL			MODEL			MODEL
	ModelUpgrade	MODELCONVERT	MODELCONVERT
	TMF				PRODSTRUCT		PRODSTRUCT
	TMF				SVCPRODSTRUCT	SVCPRODSTRUCT
	TMF				SWPRODSTRUCT	SWPRODSTRUCT
	TranslatedValues				XLATEMETA
	WWcompat		MODELCG			MODELCG

    */
    String getMQCID();

    /**********************************
    * process this, create xml and put on queue
    */
    void processThis(ADSABRSTATUS abr, Profile profileT1, Profile profileT2, EntityItem rootEntity)
    throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    //COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    ParserConfigurationException,
    java.rmi.RemoteException,
    COM.ibm.eannounce.objects.EANBusinessRuleException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    IOException,
    javax.xml.transform.TransformerException,
	MissingResourceException
    ;

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    String getVersion();
}
