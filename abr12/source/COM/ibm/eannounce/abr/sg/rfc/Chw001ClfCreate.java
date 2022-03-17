package COM.ibm.eannounce.abr.sg.rfc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class Chw001ClfCreate extends RfcCallerBase{
	
	MODEL chwModel;
	String materialType;
	String materialID;
	Connection odsConnection;	
	
	
	public Chw001ClfCreate (MODEL model, String materialType, String materialID,  Connection odsConnection ) {
		this.chwModel = model;
		this.materialType = materialType;
		this.materialID = materialID;
		this.odsConnection = odsConnection;
		
	}

	
	
	public void execute() throws Exception {
			//1. Assign the MG_COMMON classification and its characteristics to the product's material master record:
			//1.a Call the TssClassificationMaint constructor to assign the MG_COMMON classification to the product.
			if(chwModel==null) return;
			
			RdhClassificationMaint rdhClassificationMaint = 
			new RdhClassificationMaint(
					materialID 							//Copy from <materialID>
					, "MG_COMMON"  						//String class_name   Set to "MG_COMMON".
					, "001"  							//String class_type   Set to "001"
					, "H"
					);
			this.addRfcName(rdhClassificationMaint);
			//1.b Call the TssClassificationMaint.addCharacteristic() method to add the MG_PRODUCTTYPE characteristic to the MG_COMMON classification and indicate the product type.
			String value ="";
			if("Hardware".equalsIgnoreCase(chwModel.getCATEGORY())){
				value = "HW";
			}else if ("Service".equalsIgnoreCase(chwModel.getCATEGORY())){
				value = "SP";
			}			
			rdhClassificationMaint.addCharacteristic("MG_PRODUCTTYPE", value);
			rdhClassificationMaint.execute();
			this.addRfcResult(rdhClassificationMaint);
			
			
			//rdhClassificationMaint.execute();
			//2.a Call the TssClassificationMaint constructor to assign the MM_FIELDS classification to the product
			rdhClassificationMaint = null;
			rdhClassificationMaint = 
					new RdhClassificationMaint(
							materialID 								//Copy from <materialID>
							, "MM_FIELDS"  						//String class_name   Set to "MM_FIELDS".
							, "001"  							//String class_type   Set to "001"
							, "H"
							);
			this.addRfcName(rdhClassificationMaint);
			
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
			var = chwModel.getINSTALL().toUpperCase();
			if("CIF".equals(var)){
				value = "C";
			} else if("CE".equals(var)){
				value = "I";
			} else if("N/A".equals(var)){
				value = "";
			} else if("DOES NOT APPLY".equals(var)){
				value = "";
			}			
			rdhClassificationMaint.addCharacteristic("MM_FG_INSTALLABLE", value);
			
			//2.g Call the TssClassificationMaint.addCharacteristic() method to add the MM_UNSPSC characteristic to the MM_FIELDS classification
			//value  Copy from chwProduct/UNSPSC
			value = chwModel.getUNSPSC();
			rdhClassificationMaint.addCharacteristic("MM_UNSPSC", value);
			
			//2.h Call the TssClassificationMaint.addCharacteristic() method to add the MM_AMORTLENGTH characteristic to the MM_FIELDS classification.
			//confirm with carol of the design  N or empty  
			//value If containsLetter , then set value to chwProduct/AMRTZTNLNGTH
			//Else set value to "".
			String input = chwModel.getAMRTZTNLNGTH();			
			value = CommonUtils.getNoLetter(input);
			rdhClassificationMaint.addCharacteristic("MM_AMORTLENGTH", value);
			
			//2.i Call the TssClassificationMaint.addCharacteristic() method to add the MM_AMORTSTART characteristic to the MM_FIELDS classification.
			//value If containsLetter(chwProduct/AMRTZTNSTRT)='N', then set value to chwProduct/AMRTZTNSTRT
			//Else set value to "".
			input = chwModel.getAMRTZTNSTRT();			
			value = CommonUtils.getNoLetter(input);
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
			if("YES".equalsIgnoreCase(value)){
				value = "Y";
			}else if("NO".equalsIgnoreCase(value)){
				value = "N";
			}		
			
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
			if("ZPRT".equalsIgnoreCase(materialType)){
				value = chwModel.getACQRCOCD();
				rdhClassificationMaint.addCharacteristic("MM_ACQ_COMPANY", value);
			}
			//2.x Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_MTM characteristic to the MM_FIELDS classification.
			value = materialID;
			rdhClassificationMaint.addCharacteristic("MM_SP_MTM", value);
			//2.final 
			rdhClassificationMaint.execute();
			this.addRfcResult(rdhClassificationMaint);
			
			
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
				this.addRfcName(TssClassificationMaint);
				
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
				input = chwModel.getSUBCATEGORY();
				value = ATWRTforService(input);
				TssClassificationMaint.addCharacteristic("MM_SP_IDENTIFIER", value);
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
				
				
				//value Copy from SVCLEV_UPDATE/COVRSHRTDESC where SVCLEV_UPDATE/SVCLEVCD = hwProduct/SVCLEVCD
				//sql query the cache, get the xml to SVCLEV
				String SVCLEVCD = chwModel.getSVCLEVCD();
				String xml ="";
				try {
					xml = getSVCLEVFromXML(SVCLEVCD);
				} catch (SQLException e) {
				}
				if(!"".equals(xml)){
					SVCLEV SVCLEV = CommonEntities.getSVCLEVFromXml(xml);
					
					
					//3.i Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_COVHRS characteristic to the MM_SERVICEPAC classification.//3.i Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_COVHRS characteristic to the MM_SERVICEPAC classification.
					value = SVCLEV.getCOVRSHRTDESC();
					TssClassificationMaint.addCharacteristic("MM_SP_COVHRS", value);
					//3. j Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_SDM characteristic to the MM_SERVICEPAC classification.
					value = SVCLEV.getSVCDELIVMETH();
					TssClassificationMaint.addCharacteristic("MM_SP_SDM", value);
					//3.k Call the TssClassificationMaint.addCharacteristic() method to add the MM_SPFIXEDTIME characteristic to the MM_SERVICEPAC classification.
					/**
					 * If SVCLEV_UPDATE/FIXTME=''" or SVCLEV_UPDATE/FIXTMEUOM=''" or SVCLEV_UPDATE/FIXTMEOBJIVE=''", then
					 * set the value to "";
					 *	Else set the value to SVCLEV_UPDATE/FIXTME||' '||SVCLEV_UPDATE/FIXTMEUOM||' '||SVCLEV_UPDATE/FIXTMEOBJIVE.
					 */
					String FIXTME = SVCLEV.getFIXTME();
					String FIXTMEUOM = SVCLEV.getFIXTMEUOM();
					String FIXTMEOBJIVE = SVCLEV.getFIXTMEOBJIVE();
					if("".equals(FIXTME) || "".equals(FIXTMEUOM) || "".equals(FIXTMEOBJIVE)){
						value ="";
					}else{
						value = FIXTME + " " + FIXTMEUOM + " " + FIXTMEOBJIVE;
					}					
					TssClassificationMaint.addCharacteristic("MM_SPFIXEDTIME", value);
					//3.l Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_OSRESPTIME characteristic to the MM_SERVICEPAC classification.
					/**
					 * If SVCLEV_UPDATE/ONSITERESP=''" or SVCLEV_UPDATE/ONSITERESPUOM=''" or f SVCLEV_UPDATE/ONSITERESPOBJIVE=''", then
					 * set the value to "";
					 * Else set the value to SVCLEV_UPDATE/ONSITERESP||' '||SVCLEV_UPDATE/ONSITERESPUOM||' '||SUBSTRING (SVCLEV_UPDATE/ONSITERESPOBJIVE FROM 0 FOR 2)
					 */
					String ONSITERESP = SVCLEV.getONSITERESP();					
					String ONSITERESPUOM = SVCLEV.getONSITERESPUOM();
					String ONSITERESPOBJIVE = SVCLEV.getONSITERESPOBJIVE();
					if("".equals(ONSITERESP) || "".equals(ONSITERESPUOM) || "".equals(ONSITERESPOBJIVE)){
						value ="";
					}else{
						value = ONSITERESP + " " + ONSITERESPUOM + " " + CommonUtils.getFirstSubString(ONSITERESPOBJIVE, 2);
					}	
					TssClassificationMaint.addCharacteristic("MM_SP_OSRESPTIME", value);
					//3.m Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_CNTACTIME characteristic to the MM_SERVICEPAC classification.
					/**
					 * If SVCLEV_UPDATE/CONTTME=''" or f SVCLEV_UPDATE/CONTTMEUOM=''" or SVCLEV_UPDATE/CONTTMEOBJIVE=''", then
					 * set the value to "";
					 * Else set the value to SVCLEV_UPDATE/CONTTME||' '||SVCLEV_UPDATE/CONTTMEUOM||' '||SVCLEV_UPDATE/CONTTMEOBJIVE.
					 */
					String CONTTME = SVCLEV.getCONTTME();					
					String CONTTMEUOM = SVCLEV.getCONTTMEUOM();
					String CONTTMEOBJIVE = SVCLEV.getCONTTMEOBJIVE();
					if("".equals(CONTTME) || "".equals(CONTTMEUOM) || "".equals(CONTTMEOBJIVE)){
						value ="";
					}else{
						value = CONTTME + " " + CONTTMEUOM + " " + CONTTMEOBJIVE;
					}
					TssClassificationMaint.addCharacteristic("MM_SP_CNTACTIME", value);
					//3.n Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_PARIVTIME characteristic to the MM_SERVICEPAC classification.//3.m Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_CNTACTIME characteristic to the MM_SERVICEPAC classification.
					/**
					 * If SVCLEV_UPDATE/PARTARRVTME=''" or SVCLEV_UPDATE/PARTARRVTMEUOM=''" or SVCLEV_UPDATE/PARTARRVTMEOBJIVE=''", then
						     set the value to "";
						Else {
						     If UPPER(SVCLEV_UPDATE/PARTARRVTMEUOM)="HOURS" , then
						          set the value to "hrs";
						     Else
						          set the value to SVCLEV_UPDATE/PARTARRVTMEUOM;
						
						     If SVCLEV_UPDATE/PARTARRVTMEOBJIVE="T (Target)", then
						          set the value = value+ " Target";
						     Else
						          set the value =value + " " + SVCLEV_UPDATE/PARTARRVTMEOBJIVE;
						             
						      set the value = SVCLEV_UPDATE/PARTARRVTME + " " + value.
						             }
					 */
					String PARTARRVTME = SVCLEV.getPARTARRVTME();					
					String PARTARRVTMEUOM = SVCLEV.getPARTARRVTMEUOM();
					String PARTARRVTMEOBJIVE = SVCLEV.getPARTARRVTMEOBJIVE();
					if("".equals(PARTARRVTME) || "".equals(PARTARRVTMEUOM) || "".equals(PARTARRVTMEOBJIVE)){
						value ="";
					}else {
						if("HOURS".equals(PARTARRVTMEUOM.toUpperCase())){
							value = "hrs";
						}else{
							value = PARTARRVTMEUOM;
						}
						if("T (Target)".equalsIgnoreCase(PARTARRVTMEOBJIVE)){
							value = value + " " + "Target";
						}else{
							value = value + " " + PARTARRVTMEOBJIVE;
						}
						value = PARTARRVTME + " " + value;
					}
					TssClassificationMaint.addCharacteristic("MM_SP_PARIVTIME", value);
					//3.o Call the TssClassificationMaint.addCharacteristic() method to add the MM_SP_TARNDTIME characteristic to the MM_SERVICEPAC classification.
					/**
					 * If SVCLEV_UPDATE/TRNARNDTME=''" or SVCLEV_UPDATE/TRNARNDTMEUOM=''" or SVCLEV_UPDATE/TRNARNDTMEOBJIVE=''", then
					 * set the value to "";
					 * Else set the value to SVCLEV_UPDATE/TRNARNDTME||' '||SVCLEV_UPDATE/TRNARNDTMEUOM||' '||SVCLEV_UPDATE/TRNARNDTMEOBJIVE;
					 */
					String TRNARNDTME = SVCLEV.getTRNARNDTME();					
					String TRNARNDTMEUOM = SVCLEV.getTRNARNDTMEUOM();
					String TRNARNDTMEOBJIVE = SVCLEV.getTRNARNDTMEOBJIVE();
					if("".equals(TRNARNDTME) || "".equals(TRNARNDTMEUOM) || "".equals(TRNARNDTMEOBJIVE)){
						value ="";
					}else{
						value = TRNARNDTME + " " + TRNARNDTMEUOM + " " + TRNARNDTMEOBJIVE;
					}
					TssClassificationMaint.addCharacteristic("MM_SP_TARNDTIME", value);
					
				}				
				
				TssClassificationMaint.execute();
				this.addRfcResult(TssClassificationMaint);
			}
		
	}


	private String ATWRTforService(String input) {
		Map<String,String> valueMap = new HashMap<String,String>();
		valueMap.put("EURBUNSP", "OTHER");
		valueMap.put("EUREMXSP", "OTHER");
		valueMap.put("EUREXSP", "OTHER");
		valueMap.put("EURMIGSP", "OTHER");
		valueMap.put("EURMVSSP", "OTHER");
		valueMap.put("GENERICHW1", "OTHER");
		valueMap.put("GENERICHW3", "OTHER");
		valueMap.put("GENERICHW5", "OTHER");
		valueMap.put("GENERICHW7", "OTHER");
		valueMap.put("HELPDESK", "OTHER");
		
		valueMap.put("INSTALL", "OTHER");
		valueMap.put("IPSINSTALL", "OTHER");
		valueMap.put("IPSMA", "OTHER");
		valueMap.put("IPSMAEXT", "OTHER");
		valueMap.put("IPSWAMO", "OTHER");
		valueMap.put("IPSWMOEXT", "OTHER");
		valueMap.put("ITESEDUC", "OTHER");
		valueMap.put("LENINSTL", "OTHER");
		valueMap.put("LENPWUPG", "OTHER");
		valueMap.put("LENTPPOF", "OTHER");
		
		valueMap.put("LENWAUPG", "OTHER");
		valueMap.put("N/A", "OTHER");
		valueMap.put("SBUNDLE", "OTHER");
		valueMap.put("SBUNDLE1", "OTHER");
		valueMap.put("SBUNDLE2", "OTHER");
		valueMap.put("SBUNDLE3", "OTHER");
		valueMap.put("SBUNDLE4", "OTHER");
		valueMap.put("SBUNDLE5", "OTHER");
		valueMap.put("SBUNDLE6", "OTHER");
		valueMap.put("SERVACCT", "OTHER");
		
		valueMap.put("STG LAB SERVICES", "OTHER");
		
		valueMap.put("ENSPEURP", "HW");
		valueMap.put("EURETSSWU", "HW");
		valueMap.put("GENERICHW4", "HW");
		valueMap.put("MAINONLY", "HW");
		valueMap.put("MAINSWSUPP", "HW");
		valueMap.put("MEMEAMAP", "HW");
		valueMap.put("MEMEAWMO", "HW");
		valueMap.put("WMAINOCS", "HW");
		valueMap.put("WMAINTOPT", "HW");
		valueMap.put("GTMSEUR", "HW");
		
		valueMap.put("PROACTSYS", "HW");
		
		
		valueMap.put("GENERICHW2", "SW");
		valueMap.put("PSSWSUPP", "SW");
		valueMap.put("RTECHSUPEU", "SW");
		valueMap.put("RTECHSUPP", "SW");
		valueMap.put("RTSOS", "SW");
		valueMap.put("RTSSWEU", "SW");
		valueMap.put("RTSXSERIES", "SW");
		valueMap.put("SLEMEASW", "SW");
		valueMap.put("SMOOTHSTRT", "SW");
		valueMap.put("STRTUPSUP", "SW");
		
		valueMap.put("STRUPSUPEU", "SW");
		valueMap.put("SUPLINEEU", "SW");
		valueMap.put("SUPPORTLN", "SW");
		valueMap.put("SYSEXPERT", "SW");
		valueMap.put("TSEMEASW", "SW");
		valueMap.put("ETSAAEUR", "SW");
		
		
		String value = valueMap.get(input.toUpperCase());
		
		if(value ==null) value ="OTHER";
		
		return value;
	}



	private String getSVCLEVFromXML(String SVCLEVCD) throws SQLException {
		System.out.println("SVCLEVCD=" + SVCLEVCD);
		/**
		 * 
		 *	select xmlentitytype,xmlentityid,xmlmessage,xmlcachedts from cache.XMLIDLCACHE 
		 *		where XMLCACHEVALIDTO > current timestamp and  XMLENTITYTYPE = 'SVCLEV'
		 *	and xmlexists('declare default element namespace "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/SVCLEV_UPDATE"; 
		 *      $i/SVCLEV_UPDATE[SVCLEVCD/text() = "M19"]' passing cache.XMLIDLCACHE.XMLMESSAGE as "i") ORDER BY XMLCACHEDTS with ur
		 */
		String cacheSql = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLCACHEVALIDTO > current timestamp and  XMLENTITYTYPE = 'SVCLEV'"
				+ " and xmlexists('declare default element namespace \"http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/SVCLEV_UPDATE\";"
				+ " $i/SVCLEV_UPDATE[SVCLEVCD/text() = \""+SVCLEVCD+"\"]' passing cache.XMLIDLCACHE.XMLMESSAGE as \"i\") ORDER BY XMLCACHEDTS with ur";
		
		PreparedStatement statement = odsConnection.prepareStatement(cacheSql);
		//statement.setString(1, SVCLEVCD);
		ResultSet resultSet = statement.executeQuery();
		String xml = "";
		while (resultSet.next()) {
			xml = resultSet.getString("XMLMESSAGE");
			System.out.println("xml=" + xml);
			break; //only can get one SVCLEV				
		}
		return xml;
	}

	public static void main(String[] args) {
		Map<String,String> valueMap = new HashMap<String,String>();
		valueMap.put("EURBUNSP", "OTHER");
		valueMap.put("EUREMXSP", "OTHER");
		valueMap.put("EUREXSP", "OTHER");
		valueMap.put("EURMIGSP", "OTHER");
		String value = valueMap.get("123");
		System.out.println("value=" + value);
		value = valueMap.get("EURBUNSP");
		System.out.println("value=" + value);
		value = valueMap.get(null);
		System.out.println("value=" + value);

	}

}
