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

import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

import com.ibm.transform.oim.eacm.util.PokUtils;

//$Log: AITFEEDANNTMFABR.java,v $
//Revision 1.8  2017/09/05 08:04:41  wangyul
//Story 1748877 - AIT HWW - Autogen files issue - Add new ordercode fled in AIT Model XML
//
//Revision 1.7  2017/08/07 06:48:51  wangyul
//[Work Item 1726324] AIT HWW - Autogen files issue - Add new install filed in AIT Model XML
//
//Revision 1.5  2017/06/15 14:48:08  wangyul
//Defect 1709867 - AIT HWW - Autogen files issue
//
//Revision 1.4  2017/06/15 14:06:18  wangyul
//Defect 1709867 - AIT HWW - Autogen files issue
//
//Revision 1.3  2016/09/09 13:13:57  wangyul
//Task1591979 - AIT attribute change
//
//Revision 1.2  2016/05/12 12:57:37  wangyul
//Task 1510721 Codeing for  AIT feed change
//
//EACM to AIT feed
//
public class AITFEEDANNTMFABR extends AITFEEDXML {

	public void processThis(AITFEEDABRSTATUS abr, Profile profileT2,
			EntityItem rootEntity) throws FileNotFoundException,
			TransformerConfigurationException, SAXException,
			MiddlewareRequestException, SQLException, MiddlewareException {
		Vector availResultVct = new Vector();
		Vector modelResultVct = new Vector();
		Vector modelIdVct = new Vector();
		Vector tmfResultVct = new Vector();
		Vector tmfIdVct = new Vector();
		Vector relatorResultVct = new Vector();
		
		// Extract entities by VE, ANN - AVAIL
		EntityList entityList = abr.getEntityList(profileT2, getVeName(), rootEntity);
		abr.addDebug("EntityList for " + profileT2.getValOn() + " extract "
				+ getVeName() + " contains the following entities: \n"
				+ PokUtils.outputList(entityList));
		EntityItem annEntity = entityList.getParentEntityGroup().getEntityItem(0); // ANN
		
		Vector availVct = PokUtils.getAllLinkedEntities(annEntity, "ANNAVAILA", "AVAIL"); // ANN - AVAIL
		for (int i = 0; i < availVct.size(); i++) {
			EntityItem availEntity = (EntityItem) availVct.get(i);

			// avail filter, Only Plan and Last Order avail
			String availType = getAttributeFlagValue(availEntity, "AVAILTYPE");
			if (!AVAILTYPE_FILTER.contains(availType)) {
				abr.addDebug("skip " + availEntity.getKey() + " for AVAILTYPE " + availType);
				continue;
			}
			
			// Extract entities by VE2, AVAIL - MODEL/(TMF - MODEL/FEATURE)
			// Use 2 ve avoid oom, because 1 avail may link many tmfs and models
			EntityList entityList2 = abr.getEntityList(profileT2, getVeName2(), availEntity);
			abr.addDebug("EntityList for " + profileT2.getValOn() + " extract "
					+ getVeName2() + " contains the following entities: \n"
					+ PokUtils.outputList(entityList2));
			EntityItem availEntity2 = entityList2.getParentEntityGroup().getEntityItem(0);
			
			// Get MODELs for path: ANN - AVAIL - MODEL
			Vector modelVct = PokUtils.getAllLinkedEntities(availEntity2, "MODELAVAIL", "MODEL");
			abr.addDebug("model size " + modelVct.size());
			Vector tmfVct = PokUtils.getAllLinkedEntities(availEntity2, "OOFAVAIL", "PRODSTRUCT");
			abr.addDebug("tmf size " + tmfVct.size());
			if (modelVct.size() > 0 || tmfVct.size() > 0) { // Not set the avail, if didn't link other entities
				availResultVct.add(availEntity2);
			} else {
				abr.addDebug("The " + availEntity2.getKey() + " didn't link model and tmf");
			}
			
			for (int j = 0; j < modelVct.size(); j++) {
				EntityItem modelEntity = (EntityItem)modelVct.get(j);
				if (!modelIdVct.contains("" + modelEntity.getEntityID())) {
					modelIdVct.add("" + modelEntity.getEntityID());
					modelResultVct.add(modelEntity);
				} else {
					abr.addDebug("duplicate " + modelEntity.getKey());
				}
				relatorResultVct.add(new AITFEEDXMLRelator("MODEL", "" + modelEntity.getEntityID(), "AVAIL", "" + availEntity.getEntityID()));
			}
			
			for (int j = 0; j < tmfVct.size(); j++) {
				EntityItem tmfEntity = (EntityItem)tmfVct.get(j);
				if (!tmfIdVct.contains("" + tmfEntity.getEntityID())) {
					tmfIdVct.add("" + tmfEntity.getEntityID());
					tmfResultVct.add(tmfEntity);
				} else {
					abr.addDebug("duplicate " + tmfEntity.getKey());
				}
				relatorResultVct.add(new AITFEEDXMLRelator("PRODSTRUCT", "" + tmfEntity.getEntityID(), "AVAIL", "" + availEntity.getEntityID()));
			}
		}
		
		// Generate the xml
		AITFEEDSimpleSaxXML xml = null;
		try {
			xml = new AITFEEDSimpleSaxXML(ABRServerProperties.getOutputPath() + getXMLFileName() + abr.getABRTime());
	        xml.startDocument(); 
	        xml.startElement("ANNTMF", xml.createAttribute("xmlns", "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/ANNTMF"));
	        xml.addElement("ANNNUMBER", getAttributeValue(rootEntity, "ANNNUMBER"));
	        xml.addElement("INVENTORYGROUP", getAttributeValue(rootEntity, "INVENTORYGROUP"));
	        xml.addElement("ANNTYPE", getAttributeValue(rootEntity, "ANNTYPE"));
	        
	        // AVAILABILITYLIST
	        generateAvailList(xml, availResultVct, abr);
	        // MODELLIST
	        generateModelList(xml, modelResultVct, abr);
			// TMFLIST
	        generateTmfList(xml, tmfResultVct, abr);
	        // RELATORLIST
	        generateRelatorList(xml, relatorResultVct, abr);
			
			xml.endElement("ANNTMF");  
	        xml.endDocument();   
	        
	        // release memory
	        availResultVct.clear();
	        availResultVct = null;
	        modelResultVct.clear();
	        modelResultVct = null;
	        tmfResultVct.clear();
	        tmfResultVct = null;
	        relatorResultVct.clear();
	        relatorResultVct = null;
	        entityList.dereference();
	        entityList = null;
	        availVct.clear();
	        availVct = null;
		} finally {
			if (xml != null) {
				xml.close();
			}
		}
	}

