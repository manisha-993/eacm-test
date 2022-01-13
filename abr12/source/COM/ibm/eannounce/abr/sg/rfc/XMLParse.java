package COM.ibm.eannounce.abr.sg.rfc;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;



public class XMLParse {
	
	@SuppressWarnings("unchecked")
	private static <T> T getObjFromDoc(Document doc, Class<SVCMOD> class1) throws JAXBException {

		try {
			JAXBContext context = JAXBContext.newInstance(SVCMOD.class);
			Unmarshaller unMarshaller = context.createUnmarshaller();
			return (T) unMarshaller.unmarshal(doc);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			throw e;
		}				
	}
	  
	public static void main(String args[]) {
		String xml = "";

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 		
			factory.setNamespaceAware(false);
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));		 
		    //SVCMOD svcObj = getObjFromDoc(doc, SVCMOD.class);
		    SVCMOD svcmod = getSvcmodFromXml( loadXml(""));
		    if (xml != null) {
				try {
					
					RdhMatmCreate create = new RdhMatmCreate(svcmod);
					create.execute();
					if (create.getRfcrc() == 0) {
					} else {
					}

					String obj_id = svcmod.getMACHTYPE() + svcmod.getMODEL();
					String class_name = "MG_COMMON";
					String class_type = "001";
					RdhClassificationMaint cMaint = new RdhClassificationMaint(obj_id, class_name, class_type);


					String type = "MG_PRODUCTTYPE";
					String tableData = getTableMapingDate(type, svcmod);
					if (tableData != null||!"No characteristic".equals(tableData)) {
						cMaint.addCharacteristic(type, tableData);
					}//No characteristic
					cMaint.execute();
					if (cMaint.getRfcrc() == 0) {
					} else {
					}
					class_name = "MM_CUSTOM_SERVICES";
					cMaint = new RdhClassificationMaint(obj_id, class_name, class_type);
					type = "MM_CUSTOM_TYPE";
					tableData = getTableMapingDate(type, svcmod);
					if (tableData != null||!"No characteristic".equals(tableData)) {
						cMaint.addCharacteristic(type, tableData);
					}
					type = "MM_CUSTOM_COSTING";
					tableData = getTableMapingDate(type, svcmod);
					if (tableData != null||!"No characteristic".equals(tableData)) {
						cMaint.addCharacteristic(type, tableData);
					}
					type = "MM_PROFIT_CENTER";
					tableData = getTableMapingDate(type, svcmod);
					if (tableData != null||!"No characteristic".equals(tableData)) {
						cMaint.addCharacteristic(type, tableData);
					} 
					type = "MM_TAX_CATEGORY";
					tableData = getTableMapingDate(type, svcmod);
					if (tableData != null||!"No characteristic".equals(tableData)) {
						cMaint.addCharacteristic(type, tableData);
					}
					cMaint.execute();
					
					if (cMaint.getRfcrc() == 0) {
					} else {
					}

					/*
					 * class_name = "MM_CUSTOM_COSTING"; cMaint = new RdhClassificationMaint(obj_id,
					 * class_name, class_type);
					 */
					
					/*
					 * cMaint.execute(); if (cMaint.getRfcrc() == 0) { } else { }
					 */
						class_name = "MM_FIELDS";
						cMaint = new RdhClassificationMaint(obj_id, class_name, class_type);
						if("Yes".equals(svcmod.getSOPRELEVANT())){
						type = "MM_SOP_IND";
						cMaint.addCharacteristic(type, "X");
						}
						type = "MM_TASK_TYPE";
						cMaint.addCharacteristic(type, svcmod.getSOPTASKTYPE());
						type="MM_OPPORTUNITY_CODE";
						cMaint.addCharacteristic(type, svcmod.getWWOCCODE());
						cMaint.execute();
						
						if (cMaint.getRfcrc() == 0) {
						} else {
							
						}
						/*
						 * cMaint.execute(); if (cMaint.getRfcrc() == 0) { } else {
						 * 
						 * }
						 */
					
					if (svcmod.hasProds()) {
						RdhTssMatChar chart = new RdhTssMatChar(svcmod);
						
						chart.execute();
						
						if (chart.getRfcrc() == 0) {
							
						} else {
							
						}
					}
					UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_TSS_IERP", svcmod.getMACHTYPE() + svcmod.getMODEL());
					updateParkStatus.execute();
					//this.addDebug(updateParkStatus.createLogEntry());

					if (updateParkStatus.getRfcrc() == 0) {
						//this.addOutput(updateParkStatus.getRFCName() + " called successfully!");
						//this.addOutput(updateParkStatus.getError_text());
					} else {
						//this.addOutput(updateParkStatus.getRFCName() + " called  faild!");
						//.addOutput(updateParkStatus.getError_text());
					}
					RdhTssFcProd rdhTssFcProd = new RdhTssFcProd(svcmod);
					if(rdhTssFcProd.canRun())
					rdhTssFcProd.execute();
					//this.addDebug(rdhTssFcProd.createLogEntry());

					if (rdhTssFcProd.getRfcrc() == 0) {
						//this.addOutput(rdhTssFcProd.getRFCName() + " called successfully!");
						//this.addOutput(rdhTssFcProd.getError_text());
					} else {
						//this.addOutput(rdhTssFcProd.getRFCName() + " called  faild!");
						//this.addOutput(rdhTssFcProd.getError_text());
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				
				return;
			}
			    /*
			 * System.out.println(svcObj.getMACHTYPE()); for(AVAILABILITY avail :
			 * svcObj.getAVAILABILITYLIST()) {
			 * System.out.println("country"+avail.getCOUNTRY_FC()+"  planavail："+avail.
			 * getPLANNEDAVAILABILITY()); }
			 */
			 
		} catch (Throwable e) {
			e.printStackTrace();
		}  
	}
	private static String getTableMapingDate(String key, SVCMOD svcmod) {
		String value = null;
		if ("MG_PRODUCTTYPE".equals(key)) {
			if ("Service".equals(svcmod.getCATEGORY())) {
				if ("Custom".equals(svcmod.getSUBCATEGORY())) {
					if ("Project Based".equals(svcmod.getGROUP()) || "Operations Based".equals(svcmod.getGROUP())) {
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
					} else if ("ICA/NEC".equals(svcmod.getGROUP())) {
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
					if ("Project Based".equals(svcmod.getGROUP()) || "Operations Based".equals(svcmod.getGROUP())) {
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
					if ("Project Based".equals(svcmod.getGROUP()) || "Operations Based".equals(svcmod.getGROUP())) {
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
					} else if ("ICA/NEC".equals(svcmod.getGROUP())) {
						value = "No characteristic";
					}

				} else if ("Productized Services".equals(svcmod.getSUBCATEGORY())) {
					if ("Non-Federated".equals(svcmod.getGROUP())) {
						value = "WPS";
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
					if ("Project Based".equals(svcmod.getGROUP()) || "Operations Based".equals(svcmod.getGROUP())) {
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
					if ("Project Based".equals(svcmod.getGROUP()) || "Operations Based".equals(svcmod.getGROUP())) {
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
	public static SVCMOD getSvcmodFromXml(String xml) {
		  SVCMOD svcObj =null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 		
			factory.setNamespaceAware(false);
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));		 
		     svcObj = getObjFromDoc(doc, SVCMOD.class);
		     //695561U.xml
		} catch (Throwable e) {
			e.printStackTrace();
		}  
		
		return svcObj;
	}
	
	
	public static String loadXml(String xmlPath){
		xmlPath = "C:\\Users\\JianBoXu\\Desktop\\eacm\\5724D75.xml";

		StringBuffer stringBuffer = new StringBuffer();
		try {
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(xmlPath));
		
			String line = null;
			while ((line=reader.readLine())!=null) {
				stringBuffer.append(line);
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return stringBuffer.toString();
		
	}
	
	
	}

