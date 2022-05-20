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

public class ChwModelConvertMtc extends RfcCallerBase {
	
	private MODEL chwMODEL;
	private MODELCONVERT chwModelConvert;	
	private Connection rdhConnection;
	private Connection odsConnection;
	
	private String MODEL_MACHTYPE = "SELECT DISTINCT SUBSTR(t2.ATTRIBUTEVALUE,1,3) AS MODEL, trim(t3.ATTRIBUTEVALUE) AS INVNAME FROM OPICM.flag F "
			+ " INNER JOIN opicm.flag t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='MACHTYPEATR' AND T1.ATTRIBUTEVALUE = ? AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP"
			+ " INNER JOIN opicm.text t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='MODELATR' AND T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP"
			+ " INNER JOIN opicm.text t3 ON f.ENTITYID =t3.ENTITYID AND f.ENTITYTYPE =t3.ENTITYTYPE AND t3.ATTRIBUTECODE ='INVNAME' AND t3.NLSID =1 AND T3.VALTO > CURRENT  TIMESTAMP AND T3.EFFTO > CURRENT  TIMESTAMP"
			+ " INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t1.ENTITYID AND F1.ENTITYTYPE =t1.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP"
			+ " INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP"
			+ " WHERE f.ENTITYTYPE ='MODEL' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELTIERPABRSTATUS') "
			+ " AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=? "
			+ " WITH ur";
	
	
	public ChwModelConvertMtc(MODEL chwMODEL, MODELCONVERT chwProduct,Connection rdhConnection, Connection odsConnection) {	
		this.chwMODEL = chwMODEL;
		this.chwModelConvert = chwProduct;
		this.rdhConnection = rdhConnection;
		this.odsConnection = odsConnection;
	}
	public void execute() throws Exception{

			String empty ="";
			String obj_id = chwModelConvert.getTOMACHTYPE() + "MTC";
			//1. Call ChwMatmCreate to create the material master for the product object.
			ChwMatmCreate chwMatmCreate = new ChwMatmCreate(chwMODEL,"ZMAT",obj_id);
			this.addRfcName(chwMatmCreate);			
			chwMatmCreate.execute();
			this.addRfcResult(chwMatmCreate);
			
			//2. Call Chw001ClfCreate to create the standard 001 classifications and characteristics 
			//   which are tied to the offering's material master record.
			
			Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(chwMODEL,"ZMAT",obj_id, odsConnection);
			this.addDebug("Calling " + "Chw001ClfCreate");
			try{
				chw001ClfCreate.execute();
				this.addMsg(chw001ClfCreate.getRptSb());
			}catch (Exception e) {
				this.addMsg(chw001ClfCreate.getRptSb());
				throw e;
			}
			//3.Create the MK_machineType_MTC class and MK_machineType_MTC characteristic if it does not exist.  
			//Assign the MK_machineType_MTC class to the product's material master record
			
			//3.a Call the ChwCharMaintain constructor to create the MK_machineType_MTC characteristic.
			ChwCharMaintain chwCharMaintain = 
			new ChwCharMaintain(obj_id  //String obj_id Set to concatenation of chwProduct.machineType + "MTC"
								,"MK_"+chwModelConvert.getTOMACHTYPE()+"_MTC" //String charact  Set to  "MK_T_<machine_type>_MOD"
								, "CHAR" 			//String datatype
								, 15 				//int charnumber
								, empty				//String decplaces
								, empty				//String casesens
								, empty				//String neg_vals
								, empty				//String group
								, "-"				//String valassignm  Set to "-".
								, empty				//String no_entry
								, empty				//String no_display
								, "X" 				//String addit_vals Set to "X".
								, "Machine Type Conversions " + chwModelConvert.getTOMACHTYPE() //String chdescr  Set to "<machine_type> Model Characteristic" where <machine_type> is chwProduct.machineType
								);
			this.addRfcName(chwCharMaintain);
			//3.b Call ChwCharMaintain.addValue() method to add the MODELCONVERT with its description to the MK_machineType_MTC characteristic.	
			//Set to MODELCONVERT/FROMMACHTYPE + MODELCONVERT/FROMMODEL + "_" + MODELCONVERT/TOMACHTYPE+MODELCONVERT/TOMODEL
			String value = chwModelConvert.getFROMMACHTYPE().trim() + chwModelConvert.getFROMMODEL().trim() + "_" + chwModelConvert.getTOMACHTYPE().trim() + chwModelConvert.getTOMODEL().trim();
			String valdescr = chwModelConvert.getFROMMACHTYPE().trim() + chwModelConvert.getFROMMODEL().trim() + " to " + chwModelConvert.getTOMACHTYPE().trim() + chwModelConvert.getTOMODEL().trim();
			chwCharMaintain.addValue(value, valdescr);
			chwCharMaintain.execute();
			this.addRfcResult(chwCharMaintain);
			//3.c Call the ChwClassMaintain constructor to create the MK_machineType_MTC class.
			ChwClassMaintain ChwClassMaintain = 
			new ChwClassMaintain(
					obj_id 								//String obj_id Set to concatenation of chwProduct.machineType + "MTC"
					, "MK_"+chwModelConvert.getTOMACHTYPE()+"_MTC"  //String class_name   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType
					, "MK_"+chwModelConvert.getTOMACHTYPE()+"_MTC"  //String class_type   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType.
					);
			this.addRfcName(ChwClassMaintain);
			//3.d Call the ChwClassMaintain.addCharacteristic() method to add the MK_machineType_MTC characteristic to the MK_machineType_MTC  class
			ChwClassMaintain.addCharacteristic("MK_"+chwModelConvert.getTOMACHTYPE()+"_MTC"); 
			ChwClassMaintain.execute();
			this.addRfcResult(ChwClassMaintain);
			//3.e Call the TssClassificationMaint constructor to associate the MK_machineType_MTC class to the product's material master record
			//reuse ==>no TssClassificationMaint but RdhClassificationMaint 
			RdhClassificationMaint TssClassificationMaint = 
			new RdhClassificationMaint(
					obj_id 								//String obj_id Set to concatenation of chwProduct.machineType + "MTC"
					, "MK_"+chwModelConvert.getTOMACHTYPE()+"_MTC"  //String class_name   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType
					, "300"  							//String class_type   Set to "300"
					, "H"
					);
			this.addRfcName(TssClassificationMaint);
			TssClassificationMaint.execute();
			this.addRfcResult(TssClassificationMaint);
			
			//4.Create the MK_machineType_MOD class and MK_T_machineType_MOD characteristic if it does not exist.  
			//4.a Call the ChwCharMaintain constructor to create the MK_T_machineType_MOD characteristic.
			ChwCharMaintain ChwCharMaintain = 
			new ChwCharMaintain(
					obj_id				 				//String obj_id  Set to concatenation of chwProduct.machineType + "MTC"
					, "MK_T_"+chwModelConvert.getTOMACHTYPE()+"_MOD"	//String charact  Set to  "MK_machineType_MTC" where <machine_type> is chwProduct.machineType
					, "CHAR" 							//String datatype  Set to "CHAR".
					, 6 								//int charnumber  Set to "15".
					, empty 		//String decplaces
					, empty 		//String casesens
					, empty 		//String neg_vals
					, empty 		//String group
					, "S" 			//String valassignm  Set to "-".
					, empty 		//String no_entry
					, empty 		//String no_display
					, "X" 			//String addit_vals   Set to "X".
					, chwModelConvert.getTOMACHTYPE() +" Model Characteristic"	//String chdescr	Set to "Machine Type Conversions <machine_type>" 				
					);
			this.addRfcName(ChwCharMaintain);
			//4.B For For each MODEL with MODEL/MACHTYPE = MODELCONVERT/TOMACHTYPE
			String machtype = chwModelConvert.getTOMACHTYPE();
			//ArrayList<HashMap<String, String>> recordArray = getModelConvert(machtype);
			List<Map<String,String>> MODEL_MACHTYPE_LIST = this.getFromModelToModel(MODEL_MACHTYPE, chwModelConvert.getTOMACHTYPE(),chwMODEL.getPDHDOMAIN());
			String value_descr ="";
			for(Map<String,String> map : MODEL_MACHTYPE_LIST){
				
				value = map.get("MODEL");
				
				value_descr = CommonUtils.getFirstSubString(map.get("INVNAME"),25) + " " + map.get("MODEL");
				ChwCharMaintain.addValue(value, value_descr);
			}	
			ChwCharMaintain.execute();
			this.addRfcResult(ChwCharMaintain);
			//4.c Call the ChwClassMaintain constructor to create the MK_machineType_MOD class. 
			ChwClassMaintain =
			new ChwClassMaintain(
					obj_id 								//String obj_id Set to concatenation of chwProduct.machineType + "MTC"
					, "MK_"+chwModelConvert.getTOMACHTYPE()+"_MOD"  //String class_name   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType
					, "MK_"+chwModelConvert.getTOMACHTYPE()+"_MOD"  //String class_type   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType.
					);
			this.addRfcName(ChwClassMaintain);
//			//4.d Call the ChwClassMaintain.addCharacteristic() method to add the MK_T_machineType_MOD characteristic to the MK_machineType_MOD characteristic class.
			ChwClassMaintain.addCharacteristic("MK_T_"+chwModelConvert.getTOMACHTYPE()+"_MOD");
			ChwClassMaintain.execute();
			this.addRfcResult(ChwClassMaintain);
			//4.e Call the TssClassificationMaint constructor to associate the MK_machineType_MOD class to the product's material master record.
			TssClassificationMaint = 
			new RdhClassificationMaint(
					obj_id 								//String obj_id Set to concatenation of chwProduct.machineType + "MTC"
					, "MK_"+chwModelConvert.getTOMACHTYPE()+"_MOD"  //String class_name   Set to  "MK_<machine_type>_MTC" where <machine_type> is chwProduct.machineType
					, "300"  							//String class_type   Set to "300"
					, "H"
					);
			this.addRfcName(TssClassificationMaint);
			TssClassificationMaint.execute();
			this.addRfcResult(TssClassificationMaint);
			
			
			//5. Call the TssClassificationMaint constructor to associate the MK_REFERENCE class to the product's material master record.
			RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(obj_id,"MK_REFERENCE","300","H");	
			this.runRfcCaller(rdhClassificationMaint);
			
			//6. Call the TssClassificationMaint constructor to associate the MK_T_VAO_NEW class to the product's material master record. 
			rdhClassificationMaint = new RdhClassificationMaint(obj_id,"MK_T_VAO_NEW","300","H");	
			this.runRfcCaller(rdhClassificationMaint);
			
			//7. Call the TssClassificationMaint constructor to associate the MK_D_VAO_NEW class to the product's material master record. 
			rdhClassificationMaint = new RdhClassificationMaint(obj_id,"MK_D_VAO_NEW","300","H");	
			this.runRfcCaller(rdhClassificationMaint);
			
			//8.Call the TssClassificationMaint constructor to associate the MK_FC_EXCH class to the product's material master record.
			rdhClassificationMaint = new RdhClassificationMaint(obj_id,"MK_FC_EXCH","300","H");	
			this.runRfcCaller(rdhClassificationMaint);
			
			//9.Call the TssClassificationMaint constructor to associate the MK_FC_CONV class to the product's material master record.
			rdhClassificationMaint = new RdhClassificationMaint(obj_id,"MK_FC_CONV","300","H");	
			this.runRfcCaller(rdhClassificationMaint);
			
			//10 For each MODEL with MODEL/MACHTYPE = MODELCONVERT/TOMACHTYPE
			for(Map<String,String> map : MODEL_MACHTYPE_LIST){
				String obj_id_depd= chwModelConvert.getTOMACHTYPE()+"UPG";
				//Set to "SC_<MODEL/MACHTYPE>_MOD_<MODEL/MODEL>"
				String dep_extern="SC_"+chwModelConvert.getTOMACHTYPE()+"_MOD_" + map.get("MODEL");
				String dep_type="5"; 
				//Set to "SC_<MODEL/MACHTYPE>_MOD_<MODEL/MODEL>".
				String descript="SC_"+chwModelConvert.getTOMACHTYPE()+"_MOD_" + map.get("MODEL");
				
				ChwDepdMaintain chwDepdCaller	=new ChwDepdMaintain(obj_id_depd, dep_extern, dep_type, descript);
				
				String sourceLine = "$PARENT.MK_T_"+chwModelConvert.getTOMACHTYPE() +"_MOD='"+map.get("MODEL")+"'";				
				chwDepdCaller.addSourceLineCondition(sourceLine);
				runRfcCaller(chwDepdCaller);
			}
			//11. Call the ChwConpMaintain to create a configuration profile for the product's material master record.
			//11.a 
			ChwConpMaintain ChwConpMaintain  = 	new ChwConpMaintain(
					obj_id 				//String obj_id  Set to concatenation of chwProduct.machineType + "MTC"
					, "INITIAL"         //String c_profile Set to "INITIAL".
					, "SD01"			//String bomappl Set to "SD01".
					, "2"				//String bomexpl Set to "2".
					, chwModelConvert.getTOMACHTYPE() +	"MTCUI"		//String design	 Set to Set to concatenation of chwProduct.machineType + "MTCUI" 
					);
			this.addRfcName(ChwConpMaintain);
			//11.b Call the ChwConpMaintain.addConfigDependency() method.
			ChwConpMaintain.addConfigDependency("E2E", empty); //Set to "E2E".
			//11.c 
			ChwConpMaintain.addConfigDependency("PR_"+chwModelConvert.getTOMACHTYPE()+"_SET_MODEL", empty);  //Set to "PR_<chwProduct.machineType>_SET_MODEL"
			//11.d
			ChwConpMaintain.addConfigDependency("PR_E2E_SET_MTM", empty);  //Set to "PR_E2E_SET_MTM".
			//11.e 
			ChwConpMaintain.addConfigDependency("PR_E2E_PRICING_HW", empty);  //Set to "PR_E2E_PRICING_HW".
			//11.f 
			ChwConpMaintain.addConfigDependency("PR_E2E_CSTIC_HIDING_HW", empty);  //Set to "PR_E2E_PRICING_HW".
			
			ChwConpMaintain.execute();// excute 1 time 
			this.addRfcResult(ChwConpMaintain);
		
	}
	
	

