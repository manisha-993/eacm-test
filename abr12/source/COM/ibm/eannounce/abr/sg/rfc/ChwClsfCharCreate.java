package COM.ibm.eannounce.abr.sg.rfc;


public class ChwClsfCharCreate extends RfcCallerBase {
	
	
	
	public void  CreateGroupChar(String obj_id, String target_indc, String mach_type, String feature_code, String feature_code_desc) throws Exception {
		
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
		this.addRfcName(chwCharMaintain);
		//b.Call the ChwCharMaintain.addValue() method to add the feature code to the group characteristic.
		chwCharMaintain.addValue(feature_code,feature_code_desc);
		chwCharMaintain.execute();
		this.addRfcResult(chwCharMaintain);
		
		//c.Call the ChwClassMaintain constructor to create the MK_target_machineType_FC_n000 class.
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				obj_id //String obj_id
				, charact //String class_
				, charact //String class_desc
			);
		this.addRfcName(chwClassMaintain);
		//d. Call the ChwClassMaintain.addCharacteristic() method to add the MK_target_machineType_FC_n000 
		//			characteristic to the MK_target_machineType_FC_n000 class
		chwClassMaintain.addCharacteristic(charact);
		chwClassMaintain.execute();
		this.addRfcResult(chwClassMaintain);
		
