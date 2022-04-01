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
import COM.ibm.opicmpdh.middleware.MiddlewareException;

public class ChwMachTypeUpg extends RfcCallerBase{
	
	private MODEL chwModel;
	private String chwProduct;
	private Connection rdhConnection;
	private Connection odsConnection;
	
	private String MODELCONVERTSQL = " SELECT count(*) FROM OPICM.flag F"
			    + " INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP"
			    + " INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE =? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP"
			    + " INNER JOIN opicm.text t3 ON f.ENTITYID =t3.ENTITYID AND f.ENTITYTYPE =t3.ENTITYTYPE AND t3.ATTRIBUTECODE ='FROMMODEL' AND T3.VALTO > CURRENT  TIMESTAMP AND T3.EFFTO > CURRENT  TIMESTAMP"
			    + " INNER JOIN opicm.text t4 ON f.ENTITYID =t4.ENTITYID AND f.ENTITYTYPE =t4.ENTITYTYPE AND t4.ATTRIBUTECODE ='TOMODEL' AND T4.VALTO > CURRENT  TIMESTAMP AND T4.EFFTO > CURRENT  TIMESTAMP"
			    + " INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP"
			    + " INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP"
			    + " WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS') " 
			    + " AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=? "
			    + " AND T3.ATTRIBUTEVALUE!=T4.ATTRIBUTEVALUE"
			    + " WITH UR";
	
	private String FCTRANSACTIONSQL = " SELECT count(*) FROM OPICM.flag F"
		    + " INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP"
		    + " INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE =? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP"
		    + " INNER JOIN opicm.text t3 ON f.ENTITYID =t3.ENTITYID AND f.ENTITYTYPE =t3.ENTITYTYPE AND t3.ATTRIBUTECODE ='FROMMODEL' AND T3.VALTO > CURRENT  TIMESTAMP AND T3.EFFTO > CURRENT  TIMESTAMP"
		    + " INNER JOIN opicm.text t4 ON f.ENTITYID =t4.ENTITYID AND f.ENTITYTYPE =t4.ENTITYTYPE AND t4.ATTRIBUTECODE ='TOMODEL' AND T4.VALTO > CURRENT  TIMESTAMP AND T4.EFFTO > CURRENT  TIMESTAMP"
		    + " INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP"
		    + " INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP"
		    + " WHERE f.ENTITYTYPE ='FCTRANSACTION' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'FCTRANSACTIONIERPABRSTATUS') " 
		    + " AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=? "
		    + " AND T3.ATTRIBUTEVALUE!=T4.ATTRIBUTEVALUE"
		    + " WITH UR";
	
