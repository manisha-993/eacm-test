package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.CharacterIterator;
import java.text.MessageFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import COM.ibm.eannounce.abr.sg.rfc.Chw001ClfCreate;
import COM.ibm.eannounce.abr.sg.rfc.ChwBomCreate;
import COM.ibm.eannounce.abr.sg.rfc.ChwBomMaintain;
import COM.ibm.eannounce.abr.sg.rfc.ChwCharMaintain;
import COM.ibm.eannounce.abr.sg.rfc.ChwClassMaintain;
import COM.ibm.eannounce.abr.sg.rfc.ChwConpMaintain;
import COM.ibm.eannounce.abr.sg.rfc.ChwDepdMaintain;
import COM.ibm.eannounce.abr.sg.rfc.ChwFCTYMDMFCMaint;
import COM.ibm.eannounce.abr.sg.rfc.ChwMatmCreate;
import COM.ibm.eannounce.abr.sg.rfc.ChwReadSalesBom;
import COM.ibm.eannounce.abr.sg.rfc.CommonUtils;
import COM.ibm.eannounce.abr.sg.rfc.FCTRANSACTION;
import COM.ibm.eannounce.abr.sg.rfc.MODEL;
import COM.ibm.eannounce.abr.sg.rfc.MODELCONVERT;
import COM.ibm.eannounce.abr.sg.rfc.RdhBase;
import COM.ibm.eannounce.abr.sg.rfc.RdhClassificationMaint;
import COM.ibm.eannounce.abr.sg.rfc.TMF_UPDATE;
import COM.ibm.eannounce.abr.sg.rfc.UpdateParkStatus;
import COM.ibm.eannounce.abr.sg.rfc.XMLParse;
import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.abr.util.RFCConfig;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;

import com.ibm.transform.oim.eacm.util.PokUtils;

