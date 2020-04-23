// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Vector;

import javax.xml.transform.TransformerConfigurationException;

import org.xml.sax.SAXException;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

// $Log: AITFEEDANNMODELCONVERTABR.java,v $
// Revision 1.1  2015/08/05 09:27:43  wangyul
// EACM to AIT feed
//
public class AITFEEDANNMODELCONVERTABR extends AITFEEDXML {

	public void processThis(AITFEEDABRSTATUS abr, Profile profileT2,
			EntityItem rootEntity) throws FileNotFoundException,
			TransformerConfigurationException, SAXException,
			MiddlewareRequestException, SQLException, MiddlewareException {
		EntityList entityList = abr.getEntityList(profileT2, getVeName(), rootEntity);
		abr.addDebug("EntityList for "+profileT2.getValOn()+" extract "+getVeName()+" contains the following entities: \n"+
                PokUtils.outputList(entityList));
		EntityGroup availEntityGroup = entityList.getEntityGroup("AVAIL");
		EntityGroup modelConvertEntityGroup = entityList.getEntityGroup("MODELCONVERT");
		EntityGroup modelConvertAvailEntityGroup = entityList.getEntityGroup("MODELCONVERTAVAIL");
		
		AITFEEDSimpleSaxXML xml = null;
		try {
			xml = new AITFEEDSimpleSaxXML(ABRServerProperties.getOutputPath() + getXMLFileName() + abr.getABRTime());
			xml.startDocument();
			xml.startElement("ANNMODELCONVERT", xml.createAttribute("xmlns", "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/ANNMODELCONVERT"));
			xml.addElement("ANNNUMBER", getAttributeValue(rootEntity, "ANNNUMBER"));
			xml.addElement("INVENTORYGROUP", getAttributeValue(rootEntity, "INVENTORYGROUP"));
			xml.addElement("ANNTYPE", getAttributeValue(rootEntity, "ANNTYPE"));
			xml.addElement("PDHDOMAIN", getAttributeValue(rootEntity, "PDHDOMAIN"));
			// MODELCONVERTLIST
			xml.startElement("MODELCONVERTLIST");
			Vector modelConvertVct = new Vector(1);
			if (modelConvertEntityGroup != null) {
				EntityItem[] modelConvertEntityItems = modelConvertEntityGroup.getEntityItemsAsArray();
				if (modelConvertEntityItems != null && modelConvertEntityItems.length > 0) {
					for (int i = 0; i < modelConvertEntityItems.length; i++) {
						EntityItem modelConvertItem = modelConvertEntityItems[i];
						String entityID = String.valueOf(modelConvertItem.getEntityID());
						if (modelConvertVct.contains(entityID)) {
							abr.addDebug("Ignore duplicate " + modelConvertItem.getKey());
							continue;
						}
						modelConvertVct.add(entityID);
						
						xml.startElement("MODELCONVERTELEMENT");
						xml.addElement("ENTITYID", entityID);
						xml.addElement("FROMMACHTYPE", getAttributeValue(modelConvertItem, "FROMMACHTYPE"));
						xml.addElement("FROMMODEL", getAttributeValue(modelConvertItem, "FROMMODEL"));
						xml.addElement("TOMACHTYPE", getAttributeValue(modelConvertItem, "TOMACHTYPE"));
						xml.addElement("TOMODEL", getAttributeValue(modelConvertItem, "TOMODEL"));
						xml.addElement("RETURNEDPARTSMES", getAttributeValue(modelConvertItem, "RETURNEDPARTS"));
						xml.endElement("MODELCONVERTELEMENT");
					}
				}
			}
			xml.endElement("MODELCONVERTLIST");
			
			Vector availVct = new Vector(1);
			// AVAILABILITYLIST
			xml.startElement("AVAILABILITYLIST");
			if (availEntityGroup != null) {
				EntityItem[] availEntityItems = availEntityGroup.getEntityItemsAsArray();
				if (availEntityItems != null && availEntityItems.length > 0) {
					for (int i = 0; i < availEntityItems.length; i++) {
						EntityItem availItem = availEntityItems[i];
						String availEntityID = String.valueOf(availItem.getEntityID());
						String availType = getAttributeFlagValue(availItem, "AVAILTYPE");
						if (AVAILTYPE_FILTER.contains(availType)) {
							availVct.add(availEntityID);
							xml.startElement("AVAILABILITYELEMENT");
							xml.addElement("ENTITYID", availEntityID);
							xml.addElement("AVAILTYPE", getAttributeValue(availItem, "AVAILTYPE"));
							xml.addElement("EFFECTIVEDATE", getAttributeValue(availItem, "EFFECTIVEDATE"));
							createCountryListElement(xml, availItem, abr);
							xml.endElement("AVAILABILITYELEMENT");
						} else {
							abr.addDebug("Ignore AVAILTYPE:" + availType + " for " + availItem.getKey());
						}
					}
				}
			}
			xml.endElement("AVAILABILITYLIST");

			// RELATORLIST
			xml.startElement("RELATORLIST");
			if (modelConvertAvailEntityGroup != null) {
				EntityItem[] modelConvertAvailEntityItems = modelConvertAvailEntityGroup.getEntityItemsAsArray();
				if (modelConvertAvailEntityItems != null && modelConvertAvailEntityItems.length > 0) {
					for (int i = 0; i < modelConvertAvailEntityItems.length; i++) {
						EntityItem modelConvertAvail = modelConvertAvailEntityItems[i];
						String entity1ID = ""+modelConvertAvail.getUpLink(0).getEntityID();
						String entity2ID = ""+modelConvertAvail.getDownLink(0).getEntityID();
						if (availVct.contains(entity2ID)) {
							xml.startElement("RELATORELEMENT");
							xml.addElement("ENTITY1TYPE", "MODELCONVERT");
							xml.addElement("ENTITY1ID", entity1ID);
							xml.addElement("ENTITY2TYPE", "AVAIL");
							xml.addElement("ENTITY2ID", entity2ID);
							xml.endElement("RELATORELEMENT");
						}
					}
				}
			}
			xml.endElement("RELATORLIST");
			xml.endElement("ANNMODELCONVERT");
			xml.endDocument();
			
			modelConvertVct.clear();
			modelConvertVct = null;
			availVct.clear();
			availVct = null;
			entityList.dereference();
			entityList = null;
		} finally {
			if (xml != null) {
				xml.close();
			}
		}
	}

	public String getVersion() {
		return "$Revision: 1.1 $";
	}

	public String getXMLFileName() {
		return "ANNMODELCONVERT.xml";
	}

	public String getVeName() {
		return "AITANNMODELCONVERT";
	}

}