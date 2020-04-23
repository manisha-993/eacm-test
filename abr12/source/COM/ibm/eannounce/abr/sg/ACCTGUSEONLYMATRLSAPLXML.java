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
import java.util.*;
import org.w3c.dom.*;

/**********************************************************************************
* ACCTGUSEONLYMATRLSAPLXML class generates xml for ACCTGUSEONLYMATRL
*
* From "SG FS ABR SAPL 20070830.doc"
*
*/
// ACCTGUSEONLYMATRLSAPLXML.java,v
// Revision 1.2  2008/01/22 17:11:18  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/09/13 12:40:28  wendy
// Init for RQ0426071527 - XCC GX
//
//
public class ACCTGUSEONLYMATRLSAPLXML extends SAPLXMLBase
{

    private static final String SAPVE_NAME = "SAPLVEACCTMATRL";  // extract to use for xml
/*
SAPLVEACCTMATRL	0	ACCTGUSEONLYMATRL	Relator	ACCTMATRLGEODATE	GEODATE
SAPLVEACCTMATRL	1	GEODATE	Association	GEODATEGAA	GENERALAREA
*/

    private static final Vector SAPLXMLMAP_VCT;

/* SAPL XML
1   <Material>
2   <EACMEntityType>    "ACCTGUSEONLYMATRL"
2   <EACMEntityId>  EntityId of EACM EntityType
2   <Division>  ACCTGUSEONLYMATRL.ClassOf(DIV)
2   <ProductID> ACCTGUSEONLYMATRL.ACCTGUSEONLYMATRLID
2   <ProductTypeCode>  ACCTGUSEONLYMATRL.ClassOf(PRODTYPE)
2   <ProfitCenter>  ACCTGUSEONLYMATRL.ClassOf(PRFTCTR)
2   <VendorNumber>  ACCTGUSEONLYMATRL.VENDID
2   <AccountAssignmentGroup>    ACCTGUSEONLYMATRL.ClassOf(ACCTASGNGRP)
2   <DescriptionDataList>
3   <DescriptionData>
4   <MaterialDescription>   ACCTGUSEONLYMATRL.PRCFILENAM
4   <DescriptionLanguage>   NLSID ==> CHQISONLSIDMAP.CHQNLSID : CHQISONLSID.CHQISOLANG
3   </DescriptionData>
2   </DescriptionDataList>
2   <GeographyList>
3   <Geography> use GEODATE.COUNTRYLIST to GENERALAREA where GENERALAREA.GENAREATYPE=2452 (Country)
4   <RFAGEO>    GENERALAREA.RFAGEO
4   <Country>   GENERALAREA.GENAREANAME
4   <SalesOrg>  GENERALAREA.SLEORG
4   <SalesOffice>   GENERALAREA.SLEOFFC
4   <ProductAnnounceDateCountry>    GEODATE.PUBFROM
4   <ProductWithdrawalDate> GEODATE.PUBTO
3   </Geography>
2   </GeographyList>
2   <PlantDataList>
3   <PlantData>
4   <Plant> ACCTGUSEONLYMATRL..ClassOf(PLNTCD)
3   </PlantData>
2   </PlantDataList>
1   </Material>

*/

/*
1	<wsnt:Notify xmlns:wsnt="http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd" xmlns:ebi="http://ibm.com/esh/ebi">
2	<wsnt:NotificationMessage>
3	<wsnt:Topic Dialect="http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete">
	For MODEL, PRODSTRUCT, SWPRODSTRUCT, ORDABLEPARTNO
		Type/Legacy/Nomenclature/?1/Country/?2/?2/.../EndCountry/
	For ACCTGUSEONLYMATRL
		Type/Manual/Scope/?3/
3	</wsnt:Topic>
3	<wsnt:Message>
4	<ebi:MessageID>	EA00000000
4	<ebi:priority>	"Normal"
4	<PayloadFormat>	EACM_Material
4	<NativeCodePage>	0
4	<body>
	insert EACM PayLoad here
4	</body>
3	</wsnt:Message>
2	</wsnt:NotificationMessage>
1	</wsnt:Notify>
Substitution Explanation
	?1 = MTM for Model or Prodstruct
	?1 = PN for OrderablePartnumber
	?2 = Country (GENERALAREA.GENAREACODE)
	?3 = ACCTGUSEONLYMATRL.PRODTYPE

*/