public class FCTRANSACTIONIERPABRSTATUS extends PokBaseABR {
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);
	private int abr_debuglvl = D.EBUG_ERR;
	private String navName = "";
	private Hashtable metaTbl = new Hashtable();
	private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'FCTRANSACTION' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
		
	private String COVNOTEQUALSQL = "SELECT count(*) FROM OPICM.flag F\n"
			+ " INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP "
			+ " INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE = ? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP "
			+ " INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP "
			+ " INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP "
			+ " WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS') "
			+ " AND T1.ATTRIBUTEVALUE !=T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION= ? WITH UR";
	
	private String COVEQUALSQL = "SELECT count(*) FROM OPICM.flag F\n"
			+ " INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT TIMESTAMP AND T1.EFFTO > CURRENT TIMESTAMP "
			+ " INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE = ? and T2.VALTO > CURRENT TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP "
			+ " INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT TIMESTAMP "
			+ " INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT TIMESTAMP AND M.EFFTO > CURRENT TIMESTAMP "
			+ " WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS') "
			+ " AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION= ? WITH UR";

	private String FCTEQUALSQL= "SELECT count(*) FROM OPICM.flag F\n"
			+ " INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT TIMESTAMP "
			+ " INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE = ? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP "
			+ " INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP "
			+ " INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP "
			+ " WHERE f.ENTITYTYPE ='FCTRANSACTION' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'FCTRANSACTIONIERPABRSTATUS') "
			+ " AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION= ? WITH UR";
	
	private String MODEL_MACHTYPE = "SELECT DISTINCT t2.ATTRIBUTEVALUE AS MODEL, t3.ATTRIBUTEVALUE AS INVNAME FROM OPICM.flag F "
			+ " INNER JOIN opicm.flag t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='MACHTYPEATR' AND T1.ATTRIBUTEVALUE = ? AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP"
			+ " INNER JOIN opicm.text t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='MODELATR' AND t2.NLSID=1 AND T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP"
			+ " INNER JOIN opicm.text t3 ON f.ENTITYID =t3.ENTITYID AND f.ENTITYTYPE =t3.ENTITYTYPE AND t3.ATTRIBUTECODE ='INVNAME' AND t3.NLSID =1 AND T3.VALTO > CURRENT  TIMESTAMP AND T3.EFFTO > CURRENT  TIMESTAMP"
			+ " INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t1.ENTITYID AND F1.ENTITYTYPE =t1.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP"
			+ " INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP"
			+ " WHERE f.ENTITYTYPE ='MODEL' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELTIERPABRSTATUS') "
			+ " AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=? "
			+ " WITH ur";
	
	String xml = null;

	

	public String getDescription() {
		return "FCTRANSACTIONIERPABRSTATUS";
	}

	public String getABRVersion() {
		return "1.0";
	}

	public void execute_run() {
		String HEADER = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE
				+ EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">"
				+ EACustom.getMastheadDiv() + NEWLINE
				+ "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
		String HEADER2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE
				+ "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>"
				+ NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE
				+ "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->"
				+ NEWLINE;

		String header1 = "";

		MessageFormat msgf;
		String abrversion = "";
		String rootDesc = "";

		Object[] args = new String[10];

		try {
			msgf = new MessageFormat(HEADER);
			args[0] = getShortClassName(getClass());
			args[1] = "ABR";
			header1 = msgf.format(args);
			setDGTitle("FCTRANSACTIONIERPABRSTATUS report");
			setDGString(getABRReturnCode());
			setDGRptName("FCTRANSACTIONIERPABRSTATUS"); // Set the report name
			setDGRptClass("FCTRANSACTIONIERPABRSTATUS"); // Set the report class
			// Default set to pass
			setReturnCode(PASS);

			start_ABRBuild(false); // pull the VE

			abr_debuglvl = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties
					.getABRDebugLevel(m_abri.getABRCode());

			// get the root entity using current timestamp, need this to get the
			// timestamps or info for VE pulls
			  m_elist = m_db.getEntityList(m_prof,
                    new ExtractActionItem(null, m_db, m_prof,"dummy"),
                    new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });
			/*
			 * m_db.getEntityList(m_prof, new ExtractActionItem(null, m_db,
			 * m_prof,"dummy"), new EntityItem[] { new EntityItem(null, m_prof,
			 * getEntityType(), getEntityID()) });
			 */

			EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
			addDebug("*****mlm rootEntity = " + rootEntity.getEntityType() + rootEntity.getEntityID());
			// NAME is navigate attributes - only used if error rpt is generated
			navName = getNavigationName();
			rootDesc = m_elist.getParentEntityGroup().getLongDescription();
			addDebug("navName=" + navName);
			addDebug("rootDesc" + rootDesc);
			// build the text file
			String empty = "";
			Connection connection = m_db.getODSConnection();
			PreparedStatement statement = connection.prepareStatement(CACEHSQL);
			statement.setInt(1, rootEntity.getEntityID());
			ResultSet resultSet = statement.executeQuery();
		
			while (resultSet.next()) {
				xml = resultSet.getString("XMLMESSAGE");
			}
			if (xml != null) {
			
				FCTRANSACTION fctransaction = XMLParse.getObjectFromXml(xml,FCTRANSACTION.class);
				//If <entityType> = "FCTRANSACTION", then set chwProduct to the XML of MODEL where MODEL/MACHTYPE = FCTRANSACTION/TOMACHTYPE and MODEL/MODEL = FCTRANSACTION/TOMODEL
				/**
				 * 
					select XMLMESSAGE from cache.XMLIDLCACHE 
					 where XMLCACHEVALIDTO > current timestamp 
					 and  XMLENTITYTYPE = 'MODEL'
					 and xmlexists('declare default element namespace "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/MODEL_UPDATE"; $i/MODEL_UPDATE[MACHTYPE/text() = "7954" and MODEL/text() ="24X"]' passing cache.XMLIDLCACHE.XMLMESSAGE as "i") 
					 with ur;
				 */
				String modelxml = getModelFromXML(fctransaction.getTOMACHTYPE(),fctransaction.getTOMODEL(),connection);
				if("".equals(modelxml)) {
					addOutput("modelxml is empty");	
					return;				
				}
				MODEL chwMODEL =  XMLParse.getObjectFromXml(modelxml,MODEL.class);				
				if(chwMODEL==null) {
					addOutput("MODEL is Null");	
					return;	
				}			
				String obj_id = fctransaction.getTOMACHTYPE() + "UPG";
				String rfaNum = fctransaction.getFROMMACHTYPE()+fctransaction.getFROMFEATURECODE()+fctransaction.getTOMACHTYPE()+fctransaction.getTOFEATURECODE();
				//1. Call ChwMatmCreate to create the material master for the product object. 
				addDebug("FCTRANSACTION ChwMatmCreate ");	
				ChwMatmCreate chwMatmCreate = new ChwMatmCreate(chwMODEL,"ZMAT",obj_id,rfaNum);
				this.runRfcCaller(chwMatmCreate);
				//2. Call Chw001ClfCreate to create the standard 001 classifications and characteristics which are tied to the offering's material master record.
				Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(chwMODEL,"ZMAT",obj_id,rfaNum, connection);
				this.addDebug("Calling " + "Chw001ClfCreate");
				try{
					chw001ClfCreate.execute();
					this.addMsg(chw001ClfCreate.getRptSb());
				}catch (Exception e) {
					this.addMsg(chw001ClfCreate.getRptSb());
					throw e;
				}
				//3.a Call the ChwCharMaintain constructor to create the MK_T_machineType_MOD characteristic.
				ChwCharMaintain chwCharMaintain = 
				new ChwCharMaintain(rfaNum  //String obj_id Set to concatenation of chwProduct.machineType + "UPG"
									,"MK_T_"+fctransaction.getTOMACHTYPE()+"_MOD" //String charact  Set to  "MK_T_<machine_type>_MOD"
									, "CHAR" 			//String datatype
									, 6 				//int charnumber
									, empty				//String decplaces
									, empty				//String casesens
									, empty				//String neg_vals
									, empty				//String group
									, "S"				//String valassignm  Set to "-".
									, empty				//String no_entry
									, empty				//String no_display
									, "X" 				//String addit_vals Set to "X".
									, fctransaction.getTOMACHTYPE() +" Model Characteristic" //String chdescr  Set to "<machine_type> Model Characteristic" where <machine_type> is chwProduct.machineType
									);
				//3.b For each MODEL with MODEL/MACHTYPE = FCTRANSACTION/TOMACHTYPE, 
				List<Map<String,String>> MODEL_MACHTYPE_LIST = this.getFromModelToModel(MODEL_MACHTYPE, fctransaction.getTOMACHTYPE(),chwMODEL.getPDHDOMAIN(),m_db.getPDHConnection());
				String value = "";
				String value_descr = "";
				for(Map<String,String> map : MODEL_MACHTYPE_LIST){					
					value = map.get("MODEL");				
					value_descr = CommonUtils.getFirstSubString(map.get("INVNAME"),25) + " " + map.get("MODEL");
					chwCharMaintain.addValue(value, value_descr);
				}	
				this.runRfcCaller(chwCharMaintain);
				
				//3.c Call the ChwClassMaintain constructor to create the MK_machineType_MOD class.
				ChwClassMaintain ChwClassMaintain = 
				new ChwClassMaintain(
						rfaNum 								//String obj_id Set to concatenation of chwProduct.machineType + "MTC"
						, "MK_"+fctransaction.getTOMACHTYPE()+"_MOD"  //String class_name   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType
						, "MK_"+fctransaction.getTOMACHTYPE()+"_MOD"  //String class_type   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType.
						);
				//3.d Call the ChwClassMaintain.addCharacteristic() method to add the MK_T_machineType_MOD characteristic to the MK_machineType_MOD characteristic class. 
				ChwClassMaintain.addCharacteristic("MK_T_"+fctransaction.getTOMACHTYPE()+"_MOD"); 
				this.runRfcCaller(ChwClassMaintain);
				
				//3.e Call the TssClassificationMaint constructor to associate the MK_machineType_MOD class to the product's material master record.
				RdhClassificationMaint TssClassificationMaint = 
				new RdhClassificationMaint(
						obj_id 								//String obj_id Set to concatenation of chwProduct.machineType + "MTC"
						, "MK_"+fctransaction.getTOMACHTYPE()+"_MOD"  //String class_name   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType
						, "300"  							//String class_type   Set to "300"
						, "H"
						, rfaNum);
				this.runRfcCaller(TssClassificationMaint);
				
				
				if(!"***".equals(fctransaction.getTOMODEL())){
					//4.a1 Call the ChwCharMaintain constructor to create the MK_D_machineType_MOD_CONV characteristic.
					ChwCharMaintain ChwCharMaintain = 
					new ChwCharMaintain(
							rfaNum				 				//String obj_id  Set to concatenation of chwProduct.machineType + "MTC"
							, "MK_D_"+fctransaction.getTOMACHTYPE()+"_MOD_CONV"	//String charact  Set to  "MK_machineType_MTC" where <machine_type> is chwProduct.machineType
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
							, fctransaction.getTOMACHTYPE() +" Features Conversion"	//String chdescr	Set to "Machine Type Conversions <machine_type>" 				
							);
					//4.a2 Call the ChwCharMaintain.addValue() method to add the value with its description to the MK_D_machineType_MOD_CONV characteristic.
					value = fctransaction.getFROMMODEL() + "_" + fctransaction.getTOMODEL();
					//Set to "From <MODELCONVERT/TOMACHTYPE> Model <MODELCONVERT/FROMMODEL> to <MODELCONVERT/TOMODEL>"
					String valdescr = "From " + fctransaction.getTOMACHTYPE() + " Model " + fctransaction.getFROMMODEL() + " to " + fctransaction.getTOMODEL();
					ChwCharMaintain.addValue(value, valdescr);
					this.runRfcCaller(ChwCharMaintain);
				}
				
				
				
				//4.b Call the ChwClassMaintain constructor to create the MK_D_machineType_MOD_CONV class. 
				ChwClassMaintain =
				new ChwClassMaintain(
						rfaNum 								//String obj_id Set to concatenation of chwProduct.machineType + "UPG"
						, "MK_D_"+fctransaction.getTOMACHTYPE()+"_MOD_CONV"  //String class_name   Set to  "MK_D_<machine_type>_MOD_CONV" where <machine_type> is chwProduct.machineType
						, "MK_D_"+fctransaction.getTOMACHTYPE()+"_MOD_CONV"  //String class_type   Set to  "MK_D_<machine_type>_MOD_CONV" where <machine_type> is chwProduct.machineType.
						);
				//4.c Call the ChwClassMaintain.addCharacteristic() method to add the MK_T_machineType_MOD characteristic to the MK_machineType_MOD characteristic class.
				ChwClassMaintain.addCharacteristic("MK_D_"+fctransaction.getTOMACHTYPE()+"_MOD_CONV");
				this.runRfcCaller(ChwClassMaintain);
				
				//4.d Call the TssClassificationMaint constructor to associate the MK_D_machineType_MOD_CONV class to the product's material master record
				TssClassificationMaint = 
				new RdhClassificationMaint(
						obj_id 								//String obj_id Set to concatenation of chwProduct.machineType + "UPG"
						, "MK_D_"+fctransaction.getTOMACHTYPE()+"_MOD_CONV"  //String class_name   Set to  "MK_D_<machine_type>_MOD_CONV" where <machine_type> is chwProduct.machineType
						, "300"  							//String class_type   Set to "300"
						, "H"
						, rfaNum);
				this.runRfcCaller(TssClassificationMaint);
				
				//5. Call the TssClassificationMaint constructor to associate the MK_REFERENCE class to the product's material master record.
				RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(obj_id,"MK_REFERENCE","300","H", rfaNum);
				this.runRfcCaller(rdhClassificationMaint);
				
				//6. Call the TssClassificationMaint constructor to associate the MK_T_VAO_NEW class to the product's material master record. 
				rdhClassificationMaint = new RdhClassificationMaint(obj_id,"MK_T_VAO_NEW","300","H", rfaNum);
				this.runRfcCaller(rdhClassificationMaint);
				
				//7. Call the TssClassificationMaint constructor to associate the MK_D_VAO_NEW class to the product's material master record. 
				rdhClassificationMaint = new RdhClassificationMaint(obj_id,"MK_D_VAO_NEW","300","H", rfaNum);
				this.runRfcCaller(rdhClassificationMaint);
				
				//8.Call the TssClassificationMaint constructor to associate the MK_FC_EXCH class to the product's material master record.
				rdhClassificationMaint = new RdhClassificationMaint(obj_id,"MK_FC_EXCH","300","H", rfaNum);
				this.runRfcCaller(rdhClassificationMaint);
				
				//9.Call the TssClassificationMaint constructor to associate the MK_FC_CONV class to the product's material master record.
				rdhClassificationMaint = new RdhClassificationMaint(obj_id,"MK_FC_CONV","300","H", rfaNum);
				this.runRfcCaller(rdhClassificationMaint);
				//10 For each MODEL with MODEL/MACHTYPE = MODELCONVERT/TOMACHTYPE
				for(Map<String,String> map : MODEL_MACHTYPE_LIST){
					String obj_id_depd= fctransaction.getTOMACHTYPE()+"UPG";
					//Set to "SC_<MODEL/MACHTYPE>_MOD_<MODEL/MODEL>"
					String dep_extern="SC_"+fctransaction.getTOMACHTYPE()+"_MOD_" + map.get("MODEL");
					String dep_type="5"; 
					//Set to "SC_<MODEL/MACHTYPE>_MOD_<MODEL/MODEL>".
					String descript="SC_"+fctransaction.getTOMACHTYPE()+"_MOD_" + map.get("MODEL");
					
					ChwDepdMaintain chwDepdCaller	=new ChwDepdMaintain(rfaNum, dep_extern, dep_type, descript);
					
					String sourceLine = "$PARENT.MK_T_"+fctransaction.getTOMACHTYPE() +"_MOD='"+map.get("MODEL")+"'";				
					chwDepdCaller.addSourceLineCondition(sourceLine);
					runRfcCaller(chwDepdCaller);
				}
				//11. Call the ChwConpMaintain to create a configuration profile for the product's material master record.
				//11.a 
				ChwConpMaintain ChwConpMaintain  = 	new ChwConpMaintain(
						obj_id 				//String obj_id  Set to concatenation of chwProduct.machineType + "UPG"
						, "INITIAL"         //String c_profile Set to "INITIAL".
						, "SD01"			//String bomappl Set to "SD01".
						, "2"				//String bomexpl Set to "2".
						, fctransaction.getTOMACHTYPE() + "UPGUI"		//String design	 Set to Set to concatenation of chwProduct.machineType + "MTCUI" 
						, rfaNum);
				//11.b Call the ChwConpMaintain.addConfigDependency() method.
				ChwConpMaintain.addConfigDependency("E2E", empty); //Set to "E2E".
				//11.c 
				ChwConpMaintain.addConfigDependency("PR_"+fctransaction.getTOMACHTYPE()+"_SET_MODEL", empty);  //Set to "PR_<chwProduct.machineType>_SET_MODEL"
				//11.d
				ChwConpMaintain.addConfigDependency("PR_E2E_SET_MTM", empty);  //Set to "PR_E2E_SET_MTM".
				//11.e 
				ChwConpMaintain.addConfigDependency("PR_E2E_PRICING_HW", empty);  //Set to "PR_E2E_PRICING_HW".
				//11.f 
				ChwConpMaintain.addConfigDependency("PR_E2E_CSTIC_HIDING_HW", empty);  //Set to "PR_E2E_PRICING_HW".
				this.runRfcCaller(ChwConpMaintain);
				
				//12. 
				/*String tmf_xml = getTMFFromXML(fctransaction.getTOMACHTYPE(),fctransaction.getTOMODEL(),fctransaction.getTOFEATURECODE(), connection);
				if("".equals(tmf_xml)) {
					addOutput("tmf_xml is Null, not Call ChwFCTYMDMFCMaint");	
					ChwFCTYMDMFCMaint caller = new ChwFCTYMDMFCMaint(null,fctransaction);
					this.runRfcCaller(caller);
				}else{
					addDebug("tmf_xml is NOT Null");
					TMF_UPDATE tmf = XMLParse.getObjectFromXml(tmf_xml,TMF_UPDATE.class);
					ChwFCTYMDMFCMaint caller = new ChwFCTYMDMFCMaint(tmf,fctransaction);
					this.runRfcCaller(caller);
				}	*/			

				//13.
				if(fctransaction.getTOMACHTYPE().equals(fctransaction.getFROMMACHTYPE())) {
					List<MODEL> models = getMODEL(fctransaction.getTOMACHTYPE(),fctransaction.getPDHDOMAIN());
					Set<String> plnts = RFCConfig.getBHPlnts();
					this.addOutput("Start Bom Processing!");
					updateSalesBom(fctransaction,plnts,models);
					this.addOutput("Bom Processing Finished!");
				}
				
				// Call UpdateParkStatus
				UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", fctransaction.getTOMACHTYPE()+"UPG", rfaNum);
				this.addDebug("Calling "+updateParkStatus.getRFCName());
				updateParkStatus.execute();
				this.addDebug(updateParkStatus.createLogEntry());
				if (updateParkStatus.getRfcrc() == 0) {
					this.addOutput("Parking records updated successfully for ZDMRELNUM="+fctransaction.getTOMACHTYPE()+"UPG");
				} else {
					this.addOutput(updateParkStatus.getRFCName() + " called faild!");
					this.addOutput(updateParkStatus.getError_text());
				}
			}	
			
			
			// exeFtpShell(ffPathName);
			// ftpFile();
			/*
			 * } catch (Exception e) { 
			 * e.printStackTrace(); this.addError(e.getMessage()); setReturnCode(FAIL); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			// println(e.toString());
			setReturnCode(FAIL);
			java.io.StringWriter exBuf = new java.io.StringWriter();
			String Error_EXCEPTION = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
			String Error_STACKTRACE = "<pre>{0}</pre>";
			msgf = new MessageFormat(Error_EXCEPTION);
			setReturnCode(INTERNAL_ERROR);
			e.printStackTrace(new java.io.PrintWriter(exBuf));
			// Put exception into document
			args[0] = e.getMessage();
			rptSb.append(convertToTag(msgf.format(args)) + NEWLINE);
			msgf = new MessageFormat(Error_STACKTRACE);
			args[0] = exBuf.getBuffer().toString();
			rptSb.append(convertToTag(msgf.format(args)) + NEWLINE);
			logError("Exception: " + e.getMessage());
			logError(exBuf.getBuffer().toString());
			// was an error make sure user gets report
			setCreateDGEntity(true);
			
			// sentFile=exeFtpShell(ffPathName);
		} finally {
			StringBuffer sb = new StringBuffer();
			msgf = new MessageFormat(HEADER2);
			args[0] = m_prof.getOPName();
			args[1] = m_prof.getRoleDescription();
			args[2] = m_prof.getWGName();
			args[3] = getNow();
			args[4] = sb.toString();
			args[5] = abrversion + " " + getABRVersion();
			rptSb.insert(0, convertToHTML(xml)+NEW_LINE);
			rptSb.insert(0, header1 + msgf.format(args) + NEWLINE);

			println(EACustom.getDocTypeHtml()); // Output the doctype and html
			println(rptSb.toString()); // Output the Report
			printDGSubmitString();
			 if(!isReadOnly()) {
	                clearSoftLock();
	            }
			println(EACustom.getTOUDiv());
			buildReportFooter(); // Print </html>
		}
	}

	

	private List<MODEL> getMODEL(String toMachtype, String domain) {
		List<MODEL> models= new ArrayList();
		
		try {
			String sql1 = "SELECT distinct t2.ATTRIBUTEVALUE as MACHTYPEATR,substr(T1.ATTRIBUTEVALUE,1,3) as MODELATR FROM OPICM.flag F "
					+ "INNER JOIN OPICM.FLAG t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='MACHTYPEATR' AND T2.ATTRIBUTEVALUE = ? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP "
					+ "INNER JOIN OPICM.text t1 ON t1.ENTITYID =t2.ENTITYID AND t1.ENTITYTYPE =t2.ENTITYTYPE AND t1.ATTRIBUTECODE ='MODELATR' and T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP AND T1.NLSID=1 "
					+ "INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP "
					+ "INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP "
					+ "WHERE f.ENTITYTYPE ='MODEL' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELIERPABRSTATUS') AND F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION= ? WITH UR";
			Connection connection1 = m_db.getPDHConnection();
			PreparedStatement statement1 = connection1.prepareStatement(sql1);
			statement1.setString(1, toMachtype);
			statement1.setString(2, domain);
			ResultSet resultSet1 = statement1.executeQuery();
			this.addDebug("GetMODEL SQL:"+ sql1);
			while (resultSet1.next()) {
				MODEL model = new MODEL();
				model.setMACHTYPE(resultSet1.getString("MACHTYPEATR").trim());
				model.setMODEL(resultSet1.getString("MODELATR").trim());
				models.add(model);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return models;
	}
	

	public void updateSalesBom(FCTRANSACTION fctransaction, Set<String> plnts, List<MODEL> models) throws Exception {
		for(String plant : plnts) {
			//call ChwBomCreate 
			ChwBomCreate chwBomCreate = new ChwBomCreate(fctransaction.getTOMACHTYPE()+"UPG", plant);
			this.addDebug("Calling " + "ChwBomCreate");
			this.addDebug(chwBomCreate.generateJson());
			try{
				chwBomCreate.execute();
				this.addDebug(chwBomCreate.createLogEntry());
			}catch(Exception e) {
				this.addOutput(e.getMessage());
				continue;	
			}
			// start lock
			String fileName = "./locks/FCTRANSACTION" + fctransaction.getTOMACHTYPE() + "UPG" + plant + ".lock";
			File file = new File(fileName);
			new File(file.getParent()).mkdirs();
			try (FileOutputStream fos = new FileOutputStream(file); FileChannel fileChannel = fos.getChannel()) {
				while (true) {
					try {
						FileLock fileLock = fileChannel.tryLock();
						if (fileLock != null) {
							this.addDebug("Start lock, lock file " + fileName);
							// lock content
							//call ChwReadSalesBom
							ChwReadSalesBom chwReadSalesBom = new ChwReadSalesBom(fctransaction.getTOMACHTYPE()+"UPG", plant);
							this.addDebug("Calling " + "ChwReadSalesBom");
							this.addDebug(chwReadSalesBom.generateJson());
							try{
								chwReadSalesBom.execute();
								this.addDebug(chwReadSalesBom.createLogEntry());
							}catch(Exception e) {
								if(e.getMessage().contains("exists in Mast table but not defined to Stpo table")){

								} else{
									this.addOutput(e.getMessage());
									break;
								}
							}
							this.addDebug("Bom Read result:"+chwReadSalesBom.getRETURN_MULTIPLE_OBJ().toString());
							List<HashMap<String, String>> componmentList = chwReadSalesBom.getRETURN_MULTIPLE_OBJ().get("stpo_api02");
							if (componmentList != null && componmentList.size() > 0) {
								String POSNR = getMaxItemNo(componmentList);
								for(MODEL model : models) {
									String componment = model.getMACHTYPE() + model.getMODEL();
									if (hasMatchComponent(componmentList, componment)) {
										this.addDebug("updateSalesBom exist component " + componment);
									}else {
										POSNR=generateItemNumberString(POSNR);
										//call ChwBomMaintain
										ChwBomMaintain chwBomMaintain = new ChwBomMaintain(model.getMACHTYPE()+"UPG", plant, model.getMACHTYPE()+model.getMODEL(),POSNR,"SC_"+model.getMACHTYPE()+"_MOD_"+model.getMODEL());
										this.addDebug("Calling " + "chwBomMaintain");
										this.addDebug(chwBomMaintain.generateJson());
										try {
											chwBomMaintain.execute();
											this.addDebug(chwBomMaintain.createLogEntry());
										}catch(Exception e) {
											this.addOutput(e.getMessage());
											POSNR = getMaxItemNo(componmentList);
											continue;
										}
									}
								}
							}else {
								String POSNR ="0005";
								//call ChwBomMaintain
								for(MODEL model : models) {
									//call ChwBomMaintain
									ChwBomMaintain chwBomMaintain = new ChwBomMaintain(model.getMACHTYPE()+"UPG", plant, model.getMACHTYPE()+model.getMODEL(),POSNR,"SC_"+model.getMACHTYPE()+"_MOD_"+model.getMODEL());
									this.addDebug("Calling " + "chwBomMaintain");
									this.addDebug(chwBomMaintain.generateJson());
									try {
										chwBomMaintain.execute();
										this.addDebug(chwBomMaintain.createLogEntry());
									}catch(Exception e) {
										this.addOutput(e.getMessage());
										continue;
									}
									POSNR=generateItemNumberString(POSNR);
								}
							}
							// end lock content
							break;
						} else {
							this.addDebug("fileLock == null");
							Thread.sleep(5000);
						}
					} catch (OverlappingFileLockException e1) {
						this.addDebug("other abr is running createSalesBOMforType" + "UPG");
						Thread.sleep(5000);
					}
				}
			}
			// end lock
		}
		
	}
	
	private String getMaxItemNo(List<HashMap<String, String>> componmentList) {
		List<Integer> itemNo = new ArrayList<Integer>();
		for (int i = 0; i < componmentList.size(); i++) {
			String rev = componmentList.get(i).get("ITEM_NO");
			itemNo.add(Integer.parseInt(rev));
		}
		int maxItemNo = Collections.max(itemNo);
		return String.format("%04d", maxItemNo);
	}

	private String generateItemNumberString(String posnr) {
		int tempValue = Integer.parseInt(posnr)+5;
		return String.format("%04d", tempValue);
	}

	private boolean hasMatchComponent(List<HashMap<String, String>> bom, String componment){
		for (int i = 0; i < bom.size(); i++) {
			String rev = bom.get(i).get("COMPONENT");
			if (rev.trim().equals(componment)){				
			  return true;
			}
		}
		return false;
	}
	
	/*
	 * Get Name based on navigation attributes for root entity
	 *
	 * @return java.lang.String
	 */
	private String getNavigationName() throws java.sql.SQLException, MiddlewareException {
		return getNavigationName(m_elist.getParentEntityGroup().getEntityItem(0));
	}

	/**********************************************************************************
	 * Get Name based on navigation attributes for specified entity
	 *
	 * @return java.lang.String
	 */ 
	private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException {
		StringBuffer navName = new StringBuffer();
		// NAME is navigate attributes
		// check hashtable to see if we already got this meta
		EANList metaList = (EANList) metaTbl.get(theItem.getEntityType());
		if (metaList == null) {
			EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
			metaList = eg.getMetaAttribute(); // iterator does not maintain
												// navigate order
			metaTbl.put(theItem.getEntityType(), metaList);
		}
		for (int ii = 0; ii < metaList.size(); ii++) {
			EANMetaAttribute ma = (EANMetaAttribute) metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(), ", ", "", false));
			if (ii + 1 < metaList.size()) {
				navName.append(" ");
			}
		}
		return navName.toString();
	}
	
	 /********************************************************************************
     * Convert string into valid html.  Special HTML characters are converted.
     *
     * @param txt    String to convert
     * @return String
     */
    protected static String convertToHTML(String txt)
    {
        String retVal="";
        StringBuffer htmlSB = new StringBuffer();
        StringCharacterIterator sci = null;
        char ch = ' ';
        if (txt != null) {
            sci = new StringCharacterIterator(txt);
            ch = sci.first();
            while(ch != CharacterIterator.DONE)
            {
                switch(ch)
                {
                case '<':
                    htmlSB.append("&lt;");
                break;
                case '>':
                    htmlSB.append("&gt;");
                    break;
                case '"':
                    // double quotation marks could be saved as &quot; also. this will be &#34;
                    // this should be included too, but left out to be consistent with west coast
                    htmlSB.append("&quot;");
                    break;
                case '\'':
                    //IE6 doesn't support &apos; to convert single quotation marks,we can use &#39; instead
                    htmlSB.append("&#"+((int)ch)+";");
                    break;
                    //case '&': 
                    // ignore entity references such as &lt; if user typed it, user will see it
                    // could be saved as &amp; also. this will be &#38;
                    //htmlSB.append("&#"+((int)ch)+";");
                    //  htmlSB.append("&amp;");
                    //    break;
                default:
                    htmlSB.append(ch);
                break;
                }
                ch = sci.next();
            }
            retVal = htmlSB.toString();
        }

        return retVal;
    }
    
    /********************************************************************************
     * Convert string into valid html.  Special HTML characters are converted.
     *
     * @param txt    String to convert
     * @return String
     */
    protected static String convertToTag(String txt)
    {
        String retVal="";
        StringBuffer htmlSB = new StringBuffer();
        StringCharacterIterator sci = null;
        char ch = ' ';
        if (txt != null) {
            sci = new StringCharacterIterator(txt);
            ch = sci.first();
            while(ch != CharacterIterator.DONE)
            {
                switch(ch)
                {
                case '<':
                    htmlSB.append("&lt;");
                break;
                case '>':
                    htmlSB.append("&gt;");
                    break;
                default:
                    htmlSB.append(ch);
                break;
                }
                ch = sci.next();
            }
            retVal = htmlSB.toString();
        }

        return retVal;
    }
    
	 protected void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}
	 
	 protected void addMsg(StringBuffer msg) { rptSb.append(msg.toString()+NEWLINE);}


		/**********************************
	     * add debug info as html comment
	     *    EBUG_ERR = 0;
	          EBUG_WARN = 1;
	          EBUG_INFO = 2;
	          EBUG_DETAIL = 3;
	          EBUG_SPEW = 4
	     */
	   
	protected void addDebug(String msg) {
		if (D.EBUG_DETAIL <= abr_debuglvl) {
		rptSb.append("<!-- " + msg + " -->" + NEWLINE);
		}
	}
	 /**********************************
     * add error info and fail abr
     */
    protected void addError(String msg) {
        addOutput(msg);
        setReturnCode(FAIL);
    }
    
    protected void runRfcCaller(RdhBase caller) throws Exception {
		this.addDebug("Calling " + caller.getRFCName());
		caller.execute();
		this.addDebug(caller.createLogEntry());
		if (caller.getRfcrc() == 0) {
			this.addOutput(caller.getRFCName() + " called successfully!");
		} else {
			this.addOutput(caller.getRFCName() + " called  faild!");
			this.addOutput(caller.getError_text());
		}
	}
    
    private List<Map<String,String>> getFromModelToModel(String sql,String type,String pdhdomain, Connection rdhConnection) throws SQLException {
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
    
    private String getModelFromXML(String TOMACHTYPE, String TOMODEL,Connection odsConnection) throws SQLException {
		/**
		 * 
		 * select XMLMESSAGE from cache.XMLIDLCACHE 
         * where XMLCACHEVALIDTO > current timestamp 
         * and  XMLENTITYTYPE = 'MODEL'
         * and xmlexists('declare default element namespace "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/MODEL_UPDATE"; $i/MODEL_UPDATE[MACHTYPE/text() = "7954" and MODEL/text() ="24X"]' passing cache.XMLIDLCACHE.XMLMESSAGE as "i") 
         * with ur;
		 */
    	String cacheSql = "select XMLMESSAGE,XMLENTITYID from cache.XMLIDLCACHE "
    			+ " where XMLCACHEVALIDTO > current timestamp "
    			+ " and  XMLENTITYTYPE = 'MODEL'"
    			+ " and xmlexists('declare default element namespace \"http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/MODEL_UPDATE\"; "
    			+ " $i/MODEL_UPDATE[MACHTYPE/text() = \""+TOMACHTYPE+"\" and MODEL/text() =\""+TOMODEL+"\"]' passing cache.XMLIDLCACHE.XMLMESSAGE as \"i\")" 
                + " FETCH FIRST 1 ROWS ONLY with ur";	
    	addDebug("cacheSql=" + cacheSql);
		PreparedStatement statement = odsConnection.prepareStatement(cacheSql);
		ResultSet resultSet = statement.executeQuery();
		String xml = "";
		String xmlentityid ="";
		if (resultSet.next()) {
			xml = resultSet.getString("XMLMESSAGE");
			xmlentityid = resultSet.getString("XMLENTITYID");
			addDebug("getModelFromXML for MODEL TOMACHTYPE="+TOMACHTYPE+" and TOMODEL="+TOMODEL+" and XMLENTITYID=" + xmlentityid);		
		}else{
			String TOMACHTYPE_Sql = "select XMLMESSAGE,XMLENTITYID from cache.XMLIDLCACHE "
	    			+ " where XMLCACHEVALIDTO > current timestamp "
	    			+ " and  XMLENTITYTYPE = 'MODEL'"
	    			+ " and xmlexists('declare default element namespace \"http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/MODEL_UPDATE\"; "
	    			+ " $i/MODEL_UPDATE[MACHTYPE/text() = \""+TOMACHTYPE+"\"]' passing cache.XMLIDLCACHE.XMLMESSAGE as \"i\")" 
	                + " FETCH FIRST 1 ROWS ONLY with ur";
			PreparedStatement statement2 = odsConnection.prepareStatement(TOMACHTYPE_Sql);
			ResultSet resultSet2 = statement2.executeQuery();
			if (resultSet2.next()) {
				xml = resultSet2.getString("XMLMESSAGE");
				xmlentityid = resultSet2.getString("XMLENTITYID");
				addDebug("getModelFromXML for MODEL only TOMACHTYPE=" + TOMACHTYPE +" and XMLENTITYID=" + xmlentityid);		
			}else{
				addDebug("getModelFromXML for MODEL no XML");
			}			
			
		}
		return xml;
	}
    /**
     * TMF. Search for TMF where FCTRANSACTION.TOMACHTYPE = TMF.MACHTYPE 
     *                       and FCTRANSACTION.TOMODEL= TMF.MODEL 
     *                       and FCTRANSACTION.TOFEATURECODE = TMF.FEATURECODE.
     */
    private String getTMFFromXML(String TOMACHTYPE, String TOMODEL,String TOFEATURECODE, Connection odsConnection) throws SQLException {
		/**
		 * 
		 * select XMLMESSAGE from cache.XMLIDLCACHE 
 		 *	where XMLCACHEVALIDTO > current timestamp 
 		 *	and  XMLENTITYTYPE = 'PRODSTRUCT'
 		 *	and xmlexists('declare default element namespace "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/TMF_UPDATE"; $i/TMF_UPDATE[MACHTYPE/text() = "7031" and MODEL/text() ="D24" and FEATURECODE/text() ="0002"]' passing cache.XMLIDLCACHE.XMLMESSAGE as "i") 
 		 *	FETCH FIRST 1 ROWS ONLY with ur;
		 */
    	String cacheSql = "select XMLMESSAGE from cache.XMLIDLCACHE "
    			+ " where XMLCACHEVALIDTO > current timestamp "
    			+ " and  XMLENTITYTYPE = 'PRODSTRUCT'"
    			+ " and xmlexists('declare default element namespace \"http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/TMF_UPDATE\"; "
    			+ " $i/TMF_UPDATE[MACHTYPE/text() = \""+TOMACHTYPE+"\" and MODEL/text() =\""+TOMODEL+"\" and FEATURECODE/text() =\""+TOFEATURECODE+"\"]' passing cache.XMLIDLCACHE.XMLMESSAGE as \"i\")" 
                + " FETCH FIRST 1 ROWS ONLY with ur";		
		PreparedStatement statement = odsConnection.prepareStatement(cacheSql);
		addDebug("Search for TMF cacheSql=" + cacheSql);
		ResultSet resultSet = statement.executeQuery();
		String xml = "";
		if (resultSet.next()) {
			xml = resultSet.getString("XMLMESSAGE");
			addDebug("getTMFFromXML");			
		}
		return xml;
	}
    
    
    public boolean exist(String sql,String type,String pdhdomain) {
    	boolean flag = false;
    	try {
			Connection connection = m_db.getPDHConnection();
			Object[] params = new String[2]; 
			params[0] =type;
			params[1] =pdhdomain;			
			String realSql = CommonUtils.getPreparedSQL(sql, params);
			this.addDebug("querySql=" + realSql);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, type);
			statement.setString(2, pdhdomain);
			
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				int count = resultSet.getInt(1);
				flag = count>0 ? true: false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (MiddlewareException e) {
			e.printStackTrace();
		}    	
    	return flag;
    	
    }
	
}
