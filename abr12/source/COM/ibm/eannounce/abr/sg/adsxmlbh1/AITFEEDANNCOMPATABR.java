// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.xml.transform.TransformerConfigurationException;

import org.xml.sax.SAXException;

import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

import com.ibm.transform.oim.eacm.util.PokUtils;

//$Log: AITFEEDANNCOMPATABR.java,v $
//Revision 1.2  2015/08/26 07:25:25  wangyul
// change tab name TOMACHYPEATR and TOMODELATR to TOMACHTYPE and TOMODEL
//
//Revision 1.1  2015/08/05 09:27:43  wangyul
//EACM to AIT feed
//
public class AITFEEDANNCOMPATABR extends AITFEEDXML {

	public void processThis(AITFEEDABRSTATUS abr, Profile profileT2,
			EntityItem rootEntity) throws FileNotFoundException,
			TransformerConfigurationException, SAXException,
			MiddlewareRequestException, SQLException, MiddlewareException {
		AITFEEDSimpleSaxXML xml = null;
		try {
			xml = new AITFEEDSimpleSaxXML(ABRServerProperties.getOutputPath() + getXMLFileName() + abr.getABRTime());
			xml.startDocument();
			xml.startElement("ANNCOMPAT", xml.createAttribute("xmlns", "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/ANNCOMPAT"));
			xml.addElement("ANNNUMBER", getAttributeValue(rootEntity, "ANNNUMBER"));
			xml.addElement("INVENTORYGROUP", getAttributeValue(rootEntity, "INVENTORYGROUP"));
			xml.addElement("ANNTYPE", getAttributeValue(rootEntity, "ANNTYPE"));
			// AVAIL
			xml.startElement("AVAILABILITYLIST");
			Vector availVct = new Vector(1); // Store avail EntityItem after filter - plan and last order avail necessary
			String veName = getVeName(); // ANN -> AVAIL
			
//			System.gc();
//			abr.addDebug("before extract" + Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory());
			
			EntityList entityList = abr.getEntityList(profileT2, veName, rootEntity);
//			abr.addDebug("after extract" + Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory());
			abr.addDebug("EntityList for "+profileT2.getValOn()+" extract "+veName+" contains the following entities: \n"+
	                PokUtils.outputList(entityList));
			EntityGroup availEntityGroup = entityList.getEntityGroup("AVAIL");
			if (availEntityGroup != null) {
				 EntityItem[] availEntityItems = availEntityGroup.getEntityItemsAsArray();
				 if (availEntityItems != null && availEntityItems.length > 0) {
					 for (int i = 0; i < availEntityItems.length; i++) {
						 EntityItem availItem = availEntityItems[i];

						 // Avail filter, only New and End of Life - Withdrawal from Marketing
						 String availType = getAttributeFlagValue(availItem, "AVAILTYPE");
						 if (AVAILTYPE_FILTER.contains(availType)) {
							 availVct.add(availItem);
							 
							 xml.startElement("AVAILABILITYELEMENT");
							 xml.addElement("ENTITYID", String.valueOf(availItem.getEntityID()));
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
			
			// COMPAT
			xml.startElement("COMPATLIST");
			String veName2 = getVeName2(); // AVAIL -> MODEL -> MODELCG -> MODELCGOS -> MODEL
			Vector relatorList = new Vector(1); // Store the entity ids of AVAIL and MODEL for RELATORLIST tab
			Set existInXMLModelEntityIDs = new HashSet(); // Store the entity id of MODEL which display in COMPATLIST tab 
			Vector uniqueModelEntityIDs = new Vector(1); // Filter duplicate entity id of MODEL
			Vector compatVct = new Vector(); // Filter duplicate COMPATELEMENT records
			for (int i = 0; i < availVct.size(); i++) {
				EntityItem availItem = (EntityItem) availVct.get(i);
				
//				System.gc();
//				abr.addDebug("before extract" + Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory());
				
				EntityList modelEntityList = abr.getEntityList(profileT2, veName2, availItem);
//				abr.addDebug("after extract" + Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory());
				abr.addDebug("EntityList for "+profileT2.getValOn()+" extract "+veName2+" contains the following entities: \n"+
		                PokUtils.outputList(modelEntityList));
				EntityGroup modelAvailEntityGroup = modelEntityList.getEntityGroup("MODELAVAIL");
				if (modelAvailEntityGroup != null) {
					EntityItem[] modelAvailEntityItems = modelAvailEntityGroup.getEntityItemsAsArray();
					if (modelAvailEntityItems != null && modelAvailEntityItems.length > 0) {
						for (int n = 0; n < modelAvailEntityItems.length; n++) {
							EntityItem modelAvailEntityItem = modelAvailEntityItems[n];
							Vector modelVct = getUpOrDownRelatorEntityItems(modelAvailEntityItem, "UP", "MODEL");
							if (modelVct != null && modelVct.size() > 0) {
								for (int o = 0; o < modelVct.size(); o++) {
									EntityItem modelEntityItem = (EntityItem)modelVct.get(o);
									
									String modelEntityID = String.valueOf(modelEntityItem.getEntityID());
									
									// save relator
									AITFEEDXMLRelator relator = new AITFEEDXMLRelator();					
									relator.setEntity1ID(modelEntityID);
									relator.setEntity2ID(String.valueOf(availItem.getEntityID()));
									relatorList.add(relator);
									
									// duplicate model id check
									if (uniqueModelEntityIDs.contains(modelEntityID)) {
										abr.addDebug("Ignore duplicate MODEL entity id:" + modelEntityID);
										continue;
									}
									uniqueModelEntityIDs.add(modelEntityID);
									
									// MDLCGMDL
									Vector modelCGEntityItems = getUpOrDownEntityItems(modelEntityItem, "UP", "MDLCGMDL", "MODELCG");
									if (modelCGEntityItems != null && modelCGEntityItems.size() > 0) {
										for (int k = 0; k < modelCGEntityItems.size(); k++) {
											EntityItem modelCGEntityItem = (EntityItem)modelCGEntityItems.get(k);
											String okToPub = getAttributeValue(modelCGEntityItem, "OKTOPUB");
											// MODELCGOS
											Vector modelCGOSEntityItems = getUpOrDownEntityItems(modelCGEntityItem, "DOWN", "MDLCGMDLCGOS", "MODELCGOS");
											if (modelCGOSEntityItems != null && modelCGOSEntityItems.size() > 0) {
												for (int l = 0; l < modelCGOSEntityItems.size(); l++) {
													EntityItem modelCGOSEntityItem = (EntityItem)modelCGOSEntityItems.get(l);
													// TO MODEL
													Vector optionModelEntityItems = getUpOrDownEntityItems(modelCGOSEntityItem, "DOWN", "MDLCGOSMDL", "MODEL");
													if (optionModelEntityItems != null && optionModelEntityItems.size() > 0) {
														for (int m = 0; m < optionModelEntityItems.size(); m++) {
															EntityItem optionModelEntityItem = (EntityItem)optionModelEntityItems.get(m);
															
															String toEntityID = String.valueOf(optionModelEntityItem.getEntityID());
															String compatKey = modelEntityID + okToPub + toEntityID;
															// duplicate COMPATELEMENT check
															if (compatVct.contains(compatKey)) {
																abr.addDebug("Ignore duplicate COMPAT " + compatKey + " for " + modelCGEntityItem.getKey() + " for " + modelCGOSEntityItem.getKey());
															} else {
																compatVct.add(compatKey);
																existInXMLModelEntityIDs.add(modelEntityID);
																
																xml.startElement("COMPATELEMENT");
																xml.addElement("FROMENTITYID", modelEntityID);
																xml.addElement("FROMMACHTYPE", getAttributeValue(modelEntityItem, "MACHTYPEATR"));
																xml.addElement("FROMMODEL", getAttributeValue(modelEntityItem, "MODELATR"));
																xml.addElement("OKTOPUB", okToPub);
																xml.addElement("TOENTITYID", toEntityID);
																xml.addElement("TOMACHTYPE", getAttributeValue(optionModelEntityItem, "MACHTYPEATR"));
																xml.addElement("TOMODEL", getAttributeValue(optionModelEntityItem, "MODELATR"));
																xml.addElement("MKTGNAME", getAttributeValue(optionModelEntityItem, "MKTGNAME"));
																xml.addElement("COMPATDVCCAT", getAttributeValue(optionModelEntityItem, "COMPATDVCCAT"));
																xml.addElement("COMPATDVCSUBCAT", getAttributeValue(optionModelEntityItem, "COMPATDVCSUBCAT"));
																xml.endElement("COMPATELEMENT");
															}
														}
													} else {
														abr.addDebug("Not found option MODEL for " + modelCGEntityItem.getKey() + " for " + modelEntityItem.getKey() + " for " + modelCGOSEntityItem.getKey());
													}
												}
											} else {
												abr.addDebug("Not found MODELCGOS for " + modelCGEntityItem.getKey() + " for " + modelEntityItem.getKey());
											}
										}
									} else {
										abr.addDebug("Not found MODELCG for " + modelEntityItem.getKey());
									}
								}
							} else {
								abr.addDebug("Not found MODEL for " + modelAvailEntityItem.getKey());
							}
						}
					}
				}				
				modelEntityList.dereference();
				modelEntityList = null;
			}
			xml.endElement("COMPATLIST");
			
			// RELATOR
			xml.startElement("RELATORLIST");
			for (int i = 0; i < relatorList.size(); i++) {
				AITFEEDXMLRelator relator = (AITFEEDXMLRelator)relatorList.get(i);
				if (existInXMLModelEntityIDs.contains(relator.getEntity1ID())) {
					xml.startElement("RELATORELEMENT");
					xml.addElement("ENTITY1TYPE", "MODEL");
					xml.addElement("ENTITY1ID", relator.getEntity1ID());
					xml.addElement("ENTITY2TYPE", "AVAIL");
					xml.addElement("ENTITY2ID", relator.getEntity2ID());
					xml.endElement("RELATORELEMENT");
				} else {
					abr.addDebug("The MODEL not dispaly in the COMPATLIST, it will not display in RELATORLIST for MODEL" + relator.getEntity1ID());
				}
			}
			xml.endElement("RELATORLIST");
			
			xml.endElement("ANNCOMPAT");  
	        xml.endDocument(); 
			
	        relatorList.clear();
	        relatorList = null;
	        availVct.clear();
	        availVct = null;
	        entityList.dereference();
	        entityList = null;
	        uniqueModelEntityIDs.clear();
	        uniqueModelEntityIDs = null;
	        compatVct.clear();
	        compatVct = null;
	        existInXMLModelEntityIDs.clear();
	        existInXMLModelEntityIDs = null;
		} finally {
			if (xml != null) {
				xml.close();
			}
		}
	}
	
	/**
	 * Example: MODELAVAIL -> MODEL
	 * Get the MODEL EntityItems from MODELAVAIL
	 * @param item
	 * @param relatorName
	 * @param entityType
	 * @return
	 */
	private Vector getUpOrDownRelatorEntityItems(EntityItem item, String linkType, String entityType) {
		Vector entityItems = null;
		Vector linkVct;
		if ("UP".equalsIgnoreCase(linkType)) {
			linkVct = item.getUpLink();
		} else {
			linkVct = item.getDownLink();
		}
		if (linkVct != null && linkVct.size() > 0) {
			entityItems = new Vector(1);
			for (int i = 0; i < linkVct.size(); i++) {
				EntityItem linkEntityItem = (EntityItem)linkVct.get(i);
				if (linkEntityItem.getEntityType().equals(entityType)) {
					entityItems.add(linkEntityItem);
				}
			}
		}
		return entityItems;
	}
	
	/**
	 * Example: MODEL -> MDLCGMDL -> MODELCG
	 * Get the MODELCG EntityItems from MODEL
	 * @param item
	 * @param relatorName
	 * @param entityType
	 * @return
	 */
	private Vector getUpOrDownEntityItems(EntityItem item, String linkType, String relatorName, String entityType) {
		Vector entityItems = null;
		Vector linkVct;
		if ("UP".equalsIgnoreCase(linkType)) {
			linkVct = item.getUpLink();
		} else {
			linkVct = item.getDownLink();
		}
		if (linkVct != null && linkVct.size() > 0) {
			entityItems = new Vector(1);
			for (int i = 0; i < linkVct.size(); i++) {
				EntityItem linkEntityItem = (EntityItem)linkVct.get(i);
				if (linkEntityItem.getEntityType().equals(relatorName)) {
					Vector tempLinkVct;
					if ("UP".equalsIgnoreCase(linkType)) {
						tempLinkVct = linkEntityItem.getUpLink();
					} else {
						tempLinkVct = linkEntityItem.getDownLink();
					}
					if (tempLinkVct != null && tempLinkVct.size() > 0) {
						for (int j = 0; j < tempLinkVct.size(); j++) {
							EntityItem tempLinkEntityItem = (EntityItem)tempLinkVct.get(j);
							if (tempLinkEntityItem.getEntityType().equals(entityType)) {
								entityItems.add(tempLinkEntityItem);
							}
						}
					}
				}
			}
		}
		return entityItems;
	}

	public String getVeName() {
		return "AITANNCOMPAT";
	}
	
	private String getVeName2() {
		return "AITANNCOMPAT2";
	}

	public String getVersion() {
		return "$Revision: 1.2 $";
	}

	public String getXMLFileName() {
		return "ANNCOMPAT.xml";
	}

}