	private void generateAvailList(AITFEEDSimpleSaxXML xml, Vector avails, AITFEEDABRSTATUS abr) throws SAXException {
		xml.startElement("AVAILABILITYLIST");
		for (int i = 0; i < avails.size(); i++) {
			EntityItem availItem = (EntityItem) avails.get(i);
			xml.startElement("AVAILABILITYELEMENT");
			xml.addElement("ENTITYID", String.valueOf(availItem.getEntityID()));
			xml.addElement("AVAILTYPE", getAttributeValue(availItem, "AVAILTYPE"));
			xml.addElement("EFFECTIVEDATE", getAttributeValue(availItem, "EFFECTIVEDATE"));
			createCountryListElement(xml, availItem, abr);
			xml.endElement("AVAILABILITYELEMENT");
		}
		xml.endElement("AVAILABILITYLIST");
	}
	
	private void generateModelList(AITFEEDSimpleSaxXML xml, Vector models, AITFEEDABRSTATUS abr) throws SAXException {
		xml.startElement("MODELLIST");
		for (int i = 0; i < models.size(); i++) {
			EntityItem modelEntity = (EntityItem)models.get(i);
			xml.startElement("MODELELEMENT");
			xml.addElement("ENTITYID", "" + modelEntity.getEntityID());
			xml.addElement("MACHTYPEATR", getAttributeValue(modelEntity, "MACHTYPEATR"));
			xml.addElement("MODELATR", getAttributeValue(modelEntity, "MODELATR"));
			xml.addElement("MKTGNAME", getAttributeValue(modelEntity, "MKTGNAME"));
			xml.addElement("INVNAME", getAttributeValue(modelEntity, "INVNAME"));
			xml.addElement("INSTALL", getAttributeValue(modelEntity, "INSTALL"));
			xml.addElement("ORDERCODE", getAttributeValue(modelEntity, "MODELORDERCODE"));
			xml.endElement("MODELELEMENT");
		}
		xml.endElement("MODELLIST");
	}
	