    static {
        SAPLXMLMAP_VCT = new Vector();  // set of elements
        SAPLElem topElem = new SAPLElem("wsnt:Notify");

        topElem.addXMLAttribute("xmlns:wsnt",XMLNS_WSNT);
		topElem.addXMLAttribute("xmlns:ebi",XMLNS_EBI);

		SAPLXMLMAP_VCT.addElement(topElem);
		 // level2
        SAPLElem level2Elem = new SAPLElem("wsnt:NotificationMessage");
        topElem.addChild(level2Elem);
		//level 3
        level2Elem.addChild(new SAPLTopicElem());
        SAPLElem messageElem = new SAPLElem("wsnt:Message");
        level2Elem.addChild(messageElem);
        // level4
		messageElem.addChild(new SAPLMessageIDElem());
		messageElem.addChild(new SAPLFixedElem("ebi:priority","Normal"));
		messageElem.addChild(new SAPLFixedElem("PayloadFormat","EACM_Material"));
		messageElem.addChild(new SAPLFixedElem("NativeCodePage","1208"));

 	    SAPLElem bodyElem = new SAPLElem("body");
 	    messageElem.addChild(bodyElem);

        SAPLElem materialElem = new SAPLElem("Material"); // add EACM payload level1
        bodyElem.addChild(materialElem);

        // add EACM payload level2(s)
		materialElem.addChild(new SAPLFixedElem("EACMEntityType","ACCTGUSEONLYMATRL"));
		materialElem.addChild(new SAPLIdElem("EACMEntityId"));
		materialElem.addChild(new SAPLElem("Division","ACCTGUSEONLYMATRL","DIV",true,SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("ProductID","ACCTGUSEONLYMATRL","ACCTGUSEONLYMATRLID",true));
        materialElem.addChild(new SAPLElem("ProductTypeCode","ACCTGUSEONLYMATRL","PRODTYPE",true,SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("ProfitCenter","ACCTGUSEONLYMATRL","PRFTCTR",true,SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("VendorNumber","ACCTGUSEONLYMATRL","VENDID",true));
        materialElem.addChild(new SAPLElem("AccountAssignmentGroup","ACCTGUSEONLYMATRL","ACCTASGNGRP",true,SAPLElem.FLAGVAL));

        SAPLElem listElem = new SAPLElem("DescriptionDataList");
		SAPLElem elem3 = new SAPLNLSElem("DescriptionData");
		// add level4
        elem3.addChild(new SAPLElem("MaterialDescription","ACCTGUSEONLYMATRL","PRCFILENAM",true));
		elem3.addChild(new SAPLCHQISOElem("DescriptionLanguage"));

        // add level3
        listElem.addChild(elem3);
        // add level2(s)
        materialElem.addChild(listElem);

		listElem = new SAPLElem("GeographyList"); // level2
        materialElem.addChild(listElem);
        elem3 = new SAPLGEOElem("Geography","GEODATE","GEODATEGAA");  		// level3
        listElem.addChild(elem3);
        elem3.addChild(new SAPLItemElem("RFAGEO","GENERALAREA","RFAGEO"));
        elem3.addChild(new SAPLItemElem("Country","GENERALAREA","GENAREANAME"));
        elem3.addChild(new SAPLItemElem("SalesOrg","GENERALAREA","SLEORG"));
        elem3.addChild(new SAPLItemElem("SalesOffice","GENERALAREA","SLEOFFC"));
        elem3.addChild(new SAPLItemElem("ProductAnnounceDateCountry","GEODATE","PUBFROM"));
        elem3.addChild(new SAPLItemElem("ProductWithdrawalDate","GEODATE","PUBTO"));

		listElem = new SAPLElem("PlantDataList"); // level2
        materialElem.addChild(listElem);
        elem3 = new SAPLElem("PlantData");  		// level3
        listElem.addChild(elem3);
        elem3.addChild(new SAPLElem("Plant","ACCTGUSEONLYMATRL","PLNTCD",true,SAPLElem.FLAGVAL));
    }

    /**********************************
    * get the name(s) of the MQ properties file to use, could be more than one
    TIR 7STP5L
Wayne Kehrli
ESH = all but ACCGUSEONLYMATRL
OIDH = all but PRODSTRUCT & SWPRODSTRUCT
	*/
    protected Vector getMQPropertiesFN() {
        Vector vct = new Vector(1);
        vct.add(OIDHMQSERIES);
        return vct;
    }

    /**********************************
    * get the name of the VE to use for the feed
    */
    protected String getSaplVeName() { return SAPVE_NAME;}

    /**********************************
    * get SAPL xml objects
    */
    protected Vector getSAPLXMLMap() { return SAPLXMLMAP_VCT;}

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "1.2";
    }
	static class SAPLTopicElem extends SAPLElem
	{
		/**********************************************************************************
		* Constructor for Topic element
		*
		*3	<wsnt:Topic Dialect="http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete">
		*	For MODEL, PRODSTRUCT, SWPRODSTRUCT, ORDABLEPARTNO
		*		Type/Legacy/Nomenclature/?1/Country/?2/?2/.../EndCountry/
		*	For AccountingUse
		*	esh:MaterialManual/Scope/val(ACCTGUSEONLYMATRL.PRODTYPE)
		*3	</wsnt:Topic>
		*
		*?3 = ACCTGUSEONLYMATRL.PRODTYPE
		*/
		SAPLTopicElem()
		{
			super("wsnt:Topic",null,null,false);
			addXMLAttribute("Dialect", "http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete");
		}

		/**********************************************************************************
		* Create a node for this element and add to the parent and any children this node has
		*
		*@param list EntityList
		*@param document Document needed to create nodes
		*@param parent Element node to add this node too
		*@param debugSb StringBuffer for debug output
		*/
		public void addElements(Database dbCurrent,EntityList list, Document document, Element parent,
			StringBuffer debugSb)
        throws
			COM.ibm.eannounce.objects.EANBusinessRuleException,
			java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
			java.rmi.RemoteException,
			java.io.IOException,
			COM.ibm.opicmpdh.middleware.MiddlewareException,
			COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
		{
			Element elem = (Element) document.createElement(nodeName);
			addXMLAttrs(elem);
			EntityItem item = list.getParentEntityGroup().getEntityItem(0);

			String value = "esh:MaterialManual/Scope/";
			String flagcode = " ";
			//	PokUtils.getAttributeValue(item, "PRODTYPE",", ", " ", false);

			EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("PRODTYPE");
			if (fAtt!=null){
				// Get the selected Flag code
				MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
				for (int i = 0; i < mfArray.length; i++){
					// get selection
					if (mfArray[i].isSelected()){
						flagcode=(mfArray[i].getFlagCode());
						break;
					}  // metaflag is selected
				}// end of flagcodes
			}

			elem.appendChild(document.createTextNode(value+flagcode));
			parent.appendChild(elem);

			// add any children
			for (int c=0; c<childVct.size(); c++){
				SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
				childElem.addElements(dbCurrent,list, document,elem,debugSb);
			}
		}
	}
}