		//e. TssClassificationMaint 
		RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
				obj_id 		//String obj_id
				, charact 	//String class_name
				, "300" 	//String class_type
				, "H"		//String pims_identity
				);
		this.addRfcName(rdhClassificationMaint);
		System.out.println("100");
		rdhClassificationMaint.execute();
		this.addRfcResult(rdhClassificationMaint);
		
		
		
	}

	
	
	public void CreateQTYChar (String obj_id, String target_indc, String mach_type, String feature_code) throws Exception {
		//String chdescr = "";
		String characta ="MK_" + target_indc + "_" + mach_type + "_"+feature_code+"_QTY";
		ChwCharMaintain chwCharMaintain = new ChwCharMaintain(
				obj_id
				, characta 	//MK_target_machineType_FC_RPQ
				, "NUM"  	//String datatype Set to "CHAR".
				, 4			//int charnumber Set to "6".
				, ""		//String decplaces Set to NULL or empty string ("").
				, ""        //String casesens 
				, ""		//String neg_vals Set to NULL or empty string ("").
				, ""		//String group Set to NULL or empty string ("").
				, "S"		//String valassignm Set to "S".
				, ""		//String no_entry Set to NULL or empty string ("").
				, ""		//String no_display Set to NULL or empty string ("").
				, ""		//String addit_vals Set to NULL or empty string ("").
				, characta	//String chdescr
				);
		this.addRfcName(chwCharMaintain);
		chwCharMaintain.execute();
		this.addRfcResult(chwCharMaintain);
		
		
		//b.Call the ChwClassMaintain constructor to create the MK_target_machineType_FC_n000 class
		String charactb = "MK_" + target_indc + "_" + mach_type + "_FC_" + CommonUtils.getFirstSubString(feature_code, 1) +"000";
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				obj_id //String obj_id
				, charactb //String class_
				, charactb //String class_desc
			);
		this.addRfcName(chwClassMaintain);
		//d. Call the ChwClassMaintain.addCharacteristic() method to add the MK_target_machineType_featurecode_QTY characteristic
		//Set to  "MK_target_machineType_featurecode_QTY"
		String charactd = "MK_" + target_indc + "_" + mach_type + "_" + feature_code +"_QTY";
		chwClassMaintain.addCharacteristic(charactd);
		chwClassMaintain.execute();
		this.addRfcResult(chwClassMaintain);
		
		//e. Call the TssClassificationMaint constructor to associate the MK_target_machineType_FC_n000 class to the product's material master record
		RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
				obj_id 		//String obj_id
				, charactb 	//String class_name
				, "300" 	//String class_type
				, "H"		//String pims_identity
				);
		this.addRfcName(rdhClassificationMaint);
		rdhClassificationMaint.execute();
		this.addRfcResult(rdhClassificationMaint);
		
		
	}
	
	public void CreateRPQGroupChar(String obj_id, String target_indc, String mach_type, String feature_code, String feature_code_desc) throws Exception {
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
		this.addRfcName(chwCharMaintain);
		//b.Call the ChwCharMaintain.addValue() method to add the feature code to the group characteristic.
		chwCharMaintain.addValue(feature_code,feature_code_desc);
		chwCharMaintain.execute();
		this.addRfcResult(chwCharMaintain);
		
		//c.Call the ChwClassMaintain constructor to create the MK_target_machineType_RPQ class.
		String charactc = "MK_" + target_indc + "_" + mach_type + "_RPQ";
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				obj_id //String obj_id
				, charactc //String class_
				, charactc //String class_desc
			);
		this.addRfcName(chwClassMaintain);
		//d. Call the ChwClassMaintain.addCharacteristic() method to add the MK_target_machineType_FC_n000 
		//			characteristic to the MK_target_machineType_FC_n000 class
		chwClassMaintain.addCharacteristic(charact);
		chwClassMaintain.execute();
		this.addRfcResult(chwClassMaintain);
		
		//e. TssClassificationMaint 
		RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
				obj_id 		//String obj_id
				, charact 	//String class_name
				, "300" 	//String class_type
				, "H"		//String pims_identity
				);
		this.addRfcName(rdhClassificationMaint);
		
		rdhClassificationMaint.execute();
		this.addRfcResult(rdhClassificationMaint);
		
	}
	
	public void CreateRPQQTYChar(String obj_id, String target_indc, String mach_type, String feature_code) throws Exception {
		String chdescr = "";
		if("T".equalsIgnoreCase(target_indc)){
			chdescr = "RPQ Features Target";
		}else if("D".equalsIgnoreCase(target_indc)){
			chdescr = "RPQ Features Delta";
		}
		//MK_target_machineType_RPQ_featurecode_QTY
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
		this.addRfcName(chwCharMaintain);		
		
		chwCharMaintain.execute();
		this.addRfcResult(chwCharMaintain);
		
		//b.Call the ChwClassMaintain constructor to create the MK_target_machineType_RPQ class.
		String charactb = "MK_" + target_indc + "_" + mach_type + "_RPQ";
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				obj_id //String obj_id
				, charactb //String class_
				, charactb //String class_desc
			);
		this.addRfcName(chwClassMaintain);
		
		//d. Call the ChwClassMaintain.addCharacteristic() method to add the MK_target_machineType_featurecode_QTY characteristic
		//Set to  "MK_target_machineType_RPQ_featurecode_QTY"
		//String charact3 = "MK_" + target_indc + "_" + mach_type + "_RPQ_" + feature_code +"_QTY";
		chwClassMaintain.addCharacteristic(charact);
		chwClassMaintain.execute();
		this.addRfcResult(chwClassMaintain);
		
		//e. TssClassificationMaint 
		RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
				obj_id 		//String obj_id
				, charactb 	//String class_name
				, "300" 	//String class_type
				, "H"		//String pims_identity
				);
		this.addRfcName(rdhClassificationMaint);
		
		rdhClassificationMaint.execute();
		this.addRfcResult(rdhClassificationMaint);
		
		
	}
	
	public void CreateAlphaGroupChar (String obj_id, String target_indc, String mach_type, String feature_code, String feature_code_desc) throws Exception{
		//this is the new RFC and caller
		String suffix = getSuffix(obj_id, target_indc, feature_code, "G");
		
		//b. Call ChwCharMaintain to create the group characteristic for the alphanumeric feature code.
		
		String chdescr = "";
		if("T".equalsIgnoreCase(target_indc)){
			chdescr = suffix + " Alpha Features Target";
		}else if("D".equalsIgnoreCase(target_indc)){
			chdescr = suffix + " Alpha Features Delta";
		}
		//"MK_T_" + first 4 characters of <obj_id> + "_FC_ALPH_" + <suffix>
		String charactb ="MK_T_" + CommonUtils.getFirstSubString(obj_id, 4) + "_FC_ALPH_" + suffix;
		
		ChwCharMaintain chwCharMaintain = new ChwCharMaintain(
				obj_id
				, charactb  //"MK_T_" + first 4 characters of <obj_id> + "_FC_ALPH_" + <suffix>
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
				, chdescr	//String chdescr
				);
		this.addRfcName(chwCharMaintain);
		//c. Call the ChwCharMaintain.addValue() method to add the feature code to the alpha group characteristic.
		chwCharMaintain.addValue(feature_code,feature_code_desc);
		chwCharMaintain.execute();
		this.addRfcResult(chwCharMaintain);
		//d. Call the ChwClassMaintain to create the alpha group classification.
		String charactd = "MK_" + CommonUtils.getFirstSubString(obj_id, 4) + "_ALPH_" + suffix;
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				obj_id //String obj_id
				, charactd //String class_
				, charactd //String class_desc
			);
		this.addRfcName(chwClassMaintain);
		//e. Call the ChwClassMaintain.addCharacteristic() method to add the MK_target_machineType_FC_ALPH_suffix characteristic
		chwClassMaintain.addCharacteristic(charactd);
		chwClassMaintain.execute();
		this.addRfcResult(chwClassMaintain);
		//f. TssClassificationMaint 
		RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
				obj_id 		//String obj_id
				, charactd 	//String class_name
				, "300" 	//String class_type
				, "H"		//String pims_identity
				);
		this.addRfcName(rdhClassificationMaint);
		rdhClassificationMaint.execute();
		this.addRfcResult(rdhClassificationMaint);		
		
	}

	
	
	public void CreateAlphaQTYChar(String obj_id, String target_indc, String mach_type, String feature_code) throws Exception {
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
				, ""		//String addit_vals Set to NULL or empty string ("").
				, charact	//String chdescr
				);
		this.addRfcName(chwCharMaintain);
		chwCharMaintain.execute();
		this.addRfcResult(chwCharMaintain);
		
		//b. Call BapiClassCharRead to see if MK_target_machineType_featurecode_QTY characteristic exists for MachineTypeNew material
		//String suffix ="003";
		String suffix = getSuffix(obj_id, target_indc, feature_code, "Q");
		
		//c. Call the ChwClassMaintain to create the alpha group classification.
		//Set to "MK_" + <target_indc> + "_" + first 4 characters of <obj_id> + "_ALPH_" + <suffix>.
		String charactc = "MK_" + target_indc + "_" + CommonUtils.getFirstSubString(obj_id, 4) + "_ALPH_" + suffix;
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				obj_id //String obj_id
				, charactc //String class_
				, charactc //String class_desc
			);
		this.addRfcName(chwClassMaintain);
		
		chwClassMaintain.addCharacteristic(charact);
		chwClassMaintain.execute();
		this.addRfcResult(chwClassMaintain);
		
		//d.Call ChwAssignCharToClass to add the MK_target_machineType_featurecode_QTY characteristic to MK_target_machineType_ALPH_suffix class
		ChwAssignCharToClass ChwAssignCharToClass = new ChwAssignCharToClass(
				obj_id 		//String obj_id
				, charactc 	//String clazz
				, charactc 	//String characteristic
				, "WW"		//String org_area				
		);
		this.addRfcName(ChwAssignCharToClass);
		try {
			ChwAssignCharToClass.execute();
			this.addRfcResult(ChwAssignCharToClass);
			RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
					obj_id 		//String obj_id
					, charactc 	//String class_name
					, "300" 	//String class_type
					, "H"		//String pims_identity
					);
			this.addRfcName(rdhClassificationMaint);
			rdhClassificationMaint.execute();
			this.addRfcResult(rdhClassificationMaint);
			
		} catch (Exception e) {
			this.addRfcResult(ChwAssignCharToClass);
			
			int isuffix = Integer.parseInt(suffix);
			isuffix = isuffix + 1;
			suffix = CommonUtils.frontCompWithZore(isuffix, 3);
			String charact2 = "MK_" + target_indc + "_" + CommonUtils.getFirstSubString(obj_id, 4) + "_ALPH_" + suffix;
			ChwClassMaintain chwClassMaintain2  = new ChwClassMaintain(
					obj_id //String obj_id
					, charact2 //String class_
					, charact2 //String class_desc
				);
			this.addRfcName(chwClassMaintain2);
			chwClassMaintain2.execute();
			this.addRfcResult(chwClassMaintain2);
			
			
			ChwAssignCharToClass ChwAssignCharToClass2 = new ChwAssignCharToClass(
					obj_id 		//String obj_id
					, charact2 	//String clazz
					, charact 	//String characteristic
					, "WW"		//String org_area				
			);
			this.addRfcName(ChwAssignCharToClass2);
			ChwAssignCharToClass.execute();
			this.addRfcResult(ChwAssignCharToClass2);
			
			
			RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
					obj_id 		//String obj_id
					, charact2 	//String class_name
					, "300" 	//String class_type
					, "H"		//String pims_identity
					);
			this.addRfcName(rdhClassificationMaint);
			rdhClassificationMaint.execute();
			this.addRfcResult(rdhClassificationMaint);
			
		}
		