	private void generateTmfList(AITFEEDSimpleSaxXML xml, Vector tmfs, AITFEEDABRSTATUS abr) throws SAXException {
		xml.startElement("TMFLIST");
		for (int i = 0; i < tmfs.size(); i++) {
			EntityItem tmfEntity = (EntityItem)tmfs.get(i);
			EntityItem modelEntity = getModelEntityFromTmf(tmfEntity);
			EntityItem featureEntity = getFeatureEntityFromTmf(tmfEntity);
			if (modelEntity != null && featureEntity != null) {
				xml.startElement("TMFELEMENT");
				xml.addElement("ENTITYID", "" + tmfEntity.getEntityID());
				xml.addElement("ORDERCODE", getAttributeValue(tmfEntity, "ORDERCODE"));
				xml.addElement("INSTALL", getAttributeValue(tmfEntity, "INSTALL"));
				xml.addElement("RETURNEDPARTS", getAttributeValue(tmfEntity, "RETURNEDPARTS"));
				xml.addElement("SYSTEMMIN", getAttributeValue(tmfEntity, "SYSTEMMIN"));
				xml.addElement("SYSTEMMAX", getAttributeValue(tmfEntity, "SYSTEMMAX"));
				xml.addElement("INTORDERMAX", getAttributeValue(tmfEntity, "INITORDERMAX"));
				xml.addElement("OSLEVELCOMPLEMENT", getAttributeValue(tmfEntity, "OSLEVELCOMPLEMENT"));
				xml.addElement("TMFEDITORNOTE", getAttributeValue(tmfEntity, "EDITORNOTE"));
				xml.addElement("COMMENT", getAttributeValue(tmfEntity, "COMMENTS"));
				xml.addElement("PREREQ", getAttributeValue(tmfEntity, "PREREQ"));
				xml.addElement("COREQUISITE", getAttributeValue(tmfEntity, "COREQUISITE"));
				xml.addElement("LMTATION", getAttributeValue(tmfEntity, "LMTATION"));
				xml.addElement("COMPATIBILITY", getAttributeValue(tmfEntity, "COMPATIBILITY"));
				xml.addElement("CBLORD", getAttributeValue(tmfEntity, "CBLORD"));
				// MODEL attributes	
				xml.addElement("MACHTYPEATR", getAttributeValue(modelEntity, "MACHTYPEATR"));
				xml.addElement("MODELATR", getAttributeValue(modelEntity, "MODELATR"));
				// FEATURE attributes
				xml.addElement("FEATURECODE", getAttributeValue(featureEntity, "FEATURECODE"));
				xml.addElement("MODELINVNAME", getAttributeValue(modelEntity, "INVNAME"));
				xml.addElement("INVNAME", getAttributeValue(featureEntity, "INVNAME"));
				xml.addElement("EDITORNOTE", getAttributeValue(featureEntity, "EDITORNOTE"));
				xml.addElement("PRICEDFEATURE", getAttributeValue(featureEntity, "PRICEDFEATURE"));
				xml.addElement("ZEROPRICE", getAttributeValue(featureEntity, "ZEROPRICE"));
				xml.addElement("MAINTPRICE", getAttributeValue(featureEntity, "MAINTPRICE"));
				xml.addElement("HWFCCAT", getAttributeValue(featureEntity, "HWFCCAT"));
				xml.addElement("MKTGNAME", getAttributeValue(featureEntity, "MKTGNAME"));
				xml.addElement("ATTPROVIDED", getAttributeValue(featureEntity, "ATTPROVIDED"));
				xml.addElement("ATTREQUIRED", getAttributeValue(featureEntity, "ATTREQUIRED"));
				xml.addElement("FCMKTGDESC", getAttributeValue(featureEntity, "FCMKTGDESC"));
				xml.addElement("CONFIGURATORFLAG", getAttributeValue(tmfEntity, "CONFIGURATORFLAG"));
				xml.addElement("BULKMESINDC", getAttributeValue(tmfEntity, "BULKMESINDC"));
				xml.addElement("WARRSVCCOVR", getAttributeValue(tmfEntity, "WARRSVCCOVR"));
		    	xml.endElement("TMFELEMENT");
			} else {
				abr.addDebug("model or feature is null skip the " + tmfEntity.getKey());
			}
		}
		xml.endElement("TMFLIST");
	}

	private void generateRelatorList(AITFEEDSimpleSaxXML xml, Vector relators, AITFEEDABRSTATUS abr) throws SAXException {
		xml.startElement("RELATORLIST");
		for(int i = 0; i < relators.size(); i++) {
			AITFEEDXMLRelator relator = (AITFEEDXMLRelator)relators.get(i);
			xml.startElement("RELATORELEMENT");
			xml.addElement("ENTITY1TYPE", relator.getEntit1Type());
			xml.addElement("ENTITY1ID", relator.getEntity1ID());
			xml.addElement("ENTITY2TYPE", relator.getEntity2Type());
			xml.addElement("ENTITY2ID", relator.getEntity2ID());
			xml.endElement("RELATORELEMENT");
		}
		xml.endElement("RELATORLIST");
	}
	
	private EntityItem getModelEntityFromTmf(EntityItem tmfEntity) {
		EntityItem modelItem = null;
		Vector linkVct = tmfEntity.getDownLink();
		if(linkVct != null && linkVct.size() > 0) {
			for (int k = 0; k < linkVct.size(); k++) {
				EntityItem item = (EntityItem)linkVct.get(k);
				if("MODEL".equals(item.getEntityType())) {
					modelItem = item;
					break;
				}
			}
		}
		return modelItem;
	}
	
	private EntityItem getFeatureEntityFromTmf(EntityItem tmfEntity) {
		EntityItem featureItem = null;
		Vector featureVct = tmfEntity.getUpLink();
		if(featureVct != null && featureVct.size() > 0) {
			for (int k = 0; k < featureVct.size(); k++) {
				EntityItem item = (EntityItem)featureVct.get(k);
				if("FEATURE".equals(item.getEntityType())) {
					featureItem = item;
					break;
				}
			}
		}
		return featureItem;
	}
	
	/**
	 * get the name of the VE to use
	 */
	public String getVeName() {return "AITANNTMF";}
	
	private String getVeName2() {return "AITANNTMF2";}

	public String getVersion() {
		return "$Revision: 1.8 $";
	}

	public String getXMLFileName() {
		return "ANNTMF.xml";
	}
}