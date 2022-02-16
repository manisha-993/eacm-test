package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

public class ChwMachTypeUpg {
	
	private MODEL chwModel;
	private String chwProduct;
	
	public ChwMachTypeUpg(String chwProduct) {	
		this.chwModel = CommonEntities.getModelFromXml(chwProduct);	
		this.chwProduct = chwProduct;
	}
	public void excute(){
		try {
			String empty ="";
			String obj_id = chwModel.getMACHTYPE() + "UPG";
			//1. Call ChwMatmCreate to create the material master for the product object.
			//TODO ChwMatmCreate from BOB
//			RdhMatmCreate chwMatmCreate = new RdhMatmCreate(chwProduct,"ZMAT",chwProduct.machineType + "MTC");
//			RdhMatmCreate chwMatmCreate = new RdhMatmCreate(svcmod);
//			chwMatmCreate.execute();
			
			//2. Call Chw001ClfCreate to create the standard 001 classifications and characteristics 
			//   which are tied to the offering's material master record.
			//
			Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(chwProduct,"ZMAT",chwModel.getMACHTYPE()+ "UPG","MODEL");
			chw001ClfCreate.execute();
			
			//3. Call the TssClassificationMaint constructor to associate the MK_REFERENCE class to the product's material master record
			new RdhClassificationMaint(obj_id,"MK_REFERENCE","300").execute();
			
			//4. Call the TssClassificationMaint constructor to associate the MK_T_VAO_NEW class to the product's material master record
			new RdhClassificationMaint(obj_id,"MK_T_VAO_NEW","300").execute();	
			
			//5. Call the TssClassificationMaint constructor to associate the MK_D_VAO_NEW class to the product's material master record
			new RdhClassificationMaint(obj_id,"MK_D_VAO_NEW","300").execute();
			
			//6.Call the TssClassificationMaint constructor to associate the MK_FC_EXCH class to the product's material master record.
			new RdhClassificationMaint(obj_id,"MK_FC_EXCH","300").execute();
			
			//7.Call the TssClassificationMaint constructor to associate the MK_FC_CONV class to the product's material master record.
			new RdhClassificationMaint(obj_id,"MK_FC_CONV","300").execute();
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
			//8.c Call the ChwClassMaintain constructor to create the MK_machineType_MOD class. 
			//no ChwClassMaintain
			ChwClassMaintain ChwClassMaintain = 
					new ChwClassMaintain(
							obj_id 								//String obj_id Set to concatenation of chwProduct.machineType + "MTC"
							, "MK_"+chwModel.getMACHTYPE()+"_MOD"  //String class_name   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType
							, "MK_"+chwModel.getMACHTYPE()+"_MOD"  //String class_type   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType.
							);
			//8.d Call the ChwClassMaintain.addCharacteristic() method to add the MK_T_machineType_MOD characteristic to the MK_machineType_MOD characteristic class
			ChwClassMaintain.addCharacteristic("MK_"+chwModel.getMACHTYPE()+"_MOD"); 
			ChwClassMaintain.execute();
			
			//8.e Call the TssClassificationMaint constructor to associate the MK_machineType_MOD class to the product's material master record
			//reuse TssClassificationMaint
			RdhClassificationMaint TssClassificationMaint = 
			new RdhClassificationMaint(
					obj_id 								//String obj_id Set to concatenation of chwProduct.machineType + "MTC"
					, "MK_"+chwModel.getMACHTYPE()+"_MOD"  //String class_name   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType
					, "300"  							//String class_type   Set to "300"
					);
			TssClassificationMaint.execute();
			//9.Create the MK_machineType_MTC class and MK_machineType_MTC characteristic if it does not exist.
			//9.a Call the ChwCharMaintain constructor to create the MK_machineType_MTC characteristic.
			//TODO get the MODELCONVERTList and FCTRANSACTIONList from MODEL
			List<MODELCONVERT> chwMODELCONVERTList = new ArrayList<MODELCONVERT>(); 
			List<FCTRANSACTION> chwFCTRANSACTIONList = new ArrayList<FCTRANSACTION>();
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
			//9.B Create an array variable <FromModelToModel> 
			List<String> FromModelToModel = new ArrayList<String>();
			for(MODELCONVERT chwMODELCONVERT: chwMODELCONVERTList){
				String modelconvert_value = chwMODELCONVERT.getFROMMODEL() + "_" + chwMODELCONVERT.getFROMMODEL();
				if(!FromModelToModel.contains(modelconvert_value)){
					FromModelToModel.add(modelconvert_value);
				}
			}
			for(FCTRANSACTION chFCTRANSACTION: chwFCTRANSACTIONList){
				String fc_value = chFCTRANSACTION.getFROMMODEL() + "_" + chFCTRANSACTION.getFROMMODEL();
				if(!FromModelToModel.contains(fc_value)){
					FromModelToModel.add(fc_value);
				}
			}
			
            //9.c  For each entry in the array <FromModelToModel>, call the ChwCharMaintain.addValue() method to add the value
			for(String value : FromModelToModel){
				String value_descr = "From " + chwModel.getMACHTYPE() + " Model" + CommonUtils.getFirstSubString(value, 3) + CommonUtils.getLastSubString(value, 3);
				ChwCharMaintain.addValue(value, value_descr);
			}			
			ChwCharMaintain.execute();
			//9.d Call the ChwClassMaintain constructor to create the MK_D_machineType_MOD_CONV class
			ChwClassMaintain = 
					new ChwClassMaintain(
							obj_id 								//String obj_id Set to concatenation of chwProduct.machineType + "MTC"
							, "MK_D_"+chwModel.getMACHTYPE()+"_MOD_CONV"  //String class_name   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType
							, "MK_D_"+chwModel.getMACHTYPE()+"_MOD_CONV"  //String class_type   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType.
							);	
			//9.e Call the ChwClassMaintain.addCharacteristic() method to add the MK_D_machineType_MOD_CONV characteristic to the MK_D_machineType_MOD_CONV class.			
			ChwClassMaintain.addCharacteristic("MK_D_"+chwModel.getMACHTYPE()+"_MOD_CONV");
			ChwClassMaintain.execute();

			//9.f Call the TssClassificationMaint constructor to associate the MK_D_machineType_MOD_CONV class to the product's material master record.
			TssClassificationMaint = 
			new RdhClassificationMaint(
					obj_id 								//String obj_id Set to concatenation of chwProduct.machineType + "UPG"
					, "MK_D_"+chwModel.getMACHTYPE()+"MOD_CONV"  //String class_name   Set to  "MK_<machine_type>_MOD_CONV" where <machine_type> is chwProduct.machineType
					, "300"  							//String class_type   Set to "300"
					);
			TssClassificationMaint.execute();
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
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
	

	public static void main(String[] args) {
		MODEL model = new MODEL();
		model.setMACHTYPE("UTC");
		model.setMODEL("MOD_NAME");
//		MODELIERPABRSTATUSCaller MODELIERPABRSTATUSCaller = new MODELIERPABRSTATUSCaller(model);
//		MODELIERPABRSTATUSCaller.excute();
		

	}

}
