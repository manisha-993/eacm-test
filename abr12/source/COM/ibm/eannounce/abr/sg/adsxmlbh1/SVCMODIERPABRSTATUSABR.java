// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2021  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.sg.rfc.RdhClassificationMaint;
import COM.ibm.eannounce.abr.sg.rfc.RdhMatmCreate;
import COM.ibm.eannounce.abr.sg.rfc.RdhTssMatChar;
import COM.ibm.eannounce.abr.sg.rfc.SVCMOD;
import COM.ibm.eannounce.abr.sg.rfc.XMLParse;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import java.util.*;
import java.sql.*;
import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.TransformerException;

import org.w3c.dom.*;

import com.ibm.transform.oim.eacm.util.PokUtils;

/**********************************************************************************
 * This is an extract and feed of Meta Data with the source being
 * MetaDescription table.
 */

public class SVCMODIERPABRSTATUSABR extends XMLMQAdapter {

	private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'SVCMOD' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";

	/**********************************
	 * create xml and write to queue
	 */
	public void processThis(ADSABRSTATUS abr, Profile profileT1, Profile profileT2, EntityItem rootEntity)
			throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException, ParserConfigurationException,
			java.rmi.RemoteException, COM.ibm.eannounce.objects.EANBusinessRuleException,
			COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException, IOException,
			javax.xml.transform.TransformerException, MissingResourceException {
		// step1 get the attribute of rootEntity(?XML Product Price Setup?
		// (XMLPRODPRICESETUP) entity)
		// the same value with the value of ADSDTS from rootEntity
		// because in the ADSABRSTAUTS, t1DTS = PokUtils.getAttributeValue(rootEntity,
		// "ADSDTS",",", "", false);
		String t1DTS = profileT1.getValOn();

		String t2DTS = profileT2.getValOn();
		abr.addDebug("SVCMODIERPABRSTATUSABR process t1DTS=" + t1DTS);
		abr.addDebug("SVCMODIERPABRSTATUSABR process t2DTS=" + t2DTS);
		Connection connection = abr.getDB().getODSConnection();
		PreparedStatement statement = connection.prepareStatement(CACEHSQL);
		statement.setInt(1, rootEntity.getEntityID());
		ResultSet resultSet = statement.executeQuery();
		String xml = null;
		while (resultSet.next()) {
			xml = resultSet.getString("XMLMESSAGE");
		}
		abr.addDebug(xml);
		if (xml != null) {
			try {
				SVCMOD svcmod = XMLParse.getSvcmodFromXml(xml);
				RdhMatmCreate create = new RdhMatmCreate(svcmod);
				abr.addDebug("Calling " + create.getRFCName());
				create.execute();
				abr.addDebug(create.createLogEntry());
				if (create.getRfcrc() == 0) {
					abr.addOutput(create.getRFCName() + " called successfully!");
				} else {
					abr.addOutput(create.getRFCName() + " called  faild!");
					abr.addOutput(create.getError_text());
				}

				String obj_id = svcmod.getMACHTYPE() + svcmod.getMODEL();
				String class_name = "MG_COMMON";
				String class_type = "001";
				RdhClassificationMaint cMaint = new RdhClassificationMaint(obj_id, class_name, class_type);

				abr.addDebug("Calling " + cMaint.getRFCName()+" ID="+obj_id+" NAME="+class_name+" type="+class_type);

				String type = "MG_PRODUCTTYPE";
				String tableData = getTableMapingDate(type, svcmod);
				abr.addDebug("addCharacteristic for type="+type+" value=" +tableData );
				if (tableData != null && !"No characteristic".equals(tableData)) {	
					abr.addDebug("addCharacteristic for type="+type+" value=" +tableData );
					cMaint.addCharacteristic(type, tableData);
				}else {
					abr.addDebug("addCharacteristic for type="+type+" value=" +"" );
					cMaint.addCharacteristic(type, "");
				} // No characteristic
				cMaint.execute();
				abr.addDebug(cMaint.createLogEntry());
				if (cMaint.getRfcrc() == 0) {
					abr.addOutput(cMaint.getRFCName() + " called successfully!");
				} else {
					abr.addOutput(cMaint.getRFCName() + " called  faild!");
					abr.addOutput(cMaint.getError_text());
				}
				class_name = "MM_CUSTOM_SERVICES";
				cMaint = new RdhClassificationMaint(obj_id, class_name, class_type);
				type = "MM_CUSTOM_TYPE";
				tableData = getTableMapingDate(type, svcmod);
				if (tableData != null && !"No characteristic".equals(tableData)) {
					abr.addDebug("addCharacteristic for type="+type+" value=" +tableData );
					cMaint.addCharacteristic(type, tableData);
				}else {
					abr.addDebug("addCharacteristic for type="+type+" value=" +"" );
					cMaint.addCharacteristic(type, "");
				}
				type = "MM_CUSTOM_COSTING";
				tableData = getTableMapingDate(type, svcmod);
				
				if (tableData != null && !"No characteristic".equals(tableData)) {
					abr.addDebug("addCharacteristic for type="+type+" value=" +tableData );
					cMaint.addCharacteristic(type, tableData);
				}else {
					abr.addDebug("addCharacteristic for type="+type+" value=" +"" );
					cMaint.addCharacteristic(type, "");
				}
				type = "MM_PROFIT_CENTER";
				tableData = getTableMapingDate(type, svcmod);
				if (tableData != null && !"No characteristic".equals(tableData)) {
					abr.addDebug("addCharacteristic for type="+type+" value=" +tableData );
					cMaint.addCharacteristic(type, tableData);
				}else {
					abr.addDebug("addCharacteristic for type="+type+" value=" +"" );
					cMaint.addCharacteristic(type, "");
				}
				type = "MM_TAX_CATEGORY";
				tableData = getTableMapingDate(type, svcmod);
				
				if (tableData != null && !"No characteristic".equals(tableData)) {
					abr.addDebug("addCharacteristic for type="+type+" value=" +tableData );
					cMaint.addCharacteristic(type, tableData);
				}else {
					abr.addDebug("addCharacteristic for type="+type+" value=" +"" );
					cMaint.addCharacteristic(type, "");
				}
				cMaint.execute();
				abr.addDebug(cMaint.createLogEntry());
				if (cMaint.getRfcrc() == 0) {
					abr.addOutput(cMaint.getRFCName() + " called successfully!");
				} else {
					abr.addOutput(cMaint.getRFCName() + " called  faild!");
					abr.addOutput(cMaint.getError_text());
				}

				/*
				 * class_name = "MM_CUSTOM_COSTING"; cMaint = new RdhClassificationMaint(obj_id,
				 * class_name, class_type);
				 */
				
				/*
				 * cMaint.execute(); abr.addDebug(cMaint.createLogEntry()); if
				 * (cMaint.getRfcrc() == 0) { abr.addOutput(cMaint.getRFCName() +
				 * " called successfully!"); } else { abr.addOutput(cMaint.getRFCName() +
				 * " called  faild!"); abr.addOutput(cMaint.getError_text()); }
				 */
				class_name = "MM_FIELDS";
				cMaint = new RdhClassificationMaint(obj_id, class_name, class_type);
				
				if ("Yes".equals(svcmod.getSOPRELEVANT())) {
					type = "MM_SOP_IND";
					abr.addDebug("addCharacteristic for type="+type+" value=" +"X" );
					cMaint.addCharacteristic(type, "X");
				}
				type = "MM_TASK_TYPE";
				abr.addDebug("addCharacteristic for type="+type+" value=" +svcmod.getSOPTASKTYPE() );
				cMaint.addCharacteristic(type, svcmod.getSOPTASKTYPE());
				type = "MM_OPPORTUNITY_CODE";
				abr.addDebug("addCharacteristic for type="+type+" value=" +svcmod.getWWOCCODE() );
				cMaint.addCharacteristic(type, svcmod.getWWOCCODE());
				cMaint.execute();
				abr.addDebug(cMaint.createLogEntry());

				if (cMaint.getRfcrc() == 0) {
					abr.addOutput(cMaint.getRFCName() + " called successfully!");
					abr.addOutput(cMaint.getError_text());
				} else {
					abr.addOutput(cMaint.getRFCName() + " called  faild!");
					abr.addOutput(cMaint.getError_text());
				}
				/*
				 * cMaint.execute(); abr.addDebug(cMaint.createLogEntry()); if
				 * (cMaint.getRfcrc() == 0) { abr.addOutput(cMaint.getRFCName() +
				 * " called successfully!"); abr.addOutput(cMaint.getError_text()); } else {
				 * abr.addOutput(cMaint.getRFCName() + " called  faild!");
				 * abr.addOutput(cMaint.getError_text()); }
				 */
				if (svcmod.hasProds()) {
					RdhTssMatChar chart = new RdhTssMatChar(svcmod);
					abr.addDebug("Calling " + chart.getRFCName());
					chart.execute();
					abr.addDebug(chart.createLogEntry());
					if (chart.getRfcrc() == 0) {
						abr.addOutput(chart.getRFCName() + " called successfully!");
						abr.addOutput(chart.getError_text());
					} else {
						abr.addOutput(chart.getRFCName() + " called  faild!");
						abr.addOutput(chart.getError_text());
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				abr.addError(e.getMessage());
			}

		} else {
			abr.addOutput("XML file not exeit in cache,RFC caller not called!");
			return;
		}
		//abr.addOutput(xml);

	}

	private String getTableMapingDate(String key, SVCMOD svcmod) {
		String value = null;
		if ("MG_PRODUCTTYPE".equals(key)) {
			if ("Service".equals(svcmod.getCATEGORY())) {
				if ("Custom".equals(svcmod.getSUBCATEGORY())) {
					if ("Project Based".equals(svcmod.getGROUP()) || "Operation Based".equals(svcmod.getGROUP())) {
						value = "S3";
					}
				} else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
					if ("Penalty".equals(svcmod.getGROUP())) {
						value = "S6";
					} else if ("Incident".equals(svcmod.getGROUP())) {
						value = "S7";
					} else if ("Travel".equals(svcmod.getGROUP())) {
						value = "S5";
					} else if ("Activity".equals(svcmod.getGROUP())) {
						value = "S4";
					} else if ("OEM".equals(svcmod.getGROUP())) {
						value = "S4";
					}
					else if ("ICA/NEC".equals(svcmod.getGROUP())) {
						value = "S2";
					}

				} else if ("Productized Services".equals(svcmod.getSUBCATEGORY())) {
					if ("Non-Federated".equals(svcmod.getGROUP())) {
						value = "S2";
					}
				}
			} else if ("IP".equals(svcmod.getCATEGORY())) {
				if ("SC".equals(svcmod.getSUBCATEGORY())) {
					value = "S8";
				}
			}
		} else if ("MM_CUSTOM_TYPE".equals(key)) {

			if ("Service".equals(svcmod.getCATEGORY())) {
				if ("Custom".equals(svcmod.getSUBCATEGORY())) {
					if ("Project Based".equals(svcmod.getGROUP()) || "Operation Based".equals(svcmod.getGROUP())) {
						value = "OCI";
					}
				} else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
					if ("Penalty".equals(svcmod.getGROUP())) {
						value = "PC";
					} else if ("Incident".equals(svcmod.getGROUP())) {
						value = "IC";
					} else if ("Travel".equals(svcmod.getGROUP())) {
						value = "TE";
					} else if ("Activity".equals(svcmod.getGROUP())) {
						value = "SA";
					}else if ("OEM".equals(svcmod.getGROUP())) {
						value = "SA";
					} else if ("ICA/NEC".equals(svcmod.getGROUP())) {
						value = "No characteristic";
					}

				} else if ("Productized Services".equals(svcmod.getSUBCATEGORY())) {
					if ("Non-Federated".equals(svcmod.getGROUP())) {
						value = "SPI";
					}
				}
			} else if ("IP".equals(svcmod.getCATEGORY())) {
				if ("SC".equals(svcmod.getSUBCATEGORY())) {
					value = "IPSC";
				}
			}

		} else if ("MM_CUSTOM_COSTING".equals(key)) {

			if ("Service".equals(svcmod.getCATEGORY())) {
				if ("Custom".equals(svcmod.getSUBCATEGORY())) {
					if ("Project Based".equals(svcmod.getGROUP()) || "Operation Based".equals(svcmod.getGROUP())) {
						value = "WBS";
					}
				} else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
					if ("Penalty".equals(svcmod.getGROUP())) {
						value = "WBS";
					} else if ("Incident".equals(svcmod.getGROUP())) {
						value = "WBS";
					} else if ("Travel".equals(svcmod.getGROUP())) {
						value = "WBS";
					} else if ("Activity".equals(svcmod.getGROUP())) {
						value = "WBS";
					}else if ("OEM".equals(svcmod.getGROUP())) {
						value = "WBS";
					} else if ("ICA/NEC".equals(svcmod.getGROUP())) {
						value = "No characteristic";
					}

				} else if ("Productized Services".equals(svcmod.getSUBCATEGORY())) {
					if ("Non-Federated".equals(svcmod.getGROUP())) {
						value = "WBS";
					}
				}
			} else if ("IP".equals(svcmod.getCATEGORY())) {
				if ("SC".equals(svcmod.getSUBCATEGORY())) {
					value = "No characteristic";
				}
			}

		} else if ("MM_PROFIT_CENTER".equals(key)) {

			if ("Service".equals(svcmod.getCATEGORY())) {
				if ("Custom".equals(svcmod.getSUBCATEGORY())) {
					if ("Project Based".equals(svcmod.getGROUP()) || "Operation Based".equals(svcmod.getGROUP())) {
						value = "D";
					}
				} else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
					if ("Penalty".equals(svcmod.getGROUP())) {
						value = "C";
					} else if ("Incident".equals(svcmod.getGROUP())) {
						value = "C";
					} else if ("Travel".equals(svcmod.getGROUP())) {
						value = "C";
					} else if ("Activity".equals(svcmod.getGROUP())) {
						value = "C";
					}else if ("OEM".equals(svcmod.getGROUP())) {
						value = "C";
					} else if ("ICA/NEC".equals(svcmod.getGROUP())) {
						value = "No characteristic";
					}

				} else if ("Productized Services".equals(svcmod.getSUBCATEGORY())) {
					if ("Non-Federated".equals(svcmod.getGROUP())) {
						value = "C";
					}
				}
			} else if ("IP".equals(svcmod.getCATEGORY())) {
				if ("SC".equals(svcmod.getSUBCATEGORY())) {
					value = "No characteristic";
				}
			}

		} else if ("MM_TAX_CATEGORY".equals(key)) {

			if ("Service".equals(svcmod.getCATEGORY())) {
				if ("Custom".equals(svcmod.getSUBCATEGORY())) {
					if ("Project Based".equals(svcmod.getGROUP()) || "Operation Based".equals(svcmod.getGROUP())) {
						value = "D";
					}
				} else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
					if ("Penalty".equals(svcmod.getGROUP())) {
						value = "C";
					} else if ("Incident".equals(svcmod.getGROUP())) {
						value = "C";
					} else if ("Travel".equals(svcmod.getGROUP())) {
						value = "C";
					} else if ("Activity".equals(svcmod.getGROUP())) {
						value = "C";
					}else if ("OEM".equals(svcmod.getGROUP())) {
						value = "C";
					} else if ("ICA/NEC".equals(svcmod.getGROUP())) {
						value = "No characteristic";
					}

				} else if ("Productized Services".equals(svcmod.getSUBCATEGORY())) {
					if ("Non-Federated".equals(svcmod.getGROUP())) {
						value = "C";
					}
				}
			} else if ("IP".equals(svcmod.getCATEGORY())) {
				if ("SC".equals(svcmod.getSUBCATEGORY())) {
					value = "No characteristic";
				}
			}

		}
		// MM_PROFIT_CENTER
		return value;
	}

	/**
	 * convert null to ""
	 * 
	 * @param fromValue
	 * @return
	 */

	private String convertValue(String fromValue) {
		return fromValue == null ? "" : fromValue.trim();
	}

	/***********************************************
	 * Get the version
	 *
	 * @return java.lang.String
	 */
	public String getVersion() {
		return "1.1";
	}

	private boolean isValidCond(String attr) {
		return attr != null && attr.trim().length() > 0;
	}

	@Override
	public String getMQCID() {
		// TODO Auto-generated method stub
		return null;
	}
}
