package COM.ibm.eannounce.abr.sg.rfc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import COM.ibm.eannounce.abr.sg.rfc.entity.LANGUAGE;

public class ChwMachTypeUpg extends RfcCallerBase{
	
	private MODEL chwModel;
	private String chwProduct;
	private Connection rdhConnection;
	private Connection odsConnection;
	
	public ChwMachTypeUpg(MODEL chwModel,Connection rdhConnection, Connection odsConnection) {	
		this.chwModel = chwModel;
		this.rdhConnection = rdhConnection;
		this.odsConnection = odsConnection;
	}
	public void execute() throws Exception{
			String empty ="";
			String obj_id = chwModel.getMACHTYPE() + "UPG";
			//1. Call ChwMatmCreate to create the material master for the product object.
			ChwMatmCreate chwMatmCreate = new ChwMatmCreate(chwModel,"ZMAT",chwModel.getMACHTYPE() + "UPG");
			this.addRfcName(chwMatmCreate);
			chwMatmCreate.execute();
			this.addRfcResult(chwMatmCreate);
			
			//2. Call Chw001ClfCreate to create the standard 001 classifications and characteristics 
			//   which are tied to the offering's material master record.
			//
			Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(chwModel,"ZMAT",chwModel.getMACHTYPE()+ "UPG",odsConnection);
			this.addDebug("Calling " + "Chw001ClfCreate");
			try{
				chw001ClfCreate.execute();
				this.addMsg(chw001ClfCreate.getRptSb());
			}catch (Exception e) {
				this.addMsg(chw001ClfCreate.getRptSb());
				throw e;
			}
			
			//3. Call the TssClassificationMaint constructor to associate the MK_REFERENCE class to the product's material master record
			RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(obj_id,"MK_REFERENCE","300","H");
			this.addRfcName(rdhClassificationMaint);
			rdhClassificationMaint.execute();
			this.addRfcResult(rdhClassificationMaint);
			
			//4. Call the TssClassificationMaint constructor to associate the MK_T_VAO_NEW class to the product's material master record
			rdhClassificationMaint = new RdhClassificationMaint(obj_id,"MK_T_VAO_NEW","300","H");	
			this.addRfcName(rdhClassificationMaint);
			rdhClassificationMaint.execute();
			this.addRfcResult(rdhClassificationMaint);
			
			//5. Call the TssClassificationMaint constructor to associate the MK_D_VAO_NEW class to the product's material master record
			rdhClassificationMaint = new RdhClassificationMaint(obj_id,"MK_D_VAO_NEW","300","H");
			this.addRfcName(rdhClassificationMaint);
			rdhClassificationMaint.execute();
			this.addRfcResult(rdhClassificationMaint);
			
			//6.Call the TssClassificationMaint constructor to associate the MK_FC_EXCH class to the product's material master record.
			rdhClassificationMaint = new RdhClassificationMaint(obj_id,"MK_FC_EXCH","300","H");
			this.addRfcName(rdhClassificationMaint);
			rdhClassificationMaint.execute();
			this.addRfcResult(rdhClassificationMaint);
			
			//7.Call the TssClassificationMaint constructor to associate the MK_FC_CONV class to the product's material master record.
			rdhClassificationMaint = new RdhClassificationMaint(obj_id,"MK_FC_CONV","300","H");
			this.addRfcName(rdhClassificationMaint);
			rdhClassificationMaint.execute();
			this.addRfcResult(rdhClassificationMaint);
			//8.Create the MK_machineType_MOD class and MK_T_machineType_MOD characteristic if it does not exist.  
			//	Assign the MK_machineType_MOD class to the product's material master record.
			
			//8.a Call the ChwCharMaintain constructor to create the MK_T_machineType_MOD characteristic
			ChwCharMaintain chwCharMaintain = 
			new ChwCharMaintain(obj_id  //String obj_id Set to concatenation of chwProduct.machineType + "UPG"
								,"MK_T_"+chwModel.getMACHTYPE()+"_MOD" //String charact  Set to  "MK_T_<machine_type>_MOD"
								, "CHAR" 			//String datatype
								, 6 				//int charnumber
								, empty				//String decplaces
								, empty				//String casesens
								, empty				//String neg_vals
								, empty				//String group
								, "-"				//String valassignm  Set to "-".
								, empty				//String no_entry
								, empty				//String no_display
								, "X" 				//String addit_vals Set to "X".
								, chwModel.getMACHTYPE() +" Model Characteristic" //String chdescr  Set to "<machine_type> Model Characteristic" where <machine_type> is chwProduct.machineType
								);
			this.addRfcName(chwCharMaintain);
			//8.b Call the ChwCharMaintain.addValue() method to add the model with its description to the MK_T_machineType_MOD characteristic
			//8.b Call the ChwCharMaintain.addValue() method to add the model with its description to the MK_T_machineType_MOD characteristic
			String invname = "";
			List<LANGUAGE> languagesList = chwModel.getLANGUAGELIST();
			for(LANGUAGE language : languagesList){
				String nlsid = language.getNLSID();
				if("1".equals(nlsid)){					//<NLSID>1</NLSID>
					invname = language.getINVNAME();
					break;
				}
			}			
			//Set to SUBSTRING(chwProduct/INVNAME FROM 1 FOR 25)||' '||chwProduct/MODEL;
			String valdescr = CommonUtils.getFirstSubString(invname,25) + " " + chwModel.getMODEL();
			chwCharMaintain.addValue(chwModel.getMODEL(), valdescr);
			chwCharMaintain.execute();
			this.addRfcResult(chwCharMaintain);
			//8.c Call the ChwClassMaintain constructor to create the MK_machineType_MOD class. 
			//no ChwClassMaintain
			ChwClassMaintain ChwClassMaintain = 
					new ChwClassMaintain(
							obj_id 								//String obj_id Set to concatenation of chwProduct.machineType + "MTC"
							, "MK_"+chwModel.getMACHTYPE()+"_MOD"  //String class_name   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType
							, "MK_"+chwModel.getMACHTYPE()+"_MOD"  //String class_type   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType.
							);
			this.addRfcName(ChwClassMaintain);
			//8.d Call the ChwClassMaintain.addCharacteristic() method to add the MK_T_machineType_MOD characteristic to the MK_machineType_MOD characteristic class
			ChwClassMaintain.addCharacteristic("MK_"+chwModel.getMACHTYPE()+"_MOD"); 
			ChwClassMaintain.execute();
			this.addRfcResult(ChwClassMaintain);
						
			//8.e Call the TssClassificationMaint constructor to associate the MK_machineType_MOD class to the product's material master record
			//reuse TssClassificationMaint
			RdhClassificationMaint TssClassificationMaint = 
			new RdhClassificationMaint(
					obj_id 								//String obj_id Set to concatenation of chwProduct.machineType + "MTC"
					, "MK_"+chwModel.getMACHTYPE()+"_MOD"  //String class_name   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType
					, "300"  							//String class_type   Set to "300"
					, "H"
					);
			this.addRfcName(TssClassificationMaint);
			TssClassificationMaint.execute();
			this.addRfcResult(TssClassificationMaint);
			//9.Create the MK_machineType_MTC class and MK_machineType_MTC characteristic if it does not exist.
			//9.a Call the ChwCharMaintain constructor to create the MK_machineType_MTC characteristic.
			//TODO get the MODELCONVERTList and FCTRANSACTIONList from MODEL
			String machtype = chwModel.getMACHTYPE();
			ArrayList<HashMap<String, String>> recordArray = getEntityList(machtype,"MODELCONVERT");
			ArrayList<HashMap<String, String>> FCArray = getEntityList(machtype,"FCTRANSACTION");
			
			//List<MODELCONVERT> chwMODELCONVERTList = new ArrayList<MODELCONVERT>(); 
			//List<FCTRANSACTION> chwFCTRANSACTIONList = new ArrayList<FCTRANSACTION>();
			ChwCharMaintain ChwCharMaintain = 
			new ChwCharMaintain(
					obj_id				 				//String obj_id  Set to concatenation of chwProduct.machineType + "UPG"
					, "MK_"+chwModel.getMACHTYPE()+"_MOD_CONV"	//String charact  Set to  "MK_D_machineType_MOD_CONV" where <machine_type> is chwProduct.machineType
					, "CHAR" 							//String datatype  Set to "CHAR".
					, 9 								//int charnumber  Set to "15".
					, empty 		//String decplaces
					, empty 		//String casesens
					, empty 		//String neg_vals
					, empty 		//String group
					, "-" 			//String valassignm  Set to "-".
					, empty 		//String no_entry
					, empty 		//String no_display
					, "X" 			//String addit_vals   Set to "X".
					, "Machine Type Conversions" + chwModel.getMACHTYPE()	//String chdescr	Set to "Machine Type Conversions <machine_type>" 				
					);
			this.addRfcName(ChwCharMaintain);
			
			//9.B Create an array variable <FromModelToModel> 
			List<String> FromModelToModel = new ArrayList<String>();
			
			for(HashMap<String, String> modelConvertMap: recordArray){
				String modelconvert_value = modelConvertMap.get("FROMMODEL") + "_" + modelConvertMap.get("TOMODEL");
				if(!FromModelToModel.contains(modelconvert_value)){
					FromModelToModel.add(modelconvert_value);
				}
			}
			for(HashMap<String, String> modelConvertMap: FCArray){
				String modelconvert_value = modelConvertMap.get("FROMMODEL") + "_" + modelConvertMap.get("TOMODEL");
				if(!FromModelToModel.contains(modelconvert_value)){
					FromModelToModel.add(modelconvert_value);
				}
			}			
            //9.c  For each entry in the array <FromModelToModel>, call the ChwCharMaintain.addValue() method to add the value
			for(String value : FromModelToModel){
				String value_descr = "From " + chwModel.getMACHTYPE() + " Model" + CommonUtils.getFirstSubString(value, 3) + CommonUtils.getLastSubString(value, 3);
				ChwCharMaintain.addValue(value, value_descr);
			}			
			ChwCharMaintain.execute();
			this.addRfcResult(ChwCharMaintain);
			
			//9.d Call the ChwClassMaintain constructor to create the MK_D_machineType_MOD_CONV class
			ChwClassMaintain = 
					new ChwClassMaintain(
							obj_id 								//String obj_id Set to concatenation of chwProduct.machineType + "MTC"
							, "MK_D_"+chwModel.getMACHTYPE()+"_MOD_CONV"  //String class_name   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType
							, "MK_D_"+chwModel.getMACHTYPE()+"_MOD_CONV"  //String class_type   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType.
							);	
			this.addRfcName(ChwClassMaintain);
			//9.e Call the ChwClassMaintain.addCharacteristic() method to add the MK_D_machineType_MOD_CONV characteristic to the MK_D_machineType_MOD_CONV class.			
			ChwClassMaintain.addCharacteristic("MK_D_"+chwModel.getMACHTYPE()+"_MOD_CONV");
			ChwClassMaintain.execute();
			this.addRfcResult(ChwCharMaintain);
			

			//9.f Call the TssClassificationMaint constructor to associate the MK_D_machineType_MOD_CONV class to the product's material master record.
			TssClassificationMaint = 
			new RdhClassificationMaint(
					obj_id 								//String obj_id Set to concatenation of chwProduct.machineType + "UPG"
					, "MK_D_"+chwModel.getMACHTYPE()+"_MOD_CONV"  //String class_name   Set to  "MK_<machine_type>_MOD_CONV" where <machine_type> is chwProduct.machineType
					, "300"  							//String class_type   Set to "300"
					, "H"
					);
			this.addRfcName(TssClassificationMaint);
			TssClassificationMaint.execute();
			this.addRfcResult(TssClassificationMaint);
			
			//10.Call the ChwConpMaintain to create a configuration profile for the product's material master record
			//10.a Call the ChwConpMaintain constructor.
			ChwConpMaintain ChwConpMaintain  = 
			new ChwConpMaintain(
					obj_id 				//String obj_id  Set to concatenation of chwProduct.machineType + "UPG"
					, "INITIAL"         //String c_profile Set to "INITIAL".
					, "SD01"			//String bomappl Set to "SD01".
					, "2"				//String bomexpl Set to "2".
					, chwModel.getMACHTYPE() +	"UPGUI"		//String design	 Set to Set to concatenation of chwProduct.machineType + "UPGUI" 
					);
			this.addRfcName(ChwConpMaintain);
			//10.b Call the ChwConpMaintain.addConfigDependency() method.			
			ChwConpMaintain.addConfigDependency("E2E", empty); //Set to "E2E".
			//10.c 
			ChwConpMaintain.addConfigDependency("PR_"+chwModel.getMACHTYPE()+"_SET_MODEL", empty);  //Set to "PR_<chwProduct.machineType>_SET_MODEL"
			//10.d
			ChwConpMaintain.addConfigDependency("PR_E2E_SET_MTM", empty);  //Set to "PR_E2E_SET_MTM".
			//10.e 
			ChwConpMaintain.addConfigDependency("PR_E2E_PRICING_HW", empty);  //Set to "PR_E2E_PRICING_HW".
			//10.f 
			ChwConpMaintain.addConfigDependency("PR_E2E_CSTIC_HIDING_HW", empty);  //Set to "PR_E2E_PRICING_HW".
			
			ChwConpMaintain.execute();// excute 1 time 
			this.addRfcResult(ChwConpMaintain);
			
		
	}
	
