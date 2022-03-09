package COM.ibm.eannounce.abr.sg.rfc;

import com.ibm.db2.jcc.a.d;

public class ChwClsfCharCreate {
	
	public void  CreateGroupChar(String obj_id, String target_indc, String mach_type, String feature_code, String feature_code_desc) throws Exception{
		
		//a.Call the ChwCharMaintain constructor to create the group characteristic
		/**
		 * If <target> is "T", then set to "n000 Features Target";

		Else if <target> is "D", then set to "n000 Features Delta" where <n> is the first number of the parameter <feature_code>.

		Example:  If <target> is "D" and <feature_code> is "1234", then this parameter should be set to "1000 Features Delta".
		 */
		String chdescr = "";
		if("T".equalsIgnoreCase(target_indc)){
			chdescr = CommonUtils.getFirstSubString(feature_code, 1)+"000 Features Target";
		}else if("D".equalsIgnoreCase(target_indc)){
			chdescr = CommonUtils.getFirstSubString(feature_code, 1)+"000 Features Delta";
		}
		String charact ="MK_" + target_indc + "_" + mach_type + "_FC" + CommonUtils.getFirstSubString(feature_code, 1) +"000";
		
		ChwCharMaintain chwCharMaintain = new ChwCharMaintain(
				obj_id
				, charact //MK_target_machineType_FC_n000
				, "CHAR"  	//String datatype Set to "CHAR".
				, 7			//int charnumber Set to "7".
				, ""		//String decplaces Set to NULL or empty string ("").
				, ""        //String casesens 
				, ""		//String neg_vals Set to NULL or empty string ("").
				, ""		//String group Set to NULL or empty string ("").
				, "-"		//String valassignm Set to "-".
				, ""		//String no_entry Set to NULL or empty string ("").
				, ""		//String no_display Set to NULL or empty string ("").
				, ""		//String addit_vals Set to NULL or empty string ("").
				, chdescr	//String chdescr
				);
		//b.Call the ChwCharMaintain.addValue() method to add the feature code to the group characteristic.
		chwCharMaintain.addValue(feature_code,feature_code_desc);
		chwCharMaintain.execute();
		//c.Call the ChwClassMaintain constructor to create the MK_target_machineType_FC_n000 class.
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				obj_id //String obj_id
				, charact //String class_
				, charact //String class_desc
			);
		//d. Call the ChwClassMaintain.addCharacteristic() method to add the MK_target_machineType_FC_n000 
		//			characteristic to the MK_target_machineType_FC_n000 class
		chwClassMaintain.addCharacteristic(charact);
		chwClassMaintain.execute();
		//e. TssClassificationMaint 
		RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
				obj_id 		//String obj_id
				, charact 	//String class_name
				, "300" 	//String class_type
				, "H"		//String pims_identity
				);
		rdhClassificationMaint.execute();
		
		
	}
	
	public void CreateQTYChar (String obj_id, String target_indc, String mach_type, String feature_code) throws Exception{
		String chdescr = "";
		if("T".equalsIgnoreCase(target_indc)){
			chdescr = "RPQ Features Target";
		}else if("D".equalsIgnoreCase(target_indc)){
			chdescr = "RPQ Features Delta";
		}
		String charact ="MK_" + target_indc + "_" + mach_type + "_FC_RPQ";
		ChwCharMaintain chwCharMaintain = new ChwCharMaintain(
				obj_id
				, charact 	//MK_target_machineType_FC_RPQ
				, "CHAR"  	//String datatype Set to "CHAR".
				, 6			//int charnumber Set to "6".
				, ""		//String decplaces Set to NULL or empty string ("").
				, ""        //String casesens 
				, ""		//String neg_vals Set to NULL or empty string ("").
				, ""		//String group Set to NULL or empty string ("").
				, "-"		//String valassignm Set to "-".
				, ""		//String no_entry Set to NULL or empty string ("").
				, ""		//String no_display Set to NULL or empty string ("").
				, ""		//String addit_vals Set to NULL or empty string ("").
				, chdescr	//String chdescr
				);
		chwCharMaintain.execute();
		//b.Call the ChwClassMaintain constructor to create the MK_target_machineType_FC_n000 class
		String charact2 = "MK_" + target_indc + "_" + mach_type + "_FC" + CommonUtils.getFirstSubString(feature_code, 1) +"000";
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				obj_id //String obj_id
				, charact2 //String class_
				, charact2 //String class_desc
			);
		//d. Call the ChwClassMaintain.addCharacteristic() method to add the MK_target_machineType_featurecode_QTY characteristic
		//Set to  "MK_target_machineType_featurecode_QTY"
		String charact3 = "MK_" + target_indc + "_" + mach_type + "_" + feature_code +"_QTY";
		chwClassMaintain.addCharacteristic(charact3);
		chwClassMaintain.execute();
		//e. Call the TssClassificationMaint constructor to associate the MK_target_machineType_FC_n000 class to the product's material master record
		RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
				obj_id 		//String obj_id
				, charact2 	//String class_name
				, "300" 	//String class_type
				, "H"		//String pims_identity
				);
		rdhClassificationMaint.execute();
		
	}
	
	public void CreateRPQGroupChar(String obj_id, String target_indc, String mach_type, String feature_code, String feature_code_desc) throws Exception{
		String chdescr = "";
		if("T".equalsIgnoreCase(target_indc)){
			chdescr = "RPQ Features Target";
		}else if("D".equalsIgnoreCase(target_indc)){
			chdescr = "RPQ Features Delta";
		}
		String charact ="MK_" + target_indc + "_" + mach_type + "_FC_RPQ";
		
		ChwCharMaintain chwCharMaintain = new ChwCharMaintain(
				obj_id
				, charact //MK_target_machineType_FC_RPQ
				, "CHAR"  	//String datatype Set to "CHAR".
				, 6			//int charnumber Set to "6".
				, ""		//String decplaces Set to NULL or empty string ("").
				, ""        //String casesens
				, ""		//String neg_vals Set to NULL or empty string ("").
				, ""		//String group Set to NULL or empty string ("").
				, "-"		//String valassignm Set to "-".
				, ""		//String no_entry Set to NULL or empty string ("").
				, ""		//String no_display Set to NULL or empty string ("").
				, ""		//String addit_vals Set to NULL or empty string ("").
				, chdescr	//String chdescr
				);
		//b.Call the ChwCharMaintain.addValue() method to add the feature code to the group characteristic.
		chwCharMaintain.addValue(feature_code,feature_code_desc);
		chwCharMaintain.execute();
		//c.Call the ChwClassMaintain constructor to create the MK_target_machineType_FC_n000 class.
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				obj_id //String obj_id
				, charact //String class_
				, charact //String class_desc
			);
		//d. Call the ChwClassMaintain.addCharacteristic() method to add the MK_target_machineType_FC_n000 
		//			characteristic to the MK_target_machineType_FC_n000 class
		chwClassMaintain.addCharacteristic(charact);
		chwClassMaintain.execute();
		//e. TssClassificationMaint 
		RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
				obj_id 		//String obj_id
				, charact 	//String class_name
				, "300" 	//String class_type
				, "H"		//String pims_identity
				);
		rdhClassificationMaint.execute();
	}
	
	public void CreateRPQQTYChar(String obj_id, String target_indc, String mach_type, String feature_code) throws Exception{
		String chdescr = "";
		if("T".equalsIgnoreCase(target_indc)){
			chdescr = "RPQ Features Target";
		}else if("D".equalsIgnoreCase(target_indc)){
			chdescr = "RPQ Features Delta";
		}
		String charact ="MK_" + target_indc + "_" + mach_type + "_RPQ_"+feature_code +"_QTY";
		
		ChwCharMaintain chwCharMaintain = new ChwCharMaintain(
				obj_id
				, charact //MK_target_machineType_FC_RPQ
				, "NUM"  	//String datatype Set to "NUM".
				, 4			//int charnumber Set to "4".
				, ""		//String decplaces Set to NULL or empty string ("").
				, ""        //String casesens
				, ""		//String neg_vals Set to NULL or empty string ("").
				, ""		//String group Set to NULL or empty string ("").
				, "S"		//String valassignm Set to "S".
				, ""		//String no_entry Set to NULL or empty string ("").
				, ""		//String no_display Set to NULL or empty string ("").
				, "X"		//String addit_vals Set to NULL or empty string ("X").
				, charact	//String chdescr
				);
		chwCharMaintain.execute();
		//b.Call the ChwClassMaintain constructor to create the MK_target_machineType_RPQ class.
		String charact2 = "MK_" + target_indc + "_" + mach_type + "_RPQ";
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				obj_id //String obj_id
				, charact2 //String class_
				, charact2 //String class_desc
			);
		//d. Call the ChwClassMaintain.addCharacteristic() method to add the MK_target_machineType_featurecode_QTY characteristic
		//Set to  "MK_target_machineType_featurecode_QTY"
		String charact3 = "MK_" + target_indc + "_" + mach_type + "_RPQ_" + feature_code +"_QTY";
		chwClassMaintain.addCharacteristic(charact3);
		chwClassMaintain.execute();
		//e. TssClassificationMaint 
		RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
				obj_id 		//String obj_id
				, charact2 	//String class_name
				, "300" 	//String class_type
				, "H"		//String pims_identity
				);
		rdhClassificationMaint.execute();
		
	}
	public void CreateAlphaGroupChar (String obj_id, String target_indc, String mach_type, String feature_code, String feature_code_desc) throws Exception{
		//TODO this is the new RFC and caller
		String suffix = getSuffix(obj_id, target_indc, feature_code, "G");
		
		//b. Call ChwCharMaintain to create the group characteristic for the alphanumeric feature code.
		
		String chdescr = "";
		if("T".equalsIgnoreCase(target_indc)){
			chdescr = suffix + " Alpha Features Target";
		}else if("D".equalsIgnoreCase(target_indc)){
			chdescr = suffix + " Alpha Features Delta";
		}
		String charact ="MK_" + CommonUtils.getFirstSubString(obj_id, 4) + "_FC_ALPH_" + suffix;
		
		ChwCharMaintain chwCharMaintain = new ChwCharMaintain(
				obj_id
				, charact //MK_target_machineType_FC_RPQ
				, "CHAR"  	//String datatype Set to "CHAR".
				, 12			//int charnumber Set to "12".
				, ""		//String decplaces Set to NULL or empty string ("").
				, ""        //String casesens
				, ""		//String neg_vals Set to NULL or empty string ("").
				, ""		//String group Set to NULL or empty string ("").
				, "-"		//String valassignm Set to "-".
				, ""		//String no_entry Set to NULL or empty string ("").
				, ""		//String no_display Set to NULL or empty string ("").
				, "X"		//String addit_vals Set to NULL or empty string ("X").
				, charact	//String chdescr
				);
		//c. Call the ChwCharMaintain.addValue() method to add the feature code to the alpha group characteristic.
		chwCharMaintain.addValue(feature_code,feature_code_desc);
		chwCharMaintain.execute();
		//d. Call the ChwClassMaintain to create the alpha group classification.
		String charact2 = "MK_" + CommonUtils.getFirstSubString(obj_id, 4) + "_ALPH_" + suffix;
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				obj_id //String obj_id
				, charact2 //String class_
				, charact2 //String class_desc
			);
		chwClassMaintain.addCharacteristic(charact);
		chwClassMaintain.execute();
		//e. TssClassificationMaint 
		RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
				obj_id 		//String obj_id
				, charact2 	//String class_name
				, "300" 	//String class_type
				, "H"		//String pims_identity
				);
		rdhClassificationMaint.execute();
	}

	private String getSuffix(String obj_id, String target_indc,
			String feature_code, String char_type) {
		String suffix="001";
		ChwBapiClassCharRead bapiClassCharRead = new ChwBapiClassCharRead(obj_id,feature_code,target_indc,char_type);
		int RFC = bapiClassCharRead.getRfcrc();
		if(RFC==4){
			//Set to "MK_" + <target_indc> + "_" + first 4 characters of <obj_id> + "_ALPH_".
			String class_name_stem = "MK_" + target_indc + "_" + CommonUtils.getFirstSubString(obj_id, 4) + "_ALPH_";
			ChwGetMaxClass300Suffix ChwGetMaxClass300Suffix = new ChwGetMaxClass300Suffix(obj_id,class_name_stem);
			int iReturn = ChwGetMaxClass300Suffix.getRfcrc();
			if(iReturn==4){
				suffix = "001";
			} else if(iReturn==0){
				suffix = CommonUtils.frontCompWithZore(ChwGetMaxClass300Suffix.getMax_suffix(),3);
			}
		}else if(RFC==0){
			suffix = CommonUtils.frontCompWithZore(Integer.parseInt(bapiClassCharRead.getSuffix()),3);
		}
		return suffix;
	}
	
	public void CreateAlphaQTYChar(String obj_id, String target_indc, String mach_type, String feature_code) throws Exception{
		//a.
		String charact ="MK_" + target_indc + "_" + feature_code + "_QTY";
		
		ChwCharMaintain chwCharMaintain = new ChwCharMaintain(
				obj_id
				, charact //MK_target_machineType_FC_RPQ
				, "NUM"  	//String datatype Set to "NUM".
				, 4			//int charnumber Set to "4".
				, ""		//String decplaces Set to NULL or empty string ("").
				, ""        //String casesens
				, ""		//String neg_vals Set to NULL or empty string ("").
				, ""		//String group Set to NULL or empty string ("").
				, "S"		//String valassignm Set to "S".
				, ""		//String no_entry Set to NULL or empty string ("").
				, ""		//String no_display Set to NULL or empty string ("").
				, "X"		//String addit_vals Set to NULL or empty string ("X").
				, charact	//String chdescr
				);
		chwCharMaintain.execute();
		//b. TODO Call BapiClassCharRead to see if MK_target_machineType_featurecode_QTY characteristic exists for MachineTypeNew material
		//String suffix ="003";
		String suffix = getSuffix(obj_id, target_indc, feature_code, "Q");
		
		//c. Call the ChwClassMaintain to create the alpha group classification.
		String charact2 = "MK_" + target_indc + "_" + CommonUtils.getFirstSubString(obj_id, 4) + "_ALPH_" + suffix;
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				obj_id //String obj_id
				, charact2 //String class_
				, charact2 //String class_desc
			);
		chwClassMaintain.addCharacteristic(charact);
		chwClassMaintain.execute();
		//d.Call ChwAssignCharToClass to add the MK_target_machineType_featurecode_QTY characteristic to MK_target_machineType_ALPH_suffix class
		ChwAssignCharToClass ChwAssignCharToClass = new ChwAssignCharToClass(
				obj_id 		//String obj_id
				, charact2 	//String clazz
				, charact 	//String characteristic
				, "US"		//TODO String org_area				
		);
		ChwAssignCharToClass.execute();
		
		int iRFC = ChwAssignCharToClass.getRfcrc();
		if(iRFC==2){
			int isuffix = Integer.parseInt(suffix);
			isuffix = isuffix + 1;
			suffix = CommonUtils.frontCompWithZore(isuffix, 3);
			charact2 = "MK_" + target_indc + "_" + CommonUtils.getFirstSubString(obj_id, 4) + "_ALPH_" + suffix;
			ChwClassMaintain chwClassMaintain2  = new ChwClassMaintain(
					obj_id //String obj_id
					, charact2 //String class_
					, charact2 //String class_desc
				);
			ChwAssignCharToClass = new ChwAssignCharToClass(
					obj_id 		//String obj_id
					, charact2 	//String clazz
					, charact 	//String characteristic
					, "US"		//TODO String org_area				
			);
			RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
					obj_id 		//String obj_id
					, charact2 	//String class_name
					, "300" 	//String class_type
					, "H"		//String pims_identity
					);
			rdhClassificationMaint.execute();
			
		}else if(iRFC==0){
			RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
					obj_id 		//String obj_id
					, charact2 	//String class_name
					, "300" 	//String class_type
					, "H"		//String pims_identity
					);
			rdhClassificationMaint.execute();
		}
		
		
		
	}

}
