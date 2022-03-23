package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.CharacterIterator;
import java.text.MessageFormat;
import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import COM.ibm.eannounce.abr.sg.rfc.ChwCharMaintain;
import COM.ibm.eannounce.abr.sg.rfc.ChwClassMaintain;
import COM.ibm.eannounce.abr.sg.rfc.ChwClsfCharCreate;
import COM.ibm.eannounce.abr.sg.rfc.CommonUtils;
import COM.ibm.eannounce.abr.sg.rfc.FEATURE;
import COM.ibm.eannounce.abr.sg.rfc.LANGUAGEELEMENT_FEATURE;
import COM.ibm.eannounce.abr.sg.rfc.MODEL;
import COM.ibm.eannounce.abr.sg.rfc.RdhBase;
import COM.ibm.eannounce.abr.sg.rfc.RdhClassificationMaint;
import COM.ibm.eannounce.abr.sg.rfc.TMF_UPDATE;
import COM.ibm.eannounce.abr.sg.rfc.XMLParse;
import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;

import com.ibm.transform.oim.eacm.util.PokUtils;

public class TMFIERPABRSTATUS extends PokBaseABR {
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);
	private int abr_debuglvl = D.EBUG_ERR;
	private String navName = "";
	private Hashtable metaTbl = new Hashtable();
	private String PRODSTRUCTSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'PRODSTRUCT' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
	
	private String MODELSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'MODEL' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
	
	private String FEATURESQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'FEATURE' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
	
	private String COVEQUALSQL="SELECT count(*) FROM OPICM.flag F\n"
			+ "INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.ATTRIBUTEVALUE ='?' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP "
			+ "INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP "
			+ "WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS')  AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' WITH UR"
			;
	private String COVNOTEQUALSQL="SELECT count(*) FROM OPICM.flag F\n"
			+ "INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.ATTRIBUTEVALUE ='?' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP "
			+ "INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP "
			+ "WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS')  AND T1.ATTRIBUTEVALUE !=T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' WITH UR"
			;
	private String FCTEQUALSQL="SELECT count(*) FROM OPICM.flag F\n"
			+ "INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.ATTRIBUTEVALUE ='?' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP "
			+ "INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP "
			+ "WHERE f.ENTITYTYPE ='FCTRANSACTION' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'FCTRANSACTIONIERPABRSTATUS')  AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' WITH UR"
			;
	String xml = null;
	Map<String,String> FctypeEMap = new HashMap<String,String>();
	{
		FctypeEMap.put("RPQ-PLISTED", "");
		FctypeEMap.put("RPQ-ILISTED", "");
	}
	

	public String getDescription() {
		return "MODELIERPABRSTATUS";
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
			setDGTitle("TMFIERPABRSTATUS report");
			setDGString(getABRReturnCode());
			setDGRptName("TMFIERPABRSTATUS"); // Set the report name
			setDGRptClass("TMFIERPABRSTATUS"); // Set the report class
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

			String prodstuctxml = getXMLByID(PRODSTRUCTSQL, rootEntity.getEntityID());
			
			if (prodstuctxml != null) {
				//step1 Execute the steps described in the section Create SAP characteristics and classes for MachineTypeNEW material 
				//to create the SAP characteristics and classes of the feature code for MachineTypeNEW material. 
				TMF_UPDATE tmf = XMLParse.getObjectFromXml(prodstuctxml,TMF_UPDATE.class);
				//TODO get chwFeature and chwModel from tmf
				if(tmf==null) return;
				String featurexml = getXMLByID(FEATURESQL, Integer.parseInt(tmf.getFEATUREENTITYID()));
				String modelxml = getXMLByID(MODELSQL, Integer.parseInt(tmf.getMODELENTITYID()));				
				//java.password = cat9tail
				
				FEATURE chwFeature = XMLParse.getObjectFromXml(featurexml,FEATURE.class);
				MODEL chwModel = XMLParse.getObjectFromXml(modelxml,MODEL.class);
				MachineTypeNEW(tmf,chwFeature,chwModel);
				//step2 TODO
				/**
				 * If there is a MODELCONVERT which meets all of conditions below,
				 *  tomachtype = chwTMF/MACHTYPE
				 *  frommachtype !=tomachtype
				 *  past ADSABRSTATUS or MODELCONVERTIERPABRSTATUS
				 */
				//execute the steps described in the section Create SAP characteristics and classes for MachineTypeMTC material 
				// to create the SAP characteristics and classes of the feature code for MachineTypeMTC material.
				MachineTypeMTC(tmf,chwFeature,chwModel);
				//step3 TODO
				/**
				 *  If SUBCATEGORY of parent MODEL is not in (''Maintenance'',''MaintFeature"),
			        If there is a MODELCONVERT which meets all of conditions below, 
			            tomachtype = chwTMF/MACHTYPE
			            frommachtype=tomachtype
			            past ADSABRSTATUS or MODELCONVERTIERPABRSTATUS
			
			        or if there is a FCTRANSACTION which meets all of conditions below,
			            tomachtype = chwTMF/MACHTYPE
			            frommachtype=tomachtyp
			            past ADSABRSTATUS or MODELCONVERTIERPABRSTATUS
			        or if ORDERCODE of parent MODEL is  in ("M",  "B")
				 */
				MachineTypeUPG(tmf,chwFeature,chwModel);
				
				//step4 Call CHWYMDMFCMaint to populate iERP user-defined tables (UDTs) with the availability status 
				//of PRODSTRUCT by setting the input parameter for tbl_tmf_c structure
				//CHWYMDMFCMaint CHWYMDMFCMaint = new CHWYMDMFCMaint();
				

					
				
			}	
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
			rptSb.append(msgf.format(args) + NEWLINE);
			msgf = new MessageFormat(Error_STACKTRACE);
			args[0] = exBuf.getBuffer().toString();
			rptSb.append(msgf.format(args) + NEWLINE);
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

			println(EACustom.getTOUDiv());
			buildReportFooter(); // Print </html>
		}
	}

	protected String getXMLByID(String SQL ,int entityId)
			throws MiddlewareException, SQLException {
		String sReturn = "";
		Connection connection = m_db.getODSConnection();
		PreparedStatement statement = connection.prepareStatement(SQL); 
		statement.setInt(1, entityId);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			sReturn = resultSet.getString("XMLMESSAGE");
		}
		return sReturn;
	}

	


	

	private void MachineTypeUPG(TMF_UPDATE chwTMF, FEATURE chwFeature,	MODEL chwModel) throws Exception{
		if(chwFeature==null) return;
		if(chwModel==null) return;
		String feature_code = chwTMF.getFEATURECODE();
		String FCTYPE = chwTMF.getFCTYPE();
		String obj_id = chwTMF.getMACHTYPE() + "UPG";
		String target_indc = "T";
		String mach_type = chwTMF.getMACHTYPE();
		String feature_code_desc = getFeatureCodeDesc(chwFeature);
		ChwClsfCharCreate chwClsfCharCreate = new ChwClsfCharCreate();
		//step1 If chwTMF/FEATURECODE does not contain any letter and chwTMF/FCTYPE not in ("RPQ-PLISTED","RPQ-ILISTED"), then
		if(CommonUtils.isNoLetter(feature_code) && !FctypeEMap.containsKey(FCTYPE)){
			chwClsfCharCreate = new ChwClsfCharCreate();
			//1.1String obj_id, String target_indc, String mach_type, String feature_code, String feature_code_desc
			try{
				target_indc = "T";
				chwClsfCharCreate.CreateGroupChar(obj_id, target_indc, mach_type, feature_code, feature_code_desc);
				this.addMsg(chwClsfCharCreate.getRptSb());
			} catch(Exception e){
				this.addMsg(chwClsfCharCreate.getRptSb());
				throw e;
			}
			chwClsfCharCreate = new ChwClsfCharCreate();
			//String obj_id, String target_indc, String mach_type, String feature_code, String feature_code_desc
			try{
				target_indc = "D";
				chwClsfCharCreate.CreateGroupChar(obj_id, target_indc, mach_type, feature_code, feature_code_desc);
				this.addMsg(chwClsfCharCreate.getRptSb());
			} catch(Exception e){
				this.addMsg(chwClsfCharCreate.getRptSb());
				throw e;
			}
			//step 1.2 If MODEL_UPDATE/UNITCLASS = "Non SIU- CPU" or chwTMF/SYSTEMMAX > 1 or chwTMF/ORDERCODE = "B", then		
			String UNITCLASS = chwModel.getUNITCLASS();
			String SYSTEMMAX = chwTMF.getSYSTEMMAX();
			String ORDERCODE = chwTMF.getORDERCODE();
			if("Non SIU- CPU".equalsIgnoreCase(UNITCLASS) || SYSTEMMAX.compareTo("1")>0 || "B".equalsIgnoreCase(ORDERCODE)){
				chwClsfCharCreate = new ChwClsfCharCreate();
				//String obj_id, String target_indc, String mach_type, String feature_code
				try{
					target_indc = "T";
					chwClsfCharCreate.CreateQTYChar(obj_id, target_indc, mach_type, feature_code);
					this.addMsg(chwClsfCharCreate.getRptSb());
				} catch(Exception e){
					this.addMsg(chwClsfCharCreate.getRptSb());
					throw e;
				}
				try{
					target_indc = "D";
					chwClsfCharCreate.CreateQTYChar(obj_id, target_indc, mach_type, feature_code);
					this.addMsg(chwClsfCharCreate.getRptSb());
				} catch(Exception e){
					this.addMsg(chwClsfCharCreate.getRptSb());
					throw e;
				}
				
			}
		}
		//step2 If chwTMF/FEATURECODE contain letters and chwTMF/FCTYPE in ("RPQ-PLISTED","RPQ-ILISTED"), then
		if(CommonUtils.hasLetter(feature_code) && FctypeEMap.containsKey(FCTYPE)){
			//2.1.1 Call ChwClsfCharCreate.CreateRPQGroupChar() to create the Target group characteristic and class for the RPQ feature code.
			chwClsfCharCreate = new ChwClsfCharCreate();
			try{
				target_indc = "T";
				chwClsfCharCreate.CreateRPQGroupChar(obj_id, target_indc, mach_type, feature_code, feature_code_desc);
				this.addMsg(chwClsfCharCreate.getRptSb());
			} catch(Exception e){
				this.addMsg(chwClsfCharCreate.getRptSb());
				throw e;
			}
			//2.1.2 D
			chwClsfCharCreate = new ChwClsfCharCreate();
			try{
				target_indc = "D";
				chwClsfCharCreate.CreateRPQGroupChar(obj_id, target_indc, mach_type, feature_code, feature_code_desc);
				this.addMsg(chwClsfCharCreate.getRptSb());
			} catch(Exception e){
				this.addMsg(chwClsfCharCreate.getRptSb());
				throw e;
			}
			//2.2 If MODEL_UPDATE/UNITCLASS = "Non SIU- CPU" or chwTMF/SYSTEMMAX > 1 or chwTMF/ORDERCODE = "B", then
			String UNITCLASS = chwModel.getUNITCLASS();
			String SYSTEMMAX = chwTMF.getSYSTEMMAX();
			String ORDERCODE = chwTMF.getORDERCODE();
			if("Non SIU- CPU".equalsIgnoreCase(UNITCLASS) || SYSTEMMAX.compareTo("1")>0 || "B".equalsIgnoreCase(ORDERCODE)){
				//Call ChwClsfCharCreate.CreateRPQQTYChar() to create the Target QTY characteristic and class for the RPQ feature code.
				chwClsfCharCreate = new ChwClsfCharCreate();
				//String obj_id, String target_indc, String mach_type, String feature_code
				try{
					target_indc = "T";
					chwClsfCharCreate.CreateRPQQTYChar(obj_id, target_indc, mach_type, feature_code);
					this.addMsg(chwClsfCharCreate.getRptSb());
				} catch(Exception e){
					this.addMsg(chwClsfCharCreate.getRptSb());
					throw e;
				}
				try{
					target_indc = "D";
					chwClsfCharCreate.CreateRPQQTYChar(obj_id, target_indc, mach_type, feature_code);
					this.addMsg(chwClsfCharCreate.getRptSb());
				} catch(Exception e){
					this.addMsg(chwClsfCharCreate.getRptSb());
					throw e;
				}
				
			}
		}
		//step3 If chwTMF/FEATURECODE contain letters and chwTMF/FCTYPE not in ("RPQ-PLISTED","RPQ-ILISTED") 
		//and the first 3 characters of chwTMF/FEATURECODE <> "NEW",
		if(CommonUtils.hasLetter(feature_code) 
				   && !FctypeEMap.containsKey(FCTYPE) 
				   && !CommonUtils.getFirstSubString(feature_code, 3).equalsIgnoreCase("NEW")){
			//3.1 call ChwClsfCharCreate.CreateAlphaGroupChar() to create the Target characteristic and class for the alpah feature code.
			chwClsfCharCreate = new ChwClsfCharCreate();
			try{
				target_indc = "T";
				chwClsfCharCreate.CreateAlphaGroupChar(obj_id, target_indc, mach_type, feature_code,feature_code_desc);
				this.addMsg(chwClsfCharCreate.getRptSb());
			} catch(Exception e){
				this.addMsg(chwClsfCharCreate.getRptSb());
				throw e;
			}
			//D
			chwClsfCharCreate = new ChwClsfCharCreate();
			try{
				target_indc = "D";
				chwClsfCharCreate.CreateAlphaGroupChar(obj_id, target_indc, mach_type, feature_code,feature_code_desc);
				this.addMsg(chwClsfCharCreate.getRptSb());
			} catch(Exception e){
				this.addMsg(chwClsfCharCreate.getRptSb());
				throw e;
			}
			//3.2 If chwTMF/SYSTEMMAX > 1 or chwTMF/ORDERCODE = "B", then 
			String SYSTEMMAX = chwTMF.getSYSTEMMAX();
			String ORDERCODE = chwTMF.getORDERCODE();
			if(SYSTEMMAX.compareTo("1")>0 || "B".equalsIgnoreCase(ORDERCODE)){
				//3.2.1 call ChwClsfCharCreate.CreateAlphaQTYChar() to create the Target QTY characteristic and class for the alpha feature code
				chwClsfCharCreate = new ChwClsfCharCreate();
				try{
					target_indc = "T";
					chwClsfCharCreate.CreateAlphaQTYChar(obj_id, target_indc, mach_type, feature_code);
					this.addMsg(chwClsfCharCreate.getRptSb());
				} catch(Exception e){
					this.addMsg(chwClsfCharCreate.getRptSb());
					throw e;
				}
				chwClsfCharCreate = new ChwClsfCharCreate();
				try{
					target_indc = "D";
					chwClsfCharCreate.CreateAlphaQTYChar(obj_id, target_indc, mach_type, feature_code);
					this.addMsg(chwClsfCharCreate.getRptSb());
				} catch(Exception e){
					this.addMsg(chwClsfCharCreate.getRptSb());
					throw e;
				}
			}
			
		}
		
		//step4 Call the ChwCharMaintain constructor to create the MK_D_machineType_REM_FC characteristic.  
		String empty ="";
		ChwCharMaintain chwCharMaintain = new ChwCharMaintain(obj_id  //String obj_id Set to concatenation of chwProduct.machineType + "UPG"
				,"MK_D_"+mach_type+"_REM_FC" //String charact  Set to  "MK_T_<machine_type>_MOD"
				, "CHAR" 			//String datatype
				, 12 				//int charnumber
				, empty				//String decplaces
				, empty				//String casesens
				, empty				//String neg_vals
				, empty				//String group
				, "-"				//String valassignm  Set to "-".
				, empty				//String no_entry
				, empty				//String no_display
				, "X" 				//String addit_vals Set to "X".
				, "Removed Features " + mach_type //String chdescr  Set to "<machine_type> Model Characteristic" where <machine_type> is chwProduct.machineType
				);	
		this.addDebug("Calling " + chwCharMaintain.getRFCName());
		chwCharMaintain.execute();
		this.addRfcResult(chwCharMaintain);
		//step5 Call the ChwClassMaintain constructor to create the MK_D_machineType_REM_FC class. 
		obj_id = chwTMF.getMACHTYPE() + "MTC";  //TODO why only MTC
		String charactd = "MK_D_" + mach_type + "_REM_FC";
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				obj_id //String obj_id
				, charactd //String class_
				, charactd //String class_desc
			);
		this.addDebug("Calling " + chwClassMaintain.getRFCName());
		//step6. Call the ChwClassMaintain.addCharacteristic() method to add the MK_D_machineType_REM_FC characteristic to the MK_D_machineType_REM_FC class.
		chwClassMaintain.addCharacteristic(charactd);		
		chwClassMaintain.execute();
		this.addRfcResult(chwClassMaintain);
		//step 7 Call the TssClassificationMaint constructor to associate the MK_machineType_MTC class to the product's material master record.
		obj_id = chwTMF.getMACHTYPE() + "UPG";  
		callRdhClassificationMaint(obj_id, "MK_D_" + mach_type + "_REM_FC");
		//step8 Call the TssClassificationMaint constructor to associate the MK_REFERENCE class to the product's material master record. 
		callRdhClassificationMaint(obj_id, "MK_REFERENCE");
		//step9 Call the TssClassificationMaint to associate the MK_T_VAO_NEW class to the product's material master record. 
		callRdhClassificationMaint(obj_id, "MK_T_VAO_NEW");
		//step 10 Call the TssClassificationMaint to associate the MK_D_VAO_NEW class to the product's material master record.
		callRdhClassificationMaint(obj_id, "MK_D_VAO_NEW");
		//step 11 Call the TssClassificationMaint to associate the MK_FC_EXCH class to the product's material master record.
		callRdhClassificationMaint(obj_id, "MK_FC_EXCH");
		//step 12 Call the TssClassificationMaint to associate the MK_FC_CONV class to the product's material master record.
		callRdhClassificationMaint(obj_id, "MK_FC_CONV");
		
		
		
	}

	private void MachineTypeMTC(TMF_UPDATE chwTMF, FEATURE chwFeature, MODEL chwModel) throws Exception {
		
		if(chwFeature==null) return;
		if(chwModel==null) return;
		
		String feature_code = chwTMF.getFEATURECODE();
		String FCTYPE = chwTMF.getFCTYPE();
		String obj_id = chwTMF.getMACHTYPE() + "MTC";
		String target_indc = "T";
		String mach_type = chwTMF.getMACHTYPE();
		String feature_code_desc = getFeatureCodeDesc(chwFeature);
		ChwClsfCharCreate chwClsfCharCreate = new ChwClsfCharCreate();
		//step1 .If chwTMF/FEATURECODE does not contain any letter and chwTMF/FCTYPE not in ("RPQ-PLISTED","RPQ-ILISTED"), then
		if(CommonUtils.isNoLetter(feature_code) && !FctypeEMap.containsKey(FCTYPE)){
			//1.1 Call ChwClsfCharCreate.CreateGroupChar() method to create the Target group characteristic and class
			chwClsfCharCreate = new ChwClsfCharCreate();
			//String obj_id, String target_indc, String mach_type, String feature_code, String feature_code_desc
			try{
				target_indc = "T";
				chwClsfCharCreate.CreateGroupChar(obj_id, target_indc, mach_type, feature_code, feature_code_desc);
				this.addMsg(chwClsfCharCreate.getRptSb());
			} catch(Exception e){
				this.addMsg(chwClsfCharCreate.getRptSb());
				throw e;
			}
			//1.2 Call ChwClsfCharCreate.CreateGroupChar() method to create the Delta group characteristic and class
			chwClsfCharCreate = new ChwClsfCharCreate();
			try{
				target_indc ="D";
				chwClsfCharCreate.CreateGroupChar(obj_id, target_indc, mach_type, feature_code, feature_code_desc);
				this.addMsg(chwClsfCharCreate.getRptSb());
			} catch(Exception e){
				this.addMsg(chwClsfCharCreate.getRptSb());
				throw e;
			}
			//1.3 If MODEL_UPDATE/UNITCLASS = "Non SIU- CPU" or chwTMF/SYSTEMMAX > 1 or chwTMF/ORDERCODE = "B", then
			String UNITCLASS = chwModel.getUNITCLASS();
			String SYSTEMMAX = chwTMF.getSYSTEMMAX();
			String ORDERCODE = chwTMF.getORDERCODE();
			if("Non SIU- CPU".equalsIgnoreCase(UNITCLASS) || SYSTEMMAX.compareTo("1")>0 || "B".equalsIgnoreCase(ORDERCODE)){
				chwClsfCharCreate = new ChwClsfCharCreate();
				//String obj_id, String target_indc, String mach_type, String feature_code
				try{
					target_indc = "T";
					chwClsfCharCreate.CreateQTYChar(obj_id, target_indc, mach_type, feature_code);
					this.addMsg(chwClsfCharCreate.getRptSb());
				} catch(Exception e){
					this.addMsg(chwClsfCharCreate.getRptSb());
					throw e;
				}
				
				chwClsfCharCreate = new ChwClsfCharCreate();
				//String obj_id, String target_indc, String mach_type, String feature_code
				try{
					target_indc = "D";
					chwClsfCharCreate.CreateQTYChar(obj_id, target_indc, mach_type, feature_code);
					this.addMsg(chwClsfCharCreate.getRptSb());
				} catch(Exception e){
					this.addMsg(chwClsfCharCreate.getRptSb());
					throw e;
				}
			}
		}
		//step2 : If chwTMF/FEATURECODE contain letters and chwTMF/FCTYPE in ("RPQ-PLISTED","RPQ-ILISTED"), then
		if(CommonUtils.hasLetter(feature_code) && FctypeEMap.containsKey(FCTYPE)){
			//2.11 Call ChwClsfCharCreate.CreateRPQGroupChar() to create the Target group characteristic and class for the RPQ feature code
			chwClsfCharCreate = new ChwClsfCharCreate();
			try{
				target_indc = "T";
				chwClsfCharCreate.CreateRPQGroupChar(obj_id, target_indc, mach_type, feature_code, feature_code_desc);
				this.addMsg(chwClsfCharCreate.getRptSb());
			} catch(Exception e){
				this.addMsg(chwClsfCharCreate.getRptSb());
				throw e;
			}
			//2.12 D
			chwClsfCharCreate = new ChwClsfCharCreate();
			try{
				target_indc = "D";
				chwClsfCharCreate.CreateRPQGroupChar(obj_id, target_indc, mach_type, feature_code, feature_code_desc);
				this.addMsg(chwClsfCharCreate.getRptSb());
			} catch(Exception e){
				this.addMsg(chwClsfCharCreate.getRptSb());
				throw e;
			}
			//2.2 If MODEL_UPDATE/UNITCLASS = "Non SIU- CPU" or chwTMF/SYSTEMMAX > 1 or chwTMF/ORDERCODE = "B", then 
			String UNITCLASS = chwModel.getUNITCLASS();
			String SYSTEMMAX = chwTMF.getSYSTEMMAX();
			String ORDERCODE = chwTMF.getORDERCODE();
			if("Non SIU- CPU".equalsIgnoreCase(UNITCLASS) || SYSTEMMAX.compareTo("1")>0 || "B".equalsIgnoreCase(ORDERCODE)){
				chwClsfCharCreate = new ChwClsfCharCreate();
				//String obj_id, String target_indc, String mach_type, String feature_code
				try{
					target_indc = "T";
					chwClsfCharCreate.CreateRPQQTYChar(obj_id, target_indc, mach_type, feature_code);
					this.addMsg(chwClsfCharCreate.getRptSb());
				} catch(Exception e){
					this.addMsg(chwClsfCharCreate.getRptSb());
					throw e;
				}
				try{
					target_indc = "D";
					chwClsfCharCreate.CreateRPQQTYChar(obj_id, target_indc, mach_type, feature_code);
					this.addMsg(chwClsfCharCreate.getRptSb());
				} catch(Exception e){
					this.addMsg(chwClsfCharCreate.getRptSb());
					throw e;
				}
				
			}
		}
		//3. If chwTMF/FEATURECODE contain letters and chwTMF/FCTYPE not in ("RPQ-PLISTED","RPQ-ILISTED") 
		//and the first 3 characters of chwTMF/FEATURECODE <> "NEW", 
		if(CommonUtils.hasLetter(feature_code) 
				   && !FctypeEMap.containsKey(FCTYPE) 
				   && !CommonUtils.getFirstSubString(feature_code, 3).equalsIgnoreCase("NEW")){
			//3.1 call ChwClsfCharCreate.CreateAlphaGroupChar() to create the Target characteristic and class for the alpah feature code.
			chwClsfCharCreate = new ChwClsfCharCreate();
			try{
				target_indc = "T";
				chwClsfCharCreate.CreateAlphaGroupChar(obj_id, target_indc, mach_type, feature_code,feature_code_desc);
				this.addMsg(chwClsfCharCreate.getRptSb());
			} catch(Exception e){
				this.addMsg(chwClsfCharCreate.getRptSb());
				throw e;
			}
			//D
			chwClsfCharCreate = new ChwClsfCharCreate();
			try{
				target_indc = "D";
				chwClsfCharCreate.CreateAlphaGroupChar(obj_id, target_indc, mach_type, feature_code,feature_code_desc);
				this.addMsg(chwClsfCharCreate.getRptSb());
			} catch(Exception e){
				this.addMsg(chwClsfCharCreate.getRptSb());
				throw e;
			}
			//3.2 If chwTMF/SYSTEMMAX > 1 or chwTMF/ORDERCODE = "B", then 
			String SYSTEMMAX = chwTMF.getSYSTEMMAX();
			String ORDERCODE = chwTMF.getORDERCODE();
			if(SYSTEMMAX.compareTo("1")>0 || "B".equalsIgnoreCase(ORDERCODE)){
				//3.21 call ChwClsfCharCreate.CreateAlphaQTYChar() to create the Target QTY characteristic and class for the alpha feature code.
				chwClsfCharCreate = new ChwClsfCharCreate();
				try{
					target_indc = "T";
					chwClsfCharCreate.CreateAlphaQTYChar(obj_id, target_indc, mach_type, feature_code);
					this.addMsg(chwClsfCharCreate.getRptSb());
				} catch(Exception e){
					this.addMsg(chwClsfCharCreate.getRptSb());
					throw e;
				}
				chwClsfCharCreate = new ChwClsfCharCreate();
				try{
					target_indc = "D";
					chwClsfCharCreate.CreateAlphaQTYChar(obj_id, target_indc, mach_type, feature_code);
					this.addMsg(chwClsfCharCreate.getRptSb());
				} catch(Exception e){
					this.addMsg(chwClsfCharCreate.getRptSb());
					throw e;
				}
			}
			
		}
		//step 4 Call the ChwCharMaintain constructor to create the MK_machineType_MTC_REM_FC characteristic.  Set the constructor's parameters as follows.
		String empty ="";
		ChwCharMaintain chwCharMaintain = new ChwCharMaintain(obj_id  //String obj_id Set to concatenation of chwProduct.machineType + "MTC"
				,"MK_T_"+mach_type+"_MTC_REM_FC" //String charact  Set to  "MK_T_<machine_type>_MOD"
				, "CHAR" 			//String datatype
				, 12 				//int charnumber
				, empty				//String decplaces
				, empty				//String casesens
				, empty				//String neg_vals
				, empty				//String group
				, "-"				//String valassignm  Set to "-".
				, empty				//String no_entry
				, empty				//String no_display
				, "X" 				//String addit_vals Set to "X".
				, "MTC Features Removed " + mach_type //String chdescr  Set to "<machine_type> Model Characteristic" where <machine_type> is chwProduct.machineType
				);	
		this.addDebug("Calling " + chwCharMaintain.getRFCName());
		chwCharMaintain.execute();
		this.addRfcResult(chwCharMaintain);
		//step5 Call the ChwClassMaintain constructor to create the MK_machineType_MTC class. Set the constructor's input parameters as follows.
		String charactd = "MK_" + mach_type + "_MTC";
		ChwClassMaintain chwClassMaintain  = new ChwClassMaintain(
				obj_id //String obj_id
				, charactd //String class_
				, charactd //String class_desc
			);
		this.addDebug("Calling " + chwClassMaintain.getRFCName());
		//step6. Call the ChwClassMaintain.addCharacteristic() method to add the MK_machineType_MTC_REM_FC characteristic to the MK_machineType_MTC class.
		chwClassMaintain.addCharacteristic("MK_"+ mach_type+ "_MTC_REM_FC");		
		chwClassMaintain.execute();
		this.addRfcResult(chwClassMaintain);
		
		//step7 Call the TssClassificationMaint constructor to associate the MK_machineType_MTC class to the product's material master record.
		//RdhClassificationMaint rdhClassificationMaint;
		callRdhClassificationMaint(obj_id, "MK_" + mach_type + "_MTC");
		//step8 Call the TssClassificationMaint constructor to associate the MK_REFERENCE class to the product's material master record.
		callRdhClassificationMaint(obj_id, "MK_REFERENCE");
		//step 9 Call the TssClassificationMaint to associate the MK_T_VAO_NEW class to the product's material master record
		callRdhClassificationMaint(obj_id, "MK_T_VAO_NEW");
		//step 10 Call the TssClassificationMaint to associate the MK_D_VAO_NEW class to the product's material master record.
		callRdhClassificationMaint(obj_id, "MK_D_VAO_NEW");
		//step11 Call the TssClassificationMaint to associate the MK_FC_EXCH class to the product's material master record.
		callRdhClassificationMaint(obj_id, "MK_FC_EXCH");
		//step 12 Call the TssClassificationMaint to associate the MK_FC_CONV class to the product's material master record
		callRdhClassificationMaint(obj_id, "MK_FC_CONV");
		
		
	}

	protected void callRdhClassificationMaint(String obj_id, String class_name)
			throws Exception {
		RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(
				obj_id 		//String obj_id
				, class_name  	//String class_name
				, "300" 	//String class_type
				, "H"		//String pims_identity
				);		
		this.addDebug("Calling " + rdhClassificationMaint.getRFCName());
		rdhClassificationMaint.execute();
		this.addRfcResult(rdhClassificationMaint);
	}

	private void MachineTypeNEW(TMF_UPDATE chwTMF, FEATURE chwFeature, MODEL chwModel) throws Exception {
		
		//step1 If chwTMF/FEATURECODE does not contain any letter and chwTMF/FCTYPE not in ("RPQ-PLISTED","RPQ-ILISTED")
		
		if(chwFeature==null) return;
		if(chwModel==null) return;
		
		String FCTYPE = chwTMF.getFCTYPE();
		String obj_id = chwTMF.getMACHTYPE() + "NEW";
		String target_indc = "T";
		String mach_type = chwTMF.getMACHTYPE();
		String feature_code = chwTMF.getFEATURECODE();
		String feature_code_desc = getFeatureCodeDesc(chwFeature);
		ChwClsfCharCreate chwClsfCharCreate = new ChwClsfCharCreate();
		
		
		if(CommonUtils.isNoLetter(feature_code) && !FctypeEMap.containsKey(FCTYPE)){
			//1.1 Call ChwClsfCharCreate.CreateGroupChar() method to create the group characteristic and class.
			chwClsfCharCreate = new ChwClsfCharCreate();
			//String obj_id, String target_indc, String mach_type, String feature_code, String feature_code_desc
			try{
				chwClsfCharCreate.CreateGroupChar(obj_id, target_indc, mach_type, feature_code, feature_code_desc);
				this.addMsg(chwClsfCharCreate.getRptSb());
			} catch(Exception e){
				this.addMsg(chwClsfCharCreate.getRptSb());
				throw e;
			}
			//1.2. If MODEL_UPDATE/UNITCLASS = "Non SIU- CPU" or chwTMF/SYSTEMMAX > 1 or chwTMF/ORDERCODE = "B", 
			//then call ChwClsfCharCreate.CreateQTYChar() method to create the QTY characteristic and class.
			String UNITCLASS = chwModel.getUNITCLASS();
			String SYSTEMMAX = chwTMF.getSYSTEMMAX();
			String ORDERCODE = chwTMF.getORDERCODE();
			if("Non SIU- CPU".equalsIgnoreCase(UNITCLASS) || SYSTEMMAX.compareTo("1")>0 || "B".equalsIgnoreCase(ORDERCODE)){
				
				chwClsfCharCreate = new ChwClsfCharCreate();
				//String obj_id, String target_indc, String mach_type, String feature_code
				try{
					chwClsfCharCreate.CreateQTYChar(obj_id, target_indc, mach_type, feature_code);
					this.addMsg(chwClsfCharCreate.getRptSb());
				} catch(Exception e){
					this.addMsg(chwClsfCharCreate.getRptSb());
					throw e;
				}
				
				
			}			
		}
		//step2 If chwTMF/FEATURECODE contain letters and chwTMF/FCTYPE in ("RPQ-PLISTED","RPQ-ILISTED"), then
		if(CommonUtils.hasLetter(feature_code) && FctypeEMap.containsKey(FCTYPE)){
			//2.1 Call ChwClsfCharCreate.CreateRPQGroupChar() to create the group characteristic and class for the RPQ feature code.
			chwClsfCharCreate = new ChwClsfCharCreate();
			//feature_code_desc = getFeatureCodeDesc(feature);
			try{
				chwClsfCharCreate.CreateRPQGroupChar(obj_id, target_indc, mach_type, feature_code, feature_code_desc);
				this.addMsg(chwClsfCharCreate.getRptSb());
			} catch(Exception e){
				this.addMsg(chwClsfCharCreate.getRptSb());
				throw e;
			}
			//2.2 If MODEL_UPDATE/UNITCLASS = "Non SIU- CPU" or chwTMF/SYSTEMMAX > 1 or chwTMF/ORDERCODE = "B", 
			//then call ChwClsfCharCreate.CreateRPQQTYChar() to create the QTY characteristic and class for the RPQ feature code.
			String UNITCLASS = chwModel.getUNITCLASS();
			String SYSTEMMAX = chwTMF.getSYSTEMMAX();
			String ORDERCODE = chwTMF.getORDERCODE();
			if("Non SIU- CPU".equalsIgnoreCase(UNITCLASS) || SYSTEMMAX.compareTo("1")>0 || "B".equalsIgnoreCase(ORDERCODE)){
				
				chwClsfCharCreate = new ChwClsfCharCreate();
				//String obj_id, String target_indc, String mach_type, String feature_code
				try{
					chwClsfCharCreate.CreateRPQQTYChar(obj_id, target_indc, mach_type, feature_code);
					this.addMsg(chwClsfCharCreate.getRptSb());
				} catch(Exception e){
					this.addMsg(chwClsfCharCreate.getRptSb());
					throw e;
				}
			}	
			
		}
		//step3. If chwTMF/FEATURECODE contain letters and chwTMF/FCTYPE not in ("RPQ-PLISTED","RPQ-ILISTED") 
		//and the first 3 characters of chwTMF/FEATURECODE <> "NEW",
		if(CommonUtils.hasLetter(feature_code) 
		   && !FctypeEMap.containsKey(FCTYPE) 
		   && !CommonUtils.getFirstSubString(feature_code, 3).equalsIgnoreCase("NEW")){
			//3.1 call ChwClsfCharCreate.CreateAlphaGroupChar() to create the group characteristic and class for the alpah feature code.
			chwClsfCharCreate = new ChwClsfCharCreate();
			//feature_code_desc = getFeatureCodeDesc(feature);
			try{
				chwClsfCharCreate.CreateAlphaGroupChar(obj_id, target_indc, mach_type, feature_code, feature_code_desc);
				this.addMsg(chwClsfCharCreate.getRptSb());
			} catch(Exception e){
				this.addMsg(chwClsfCharCreate.getRptSb());
				throw e;
			}
			//3.2 If chwTMF/SYSTEMMAX > 1 or chwTMF/ORDERCODE = "B", 
			//then call ChwClsfCharCreate.CreateAlphaQTYChar() to create the QTY characteristic and class for the alpha feature code.
			String SYSTEMMAX = chwTMF.getSYSTEMMAX();
			String ORDERCODE = chwTMF.getORDERCODE();
			if(SYSTEMMAX.compareTo("1")>0 || "B".equalsIgnoreCase(ORDERCODE)){
				chwClsfCharCreate = new ChwClsfCharCreate();
				try{
					chwClsfCharCreate.CreateAlphaQTYChar(obj_id, target_indc, mach_type, feature_code);
					this.addMsg(chwClsfCharCreate.getRptSb());
				} catch(Exception e){
					this.addMsg(chwClsfCharCreate.getRptSb());
					throw e;
				}
			}
		}
		//step5 Call the TssClassificationMaint constructor to associate the MK_target_machineType_FC_n000 class 
		// to the product's material master record. Set the constructor's input parameters as follows.
		callRdhClassificationMaint(obj_id, "MK_REFERENCE");
		//step 6. Call the TssClassificationMaint to associate the MK_T_VAO_NEW class to the product's material master record.
		callRdhClassificationMaint(obj_id, "MK_T_VAO_NEW");		
		
	}

	protected void addRfcResult(RdhBase rdhBase) {
		this.addDebug(rdhBase.createLogEntry());
		if (rdhBase.getRfcrc() == 0) {
			this.addOutput(rdhBase.getRFCName() + " called successfully!");
		} else {
			this.addOutput(rdhBase.getRFCName() + " called  faild!");
			this.addOutput("return code is " + rdhBase.getRfcrc());
			this.addOutput(rdhBase.getError_text());
		}
	}

	protected String getFeatureCodeDesc(FEATURE feature) {
		String BHINVNAME ="";
		for (LANGUAGEELEMENT_FEATURE languageElement : feature.getLANGUAGELIST())
		{
			if ("1".equals(languageElement.getNLSID()))
			{
				BHINVNAME= languageElement.getBHINVNAME();
			}
		}
		String feature_code_desc = CommonUtils.getFirstSubString(BHINVNAME, 30);
		return feature_code_desc;
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
}
