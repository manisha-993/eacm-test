package COM.ibm.eannounce.abr.sg.rfc;


public class ChwClsfCharCreate extends RfcCallerBase {
	
	
	
	public void  CreateGroupChar(String obj_id, String target_indc, String mach_type, String feature_code, String feature_code_desc, String rfaNum) throws Exception {
		
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
		String charact ="MK_" + target_indc + "_" + mach_type + "_FC_" + CommonUtils.getFirstSubString(feature_code, 1) +"000";
		
		ChwCharMaintain chwCharMaintain = new ChwCharMaintain(
				rfaNum
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
				rfaNum //String obj_id
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
				, rfaNum);
		this.addRfcName(rdhClassificationMaint);
		System.out.println("100");
		rdhClassificationMaint.execute();
		this.addRfcResult(rdhClassificationMaint);
		
		
		
	}

	
	
	public void CreateQTYChar (String obj_id, String target_indc, String mach_type, String feature_code, String rfaNum) throws Exception {
		//String chdescr = "";
		String characta ="MK_" + target_indc + "_" + mach_type + "_"+feature_code+"_QTY";
		ChwCharMaintain chwCharMaintain = new ChwCharMaintain(
				rfaNum
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
		String classb = "MK_" + target_indc + "_" + mach_type + "_FC_" + CommonUtils.getFirstSubString(feature_code, 1) +"000";
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				rfaNum //String obj_id
				, classb //String class_
				, classb //String class_desc
			);
		this.addRfcName(chwClassMaintain);
		//d. Call the ChwClassMaintain.addCharacteristic() method to add the MK_target_machineType_featurecode_QTY characteristic
		//Set to  "MK_target_machineType_featurecode_QTY"
		chwClassMaintain.addCharacteristic(characta);
		chwClassMaintain.execute();
		this.addRfcResult(chwClassMaintain);
		
		//e. Call the TssClassificationMaint constructor to associate the MK_target_machineType_FC_n000 class to the product's material master record
		RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
				obj_id 		//String obj_id
				, classb 	//String class_name
				, "300" 	//String class_type
				, "H"		//String pims_identity
				, rfaNum);
		this.addRfcName(rdhClassificationMaint);
		rdhClassificationMaint.execute();
		this.addRfcResult(rdhClassificationMaint);
		
		
	}
	
	public void CreateRPQGroupChar(String obj_id, String target_indc, String mach_type, String feature_code, String feature_code_desc, String rfaNum) throws Exception {
		String chdescr = "";
		if("T".equalsIgnoreCase(target_indc)){
			chdescr = "RPQ Features Target";
		}else if("D".equalsIgnoreCase(target_indc)){
			chdescr = "RPQ Features Delta";
		}
		String charact ="MK_" + target_indc + "_" + mach_type + "_FC_RPQ";
		
		ChwCharMaintain chwCharMaintain = new ChwCharMaintain(
				rfaNum
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
		String classc = "MK_" + target_indc + "_" + mach_type + "_RPQ";
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				rfaNum //String obj_id
				, classc //String class_
				, classc //String class_desc
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
				, classc 	//String class_name
				, "300" 	//String class_type
				, "H"		//String pims_identity
				, rfaNum);
		this.addRfcName(rdhClassificationMaint);
		
		rdhClassificationMaint.execute();
		this.addRfcResult(rdhClassificationMaint);
		
	}
	
	public void CreateRPQQTYChar(String obj_id, String target_indc, String mach_type, String feature_code, String rfaNum) throws Exception {
//		String chdescr = "";
//		if("T".equalsIgnoreCase(target_indc)){
//			chdescr = "RPQ Features Target";
//		}else if("D".equalsIgnoreCase(target_indc)){
//			chdescr = "RPQ Features Delta";
//		}
		//MK_target_machineType_RPQ_featurecode_QTY
		String charact ="MK_" + target_indc + "_" + mach_type + "_RPQ_"+feature_code +"_QTY";
		
		ChwCharMaintain chwCharMaintain = new ChwCharMaintain(
				rfaNum
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
		String classb = "MK_" + target_indc + "_" + mach_type + "_RPQ";
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				rfaNum //String obj_id
				, classb //String class_
				, classb //String class_desc
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
				, classb 	//String class_name
				, "300" 	//String class_type
				, "H"		//String pims_identity
				, rfaNum);
		this.addRfcName(rdhClassificationMaint);
		
		rdhClassificationMaint.execute();
		this.addRfcResult(rdhClassificationMaint);
		
		
	}
	
	public void CreateAlphaGroupChar (String obj_id, String target_indc, String mach_type, String feature_code, String feature_code_desc, String rfaNum) throws Exception{
		//this is the new RFC and caller
		this.addOutput("CreateAlphaGroupChar obj_id" + obj_id);
		String suffix = getSuffix(obj_id, target_indc, feature_code, "G");
		
		//b. Call ChwCharMaintain to create the group characteristic for the alphanumeric feature code.
		
		String chdescr = "";
		if("T".equalsIgnoreCase(target_indc)){
			chdescr = suffix + " Alpha Features Target";
		}else if("D".equalsIgnoreCase(target_indc)){
			chdescr = suffix + " Alpha Features Delta";
		}
		//"MK_T_" + first 4 characters of <obj_id> + "_FC_ALPH_" + <suffix>
		String charactb ="MK_" + target_indc+ "_" + CommonUtils.getFirstSubString(obj_id, 4) + "_FC_ALPH_" + suffix;
		
		ChwCharMaintain chwCharMaintain = new ChwCharMaintain(
				rfaNum
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
		String classd = "MK_" + target_indc+ "_" + CommonUtils.getFirstSubString(obj_id, 4) + "_ALPH_" + suffix;
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				rfaNum //String obj_id
				, classd //String class_
				, classd //String class_desc
			);
		this.addRfcName(chwClassMaintain);
		//e. Call the ChwClassMaintain.addCharacteristic() method to add the MK_target_machineType_FC_ALPH_suffix characteristic
		chwClassMaintain.addCharacteristic(charactb);
		chwClassMaintain.execute();
		this.addRfcResult(chwClassMaintain);
		//f. TssClassificationMaint 
		RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
				obj_id 		//String obj_id
				, classd 	//String class_name
				, "300" 	//String class_type
				, "H"		//String pims_identity
				, rfaNum);
		this.addRfcName(rdhClassificationMaint);
		rdhClassificationMaint.execute();
		this.addRfcResult(rdhClassificationMaint);		
		
	}

	
	
	public void CreateAlphaQTYChar(String obj_id, String target_indc, String mach_type, String feature_code, String material, String rfaNum) throws Exception {
		//a.
		String charact ="MK_" + target_indc + "_" + mach_type +"_" + feature_code + "_QTY";
		
		ChwCharMaintain chwCharMaintain = new ChwCharMaintain(
				rfaNum
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
		this.addOutput("CreateAlphaQTYChar obj_id" + obj_id);
		String suffix = getSuffix(obj_id, target_indc, feature_code, "Q");
		
		//c. Call the ChwClassMaintain to create the alpha group classification.
		//Set to "MK_" + <target_indc> + "_" + first 4 characters of <obj_id> + "_ALPH_" + <suffix>.
		String classc = "MK_" + target_indc + "_" + CommonUtils.getFirstSubString(obj_id, 4) + "_ALPH_" + suffix;
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				rfaNum //String obj_id
				, classc //String class_
				, classc //String class_desc
			);
		this.addRfcName(chwClassMaintain);
		
		chwClassMaintain.execute();
		this.addRfcResult(chwClassMaintain);
		//d. Call TssClassificationMaint to assign the classification to the product's material master record. 
		RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
				obj_id 		//String obj_id
				, classc 	//String class_name
				, "300" 	//String class_type
				, "H"		//String pims_identity
				, rfaNum);
		this.addRfcName(rdhClassificationMaint);
		rdhClassificationMaint.execute();
		this.addRfcResult(rdhClassificationMaint);		
		
		//e.Call ChwAssignCharToClass to add the MK_target_machineType_featurecode_QTY characteristic to MK_target_machineType_ALPH_suffix class
		ChwAssignCharToClass ChwAssignCharToClass = new ChwAssignCharToClass(
				rfaNum 		//String obj_id
				, classc 	//String clazz
				, charact 	//String characteristic
				, ""		//Story EACM-6831 change WW to ""
				, material  // material
		);
		this.addRfcName(ChwAssignCharToClass);