//		int iRFC = ChwAssignCharToClass.getRfcrc();
//		if(iRFC==2){
//			int isuffix = Integer.parseInt(suffix);
//			isuffix = isuffix + 1;
//			suffix = CommonUtils.frontCompWithZore(isuffix, 3);
//			charact2 = "MK_" + target_indc + "_" + CommonUtils.getFirstSubString(obj_id, 4) + "_ALPH_" + suffix;
//			ChwClassMaintain chwClassMaintain2  = new ChwClassMaintain(
//					obj_id //String obj_id
//					, charact2 //String class_
//					, charact2 //String class_desc
//				);
//			ChwAssignCharToClass = new ChwAssignCharToClass(
//					obj_id 		//String obj_id
//					, charact2 	//String clazz
//					, charact 	//String characteristic
//					, "US"		//String org_area				
//			);
//			RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
//					obj_id 		//String obj_id
//					, charact2 	//String class_name
//					, "300" 	//String class_type
//					, "H"		//String pims_identity
//					);
//			rdhClassificationMaint.execute();
//			
//		}else if(iRFC==0){
//			
//		}
		
		
		
	}
	
	private String getSuffix(String obj_id, String target_indc,
			String feature_code, String char_type) {
		String suffix="001";
		int RFC = 0;
		ChwBapiClassCharRead bapiClassCharRead = new ChwBapiClassCharRead(obj_id,feature_code,target_indc,char_type);
		this.addRfcName(bapiClassCharRead);
		try {
			bapiClassCharRead.execute();
			suffix = CommonUtils.frontCompWithZore(Integer.parseInt(bapiClassCharRead.getSuffix()),3);
			this.addRfcResult(bapiClassCharRead);
		} catch (Exception e) {
			this.addRfcResult(bapiClassCharRead);
			RFC = 4;
		}
		if(RFC==4){
			//Set to "MK_" + <target_indc> + "_" + first 4 characters of <obj_id> + "_ALPH_".
			String class_name_stem = "MK_" + target_indc + "_" + CommonUtils.getFirstSubString(obj_id, 4) + "_ALPH_";
			ChwGetMaxClass300Suffix ChwGetMaxClass300Suffix = new ChwGetMaxClass300Suffix(obj_id,class_name_stem);
			this.addRfcName(ChwGetMaxClass300Suffix);
			try {
				ChwGetMaxClass300Suffix.execute();
				this.addRfcResult(ChwGetMaxClass300Suffix);
				suffix = CommonUtils.frontCompWithZore(ChwGetMaxClass300Suffix.getMax_suffix(),3);
			} catch (Exception e) {
				this.addRfcResult(ChwGetMaxClass300Suffix);
				suffix = "001";
			}
		}
		return suffix;
	}

}
