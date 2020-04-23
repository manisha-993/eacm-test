// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.xml.transform.TransformerConfigurationException;

import org.xml.sax.SAXException;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

import com.ibm.transform.oim.eacm.util.PokUtils;

// $Log: AITFEEDANNFCTRANSACTIONABR.java,v $
// Revision 1.6  2017/09/07 12:45:17  wangyul
// Story 1749137 - AIT HWW - Autogen - logic change to derive FCTRANSACTIONs for AIT
//
// Revision 1.5  2017/09/07 11:35:10  wangyul
// Story 1749137 - AIT HWW - Autogen - logic change to derive FCTRANSACTIONs for AIT
//
// Revision 1.4  2017/09/06 13:14:51  wangyul
// Story 1749137 - AIT HWW - Autogen - logic change to derive FCTRANSACTIONs for AIT
//
// Revision 1.3  2017/09/06 12:27:26  wangyul
// Story 1749137 - AIT HWW - Autogen - logic change to derive FCTRANSACTIONs for AIT
//
// Revision 1.2  2017/05/24 07:57:54  wangyul
// story 1703408 - HWW - Autogen files - AIT XML issue fix
//
// Revision 1.1  2015/08/05 09:27:43  wangyul
// EACM to AIT feed
//
public class AITFEEDANNFCTRANSACTIONABR extends AITFEEDXML {
    private Hashtable featureCodeMktgNameTbl = new Hashtable();

    public void processThis(AITFEEDABRSTATUS abr, Profile profileT2,
                            EntityItem rootEntity) throws FileNotFoundException,
            TransformerConfigurationException, SAXException,
            MiddlewareRequestException, SQLException, MiddlewareException {
        AITFEEDSimpleSaxXML xml = null;
        try {
            String annNumber = getAttributeValue(rootEntity, "ANNNUMBER");

            xml = new AITFEEDSimpleSaxXML(ABRServerProperties.getOutputPath() + getXMLFileName() + abr.getABRTime());
            xml.startDocument();
            xml.startElement("ANNFCTRANSACTION", xml.createAttribute("xmlns", "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/ANNFCTRANSACTION"));
            xml.addElement("ANNNUMBER", annNumber);
            xml.addElement("INVENTORYGROUP", getAttributeValue(rootEntity, "INVENTORYGROUP"));
            xml.addElement("ANNTYPE", getAttributeValue(rootEntity, "ANNTYPE"));
            xml.addElement("ANNDATE", getAttributeValue(rootEntity, "ANNDATE"));
            xml.addElement("PDHDOMAIN", getAttributeValue(rootEntity, "PDHDOMAIN"));
            // FCTRANSACTIONTLIST
            xml.startElement("FCTRANSACTIONTLIST");
            StringBuffer debugSb = new StringBuffer();
            /*
	        if ANNTYPE = New of ANNOUNCMENT, then search all FCTRANSACTIONs the ANNRFANUMBER = ANNOUNCMENT.ANNNUMBER
			if ANNTYPE = End Of Life - Withdrawal from mktg of ANNOUNCMENT, then search all FCTRANSACTIONs the WDRFANUMBER = ANNOUNCMENT.ANNNUMBER
			this has to work across Domains
			*/
            String annType = PokUtils.getAttributeFlagValue(rootEntity, "ANNTYPE");
            List entityIds = null;
            if (ANNTYPE_NEW.equals(annType)) {
                entityIds = searchFCTRANSACTIONEntityIdsForRfaNumber("ANNRFANUMBER", annNumber, abr);
            } else if (ANNTYPE_EOL.equals(annType)) {
                entityIds = searchFCTRANSACTIONEntityIdsForRfaNumber("WDRFANUMBER", annNumber, abr);
            } else {
                // We already have the check in the ABR, make sure no issue here.
                throw new RuntimeException("Unsupport ANNTYPE " + annType + " for AIT feed ABR");
            }

            if (entityIds != null && entityIds.size() > 0) {
                for (int i = 0; i < entityIds.size(); i++) {
                    int entityId = ((Integer) entityIds.get(i)).intValue();
                    EntityItem fctransactionEntityItem = abr.getEntityItem(profileT2, "FCTRANSACTION", entityId);
                    xml.startElement("FCTRANSACTIONELEMENT");
                    xml.addElement("ANNDATE", getAttributeValue(fctransactionEntityItem, "ANNDATE"));
                    xml.addElement("FROMMACHTYPE", getAttributeValue(fctransactionEntityItem, "FROMMACHTYPE"));
                    xml.addElement("FROMMODEL", getAttributeValue(fctransactionEntityItem, "FROMMODEL"));
                    String fromFeatureCode = getAttributeValue(fctransactionEntityItem, "FROMFEATURECODE");
                    xml.addElement("FROMFEATURECODE", fromFeatureCode);
                    xml.addElement("FROMMKTGNAME", getFeatureMktgName(fromFeatureCode, fctransactionEntityItem, abr, debugSb));
                    xml.addElement("TOMACHTYPE", getAttributeValue(fctransactionEntityItem, "TOMACHTYPE"));
                    xml.addElement("TOMODEL", getAttributeValue(fctransactionEntityItem, "TOMODEL"));
                    String toFeatureCode = getAttributeValue(fctransactionEntityItem, "TOFEATURECODE");
                    xml.addElement("TOFEATURECODE", toFeatureCode);
                    xml.addElement("TOMKTGNAME", getFeatureMktgName(toFeatureCode, fctransactionEntityItem, abr, debugSb));
                    xml.addElement("FEATURETRANSACTIONCATEGORY", getAttributeValue(fctransactionEntityItem, "FTCAT"));
                    xml.addElement("RETURNEDPARTSMES", getAttributeValue(fctransactionEntityItem, "RETURNEDPARTS"));
                    xml.endElement("FCTRANSACTIONELEMENT");
                }
            } else {
                abr.addDebug("Not found FCTRANSACTIONs for ANNDATE of ANN:" + getAttributeValue(rootEntity, "ANNDATE") + rootEntity.getKey());
            }
            abr.addDebug("GenXML debug:" + AITFEEDABRSTATUS.NEWLINE + debugSb.toString());
            xml.endElement("FCTRANSACTIONTLIST");
            xml.endElement("ANNFCTRANSACTION");
            xml.endDocument();

            featureCodeMktgNameTbl.clear();
            featureCodeMktgNameTbl = null;
            entityIds = null;
        } finally {
            if (xml != null) {
                xml.close();
            }
        }
    }