//		try {
			ChwAssignCharToClass.execute();
			this.addRfcResult(ChwAssignCharToClass);
			this.addOutput("ChwAssignCharToClass.getRfcrc()11=" + ChwAssignCharToClass.getRfcrc());
			if (ChwAssignCharToClass.getRfcrc() == 2) {
				int isuffix = Integer.parseInt(suffix);
				this.addOutput("isuffix=" + isuffix);
				isuffix = isuffix + 1;
				this.addOutput("isuffix=" + isuffix);
				suffix = CommonUtils.frontCompWithZore(isuffix, 3);
				//String classc = "MK_" + target_indc + "_" + CommonUtils.getFirstSubString(obj_id, 4) + "_ALPH_" + suffix;
				this.addOutput("suffix=" + suffix);
				String class2 = "MK_" + target_indc + "_" + CommonUtils.getFirstSubString(obj_id, 4) + "_ALPH_" + suffix;
				ChwClassMaintain chwClassMaintain2  = new ChwClassMaintain(
						rfaNum //String obj_id
						, class2 //String class_
						, class2 //String class_desc
					);
				this.addRfcName(chwClassMaintain2);
				chwClassMaintain2.execute();
				this.addRfcResult(chwClassMaintain2);
				
				RdhClassificationMaint rdhClassificationMaint2 = new RdhClassificationMaint(
						obj_id 		//String obj_id
						, class2 	//String class_name
						, "300" 	//String class_type
						, "H"		//String pims_identity
						, rfaNum);
				this.addRfcName(rdhClassificationMaint2);
				rdhClassificationMaint2.execute();
				this.addRfcResult(rdhClassificationMaint2);				
				
				ChwAssignCharToClass ChwAssignCharToClass2 = new ChwAssignCharToClass(
						rfaNum 		//String obj_id
						, class2 	//String clazz
						, charact 	//String characteristic
						, ""		//Story EACM-6831 change WW to ""
						, material  //
				);
				this.addRfcName(ChwAssignCharToClass2);
				ChwAssignCharToClass2.execute();
				this.addRfcResult(ChwAssignCharToClass2);
				
				
				
			}
			