	private ArrayList<HashMap<String, String>> getEntityList(String machtype, String entityType) throws SQLException {
		
		ArrayList<HashMap<String, String>> recordArray = new ArrayList<HashMap<String, String>>();
		String modelConvertQuery ="SELECT MT.ENTITYID FROM OPICM.TEXT AS MT "
				+ " JOIN  OPICM.TEXT AS MF "
				+ " ON MT.ENTITYTYPE = MF.ENTITYTYPE "
				+ " and MT.ENTITYID = MF.ENTITYID "
				+ " WHERE MT.EFFTO >current timestamp "
				+ " AND MT.VALTO >current timestamp "
				+ " AND MF.EFFTO >current timestamp "
				+ " AND MF.VALTO >current timestamp "
				+ " AND MT.ENTITYTYPE ='"+entityType+"' "
				+ " AND MT.ATTRIBUTECODE ='TOMACHTYPE' "
				+ " AND MF.ATTRIBUTECODE ='FROMMACHTYPE' "
				+ " AND MT.ATTRIBUTEVALUE <> MF.ATTRIBUTEVALUE "
				+ " AND MT.ATTRIBUTEVALUE =? "
				+ " AND MT.ENTITYID IN (  SELECT ENTITYID FROM OPICM.FLAG "
				+ " WHERE ENTITYTYPE ='"+entityType+"' "
				+ " AND ((ATTRIBUTECODE ='ADSABRSTATUS' AND ATTRIBUTEVALUE = '0040') "
				+ " OR (ATTRIBUTECODE ='MODELCONVERTIERPABRSTATUS' AND ATTRIBUTEVALUE = '0040')) "
				+ " AND EFFTO >current timestamp "
				+ " AND VALTO > current timestamp )"
				+ " WITH ur";
		PreparedStatement statement = rdhConnection.prepareStatement(modelConvertQuery);
		statement.setString(1, machtype);
		ResultSet resultSet = statement.executeQuery();
		Map<String,String> attributeMap = new HashMap<String,String>();
//		attributeMap.put("FROMMACHTYPE", "");
		attributeMap.put("FROMMODEL", "");
//		attributeMap.put("TOMACHTYPE", "");
		attributeMap.put("TOMODEL ", "");
		
		while(resultSet.next()){
			int entityid = resultSet.getInt("ENTITYID");
			String modelConvert = "SELECT ATTRIBUTECODE,ATTRIBUTEVALUE FROM OPICM.TEXT "
					+ " WHERE ENTITYTYPE ='"+entityType+"' "
					+ " AND ENTITYID=? "
					+ " AND EFFTO >current timestamp "
					+ " AND VALTO > current timestamp WITH ur";
			PreparedStatement attributeStatement = rdhConnection.prepareStatement(modelConvert);
			statement.setInt(1, entityid);
			ResultSet attributeResultSet = attributeStatement.executeQuery();
			HashMap<String,String> modelConvertMap = new HashMap<String,String>();
			while(attributeResultSet.next()){
				String attributecode = attributeResultSet.getString("ATTRIBUTECODE");
				if(attributeMap.containsKey(attributecode)){
					modelConvertMap.put(attributecode, attributeResultSet.getString("ATTRIBUTEVALUE"));					
				}
			}
//			if(!modelConvertMap.containsKey("FROMMACHTYPE")){
//				modelConvertMap.put("FROMMACHTYPE", "");
//			}
			if(!modelConvertMap.containsKey("FROMMODEL")){
				modelConvertMap.put("FROMMODEL", "");
			}
//			if(!modelConvertMap.containsKey("TOMACHTYPE")){
//				modelConvertMap.put("TOMACHTYPE", "");
//			}
			if(!modelConvertMap.containsKey("TOMODEL")){
				modelConvertMap.put("TOMODEL", "");
			}
			recordArray.add(modelConvertMap);			
		}
		return recordArray;
	}
	
	
	

	public static void main(String[] args) {
		MODEL model = new MODEL();
		model.setMACHTYPE("UTC");
		model.setMODEL("MOD_NAME");
//		MODELIERPABRSTATUSCaller MODELIERPABRSTATUSCaller = new MODELIERPABRSTATUSCaller(model);
//		MODELIERPABRSTATUSCaller.excute();
		

	}

}