	private String FROMMODELTOMODEL = "SELECT DISTINCT FROMMODEL||'_'||TOMODEL AS FromModelToModel FROM ("
		    + " SELECT DISTINCT t3.ATTRIBUTEVALUE AS FROMMODEL, t4.ATTRIBUTEVALUE AS TOMODEL FROM OPICM.flag F"
		    + " INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP"
		    + " INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE =? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP"
		    + " INNER JOIN opicm.text t3 ON f.ENTITYID =t3.ENTITYID AND f.ENTITYTYPE =t3.ENTITYTYPE AND t3.ATTRIBUTECODE ='FROMMODEL' AND T3.VALTO > CURRENT  TIMESTAMP AND T3.EFFTO > CURRENT  TIMESTAMP"
		    + " INNER JOIN opicm.text t4 ON f.ENTITYID =t4.ENTITYID AND f.ENTITYTYPE =t4.ENTITYTYPE AND t4.ATTRIBUTECODE ='TOMODEL' AND T4.VALTO > CURRENT  TIMESTAMP AND T4.EFFTO > CURRENT  TIMESTAMP"
		    + " INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP"
		    + " INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP"
		    + " WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS')  "
		    + " AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=? "
		    + " AND T3.ATTRIBUTEVALUE!=T4.ATTRIBUTEVALUE"
		    + " UNION all"
		    + " SELECT DISTINCT t3.ATTRIBUTEVALUE AS FROMMODEL, t4.ATTRIBUTEVALUE AS TOMODEL FROM OPICM.flag F"
		    + " INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP"
		    + " INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE =? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP"
		    + " INNER JOIN opicm.text t3 ON f.ENTITYID =t3.ENTITYID AND f.ENTITYTYPE =t3.ENTITYTYPE AND t3.ATTRIBUTECODE ='FROMMODEL' AND T3.VALTO > CURRENT  TIMESTAMP AND T3.EFFTO > CURRENT  TIMESTAMP"
		    + " INNER JOIN opicm.text t4 ON f.ENTITYID =t4.ENTITYID AND f.ENTITYTYPE =t4.ENTITYTYPE AND t4.ATTRIBUTECODE ='TOMODEL' AND T4.VALTO > CURRENT  TIMESTAMP AND T4.EFFTO > CURRENT  TIMESTAMP"
		    + " INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP"
		    + " INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP"
		    + " WHERE f.ENTITYTYPE ='FCTRANSACTION' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'FCTRANSACTIONIERPABRSTATUS')  "
		    + " AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=? "
		    + " AND T3.ATTRIBUTEVALUE!=T4.ATTRIBUTEVALUE"
		    + " ) AS TT"
		    + " WITH UR";
	
	
	
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
			ChwClassMaintain.addCharacteristic("MK_T_"+chwModel.getMACHTYPE()+"_MOD"); 
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
			if(exist(MODELCONVERTSQL, chwModel.getMACHTYPE(),chwModel.getPDHDOMAIN())||exist(FCTRANSACTIONSQL, chwModel.getMACHTYPE(),chwModel.getPDHDOMAIN())) {
				String machtype = chwModel.getMACHTYPE();
				List<String> FromModelToModel = this.getFromModelToModel(FROMMODELTOMODEL, chwModel.getMACHTYPE(),chwModel.getPDHDOMAIN());				
				ChwCharMaintain ChwCharMaintain = 
				new ChwCharMaintain(
						obj_id				 				//String obj_id  Set to concatenation of chwProduct.machineType + "UPG"
						, "MK_D_"+chwModel.getMACHTYPE()+"_MOD_CONV"	//String charact  Set to  "MK_D_machineType_MOD_CONV" where <machine_type> is chwProduct.machineType
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
						, "Machine Type Conversions " + chwModel.getMACHTYPE()	//String chdescr	Set to "Machine Type Conversions <machine_type>" 				
						);
				this.addRfcName(ChwCharMaintain);
				
				//9.B Create an array variable <FromModelToModel> in the SQL
	            //9.c For each entry in the array <FromModelToModel>, call the ChwCharMaintain.addValue() method to add the value
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
				this.addRfcResult(ChwClassMaintain);
				

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
			}
			
			
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
	
	private List<String> getFromModelToModel(String sql,String type,String pdhdomain) throws SQLException {
		List<String> fromModelToModelList = new ArrayList<String>();
		Object[] params = new String[4]; 
		params[0] =type;
		params[1] =pdhdomain;	
		params[2] =type;
		params[3] =pdhdomain;
		String realSql = CommonUtils.getPreparedSQL(sql, params);
		this.addDebug("querySql=" + realSql);
		
		PreparedStatement statement = rdhConnection.prepareStatement(sql);
		statement.setString(1, type);
		statement.setString(2, pdhdomain);
		statement.setString(3, type);
		statement.setString(4, pdhdomain);
		ResultSet resultSet = statement.executeQuery();
		while(resultSet.next()){
			fromModelToModelList.add(resultSet.getString("FromModelToModel"));
		}		
		return fromModelToModelList;
	}
	
	public boolean exist(String sql,String type,String pdhdomain) {
    	boolean flag = false;
    	try {
			Connection connection = rdhConnection;
			Object[] params = new String[2]; 
			params[0] =type;
			params[1] =pdhdomain;
			String realSql = CommonUtils.getPreparedSQL(sql, params);
			this.addDebug("querySql=" + realSql);
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, type);
			statement.setString(2, pdhdomain);
			
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int count = resultSet.getInt(1);
				flag = count>0 ? true: false;				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}    	
    	return flag;
    	
    }
	

	public static void main(String[] args) {
		MODEL model = new MODEL();
		model.setMACHTYPE("UTC");
		model.setMODEL("MOD_NAME");
//		MODELIERPABRSTATUSCaller MODELIERPABRSTATUSCaller = new MODELIERPABRSTATUSCaller(model);
//		MODELIERPABRSTATUSCaller.excute();
		

	}

}