    /**
     * Search FCTRANSACTION Entity ids for RFANUMBER
     *
     * @param attributeCode
     * @param attributeValue
     * @param abr
     * @return
     */
    private List searchFCTRANSACTIONEntityIdsForRfaNumber(String attributeCode, String attributeValue, AITFEEDABRSTATUS abr) {
        return searchEntityIdsByAttributeCodeAndValueForText("FCTRANSACTION", attributeCode, attributeValue, abr);
    }

    /**
     * Search entity ids for attribute code and value for text type
     *
     * @param attributeCode
     * @param attributeValue
     * @param abr
     * @return
     */
    private List searchEntityIdsByAttributeCodeAndValueForText(String entityType, String attributeCode, String attributeValue, AITFEEDABRSTATUS abr) {
        List entityIds = new ArrayList();
        PreparedStatement ps = null;
        String sql = "SELECT entityid FROM opicm.text WHERE entitytype='" + entityType + "' AND attributecode='" + attributeCode + "' AND attributevalue='" + attributeValue + "' AND valto > CURRENT TIMESTAMP AND effto > CURRENT TIMESTAMP";
        abr.addDebug("searchEntityIdsByAttributeCodeAndValueForText sql: " + sql);
        try {
            ps = abr.getDatabase().getPDHConnection().prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                entityIds.add(new Integer(result.getInt(1)));
            }
        } catch (MiddlewareException e) {
            e.printStackTrace();
            abr.addDebug("searchEntityIdsByAttributeCodeAndValueForText MiddlewareException on " + e.getMessage());
        } catch (SQLException rx) {
            rx.printStackTrace();
            abr.addDebug("searchEntityIdsByAttributeCodeAndValueForText SQLException on " + rx.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        abr.addDebug("searchEntityIdsByAttributeCodeAndValueForText entity id size: " + entityIds.size());
        return entityIds;
    }

    /**
     * Search FEATURE entity id by FEATURECODE and PDHDOMAIN
     *
     * @param featureCode
     * @param domain
     * @param abr
     * @return
     */
    private int searchFEATUREEntityIdByFeatureCodeAndDomain(String featureCode, String domain, AITFEEDABRSTATUS abr) {
        int entityId = -1;
        PreparedStatement ps = null;
        String sql = "SELECT entityid FROM opicm.flag WHERE entitytype='FEATURE' AND entityid IN (SELECT entityid FROM opicm.text WHERE entitytype='FEATURE' AND attributecode='FEATURECODE' AND attributevalue='" + featureCode + "' AND valto > CURRENT TIMESTAMP AND effto > CURRENT TIMESTAMP) AND attributecode='PDHDOMAIN' AND attributevalue='" + domain + "' AND valto > CURRENT TIMESTAMP AND effto > CURRENT TIMESTAMP";
        try {
            ps = abr.getDatabase().getPDHConnection().prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                entityId = result.getInt(1);
            }
        } catch (MiddlewareException e) {
            e.printStackTrace();
            abr.addDebug("searchFEATUREEntityIdFeatureCodeAndDomain MiddlewareException on " + e.getMessage());
        } catch (SQLException rx) {
            rx.printStackTrace();
            abr.addDebug("searchFEATUREEntityIdFeatureCodeAndDomain SQLException on " + rx.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        abr.addDebug("searchFEATUREEntityIdFeatureCodeAndDomain entity id: " + entityId + " for FEATURECODE=" + featureCode + " PDHDOMAIN=" + domain);
        return entityId;
    }

    /**
     * Get FEATURE MKTGNAME attribute value
     *
     * @param featureCode
     * @param fctransactionEntityItem
     * @param abr
     * @param debugSb
     * @return
     * @throws MiddlewareRequestException
     * @throws SQLException
     * @throws MiddlewareException
     */
    private String getFeatureMktgName(String featureCode, EntityItem fctransactionEntityItem, AITFEEDABRSTATUS abr, StringBuffer debugSb) throws MiddlewareRequestException, SQLException, MiddlewareException {
        String domain = PokUtils.getAttributeFlagValue(fctransactionEntityItem, "PDHDOMAIN");
        String key = featureCode + "|" + domain;
        String mktgName = (String) featureCodeMktgNameTbl.get(key);
        if (mktgName == null) {
            int entityId = searchFEATUREEntityIdByFeatureCodeAndDomain(featureCode, domain, abr);
            if (entityId > 0) {
                EntityItem featureItem = abr.getEntityItem(abr.getProfile(), "FEATURE", entityId);
                mktgName = getAttributeValue(featureItem, "MKTGNAME");
            } else {
                mktgName = "";
                abr.addDebug("getFeatureMktgName not found the FEATURE set MKTGNAME to empty");
            }
        }
        featureCodeMktgNameTbl.put(key, mktgName);
        return mktgName;
    }

    public String getVersion() {
        return "$Revision: 1.6 $";
    }

    public String getXMLFileName() {
        return "ANNFCTRANSACTION.xml";
    }

    public String getVeName() {
        return "dummy";
    }

}