//		} catch (Exception e) {	
//			this.addOutput("ChwAssignCharToClass.getRfcrc()22=" + ChwAssignCharToClass.getRfcrc());
//			this.addOutput("ChwAssignCharToClass.getRfcrc()22=" + ChwAssignCharToClass.getRfcrc());
//			
////			if (ChwAssignCharToClass.getRfcrc() == 2) {
////				int isuffix = Integer.parseInt(suffix);
////				this.addOutput("isuffix=" + isuffix);
////				isuffix = isuffix + 1;
////				this.addOutput("isuffix=" + isuffix);
////				suffix = CommonUtils.frontCompWithZore(isuffix, 3);
////				//String classc = "MK_" + target_indc + "_" + CommonUtils.getFirstSubString(obj_id, 4) + "_ALPH_" + suffix;
////				this.addOutput("suffix=" + suffix);
////				String class2 = "MK_" + target_indc + "_" + CommonUtils.getFirstSubString(obj_id, 4) + "_ALPH_" + suffix;
////				ChwClassMaintain chwClassMaintain2  = new ChwClassMaintain(
////						obj_id //String obj_id
////						, class2 //String class_
////						, class2 //String class_desc
////					);
////				this.addRfcName(chwClassMaintain2);
////				chwClassMaintain2.execute();
////				this.addRfcResult(chwClassMaintain2);
////				
////				
////				ChwAssignCharToClass ChwAssignCharToClass2 = new ChwAssignCharToClass(
////						obj_id 		//String obj_id
////						, class2 	//String clazz
////						, charact 	//String characteristic
////						, "WW"		//String org_area				
////				);
////				this.addRfcName(ChwAssignCharToClass2);
////				ChwAssignCharToClass2.execute();
////				this.addRfcResult(ChwAssignCharToClass2);
////				
////				
////				RdhClassificationMaint rdhClassificationMaint2 = new RdhClassificationMaint(
////						obj_id 		//String obj_id
////						, class2 	//String class_name
////						, "300" 	//String class_type
////						, "H"		//String pims_identity
////						);
////				this.addRfcName(rdhClassificationMaint2);
////				rdhClassificationMaint2.execute();
////				this.addRfcResult(rdhClassificationMaint2);
////			}
//			
//				
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
			this.addRfcResult(bapiClassCharRead);
			this.addOutput("bapiClassCharRead target_indc = "+target_indc+"; and char_type="+char_type+"; rc="+ bapiClassCharRead.getRfcrc());
			String bapi_suffix = bapiClassCharRead.getSuffix();
			this.addOutput("bapiClassCharRead bapi_suffix="+ bapi_suffix);
			suffix = CommonUtils.frontCompWithZore(Integer.parseInt(bapiClassCharRead.getSuffix()),3);	
			this.addOutput("bapiClassCharRead suffix="+ suffix);
		} catch (Exception e) {
			this.addRfcResult(bapiClassCharRead);
			String message = bapiClassCharRead.getError_text();
			if(message.contains("The feature code is not associated")
			||message.contains("The QTY characteristic is not associated")){
				this.addOutput("change the RFC code to 4");
				RFC = 4;
				this.addOutput("bapiClassCharRead exception target_indc = "+target_indc+"; and char_type="+char_type+"; rc="+ RFC);
			}else{
				RFC = bapiClassCharRead.getRfcrc();
				this.addOutput("bapiClassCharRead exception target_indc = "+target_indc+"; and char_type="+char_type+"; rc="+ RFC);
			}			
		}
		if(RFC==4){
			//Set to "MK_" + <target_indc> + "_" + first 4 characters of <obj_id> + "_ALPH_".
			String class_name_stem = "MK_" + target_indc + "_" + CommonUtils.getFirstSubString(obj_id, 4) + "_ALPH_";
			ChwGetMaxClass300Suffix ChwGetMaxClass300Suffix = new ChwGetMaxClass300Suffix(obj_id,class_name_stem);
			this.addRfcName(ChwGetMaxClass300Suffix);
			try {
				ChwGetMaxClass300Suffix.execute();
				this.addRfcResult(ChwGetMaxClass300Suffix);
				this.addOutput("ChwGetMaxClass300Suffix target_indc = "+target_indc+"; and char_type="+char_type+"; rc="+ ChwGetMaxClass300Suffix.getRfcrc());
				suffix = CommonUtils.frontCompWithZore(ChwGetMaxClass300Suffix.getMax_suffix(),3);
				if("000".equals(suffix)){
					suffix = "001";
				}
				this.addOutput("ChwGetMaxClass300Suffix target_indc = "+target_indc+"; and char_type="+char_type+"; max_suffix ="+ suffix);
			} catch (Exception e) {
				this.addRfcResult(ChwGetMaxClass300Suffix);
				suffix = "001";
				this.addOutput("ChwGetMaxClass300Suffix exception target_indc = "+target_indc+"; and char_type="+char_type+"; max_suffix ="+ suffix +"; rc="+ ChwGetMaxClass300Suffix.getRfcrc());
			}
		}
		return suffix;
	}

}
