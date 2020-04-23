// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2007  All Rights Reserved.
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
* ORDABLEPARTNOSAPLXML class
*
* From "SG FS ABR SAPL 20070830.doc"
*
* The Orderable Part Number is a 'stand alone' entity and may have one or more
* Availabilities (AVAIL) as children
*
*/
// ORDABLEPARTNOSAPLXML.java,v
// Revision 1.2  2008/01/30 19:39:16  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/09/13 12:40:28  wendy
// Init for RQ0426071527 - XCC GX
//
public class ORDABLEPARTNOSAPLXML extends SAPLXMLBase
{
/*
SAPLVEORDPARTN  0   ORDABLEPARTNO   Relator ORDABLEPARTNOGEODATE    GEODATE
SAPLVEORDPARTN  1   GEODATE Association GEODATEGAA  GENERALAREA
*/
    private static final String SAPVE_NAME = "SAPLVEORDPARTN";  // extract to use for xml
    private static final Vector SAPLXMLMAP_VCT;

/* SAPL XML
1   <Material>
2   <EACMEntityType>    "ORDABLEPARTNO"
2   <EACMEntityId>  EntityId of EACM EntityType
2   <Division>  ORDABLEPARTNO.ClassOf(DIV)
2   <LowEndIndicator>   ORDABLEPARTNO.LOWENDFLG
2   <PlantOfManufacture>    ORDABLEPARTNO.ClassOf(PLNTOFMFR)
TIR73RS77 - XML Mapping updated
2   <ProductGroupingCode>   ORDABLEPARTNO.PRODGRPCD
2   <ProductTypeCategory>   ORDERABLEPARTNO.ClassOf(PRODTYPECATG)
end TIR chg
2orig   <ProductGroupingCode>   ORDERABLEPARTNO.ClassOf(PRODTYPECATG)
2   <ProductID> ORDABLEPARTNO.ORDABLEPARTNOATR
2   <ProductTypeCode>   ORDABLEPARTNO.ClassOf(PRODTYPE)
2   <ProfitCenter>  ORDERABLEPARTNO.ClassOf(PRFTCTR)
2   <SalesManualIndicator>  ORDABLEPARTNO.SLEMANLVIEWABL
2orig   <StockCategoryCode> ORDERABLEPARTNO.ClassOf(STOCKCATCD)
2   <StockCategoryCode> ORDERABLEPARTNO.STOCKCATCD
2   <VendorNumber>  ORDERABLEPARTNO.VENDID
2   <DescriptionDataList>
3   <DescriptionData>
4   <MaterialDescription>   ORDABLEPARTNO.PRCFILENAM
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
1   </Material>

*/
/*
1   <wsnt:Notify xmlns:wsnt="http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd" xmlns:ebi="http://ibm.com/esh/ebi">
2   <wsnt:NotificationMessage>
3   <wsnt:Topic Dialect="http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete">
    For Model, Prodstruct, Orderable
        Type/Legacy/Nomenclature/?1/Country/?2/?2/.../EndCountry/
    For AccountingUse
        Type/Manual/Scope/?3/
3   </wsnt:Topic>
3   <wsnt:Message>
4   <ebi:MessageID> EA00000000  <ebi:MessageID> increment as a number
4   <ebi:priority>  "Normal"    <ebi:priority>  constant
4   <PayloadFormat  EACM_Material   </PayloadFormat
4   <NativeCodePage>    0   </NativeCodePage>
4   <body>
    insert EACM PayLoad here
4   </body>
3   </wsnt:Message>
2   </wsnt:NotificationMessage>
1   </wsnt:Notify>

    ?1 = MTM for Model or Prodstruct
    ?1 = PN for OrderablePartnumber
    ?2 = Country Code like US, CA
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
        materialElem.addChild(new SAPLFixedElem("EACMEntityType","ORDABLEPARTNO"));
        materialElem.addChild(new SAPLIdElem("EACMEntityId"));
        materialElem.addChild(new SAPLElem("Division","ORDABLEPARTNO","DIV", true,SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("LowEndIndicator","ORDABLEPARTNO","LOWENDFLG",true));
        materialElem.addChild(new SAPLElem("PlantOfManufacture","ORDABLEPARTNO","PLNTOFMFR",true,SAPLElem.FLAGVAL));

//TIR73RS77  materialElem.addChild(new SAPLElem("ProductGroupingCode","ORDERABLEPARTNO","PRODTYPECATG",true,SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("ProductGroupingCode","ORDERABLEPARTNO","PRODGRPCD",true)); //TIR73RS77
        materialElem.addChild(new SAPLElem("ProductTypeCategory","ORDERABLEPARTNO","PRODTYPECATG",true,SAPLElem.FLAGVAL)); //TIR73RS77
        materialElem.addChild(new SAPLElem("ProductID","ORDABLEPARTNO","ORDABLEPARTNOATR",true));
        materialElem.addChild(new SAPLElem("ProductTypeCode","ORDERABLEPARTNO","PRODTYPE",true,SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("ProfitCenter","ORDERABLEPARTNO","PRFTCTR",true,SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("SalesManualIndicator","ORDABLEPARTNO","SLEMANLVIEWABL",true));
//TIR747QEJ        materialElem.addChild(new SAPLElem("StockCategoryCode","ORDERABLEPARTNO","STOCKCATCD",true,SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("StockCategoryCode","ORDERABLEPARTNO","STOCKCATCD",true));
        materialElem.addChild(new SAPLElem("VendorNumber","ORDERABLEPARTNO","VENDID",true));

        SAPLElem listElem = new SAPLElem("DescriptionDataList");
        materialElem.addChild(listElem);

        SAPLElem elem3 = new SAPLNLSElem("DescriptionData");
        listElem.addChild(elem3);
        // add level4
        elem3.addChild(new SAPLElem("MaterialDescription","ORDABLEPARTNO","PRCFILENAM",true));
        elem3.addChild(new SAPLCHQISOElem("DescriptionLanguage"));

        listElem = new SAPLElem("GeographyList"); // level2
        materialElem.addChild(listElem);

        elem3 = new SAPLGEOElem("Geography","GEODATE","GEODATEGAA");        // level3
        listElem.addChild(elem3);
        elem3.addChild(new SAPLItemElem("RFAGEO","GENERALAREA","RFAGEO"));
        elem3.addChild(new SAPLItemElem("Country","GENERALAREA","GENAREANAME"));
        elem3.addChild(new SAPLItemElem("SalesOrg","GENERALAREA","SLEORG"));
        elem3.addChild(new SAPLItemElem("SalesOffice","GENERALAREA","SLEOFFC"));
        elem3.addChild(new SAPLItemElem("ProductAnnounceDateCountry","GEODATE","PUBFROM"));
        elem3.addChild(new SAPLItemElem("ProductWithdrawalDate","GEODATE","PUBTO"));

    }

    /**********************************
    * get the name(s) of the MQ properties file to use, could be more than one
Wayne Kehrli
ESH = all but ACCGUSEONLYMATRL
OIDH = all but PRODSTRUCT & SWPRODSTRUCT
    */
    protected Vector getMQPropertiesFN() {
        Vector vct = new Vector(2);
        vct.add(OIDHMQSERIES);
        vct.add(ESHMQSERIES);
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
        *3  <wsnt:Topic Dialect="http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete">
        *   For MODEL, PRODSTRUCT, SWPRODSTRUCT, ORDABLEPARTNO
        *       esh:MaterialLegacy/Nomenclature/{MODEL | PRODSTRUCT | SWPRODSTRUCT | ORDABLEPARTNO}/Country/?2/?2/EndCountry
        *3  </wsnt:Topic>
        *
        *?1 = PN for OrderablePartnumber
        *?2 = Country Code like US, CA
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
           // EntityItem item = list.getParentEntityGroup().getEntityItem(0);
            Vector itemVct = getEntities(list.getEntityGroup("GEODATE")); // just convert into vector
            String ctrys = getCountryCodes(list, itemVct, "GEODATEGAA", debugSb);
            String value = "esh:MaterialLegacy/Nomenclature/ORDABLEPARTNO/Country"+ctrys+"/EndCountry";
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