	private List<Map<String,String>> getFromModelToModel(String sql,String type,String pdhdomain) throws SQLException {
		List<Map<String,String>> fromModelToModelList = new ArrayList<Map<String,String>>();
		Object[] params = new String[2]; 
		params[0] =type;
		params[1] =pdhdomain;
		String realSql = CommonUtils.getPreparedSQL(sql, params);
		this.addDebug("querySql=" + realSql);
		
		PreparedStatement statement = rdhConnection.prepareStatement(sql);
		statement.setString(1, type);
		statement.setString(2, pdhdomain);
		ResultSet resultSet = statement.executeQuery();
		while(resultSet.next()){
			Map<String,String> map = new HashMap<String,String>();
			map.put("MODEL", resultSet.getString("MODEL"));
			map.put("INVNAME", resultSet.getString("INVNAME"));
			fromModelToModelList.add(map);
		}		
		return fromModelToModelList;
	}
	public static void main(String[] args) {
		MODEL model = new MODEL();
		model.setMACHTYPE("UTC");
		model.setMODEL("MOD_NAME");
//		MODELIERPABRSTATUSCaller MODELIERPABRSTATUSCaller = new MODELIERPABRSTATUSCaller(model);
//		MODELIERPABRSTATUSCaller.excute();
		

	}

}
