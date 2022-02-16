package COM.ibm.eannounce.abr.sg.rfc;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;


public class Chw001ClfCreate {
	MODEL chwModel;
	MODELCONVERT chwMODELCONVERT;
	FCTRANSACTION chwFCTRANSACTION;
	
	String materialType;
	String materialID;
	
	
	public Chw001ClfCreate (String chwProduct, String materialType, String materialID, String entityType ){
		if("MODEL".equalsIgnoreCase(entityType)){
			this.chwModel = CommonEntities.getModelFromXml(chwProduct);
		} else if("MODELCONVERT".equalsIgnoreCase(entityType)){
			//TODO should get the model from modelIERPAbrStatus.java 
			//If <entityType> = "MODELCONVERT", then set chwProduct to the XML of MODEL 
			//where MODEL/MACHTYPE = MODELCONVERT/TOMACHTYPE and MODEL/MODEL = MODELCONVERT/TOMODEL?? 
			
			this.chwModel = CommonEntities.getModelFromXml(chwProduct);
		}else if("FCTRANSACTION".equalsIgnoreCase(entityType)){
			//TODO
			//If <entityType> = "FCTRANSACTION", then set chwProduct to the XML of MODEL 
			//where MODEL/MACHTYPE = FCTRANSACTION/TOMACHTYPE and MODEL/MODEL = FCTRANSACTION/TOMODEL
			this.chwModel = CommonEntities.getModelFromXml(chwProduct);
		}
		
		this.materialType = materialType;
		this.materialID = materialID;
		
	}
	
	
	public void execute(){
		
		try {
			//1. Assign the MG_COMMON classification and its characteristics to the product's material master record:
			//1.a Call the TssClassificationMaint constructor to assign the MG_COMMON classification to the product. 
			RdhClassificationMaint rdhClassificationMaint = 
			new RdhClassificationMaint(
					materialID 							//Copy from <materialID>
					, "MG_COMMON"  						//String class_name   Set to "MG_COMMON".
					, "001"  							//String class_type   Set to "001"
					, "H"
					);
			//1.b Call the TssClassificationMaint.addCharacteristic() method to add the MG_PRODUCTTYPE characteristic to the MG_COMMON classification and indicate the product type.
			String value ="";
			if("Hardware".equalsIgnoreCase(chwModel.getCATEGORY())){
				value = "HW";
			}else if ("Service".equalsIgnoreCase(chwModel.getCATEGORY())){
				value = "SP";
			}			
			rdhClassificationMaint.addCharacteristic("MG_PRODUCTTYPE", value);
			rdhClassificationMaint.execute();
			//rdhClassificationMaint.execute();
			//2.a Call the TssClassificationMaint constructor to assign the MM_FIELDS classification to the product
			rdhClassificationMaint = 
					new RdhClassificationMaint(
							materialID 								//Copy from <materialID>
							, "MM_FIELDS"  						//String class_name   Set to "MM_FIELDS".
							, "001"  							//String class_type   Set to "001"
							, "H"
							);
			//2.b Call the TssClassificationMaint.addCharacteristic() method to add the MM_MACH_TYPE characteristic to the MM_FIELDS classification.
			//Set to first 4 characters of <materialID>. Example: If <materialID> == "9080HC1", then set to "9080".
			value = CommonUtils.getFirstSubString(materialID,4);
			rdhClassificationMaint.addCharacteristic("MM_MACH_TYPE", value);
			//2.c If <materialType> = "ZPRT", then call the TssClassificationMaint.addCharacteristic() method to add the MM_MODEL characteristic to the MM_FIELDS classification. S
			if("ZPRT".equalsIgnoreCase(materialType)){
				value = CommonUtils.getLastSubString(materialID,3);
				rdhClassificationMaint.addCharacteristic("MM_MODEL", value);
				//rdhClassificationMaint.execute();
			}
			//2.d Call the TssClassificationMaint.addCharacteristic() method to add the MM_SIU characteristic to the MM_FIELDS classification.
			/**
			 * If chwProduct/UNITCLASS = "SIU-CPU", then set to "1";
				Else if chwProduct/UNITCLASS = "Non SIU- CPU", then set to "0";
				Else if chwProduct/UNITCLASS = "SIU-Non CPU", then set to "2";
			 */
			value ="";
			if("SIU-CPU".equalsIgnoreCase(chwModel.getUNITCLASS())){
				value = "1";
			} else if("Non SIU-CPU".equalsIgnoreCase(chwModel.getUNITCLASS())){
				value = "0";
			} else if("SIU-Non CPU".equalsIgnoreCase(chwModel.getUNITCLASS())){
				value = "2";
			}
			
			rdhClassificationMaint.addCharacteristic("MM_SIU", value);
			//2.e Call the TssClassificationMaint.addCharacteristic() method to add the MM_PRICERELEVANT characteristic to the MM_FIELDS classification
			/**
			 *  Set var=UPPER(chwProduct/PRICEDIND);
				Set var1=UPPER(chwProduct/ZEROPRICE);
				If (var='YES' AND var1='YES') or (var='NO' AND var1='NO'), then
				     Set value to 'Z';
				Eles if (var='YES' and var1='NO'), then
				      Set value to 'G'.
			 */
			value = "";
			String var= chwModel.getPRICEDIND().toUpperCase();
			String var1= chwModel.getPRICEDIND().toUpperCase();
			if("YES".equals(var) && "YES".equals(var1)){
				value ="Z";
			}else if("NO".equals(var) && "NO".equals(var1)){
				value ="Z";
			}else if("YES".equals(var) && "NO".equals(var1)){
				value ="G";
			} 
			rdhClassificationMaint.addCharacteristic("MM_PRICERELEVANT", value);
			//2.f Call the TssClassificationMaint.addCharacteristic() method to add the MM_FG_INSTALLABLE characteristic to the MM_FIELDS classification. 
			/**
			 * Set var=UPPER(chwProduct/INSTALL);
				If var = 'CIF', then set value to C';
				Else if var = 'CE', then set value to 'I';
				Else if var = 'N/A', then set value to '';
				Else if var = 'DOES NOT APPLY', then set value to ''.
			 */
			value ="";
			var = chwModel.getINSTALL();
			if("CIF".equalsIgnoreCase(var)){
				value = "1";
			} else if("N/A".equalsIgnoreCase(var)){
				value = "";
			} else if("DOES NOT APPLY".equalsIgnoreCase(var)){
				value = "";
			}			
			rdhClassificationMaint.addCharacteristic("MM_FG_INSTALLABLE", value);
			
			//2.g Call the TssClassificationMaint.addCharacteristic() method to add the MM_UNSPSC characteristic to the MM_FIELDS classification
			//value  Copy from chwProduct/UNSPSC
			value = chwModel.getUNSPSC();
			rdhClassificationMaint.addCharacteristic("MM_UNSPSC", value);
			
			//2.h Call the TssClassificationMaint.addCharacteristic() method to add the MM_AMORTLENGTH characteristic to the MM_FIELDS classification.
			//value If containsLetter(chwProduct/AMRTZTNLNGTH)='N', then set value to chwProduct/AMRTZTNLNGTH
			//Else set value to "".
			value = chwModel.getAMRTZTNLNGTH().contains("N")? chwModel.getAMRTZTNLNGTH() : "";
			rdhClassificationMaint.addCharacteristic("MM_AMORTLENGTH", value);
			
			//2.i Call the TssClassificationMaint.addCharacteristic() method to add the MM_AMORTSTART characteristic to the MM_FIELDS classification.
			//value If containsLetter(chwProduct/AMRTZTNSTRT)='N', then set value to chwProduct/AMRTZTNSTRT
			//Else set value to "".
			value = chwModel.getAMRTZTNSTRT().contains("N")? chwModel.getAMRTZTNSTRT() : "";
			rdhClassificationMaint.addCharacteristic("MM_AMORTSTART", value);
			
			//2.j Call the TssClassificationMaint.addCharacteristic() method to add the MM_IDENTITY characteristic to the MM_FIELDS classification.
			//value Set var=UPPER(chwProduct/PRODID);
			//		If var = 'O-OTHER/OPTIONS', then set value to 'O';
			//		Else if var = 'U-SYSTEM UNIT', then set value to 'U';  
			value = "";
			var = chwModel.getPRODID().toUpperCase();
			if("O-OTHER/OPTIONS".equalsIgnoreCase(var)){
				value = "O";
			} else if("U-SYSTEM UNIT".equalsIgnoreCase(var)){
				value = "U";
			} 
			rdhClassificationMaint.addCharacteristic("MM_IDENTITY", value);
			//2.K Call the TssClassificationMaint.addCharacteristic() method to add the MM_ROYALTY_BEAR_IND characteristic to the MM_FIELDS classification.
			//value 	Copy from chwProduct/SWROYALBEARING
			value = chwModel.getSWROYALBEARING();
			rdhClassificationMaint.addCharacteristic("MM_ROYALTY_BEAR_IND", value);
			
			//2.l Call the TssClassificationMaint.addCharacteristic() method to add the MM_SOM_FAMILY characteristic to the MM_FIELDS classification.
			value = chwModel.getSOMFAMILY();
			rdhClassificationMaint.addCharacteristic("MM_SOM_FAMILY", value);
			
			//2.m Call the TssClassificationMaint.addCharacteristic() method to add the MM_LIC characteristic to the MM_FIELDS classification. 
			value = chwModel.getLIC();
			rdhClassificationMaint.addCharacteristic("MM_LIC", value);
			
			//2.n Call the TssClassificationMaint.addCharacteristic() method to add the MM_BP_CERT_SPECBID characteristic to the MM_FIELDS classification 
			value = chwModel.getBPCERTSPECBID();
			rdhClassificationMaint.addCharacteristic("MM_BP_CERT_SPECBID", value);
			
			//2.O Call the TssClassificationMaint.addCharacteristic() method to add the MM_RPQTYPE characteristic to the MM_FIELDS classification
			/**
			 * Set var=UPPER(chwProduct/PRPQAPPRVTYPE);
				If var = 'RPQ APPROVE I-LISTED', then set value to 'RPQ APPROVE';
				Else if var = 'RPQ OTHER P-LISTED', then set value to 'RPQ OTHER';
			 */
			value = "";
			var = chwModel.getPRPQAPPRVTYPE().toUpperCase();
			if("RPQ APPROVE I-LISTED".equalsIgnoreCase(var)){
				value = "RPQ APPROVE";
			} else if("RPQ OTHER P-LISTED".equalsIgnoreCase(var)){
				value = "RPQ OTHER";
			} 
			rdhClassificationMaint.addCharacteristic("MM_RPQTYPE", value);
			//2.P Call the TssClassificationMaint.addCharacteristic() method to add the MM_ANNOUNCEMENT_TYPE characteristic to the MM_FIELDS classification
			//value If chwProduct/SPECBID = 'Yes', then set value to 'SPB';
			//Else set value to 'RFA';
			value = "YES".equalsIgnoreCase(chwModel.getSPECBID())? "SPB" : "RFA";
			rdhClassificationMaint.addCharacteristic("MM_ANNOUNCEMENT_TYPE", value);
			
			//2.Q Call the TssClassificationMaint.addCharacteristic() method to add the MM_PRODUCT_SUPPORT_CODE characteristic to the MM_FIELDS classification
			value = chwModel.getPRODSUPRTCD();
			rdhClassificationMaint.addCharacteristic("MM_PRODUCT_SUPPORT_CODE", value);
			//2.r Call the TssClassificationMaint.addCharacteristic() method to add the MM_SYSTEM_TYPE characteristic to the MM_FIELDS classification.
			value = chwModel.getSYSTEMTYPE();
			rdhClassificationMaint.addCharacteristic("MM_SYSTEM_TYPE", value);
			//2.s If <materialType> = "ZPRT", then call the TssClassificationMaint.addCharacteristic() method to add the MM_PHANTOM_IND characteristic to the MM_FIELDS classification
			if("ZPRT".equalsIgnoreCase(materialType)){
				value = chwModel.getPHANTOMMODINDC();
				rdhClassificationMaint.addCharacteristic("MM_PHANTOM_IND", value);
			}
			//2.t Call the TssClassificationMaint.addCharacteristic() method to add the REMARKETER_REPORTER characteristic to the MM_FIELDS classification
			value = "Yes";
			rdhClassificationMaint.addCharacteristic("REMARKETER_REPORTER", value);
			//2.u Call the TssClassificationMaint.addCharacteristic() method to add the MM_PHYSICAL_RETURN characteristic to the MM_FIELDS classification.
			value ="Hardware".equalsIgnoreCase(chwModel.getCATEGORY()) ? "1": "";
			rdhClassificationMaint.addCharacteristic("MM_PHYSICAL_RETURN", value);
			//2. v Call the TssClassificationMaint.addCharacteristic() method to add the MM_OPPORTUNITY_CODE characteristic to the MM_FIELDS classification. 
			value = chwModel.getWWOCCODE();
			rdhClassificationMaint.addCharacteristic("MM_OPPORTUNITY_CODE", value);
			//2.w If <materialType> = "ZPRT", then call the TssClassificationMaint.addCharacteristic() method to add the MM_ACQ_COMPANY characteristic to the MM_FIELDS classification.
			value = chwModel.getACQRCOCD();
			rdhClassificationMaint.addCharacteristic("MM_ACQ_COMPANY", value);
			//2.final 
			rdhClassificationMaint.execute();
			
			//3. If <chwProduct/CATEGORY>="Service", assign the MM_SERVICEPAC classification and its characteristics to the product's material master record
			if("Service".equalsIgnoreCase(chwModel.getCATEGORY())){
				//3.a Call the TssClassificationMaint constructor to assign the MM_SERVICEPAC classification to the product. 
				RdhClassificationMaint TssClassificationMaint = 
						new RdhClassificationMaint(
								materialID 								//Copy from <materialID>
								, "MM_SERVICEPAC"  					//String class_name   Set to "MM_SERVICEPAC".
								, "001"  							//String class_type   Set to "001"
								, "H"
								);
				//3.b Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_MTM characteristic to the MM_SERVICEPAC classification.
				value = materialID;
				TssClassificationMaint.addCharacteristic("MM_SP_MTM", value);
				//3.c Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_SDF characteristic to the MM_SERVICEPAC classification.
				value = chwModel.getSDFCD();
				TssClassificationMaint.addCharacteristic("MM_SP_SDF", value);
				//3.d Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_SLC characteristic to the MM_SERVICEPAC classification
				value = chwModel.getSVCLEVCD();
				TssClassificationMaint.addCharacteristic("MM_SP_SLC", value);
				//3.e Call the TssClassificationMaint.addCharacteristic() method to add the MM_HW_SPMACHBRAND characteristic to the MM_SERVICEPAC classification. 
				value = chwModel.getSVCPACMACHBRAND();
				TssClassificationMaint.addCharacteristic("MM_HW_SPMACHBRAND", value);
				//3.f Call the TssClassificationMaint.addCharacteristic() method to add the MM_HW_SPTYPE characteristic to the MM_SERVICEPAC classification 
				/**
				 * Set postn=POSITION(' ' in wProduct/SUBCATEGORY;
						If (postn > 0), then set the value to SUBSTRING(hwProduct/SUBCATEGORY from 1 for postn);
						Else set the value to hwProduct/SUBCATEGORY.
				 */
				int postn = chwModel.getSUBCATEGORY().indexOf(" ");
				if(postn>0) {
					value = CommonUtils.getFirstSubString(chwModel.getSUBCATEGORY(), postn);
				}else{
					value = chwModel.getSUBCATEGORY();
				}
				TssClassificationMaint.addCharacteristic("MM_HW_SPTYPE", value);
				//3.g Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_IDENTIFIER characteristic to the MM_SERVICEPAC classification.
				value = chwModel.getSUBCATEGORY();
				TssClassificationMaint.addCharacteristic("MM_HW_SPTERM", value);
				//3.h Call the TssClassificationMaint.addCharacteristic() method to add the MM_HW_SPTERM characteristic to the MM_SERVICEPAC classification.
				/**
				 * Set var=UPPER(hwProduct/COVRPRIOD);
					If var = 'FIVE YEARS', then set the value to '60';
					Else if var = 'FIVE YEAR PARTS AND LABOR', then set the value to '60';
					Else if var = 'FOUR YEARS', then set the value to '48';
					Else if var = 'ONE YEAR' , then set the value to '12';
					Else if var = 'FOUR YEAR PARTS AND LABOR' , then set the value to '48';
					Else if var = 'THREE YEARS',  then set the value to '36';
					Else if var = 'THREE YEAR PARTS AND LABOR' , then set the value to '36';
					Else if var = 'TWO YEARS' , then set the value to '24';
					Else if var =  'TWO YEAR PARTS AND LABOR (EXTENDS EXISTING 3 YEAR COVERAGE)' , then set the value to '24';
					Else if var = 'SIX YEARS' , then set the value to '72';
					Else if var =  'SEVEN YEARS' , then set the value to '84';
					Else if var =  'THREE MONTHS' , then set the value to '3';
					Else if var =  '3 MONTHS', then set the value to '3';
				 */
				var = chwModel.getCOVRPRIOD().toUpperCase();
				Map<String, String> COVRPRIOD = new HashMap<String, String>();
				COVRPRIOD.put("FIVE YEARS", "60");
				COVRPRIOD.put("FOUR YEARS", "48");
				COVRPRIOD.put("ONE YEAR", "12");
				COVRPRIOD.put("FOUR YEAR PARTS AND LABOR", "48");
				COVRPRIOD.put("THREE YEARS", "36");
				COVRPRIOD.put("TWO YEARS", "24");
				COVRPRIOD.put("TWO YEAR PARTS AND LABOR (EXTENDS EXISTING 3 YEAR COVERAGE)", "24");
				COVRPRIOD.put("SIX YEARS", "72");
				COVRPRIOD.put("SEVEN YEARS", "84");
				COVRPRIOD.put("THREE MONTHS", "3");
				COVRPRIOD.put("3 MONTHS", "3");
				value = COVRPRIOD.get(var);
				TssClassificationMaint.addCharacteristic("MM_HW_SPTERM", value);
				//3.i Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_COVHRS characteristic to the MM_SERVICEPAC classification.
				//TODO value Copy from SVCLEV_UPDATE/COVRSHRTDESC where SVCLEV_UPDATE/SVCLEVCD = hwProduct/SVCLEVCD
				//TODO no SVCLEV sql query the cache, get the xml to SVCLEV
				value = chwModel.getSVCLEVCD();
				TssClassificationMaint.addCharacteristic("MM_SP_COVHRS", value);
				//3. j Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_SDM characteristic to the MM_SERVICEPAC classification.
				
				//TODO VF! no SVCLEV sql query the cache, get the xml to SVCLEV 
				value = chwModel.getSVCLEVCD();
				TssClassificationMaint.addCharacteristic("MM_SP_SDM", value);
				//3.k Call the TssClassificationMaint.addCharacteristic() method to add the MM_SPFIXEDTIME characteristic to the MM_SERVICEPAC classification.
				//TODO no SVCLEV sql query the cache, get the xml to SVCLEV
				value = chwModel.getSVCLEVCD();
				TssClassificationMaint.addCharacteristic("MM_SPFIXEDTIME", value);
				//3.l Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_OSRESPTIME characteristic to the MM_SERVICEPAC classification. 
				//TODO no SVCLEV sql query the cache, get the xml to SVCLEV
				value = chwModel.getSVCLEVCD();
				TssClassificationMaint.addCharacteristic("MM_SP_OSRESPTIME", value);
				//3.m Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_CNTACTIME characteristic to the MM_SERVICEPAC classification. 
				value = chwModel.getSVCLEVCD();
				TssClassificationMaint.addCharacteristic("MM_SP_CNTACTIME", value);
				//3.n Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_PARIVTIME characteristic to the MM_SERVICEPAC classification.//3.m Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_CNTACTIME characteristic to the MM_SERVICEPAC classification. 
				value = chwModel.getSVCLEVCD();
				TssClassificationMaint.addCharacteristic("MM_SP_PARIVTIME", value);
				//3.o Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_TARNDTIME characteristic to the MM_SERVICEPAC classification.
				value = chwModel.getSVCLEVCD();
				TssClassificationMaint.addCharacteristic("MM_SP_TARNDTIME", value);
				TssClassificationMaint.execute();
				
			}
			
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public static void main(String[] args) {

	}

}
