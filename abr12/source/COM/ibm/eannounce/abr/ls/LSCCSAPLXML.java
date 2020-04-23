// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.ls;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;
import COM.ibm.eannounce.abr.sg.SAPLXMLBase;

import java.util.*;
import org.w3c.dom.*;

/**********************************************************************************
* LSCCSAPLXML class
*
* From "LS FS ABR SAPL 20071211.doc" RQ0117074421
* For LS, a Country Course (LSCC) is for a single country and hence the relationship
* to LSCCF and then LSCT provides Sales Org (SLEORG) as an attribute.
*/
// $Log: LSCCSAPLXML.java,v $
// Revision 1.2  2008/01/30 19:26:59  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2008/01/09 18:43:45  wendy
// Init for RQ0117074421
//

public class LSCCSAPLXML extends SAPLXMLBase
{
    private static final String SAPVE_NAME = "SAPLVELSCC";  // extract to use for xml

/*
SAPLVELSCC 0 D  LSCC   Relator LSCCRC  LSRC
SAPLVELSCC 1 U  LSRC   Relator     LSDIVRC  LSDIV
SAPLVELSCC 0 U  LSCC   Relator     LSCCFCC  LSCCF
SAPLVELSCC 1 U  LSCCF   Relator     LSCTCCF  LSCT
*/
    private static final Vector SAPLXMLMAP_VCT;

/* SAPL XML
1	<Material>
2	<EACMEntityType>	"LSCC"
2	<EACMEntityId>	EntityId of LSCC
2	<Division>	LSCC via LSCCRC to LSRC via LSDIVRC to LSDIV ClassOf(LSDIV.NAME)
2	<ProductID>	LSCC.LSCRSID
2	<ProfitCenter>	LSCC via LSCCRC to LSRC.ClassOf(PRFTCTR)
2	<RevenueCode>	LSCC via LSCCRC to LSRC.LSRCID
2	<DescriptionDataList>
3	<DescriptionData>
4	<MaterialDescription>	LSCC.LSCRSTITLE
4	<DescriptionLanguage>	NLSID ==> CHQISONLSIDMAP.CHQNLSID : CHQISONLSID.CHQISOLANG

how to do this in LS enterprise? the search action is defined for SG
SG	Action/Attribute	SRDCHQISONLSIDMAP	TYPE	Attributes	Yes
SG	Action/Attribute	SRDCHQISONLSIDMAP	TYPE	DynaSearch	CHQISONLSIDMAP
SG	Action/Attribute	SRDCHQISONLSIDMAP	TYPE	LikeEnabled	Y
SG	Action/Attribute	SRDCHQISONLSIDMAP	ENTITYTYPE	Link	WG
SG	Action/Attribute	SRDCHQISONLSIDMAP	TYPE	ParentLess	Y
SG	Action/Category	SRDCHQISONLSIDMAP	ISOCAT	Link	Y
SG	Group/Action	ADMINWGGRP	SRDCHQISONLSIDMAP	Link	LBB
SG	Role/Action/Entity/Group	ADMIN	SRDCHQISONLSIDMAP	CHQISONLSIDMAP	SRDMCHQISONLSIDMAPGRP



3	</DescriptionData>
2	</DescriptionDataList>
2	<GeographyList>
3	<Geography>
4	<SalesOrg>	LSCC via LSCCFCC up to LSCCF via LSCTCCF up to LSCT.SLEORG
4	<ProductAnnounceDateCountry>	LSCC.LSCRSAVLDATE
4	<ProductWithdrawalDate>	LSCC.LSCRSEXPDATE
3	</Geography>
2	</GeographyList>
1	</Material>

*/

/*
1   <wsnt:Notify xmlns:wsnt="http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd" xmlns:ebi="http://ibm.com/esh/ebi">
2   <wsnt:NotificationMessage>
3   <wsnt:Topic Dialect="http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete">
	esh:MaterialLegacy/Nomenclature/{MODEL | PRODSTRUCT | SWPRODSTRUCT | ORDABLEPARTNO | LSCC}/Country/?2/?2/EndCountry
For LSCC	?2 = LEFT(LSCT.LSCTCOUNTRY,3) -- i.e. Country Code - the left most 3 characters of Country
3   </wsnt:Topic>
3   <wsnt:Message>
4   <ebi:MessageID> EA00000000  <ebi:MessageID> increment as a number
4   <ebi:priority>  "Normal"    <ebi:priority>  constant
4   <PayloadFormat>  EACM_Material   </PayloadFormat
4   <NativeCodePage>    1208   </NativeCodePage>
4   <body>
    insert EACM PayLoad here
4   </body>
3   </wsnt:Message>
2   </wsnt:NotificationMessage>
1   </wsnt:Notify>
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
        materialElem.addChild(new SAPLFixedElem("EACMEntityType","LSCC"));
        materialElem.addChild(new SAPLIdElem("EACMEntityId"));
        materialElem.addChild(new SAPLElem("Division","LSDIV","NAME"));
        materialElem.addChild(new SAPLElem("ProductID","LSCC","LSCRSID",true));
        materialElem.addChild(new SAPLElem("ProfitCenter","LSRC","PRFTCTR",SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("RevenueCode","LSRC","LSRCID"));

        SAPLElem listElem = new SAPLElem("DescriptionDataList");
        SAPLElem elem3 = new SAPLNLSElem("DescriptionData");
        // add level3
        listElem.addChild(elem3);
        // add level2(s)
        materialElem.addChild(listElem);
        // add level4
        elem3.addChild(new SAPLElem("MaterialDescription","LSCC","LSCRSTITLE",true));
        elem3.addChild(new SAPLCHQISOElem("DescriptionLanguage"));

        listElem = new SAPLElem("GeographyList"); // level2
        materialElem.addChild(listElem);

        elem3 = new SAPLElem("Geography");        // level3 there will only be one LSCT
        listElem.addChild(elem3);
        elem3.addChild(new SAPLElem("SalesOrg","LSCT","SLEORG"));;
        elem3.addChild(new SAPLElem("ProductAnnounceDateCountry","LSCC","LSCRSAVLDATE",true));
        elem3.addChild(new SAPLElem("ProductWithdrawalDate","LSCC", "LSCRSEXPDATE",true));
    }

    public LSCCSAPLXML() {
		super(false); // SAPL will not be in the meta
	}

    /**********************************
    * get the name(s) of the MQ properties file to use, could be more than one
    * For Learning Services, the In-Country Course (LSCC) XML is only sent to OIDH.
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
        return "$Revision: 1.2 $";
    }

    static class SAPLTopicElem extends SAPLElem
    {
        /**********************************************************************************
        * Constructor for Topic element
        *
        *3  <wsnt:Topic Dialect="http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete">
        *   For MODEL, PRODSTRUCT, SWPRODSTRUCT, ORDABLEPARTNO
        *       	esh:MaterialLegacy/Nomenclature/{MODEL | PRODSTRUCT | SWPRODSTRUCT | ORDABLEPARTNO | LSCC}/Country/?2/?2/EndCountry
        *3  </wsnt:Topic>
        *
        * LSCC	?2 = LEFT(LSCT.LSCTCOUNTRY,3) -- i.e. Country Code - the left most 3 characters of Country
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
        public void addElements(Database dbCurrent, EntityList list, Document document, Element parent,
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
//            EntityItem item = list.getParentEntityGroup().getEntityItem(0);

            EntityGroup lsctgrp = list.getEntityGroup("LSCT");
          	StringBuffer sb = new StringBuffer();
			Vector ctryCodeVct = new Vector(1);
            for (int i=0; i<lsctgrp.getEntityItemCount(); i++){ // there should only be one ctry
				EntityItem ctitem = lsctgrp.getEntityItem(i);
				String ctryCode = PokUtils.getAttributeValue(ctitem, "LSCTCOUNTRY",", ", "", false);
				// get left 3 chars
				if (ctryCode.length()<3){
					ctryCode+="   ";
				}
				ctryCode = ctryCode.substring(0,3);

				// avoid duplicates
				if (!ctryCodeVct.contains(ctryCode)){
					ctryCodeVct.add(ctryCode);
				}
			}

			Collections.sort(ctryCodeVct); // sort alphabetically

			for (int i=0; i<ctryCodeVct.size(); i++){
				String ctryCode = ctryCodeVct.elementAt(i).toString();
				sb.append("/"+ctryCode);
			}
			if (sb.length()==0){
				sb.append("/ ");
			}

            String value = "esh:MaterialLegacy/Nomenclature/LSCC/Country"+sb+"/EndCountry";
            elem.appendChild(document.createTextNode(value));
            parent.appendChild(elem);

            // add any children
            for (int c=0; c<childVct.size(); c++){
                SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
                childElem.addElements(dbCurrent,list, document,elem,debugSb);
            }
        }
    }
}
